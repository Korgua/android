package hu.vhcom.www.petrolcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;

import StoreData.Data;
import Utils.*;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class GetData extends AppCompatActivity {

    private static OkHttpClient okHttpClient;
    private Data data;

    private static String URL;
    private static String SERVICE_CODE;
    private static String REALM_CODE;
    private static String COOKIE;
    private static String PETROLCARD_DATA;
    private static String XML;


    public static void setXML(String xml){XML = xml;}
    public static String getXml(){return XML;}
    public static String getPetrolcardData() {return PETROLCARD_DATA;}
    public static String getRealmCode() {return REALM_CODE;}
    public static void setRealmCode(String realmCode) {REALM_CODE = realmCode;}
    public static String getCookie() {return COOKIE;}
    public static void setCookie(String COOKIE) {GetData.COOKIE = COOKIE;}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);
        data = new Data(GetData.this);
        try {
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void asyncSucces(){
        data.storeData("cookie",getCookie());
        Log.i("asyncSucces --> getCookie()",data.getData("cookie"));
        Log.i("asyncSucces --> xml",getXml());
        try{
            
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("data.xml",Context.MODE_PRIVATE));
            outputStreamWriter.write(getXml());
            outputStreamWriter.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void connect() throws Exception {

        URL = Utils.getPetrolcardAuth();
        SERVICE_CODE = data.getData(Utils.getServiceCodeKey());
        PETROLCARD_DATA = Utils.getPetrolcardData();
        OkHttpClient.Builder okHttpClientBuilder;
        okHttpClientBuilder = new OkHttpClient()
                .newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS);
        okHttpClient = okHttpClientBuilder.build();
        //authenticate(URL, SERVICE_CODE);
        connectAsync connectAsync = new connectAsync();
        connectAsync.execute(data);
    }

    private static void getCookieAndRealmCode(String anyURL) throws Exception {
        Log.i("getCookieAndRealmCode --> url", anyURL);
        Request request = new Request.Builder().url(anyURL).build();
        Response response = okHttpClient.newCall(request).execute();
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
        String[] realmCodeParts = new String[0];
        if (realmCode != null) {
            realmCodeParts = realmCode.split("-");
        }
        realmCode = realmCodeParts[1];
        Log.i("getCookie --> realmCode", realmCode);
        Log.i("getCookie --> cookie", phpSessionID);
        setCookie(phpSessionID);
        setRealmCode(realmCode);
    }

    private static void authenticate(String anyURL, String serviceCode) throws Exception {
        try {
            Log.i("authenticate --> url", anyURL);
            getCookieAndRealmCode(anyURL);
            String phpSessionID = getCookie();
            String realmCode = getRealmCode();

            fakeHttpRequest(anyURL, phpSessionID);

            Log.i("authenticate --> realmCode", realmCode);
            Log.i("authenticate --> cookie", phpSessionID);
            String realmResponseCode = new CodeCalculator(realmCode, serviceCode).Calc();
            okHttpClient = createAuthenticatedClient(realmResponseCode);
            Request request = new Request.Builder().url(anyURL).header("Cookie", phpSessionID).build();
            Response response = okHttpClient.newCall(request).execute();
            Log.i("authenticate", response.message());
            if (!response.isSuccessful()) {
                Log.i("!response.isSuccessful()", "if");
                throw new Exception();
            } else {
                Log.i("!response.isSuccessful()", "else");
                setXML(connectPetrolcardData(okHttpClient, getPetrolcardData(), phpSessionID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void fakeHttpRequest(String anyURL, String cookie) throws Exception {
        Request request = new Request.Builder().url(anyURL).header("Cookie", cookie).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            Log.i("fakeHttpRequest", "Succesfull request");
        }
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
    private static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

    private static String connectPetrolcardData(OkHttpClient httpClient, String anyURL, String phpSessionId) throws Exception {
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

    @SuppressLint("StaticFieldLeak")
    private class connectAsync extends AsyncTask<Data,Void,GetData>{
        @Override
        protected void onPostExecute(GetData getData) {
            super.onPostExecute(getData);
            asyncSucces();
        }

        @Override
        protected GetData doInBackground(Data... data) {
            try {
                authenticate(URL, SERVICE_CODE);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
