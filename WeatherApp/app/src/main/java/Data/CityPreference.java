package Data;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by korgua on 2017. 11. 05..
 */

public class CityPreference {

    SharedPreferences prefs;
    public CityPreference(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public String getCity(){
        return prefs.getString("city","Budapest,HU");
    }

    public void setCity(String city){
        prefs.edit().putString("city",city).commit();
    }

}
