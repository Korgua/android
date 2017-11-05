package Utils;

import android.util.Log;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by korgua on 2017. 11. 05..
 */

public class Regexp {

    private boolean MATCHED = false;

    public Regexp(String string, String regexp){
        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(string);
        if(!m.matches()){
            Log.v("Regexp - not match",string+", "+regexp);
            this.MATCHED = false;
        }
        else{
            Log.v("Regexp - match",string+", "+regexp);
            this.MATCHED = true;
        }
    }

    public boolean getResult(){
        return this.MATCHED;
    }

}
