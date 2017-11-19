package Utils;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import StoreData.Data;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import static Utils.PartnersHttpClient.setDataXml;


public class PartnersHttpClient extends Activity {
    public Data data;
    public Context context;
    public static JSONObject PETROLCARD_DATA = new JSONObject();
    private static String dataXml;

    public String getDataXml() {
        return dataXml;
    }

    public static void setDataXml(String data_xml) {
        dataXml = data_xml;
    }

    public static JSONObject getPetrolcardData() {
        return PETROLCARD_DATA;
    }

    public static void setPetrolcardData(JSONObject petrolcardData) {
        PETROLCARD_DATA = petrolcardData;
    }

    public Data getData() {
        return data;
    }

    public Context getContext() {
        return context;
    }


    private static OkHttpClient createAuthenticatedClient(final String password) {
        // build client with authentication information.
        return new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS).
                        authenticator(new Authenticator() {
                            public Request authenticate(@NonNull Route route, @NonNull Response response) throws IOException {
                                String credential = Credentials.basic(Utils.getPetrolcardUsername(), password);
                                if (responseCount(response) >= 3) {
                                    return null;
                                }
                                return response.request().newBuilder().header("Authorization", credential).build();
                            }
                        }).build();
    }

    public static void getCookieAndRealmCode(OkHttpClient httpClient, String anyURL) throws Exception {
        Log.i("getCookieAndRealmCode --> url", anyURL);
        Request request = new Request.Builder().url(anyURL).build();
        Response response = httpClient.newCall(request).execute();
        httpClient.newCall(request).execute();

        //Get cookie
        String phpSessionID = response.headers("Set-Cookie").toString();
        String[] phpSessIDParts = phpSessionID.split(";");
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < phpSessIDParts[0].length(); i++) {
            sb.append(phpSessIDParts[0].charAt(i));
        }
        phpSessionID = sb.toString();

        //Get realmCode
        String realmCode = response.header("WWW-Authenticate");
        String[] realmCodeParts = realmCode.split("-");
        realmCode = realmCodeParts[1];
        Log.i("getCookie --> realmCode", realmCode);
        Log.i("getCookie --> cookie", phpSessionID);
        PETROLCARD_DATA.put("realmCode", realmCode);
        PETROLCARD_DATA.put("cookie", phpSessionID);
    }

    public static void fakeHttpRequest(OkHttpClient httpClient, String anyURL, String cookie) throws Exception {
        Request request = new Request.Builder().url(anyURL).header("Cookie", cookie).build();
        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            Log.i("fakeHttpRequest", "Succesfull request");
        }
    }

    public static String connectPetrolcardData(OkHttpClient httpClient, String anyURL, String phpSessionId) throws Exception {
        try {
            Request request = new Request.Builder()
                    .url(anyURL)
                    .header("Cookie", phpSessionId)
                    .addHeader("Origin", Utils.getPetrolcardBaseUrl())
                    .addHeader("Referer", Utils.getPetrolcardData())
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                    .build();
            Response response = httpClient.newCall(request).execute();
            return response.body().string();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String exportXMLfromPetrolcard() {
        try {
            String phpSessionID = getPetrolcardData().getString("cookie");
            String xml = PartnersHttpClient.connectPetrolcardData(new OkHttpClient(), Utils.getPetrolcardData(), phpSessionID);
            Log.i("exportXMLfromPetrolcard xml", xml);
            //FileOutputStream fos = new FileOutputStream("alma.txt",false);
            return xml;
            /*
            String file_name = "petrolcard_data.xml";
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(file_name, Context.MODE_PRIVATE));
                outputStreamWriter.write(xml);
                outputStreamWriter.close();

            } catch (Exception e) {
                e.printStackTrace();
            }*/
        } catch (Exception e) {
            Log.i("exportXMLfromPetrolcard", e.toString());
        }
        return null;
    }

    public static JSONObject authenticate(OkHttpClient httpClient, String anyURL, String serviceCode) throws Exception {
        try {
            Log.i("authenticate --> url", anyURL);
            getCookieAndRealmCode(httpClient, anyURL);
            String phpSessionID = PETROLCARD_DATA.getString("cookie");
            String realmCode = PETROLCARD_DATA.getString("realmCode");

            fakeHttpRequest(httpClient, anyURL, phpSessionID);

            Log.i("authenticate --> realmCode", realmCode);
            Log.i("authenticate --> cookie", phpSessionID);
            String realmResponseCode = new CodeCalculator(realmCode, serviceCode).Calc();
            httpClient = createAuthenticatedClient(realmResponseCode);
            Request request = new Request.Builder().url(anyURL).header("Cookie", phpSessionID).build();
            Response response = httpClient.newCall(request).execute();
            Log.i("authenticate", response.message());
            if (!response.isSuccessful())
                throw new Exception();

            return PETROLCARD_DATA;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }


    public PartnersHttpClient(Context context) throws Exception {
        this.context = context;
        this.data = new Data(this.context);
        JSONObject jsonObject = getPetrolcardData();
        try {
            jsonObject.put(Utils.getServiceCodeKey(), data.getData(Utils.getServiceCodeKey()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setPetrolcardData(jsonObject);
        Log.i("PartnersHttpClient", "before AsyncRequest");
        AsyncRequest asyncRequest = new AsyncRequest(context);
        asyncRequest.execute();
        Log.i("PartnersHttpClient", "After AsyncRequest");
    }
}
    class AsyncRequest extends AsyncTask<Data, Void, PartnersHttpClient> {

        private String xml = "";

        private final WeakReference<Context> contextWeakReference;

        AsyncRequest(Context ctx) {
            contextWeakReference = new WeakReference<>(ctx);
        }


        @Override
        public void onPostExecute(PartnersHttpClient v) {

            Log.i("onPostExecute", "before set xml");
            super.onPostExecute(v);
            Log.i("xml","vajh üres? -->"+xml);
            if(xml != null){
                setDataXml(xml);
            }
            Log.i("onPostExecute", "After set xml");

            //v.setDataXml(xml);
        }

        @Override
        protected PartnersHttpClient doInBackground(Data... context) {
            try {
                Context ctx = contextWeakReference.get();
                Log.i("ctx.getPackageName()",ctx.getPackageName());
                String url = Utils.getPetrolcardAuth();

                OkHttpClient.Builder client = new OkHttpClient()
                        .newBuilder()
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS);
                String service_code = PartnersHttpClient.getPetrolcardData().getString(Utils.getServiceCodeKey());
                PartnersHttpClient.authenticate(client.build(), url, service_code);
                Log.i("PartnersHttpClient doInBackground", "before exportXML");
                xml = PartnersHttpClient.exportXMLfromPetrolcard();
                //Log.i("xml: ",xml);
                Log.i("PartnersHttpClient doInBackground", "After exportXML");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }



