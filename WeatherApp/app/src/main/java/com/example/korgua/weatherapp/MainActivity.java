package com.example.korgua.weatherapp;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Locale;
import java.util.PriorityQueue;

import Data.CityPreference;
import Data.JSONWeatherParser;
import Data.WeatherHttpClient;
import Model.Weather;
import Util.Utils;

public class MainActivity extends AppCompatActivity {

    private TextView cityName, temp, description, humidity, pressure, wind, sunrise, sunset, updated;
    private ImageView iconView;

    Weather weather = new Weather();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.change_cityId){
            showInputDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = (TextView)findViewById(R.id.cityText);
        iconView = (ImageView)findViewById(R.id.thumbnailIcon);
        temp = (TextView)findViewById(R.id.tempText);
        description = (TextView)findViewById(R.id.cloudText);
        humidity = (TextView)findViewById(R.id.humidText);
        pressure = (TextView)findViewById(R.id.pressureText);
        wind = (TextView)findViewById(R.id.windText);
        sunrise = (TextView)findViewById(R.id.riseText);
        sunset = (TextView)findViewById(R.id.setText);
        updated = (TextView)findViewById(R.id.updateText);

        CityPreference cityPreference = new CityPreference(MainActivity.this);

        RenderWeatherData(cityPreference.getCity());
    }

    private void showInputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Change city");
        final EditText cityInput  = new EditText(getApplicationContext());
        cityInput.setInputType(InputType.TYPE_CLASS_TEXT);
        cityInput.setHint("Budapest,HU");
        builder.setView(cityInput);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CityPreference cityPreference = new CityPreference(MainActivity.this);
                cityPreference.setCity(cityInput.getText().toString());

                String newCity = cityPreference.getCity();

                RenderWeatherData(newCity);
            }
        });
        builder.show();
    }

    public void RenderWeatherData(String city){

        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(new String[]{city});

    }

    private class WeatherTask extends AsyncTask<String, Void, Weather>{

        @Override
        protected Weather doInBackground(String... strings) {
            String data = ((new WeatherHttpClient()).getWeatherData(strings[0]));

            weather = JSONWeatherParser.getWeather(data);
            Log.v("Weather data: ", weather.place.getCity());
            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            weather.iconData = weather.currentCondition.getIcon();
            cityName.setText(weather.place.getCity().toString());
            temp.setText((weather.currentCondition.getTemperature()+" °C").replace('.',','));
            description.setText("Current condition: " + weather.currentCondition.getCondition()+" ("+weather.currentCondition.getDescription()+")");
            humidity.setText("Humidity: " + weather.currentCondition.getHumidity()+"%");
            pressure.setText("Pressure: " +weather.currentCondition.getPressure()+" kpa");
            wind.setText("Wind speed: " + weather.wind.getSpeed()+" m/s");
            sunrise.setText("Sunrise: "+formatter.format(new java.util.Date(weather.place.getSunrise()*1000)).toString());
            sunset.setText("Sunset: "+formatter.format(new java.util.Date(weather.place.getSunset()*1000)).toString());
            updated.setText("Last updated: "+formatter.format(new java.util.Date(weather.place.getLastupdate()*1000)).toString());
            new DownloadImageAsync().execute(weather.iconData);
        }
    }

    private class DownloadImageAsync extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            iconView.setImageBitmap(bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            //Log.v("doInBackground",strings[0].toString());
            return DownloadImage(strings[0]);
        }

        private Bitmap DownloadImage(String code){
            try {
                URL url = new URL(Utils.ICON_URL + code+".png");
                URLConnection con = url.openConnection();
                HttpURLConnection httpURLConnection = (HttpURLConnection)con;
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStream is = null;
                    is = (InputStream)httpURLConnection.getContent();
                    final Bitmap bitmap = BitmapFactory.decodeStream(is);
                    return bitmap;
                }
                else {
                    Log.v("Bitmap download","Errrrorrrrrrrrrrrr: "+Utils.ICON_URL + code+".png");
                }
            }
            catch (Exception e){

            }
            return null;
        }
    }

}
