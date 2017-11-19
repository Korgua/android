package Utils;

/**
 * Created by korgua on 2017. 11. 05..
 */

public class Utils {

    //public static final String PETROLCARD_URL = "http://www.petrolcard.hu:7621/new";
    private static final String PETROLCARD_BASE_URL = "http://91.82.85.251:7621/";
    private static final String PETROLCARD_NEW = PETROLCARD_BASE_URL+"new/";
    private static final String PETROLCARD_AUTH = PETROLCARD_NEW+"authme.php";
    private static final String PETROLCARD_DATA = PETROLCARD_NEW + "data.php?sortby=company&ver=1.41&hash=";
    private static final String PETROLCARD_USERNAME = "vhcom";


    private static final String vhServiceCodeRegexp = "^[0-9]{4}[a-zA-Z]{1}$";
    private static final String SERVICE_CODE_KEY = "code";

    private static final String PREFS_NAME = "MyPrefsFile";

    public static String getPetrolcardBaseUrl() {return PETROLCARD_BASE_URL;}
    public static String getPetrolcardNew() {return PETROLCARD_NEW;}
    public static String getPetrolcardAuth() {return PETROLCARD_AUTH;}
    public static String getPetrolcardData() {return PETROLCARD_DATA;}
    public static String getPetrolcardUsername() {return PETROLCARD_USERNAME;}
    public static String getVhServiceCodeRegexp() {return vhServiceCodeRegexp;}
    public static String getServiceCodeKey() {return SERVICE_CODE_KEY;}
    public static String getPrefsName() {return PREFS_NAME;}
}
