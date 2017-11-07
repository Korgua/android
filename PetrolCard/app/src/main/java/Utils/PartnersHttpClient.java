package Utils;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

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
    private static Response rsp;
    private static Data data;
    private Context context;
    private static boolean executed = false;




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


    private static Response doRequest(OkHttpClient httpClient, String anyURL) throws Exception {
        Request request = new Request.Builder().url(anyURL).build();
        Response response = httpClient.newCall(request).execute();
        Log.v("Response",response.networkResponse().toString());
        rsp = response;
        if (!response.isSuccessful()) {
            String responseHeader = response.header("WWW-Authenticate");

        }

        return response;
    }

    private static Response doHeaderRequest(OkHttpClient httpClient, String anyURL) throws Exception {
        String phpSessID = "";
        Request request = null;
        Response response = null;
            request = new Request.Builder().url(anyURL).build();
            response = httpClient.newCall(request).execute();
            httpClient.newCall(request).execute();
                phpSessID = response.headers("Set-Cookie").toString();
                String[] phpSessIDParts = phpSessID.split(";");
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i < phpSessIDParts[0].length(); i++)
                    sb.append(phpSessIDParts[0].charAt(i));
                phpSessID = sb.toString();
                data.storeData("cookie", phpSessID);

            Log.v("PHPSESSID",phpSessID);
            Log.v("Stored sess_id",data.getData("cookie"));
        String responseHeader = response.header("WWW-Authenticate");
        Log.v("ResponseHeader",response.headers("Set-Cookie").toString());
        String[] responseHeaderParts = responseHeader.split("-");
        String VhServiceCodeFromRealm = responseHeaderParts[1].toString();
        Log.v("VhServiceCodeFromRealm",VhServiceCodeFromRealm+", "+data.getData(Utils.SERVICE_CODE_KEY));
        CodeCalculator codeCalculator = new CodeCalculator(VhServiceCodeFromRealm,data.getData(Utils.SERVICE_CODE_KEY));
        String responseCode = codeCalculator.Calc();
        //httpClient = createAuthenticatedClient("vhcom", responseCode);
        request = new Request.Builder().url(anyURL).header("Cookie",phpSessID).build();
        response = httpClient.newCall(request).execute();
        Log.v("ResponseHeader",response.headers("Set-Cookie").toString());
        Log.v("Response",response.body().string());
        httpClient = createAuthenticatedClient("vhcom", responseCode);
        request = new Request.Builder().url(anyURL).header("Cookie",phpSessID).build();
        response = httpClient.newCall(request).execute();
        Log.v("ResponseHeader",response.headers("Set-Cookie").toString());
        Log.v("Response",response.body().string());
        return response;
    }


    public static Response fetch(String url, String username, String password) throws Exception {
        OkHttpClient httpClient = new OkHttpClient();
        // execute request
        return doHeaderRequest(httpClient, url);
    }

    private static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

    public void testFetchFailed() throws Exception {
        String url = Utils.PETROLCARD_URL;
        Response response = fetch(url, "user", "passwd");
        Log.v("Response", response.toString());
    }

    public PartnersHttpClient(Context context) {
        this.data = new Data(context);
        this.context = context;
        Log.v("almakecskeborsó",data.getData(Utils.SERVICE_CODE_KEY));
        AsyncRequest asyncRequest = new AsyncRequest();
        asyncRequest.execute("");
    }

    public static void alma(){
        OkHttpClient client = new OkHttpClient();

        Response response = null;
        try {
            response = doHeaderRequest(client, Utils.PETROLCARD_DATA);
            //Log.v("alma",response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //return response.body().string();
        }

}
class AsyncRequest extends AsyncTask<String,Void,PartnersHttpClient>{
        Response response = null;
        @Override
        protected void onPostExecute(PartnersHttpClient partnersHttpClient) {
            //Log.v("Response",response.toString());

        }

        @Override
        protected PartnersHttpClient doInBackground(String... strings) {
            String url = Utils.PETROLCARD_DATA;
            try{
                response = PartnersHttpClient.fetch(url, "user", "passwd");
                //Log.v("Response",response.body().string());
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }


