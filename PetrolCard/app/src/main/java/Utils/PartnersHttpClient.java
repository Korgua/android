package Utils;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.Stack;

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

public class PartnersHttpClient {
    private static Response rsp;

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
        ResponseBody responseBodyCopy = response.peekBody(Long.MAX_VALUE);
        rsp = response;
        if (!response.isSuccessful()) {
            Log.v("isSuccessful",response.header("WWW-Authenticate"));

            OkHttpClient client = new OkHttpClient();
            request = new Request.Builder().url(Utils.PETROLCARD_DATA).build();
            response = httpClient.newCall(request).execute();
            //Log.v("res",response.challenges().toString());
            //if(responseBodyCopy.string().equals("Access denied.")){
            //}
            //throw new IOException("Unexpected code " + response);
        }
        return response;
    }


    public static Response fetch(String url, String username, String password) throws Exception {
        OkHttpClient httpClient = createAuthenticatedClient(username, password);
        // execute request
        return doRequest(httpClient, url);
    }

    private static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

    public void testFetchFailed() throws Exception {
        String url = Utils.PETROLCARD_DATA;
        Response response = fetch(url, "user", "passwd");
        Log.v("Response", response.toString());
    }

    public PartnersHttpClient() {
        AsyncRequest asyncRequest = new AsyncRequest();
        asyncRequest.execute("");
    }

    public static void alma(){
        OkHttpClient client = new OkHttpClient();

        Response response = null;
        try {
            response = doRequest(client, Utils.PETROLCARD_DATA);
            Log.v("alma",response.body().string());
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


