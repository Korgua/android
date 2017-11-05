package Utils;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by korgua on 2017. 11. 05..
 */

public class PartnersHttpClient {
    //string credentials = username + ":" + password;
    //string credBase64 = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT).replace("\n", "");

    public PartnersHttpClient(){
        Teszt teszt = new Teszt();
        teszt.execute();
    }


}

class Teszt extends AsyncTask<String,Void,PartnersHttpClient>{
    @Override
    protected void onPostExecute(PartnersHttpClient partnersHttpClient) {
        super.onPostExecute(partnersHttpClient);
    }

    @Override
    protected PartnersHttpClient doInBackground(String... strings) {
        BufferedReader reader = null;
        byte[] loginBytes = ("username:password").getBytes();
        StringBuilder loginBuilder = new StringBuilder()
                .append("Basic: ")
                .append(Base64.encodeToString(loginBytes, Base64.DEFAULT));



        try{
            URL url = new URL("https://jigsaw.w3.org/HTTP/Basic/");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            con.setRequestProperty("Authorization", loginBuilder.toString());
            con.connect();
            Log.v("alma",con.getErrorStream()+"");
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\r\n");
                Log.v("Line",line);
            }
        } catch (Exception e) {
            Log.v("Excpetion", e.toString());
        }
        return null;
    }
}
