package ir.tdaapp.diako.shaar.ETC;

/**
 * Created by Diako on 8/26/2019.
 */

public class ReplaceData {

    public String Number_PersianToEnglish(String Number) {
        Number.replace("۰", "0");
        Number.replace("۱", "1");
        Number.replace("۲", "2");
        Number.replace("۳", "3");
        Number.replace("۴", "4");
        Number.replace("۵", "5");
        Number.replace("۶", "6");
        Number.replace("۷", "7");
        Number.replace("۸", "8");
        Number.replace("۹", "9");
        return Number;
    }

}
