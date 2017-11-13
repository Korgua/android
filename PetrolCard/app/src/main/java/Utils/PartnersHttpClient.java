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
import hu.vhcom.www.petrolcard.MainActivity;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;

/**
 * Created by korgua on 2017. 11. 05..
 */

public class PartnersHttpClient extends Activity{
    public Data data;
    public Context context;

    public static JSONObject getPetrolcardData() {return PETROLCARD_DATA;}

    public static void setPetrolcardData(JSONObject petrolcardData) {PETROLCARD_DATA = petrolcardData;}

    public static JSONObject PETROLCARD_DATA = new JSONObject();

    public Data getData() {return data;}

    public Context getContext() {return context;}


    private static OkHttpClient createAuthenticatedClient(final String username, final String password) {
        // build client with authentication information.
        OkHttpClient httpClient = new OkHttpClient
                                    .Builder()
                                    .connectTimeout(120,TimeUnit.SECONDS)
                                    .readTimeout(120,TimeUnit.SECONDS)
                                    .writeTimeout(120,TimeUnit.SECONDS).
                                    authenticator(new Authenticator() {
                                        public Request authenticate(@NonNull Route route, @NonNull Response response) throws IOException {
                                            String credential = Credentials.basic(username, password);
                                            if (responseCount(response) >= 3) {
                                                return null;
                                            }
                                            return response.request().newBuilder().header("Authorization", credential).build();
                                        }
        }).build();
        return httpClient;
    }

    public static void getCookieAndRealmCode(OkHttpClient httpClient, String anyURL) throws Exception {
        Request request = new Request.Builder().url(anyURL).build();
        Response response = httpClient.newCall(request).execute();
        httpClient.newCall(request).execute();

        //Get cookie
        String phpSessionID = response.headers("Set-Cookie").toString();
        String[] phpSessIDParts = phpSessionID.split(";");
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < phpSessIDParts[0].length(); i++){
            sb.append(phpSessIDParts[0].charAt(i));
        }
        phpSessionID = sb.toString();

        //Get realmCode
        String realmCode = response.header("WWW-Authenticate");
        String[] realmCodeParts = realmCode.split("-");
        realmCode = realmCodeParts[1];
        Log.v("getCookie --> realmCode",realmCode);
        Log.v("getCookie --> cookie",phpSessionID);
        PETROLCARD_DATA.put("realmCode",realmCode);
        PETROLCARD_DATA.put("cookie",phpSessionID);
    }

    public static void fakeHttpRequest(OkHttpClient httpClient, String anyURL, String cookie) throws Exception{
        Request request = new Request.Builder().url(anyURL).header("Cookie",cookie).build();
        Response response = httpClient.newCall(request).execute();
        Log.v("getRealmCode --> responseBody",response.body().string());
    }

    public static String connectPetrolcardData(OkHttpClient httpClient, String anyURL, String cookie) throws Exception{
        String phpSessionId = cookie;
        Request request = new Request.Builder()
                                .url(anyURL)
                                .header("Cookie",phpSessionId)
                                .addHeader("Origin","http://petrolcard.hu:7621")
                                .addHeader("Referer","http://petrolcard.hu:7621/new/index.php")
                                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                                .build();
        Response response = httpClient.newCall(request).execute();
        Log.v("connectPetrolcardData --> responseBody",response.body().string());
        Log.v("connectPetrolcardData --> responseHeader",response.headers().toString());
        return response.body().string();
    }

    public static boolean exportXMLfromPetrolcard(Context context){
        Data data = new Data(context);
        String phpSessionID = data.getData("cookie");
        try{
            String xml = PartnersHttpClient.connectPetrolcardData(new OkHttpClient(),"http://petrolcard.hu:7621/new/data.php?sortby=company&ver=1.41&hash=",phpSessionID);
            String file_name = "petrolcard_data.xml";
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(file_name, Context.MODE_PRIVATE));
                outputStreamWriter.write(xml);
                outputStreamWriter.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e){
            Log.v("onPostExecute",e.toString());
        }
        return false;
    }

    public static JSONObject authenticate(OkHttpClient httpClient, String anyURL, String serviceCode)throws Exception{
        try{
            getCookieAndRealmCode(httpClient,anyURL);
            String phpSessionID = PETROLCARD_DATA.getString("cookie");
            String realmCode = PETROLCARD_DATA.getString("realmCode");

            fakeHttpRequest(httpClient,anyURL,phpSessionID);

            Log.v("authenticate --> realmCode",realmCode);
            Log.v("authenticate --> cookie",phpSessionID);
            String realmResponseCode = new CodeCalculator(realmCode,serviceCode).Calc();
            httpClient = createAuthenticatedClient("vhcom", realmResponseCode);
            Request request = new Request.Builder().url(anyURL).header("Cookie",phpSessionID).build();
            Response response = httpClient.newCall(request).execute();
            Log.v("authenticate",response.message());
            Log.v("authenticate",response.body().string());
            if(!response.isSuccessful())
                throw new Exception();

            return PETROLCARD_DATA;
        }
        catch (Exception e){
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


    public PartnersHttpClient(Context context) {
        this.context = context;
        this.data = new Data(this.context);
        try{
            this.PETROLCARD_DATA.put(Utils.SERVICE_CODE_KEY,data.getData(Utils.SERVICE_CODE_KEY));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        AsyncRequest asyncRequest = new AsyncRequest();
        asyncRequest.execute(this.PETROLCARD_DATA);
    }

}
class AsyncRequest extends AsyncTask<JSONObject, Void,PartnersHttpClient> {
        @Override
        protected void onPostExecute(PartnersHttpClient partnersHttpClient) {
            //PartnersHttpClient.exportXMLfromPetrolcard(partnersHttpClient.getContext());
        }

        @Override
        protected PartnersHttpClient doInBackground(JSONObject... jsonArr) {
            try{
                String url = Utils.PETROLCARD_DATA;
                OkHttpClient.Builder client = new OkHttpClient()
                                        .newBuilder()
                                        .connectTimeout(120,TimeUnit.SECONDS)
                                        .readTimeout(120,TimeUnit.SECONDS)
                                        .writeTimeout(120,TimeUnit.SECONDS);
                String service_code = PartnersHttpClient.getPetrolcardData().getString(Utils.SERVICE_CODE_KEY);
                PartnersHttpClient.authenticate(client.build(),url,service_code);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
}


