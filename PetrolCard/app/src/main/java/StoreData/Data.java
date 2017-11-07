package StoreData;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import Utils.Utils;

/**
 * Created by korgua on 2017. 11. 05..
 */

public class Data extends AppCompatActivity{
    private Utils utils = new Utils();
    private Context context;

    public Data(Context context){
        this.context = context;
    }

    public void storeData(String key, String value){
        if(key.equals("") || value.equals("")){
            Log.v("StoreData: ","Key or Value is empty");
        }
        else {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Utils.PREFS_NAME,0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key,value);
            try{
                editor.apply();
            }
            catch (Exception e){
                Log.v("Exception",e.toString());
            }
        }
    }

    public String getData(String key){
        String value;
        SharedPreferences sharedPreferences = context.getSharedPreferences(Utils.PREFS_NAME,0);
        if(key.equals("")){
        }
        else {
            value = sharedPreferences.getString(key,"00000");
            return value;
        }
        return key;
    }

    public void deleteData(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Utils.PREFS_NAME,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        try{
            editor.apply();
        }
        catch (Exception e){
        }
    }
}
