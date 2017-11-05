package Data;

import android.util.Log;
import android.widget.Toast;

import com.example.korgua.weatherapp.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Model.Place;
import Model.Weather;
import Util.Utils;

/**
 * Created by korgua on 2017. 11. 05..
 */

public class JSONWeatherParser {
    public static Weather getWeather(String data){
        Weather weather = new Weather();

        //create JSON object from data
        try {
            JSONObject jsonObject = new JSONObject(data);
            Place place = new Place();
            JSONObject coordObj = Utils.getObject("coord", jsonObject);
            place.setLat(Utils.getFloat("lat",coordObj));
            place.setLon(Utils.getFloat("lon",coordObj));

            //get the sys object
            JSONObject sysObj = Utils.getObject("sys", jsonObject);
            place.setCountry(Utils.getString("country",sysObj));
            place.setLastupdate(Utils.getInt("dt",jsonObject));
            place.setSunrise(Utils.getInt("sunrise",sysObj));
            place.setSunset(Utils.getInt("sunset",sysObj));
            place.setCity(Utils.getString("name",jsonObject));

            weather.place = place;

            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            JSONObject jsonWeather = jsonArray.getJSONObject(0);
            weather.currentCondition.setWeatherId(Utils.getInt("id",jsonWeather));
            weather.currentCondition.setDescription(Utils.getString("description",jsonWeather));
            weather.currentCondition.setCondition(Utils.getString("main",jsonWeather));
            weather.currentCondition.setIcon(Utils.getString("icon",jsonWeather));

            JSONObject windObject = Utils.getObject("wind",jsonObject);
            weather.wind.setSpeed(Utils.getFloat("speed",windObject));
            weather.wind.setDeg(Utils.getFloat("deg",windObject));

            JSONObject mainObject = Utils.getObject("main",jsonObject);
            weather.currentCondition.setTemperature(Utils.getDouble("temp",mainObject));
            weather.currentCondition.setPressure(Utils.getFloat("pressure",mainObject));
            weather.currentCondition.setMaxTemp(Utils.getFloat("temp_max",mainObject));
            weather.currentCondition.setMinTemp(Utils.getFloat("temp_min",mainObject));
            weather.currentCondition.setHumidity(Utils.getFloat("humidity",mainObject));

            JSONObject cloudObject = Utils.getObject("clouds",jsonObject);
            weather.clouds.setPrecipitation(Utils.getInt("all", cloudObject));

            return weather;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
