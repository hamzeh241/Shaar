package ir.tdaapp.diako.shaar.Data;

import java.util.List;

/**
 * Created by Diako on 6/16/2019.
 */

public class DA_Add_Home {

    //برای radio button فروش
    public static boolean rdo_Sell=true;

    //برای radio button اجاره
    public static boolean rdo_Rent;

    //برای radio button مسکونی
    public static boolean rdo_Residential;

    //برای radio button تجاری
    public static boolean rdo_Commercial;

    //برای radio button صنعتی
    public static boolean rdo_Industrial;

    //برای تایپ انتخاب شده
    public static int TypeHome=0;

    //برای سر تیتر Fragment Type Home
    public static String TitleProperty;

    //برای اسپینر موقعیت
    public static int cmb_Location=0;

    //برای اسپینر سال ساخت
    public static int cmb_Year_of_construction=-1;

    // برای اسپینر تعداد اتاق خواب
    public static int CountRoom=0;

    //برای ادیت تکست عنوان آگهی
    public static String txt_AdTitle;

    //برای ادیت تکست توضیحات
    public static String txt_DiscriptionHome;

    //برای ادیت تکست قیمت
    public static String txt_Price;

    //برای ادیت تکست اجاره
    public static String txt_Rent;

    //برای ادیت تکست رهن
    public static String txt_Mortgage;

    //برای ادیت تکست متراژ
    public static String txt_TheArea;

    //برای ادیت تکست موبایل
    public static String txt_Mob;

    //برای ادیت تکست تلفن
    public static String txt_Phone;

    //برای ادیت تکست آدرس فیلم
    public static String txt_VideoLink;

    //برای ادیت تکست آدرس
    public static String txt_Address;

    //برای آدرس ادیت تکست کد پستی
    public static String txt_CodePosty;

    //این متغیر برای ذخیره کردن id منزل که اگر مقدار صفر باشد یک آیتم جدید اضافه شود در غیر این صورت این آیتم با این id حذف و یک آیتم جدید جایگزین شود
    public static int ItemId=0;


    //لیستی از چک باکس ها در ویژگی های خانه(TypeHome)
    public static List<DA_TypeHome_Boolean> booleans;

    //لیستی از اعداد در ویژگی های خانه(TypeHome)
    public static  List<DA_TypeHome_Number> numbers;

    //لیستی از اسنیپر در ویژگی های خانه(TypeHome)
    public static List<DA_TypeHome_Select> selects;

    //لیستی از متن ها در ویژگی های خانه(TypeHome)
    public static List<DA_TypeHome_Text> texts;

    //برای لیستی از عکس ها که کاربر انتخاب کرده است
    public static List<String> Images;

    ///برای تخلیه کردن مقادیر بالا
    public static void ClearDA_AddHome() {
        texts = null;
        numbers = null;
        selects = null;
        booleans = null;
        rdo_Rent = false;
        rdo_Sell = true;
        CountRoom = 0;
        cmb_Year_of_construction = -1;
        TypeHome = 0;
        txt_AdTitle = "";
        cmb_Location = 0;
        rdo_Commercial = false;
        rdo_Industrial = false;
        rdo_Residential = false;
        TitleProperty = "";
        txt_DiscriptionHome = "";
        txt_Mob = "";
        txt_Mortgage = "";
        txt_Phone = "";
        txt_Price = "";
        txt_Rent = "";
        txt_TheArea = "";
        txt_VideoLink = "";
        txt_CodePosty = "";
        txt_Address = "";
        ItemId=0;
        Images=null;
    }
}
