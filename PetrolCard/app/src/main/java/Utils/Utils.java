package Utils;

/**
 * Created by korgua on 2017. 11. 05..
 */

public class Utils {

    //public static final String PETROLCARD_URL = "http://www.petrolcard.hu:7621/new";
    public static final String PETROLCARD_BASE_URL = "http://91.82.85.251:7621/";
    public static final String PETROLCARD_NEW = PETROLCARD_BASE_URL+"new/";
    public static final String PETROLCARD_AUTH = PETROLCARD_NEW+"authme.php";
    public static final String PETROLCARD_DATA = PETROLCARD_NEW + "data.php?sortby=company&ver=1.41&hash=";

    public static final String vhServiceCodeRegexp = "^[0-9]{4}[a-zA-Z]{1}$";
    public static final String SERVICE_CODE_KEY = "code";

    public static final String PREFS_NAME = "MyPrefsFile";



}
