package Model;

/**
 * Created by korgua on 2017. 11. 05..
 */

public class Weather {

    public  Place place;
    public String iconData;
    public CurrentCondition currentCondition = new CurrentCondition();
    public Temperature temperature = new Temperature();
    public Wind wind = new Wind();
    public Snow snow = new Snow();
    public Clouds clouds = new Clouds();


}
