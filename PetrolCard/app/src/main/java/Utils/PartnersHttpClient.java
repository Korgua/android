package Utils;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;

import StoreData.Data;
import hu.vhcom.www.petrolcard.MainActivity;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;

/**
 * Created by korgua on 2017. 11. 05..
 */

public class PartnersHttpClient extends Activity{
    public Data data;
    public Context context;
    public static JSONObject PETROLCARD_DATA = new JSONObject();




    private static OkHttpClient createAuthenticatedClient(final String username, final String password) {
        // build client with authentication information.
        OkHttpClient httpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
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
        String phpSessionId = cookie;
        Request request = new Request.Builder().url(anyURL).header("Cookie",phpSessionId).build();
        Response response = httpClient.newCall(request).execute();
        Log.v("getRealmCode --> responseBody",response.body().string());
    }

    public static void authenticate(OkHttpClient httpClient, String anyURL, String serviceCode)throws Exception{
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
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }


    public PartnersHttpClient(Context context) {
        this.data = new Data(context);
        this.context = context;
        AsyncRequest asyncRequest = new AsyncRequest();
        asyncRequest.execute(data);
    }

}
class AsyncRequest extends AsyncTask<Data,Void,PartnersHttpClient>{
        @Override
        protected void onPostExecute(PartnersHttpClient partnersHttpClient) {
            //Log.v("Response",response.toString());

        }

        @Override
        protected PartnersHttpClient doInBackground(Data... dataArray) {
            Data data = dataArray[0];
            String url = Utils.PETROLCARD_DATA;
            try{
                OkHttpClient okHttpClient = new OkHttpClient();
                PartnersHttpClient.authenticate(okHttpClient,Utils.PETROLCARD_DATA,data.getData(Utils.SERVICE_CODE_KEY));
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }


