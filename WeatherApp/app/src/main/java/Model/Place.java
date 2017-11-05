package Model;

/**
 * Created by korgua on 2017. 11. 04..
 */

public class Place {
    private float lon, lat;
    private long sunset, sunrise, lastupdate;
    private String country, city;

    public long getSunset() {return sunset;}

    public void setSunset(long sunset) {this.sunset = sunset;}

    public long getSunrise() {return sunrise;}

    public void setSunrise(long sunrise) {this.sunrise = sunrise;}

    public long getLastupdate() {return lastupdate;}

    public void setLastupdate(long lastupdate) {this.lastupdate = lastupdate;}

    public String getCountry() {return country;}

    public void setCountry(String country) {this.country = country;}

    public String getCity() {return city;}

    public void setCity(String city) {this.city = city;}

    public float getLon() {return lon;}

    public void setLon(float lon) {this.lon = lon;}

    public float getLat() {return lat;}

    public void setLat(float lat) {this.lat = lat;}
}
