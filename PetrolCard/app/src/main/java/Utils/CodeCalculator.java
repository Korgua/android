package Utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by korgua on 2017. 11. 06..
 */

public class CodeCalculator {
    private static String INPUT_CODE;
    private static String OUTPUT_CODE;
    private static String PERSONAL_CODE;

    public CodeCalculator(String inputCode, String personalCode) {
        this.INPUT_CODE = inputCode;
        this.PERSONAL_CODE = personalCode;
    }

    public String Calc(){
        //0710x
        //51243 --> 5 x 3 = 15 % 10 = 5;
        //5 + dayOfWeek(1) + 0 = 6
        //6 + month%10(1) + 7 = 14%10 = 4
        //4 + floor(dayOfMonth / 10)(1) + 1 = 6
        //6 + dayOfMonth%10(6) + 0 = 12 % 10 = 2
        //6462x

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("F");
        Date now = new Date();
        String date = simpleDateFormat.format(now);
        Log.v("date",now.toString());

        int temp;
        int firstNum,lastNum;
        int dayOfWeek,dayOfMonth,month;
        int  persCode_1st_char = PERSONAL_CODE.charAt(0)-'0',
             persCode_2nd_char = PERSONAL_CODE.charAt(1)-'0',
             persCode_3rd_char = PERSONAL_CODE.charAt(2)-'0',
             persCode_4th_char = PERSONAL_CODE.charAt(3)-'0';
        char persCode_5th_char = PERSONAL_CODE.charAt(4);
        firstNum = INPUT_CODE.charAt(0)-'0';
        lastNum = INPUT_CODE.charAt(4)-'0';



        dayOfWeek = (new SimpleDateFormat("u").format(now).charAt(0))-'0';
        dayOfMonth = (new SimpleDateFormat("d").format(now).charAt(0))-'0';
        month = Integer.parseInt(new SimpleDateFormat("MM").format(now))%10;

        StringBuilder sb = new StringBuilder();

        temp = (firstNum * lastNum + persCode_1st_char + dayOfWeek)%10;
        sb.append(Integer.toString(temp));
        temp += (month + persCode_2nd_char)%10;
        temp %= 10;
        sb.append(Integer.toString(temp));
        temp += (Math.floor(dayOfMonth/10) + persCode_3rd_char)%10;
        temp %= 10;
        sb.append(Integer.toString(temp));
        temp += (dayOfMonth%10 + persCode_4th_char)%10;
        temp %= 10;
        sb.append(Integer.toString(temp));
        sb.append(Character.toString(persCode_5th_char));

        Log.v("dayOfWeek",Integer.toString(dayOfWeek));
        Log.v("dayOfMonth",Integer.toString(dayOfMonth));
        Log.v("OUTPUT_CODE",sb.toString());
        this.OUTPUT_CODE = sb.toString();
        return OUTPUT_CODE;
    }

}
