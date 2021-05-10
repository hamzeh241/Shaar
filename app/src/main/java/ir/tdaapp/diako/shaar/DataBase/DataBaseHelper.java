package ir.tdaapp.diako.shaar.DataBase;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ir.tdaapp.diako.shaar.Adapter.DBAdapter;

public class DataBaseHelper extends SQLiteOpenHelper {

    String DB_PATH;// = "/data/data/" + this.getClass().getPackage().getName() + "/databases/";
    private static String DB_NAME = "shar.db";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public int getVer() {

        return 0;
    }

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 6);

        this.myContext = context;
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            //do nothing - database already exist
        } else {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = myContext.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + myContext.getPackageName() + "/databases/";
        }
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() throws IOException {
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = myContext.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + myContext.getPackageName() + "/databases/";
        }
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        myContext.getAssets().open(DB_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {

        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = myContext.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + myContext.getPackageName() + "/databases/";
        }
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        String filePath = myContext.getDatabasePath(DB_NAME).getAbsolutePath();
        myPath = filePath;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.disableWriteAheadLogging();
        }
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String[] AllUpdateFeatures = {
                "DELETE FROM TblFeatures WHERE Id in(59,64,68) ",
                "update TblFeatures set Title='حیاط' where Id=8",
                "update TblFeatures set Title='پارکینگ' where Id=24",
                "DELETE FROM TblFeatures WHERE Id in(78) ",
                "update TblSetting set Version='2.0',Exter1='3.5'"


        };
        String[] AllUpdateLocation = {
                "INSERT into TblLocation (Id, Title, FkCity) VALUES (18, 'دوشان', 1)",
                "INSERT into TblLocation (Id, Title, FkCity) VALUES (19, 'گریزه', 1)",
                "DELETE FROM TblLocation WHERE Id in(24)",
                "update TblLocation set Title = 'چهار باغ' where Id = 32",
                "update TblLocation set Title='شهرک ساحلی' where Id=33",
                "update TblLocation set Title='آبیدر' where Id=7",
                "insert into TblLocation (Id,Title,FkCity) values (52, 'زاگرس',1)",
                "update TblLocation set Title='زاگرس' where Id=52",
                "INSERT into TblLocation (Id, Title, FkCity) VALUES (53, 'خیابان حسن آباد', 1)",
                "INSERT into TblLocation (Id, Title, FkCity) VALUES (54, 'فردوسی', 1)",
                "INSERT into TblLocation (Id, Title, FkCity) VALUES (55, 'شهرک سپاه', 1)",
                "INSERT into TblLocation (Id, Title, FkCity) VALUES (56, 'شهرک کردستان', 1)",
                "INSERT into TblLocation (Id, Title, FkCity) VALUES (57, '1/19', 1)",
                "INSERT into TblLocation (Id, Title, FkCity) VALUES (58, '2/19', 1)",
                "INSERT into TblLocation (Id, Title, FkCity) VALUES (59, '3/19', 1)",
                "INSERT into TblLocation (Id, Title, FkCity) VALUES (60, '4/19', 1)",
                "INSERT into TblLocation (Id, Title, FkCity) VALUES (61, '1/17', 1)",
                "insert into TblLocation (Id,Title,FkCity) values (62, 'حومه شهر',1)"


        };
        String[] AllUpdateLocationV3 = {
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (63, 'شهرک نخشین', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (64, 'شهرک احمدی', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (65, 'شهرک مولوی', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (66, 'شهرک اتوبوس رانی', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (67, 'فرجه', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (68, 'عباس آباد', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (69, 'شهرک فردوس', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (70, 'شهرک دادگستری', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (71, 'دگایران', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (72, 'شهرک کارگران', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (73, 'شهرک نساجی', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (74, 'شهرک پست', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (75, 'شهرک پردیس', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (76, 'شهرک نظام مهندسی', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (77, 'شهرک علوم پزشکی', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (78, 'باغ ژاله', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (79, 'جاده ساحلی', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (80, 'شهرک نیرو انتظامی', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (81, 'گریاشان', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (82, 'شهرک پژوهش', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (83, 'مجتمع اپارتمانی ادب', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (84, 'سپور آباد', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (85, 'بردشت', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (86, 'آغازمان', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (87, 'مولوی', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (88, 'بلوار جانبازان', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (89, 'زور آباد', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (90, 'قوپال', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (91, 'کمربندی آبیدر', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (92, 'صادق آباد', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (93, 'فراز', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (94, '2/17', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (95, 'قرادیان', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (96, 'زیبا شهر', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (97, 'میدان کوهنورد', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (98, 'خیابان کشاورز', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (101, 'میدان امام شافعی', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (102, 'میدان ظفریه', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (103, 'خسرو آباد', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (104, 'میدان شورا', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (105, 'حاجی آباد بالا', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (106, 'چهار راه کشاورز', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (107, 'چهار دیواری', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (108, 'میدان انقلاب', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (109, 'میدان آزادی', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (110, 'سلیمان خاطر', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (111, 'سه راه شیخان', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (112, 'جور آباد', 1)",
                "INSERT into TblLocation ([Id], [Title],  [FkCity]) VALUES (113, 'چهار راه شهدا', 1)"
        };
        String[] bug={ "DELETE FROM TblLocation WHERE Id in(99,100) "};

        String[] AllUpdateLocationV5={
                "DELETE FROM TblLocation WHERE Id =1 ",
                "update TblLocation set Title='مسکن مهر(بهاران)' where Id=49",
                "update TblLocation set Title='بلوار شبلی' where Id=5"};
        String[] AllUpdateLocationV6={ "update TblSetting set Version='3.1',Exter1='3.5'"};

        if (oldVersion < 2) {
            for (int i = 0; i < AllUpdateLocation.length; i++)
                try {
                    db.execSQL(AllUpdateLocation[i]);
                } catch (Exception e) {

                }
            for (int i = 0; i < AllUpdateFeatures.length; i++)
                try {
                    db.execSQL(AllUpdateFeatures[i]);
                } catch (Exception e) {

                }
        }
        if (oldVersion < 3) {
            for (int i = 0; i < AllUpdateLocationV3.length; i++)
                try {
                    db.execSQL(AllUpdateLocationV3[i]);
                } catch (Exception e) {

                }

        }
        if (oldVersion < 4) {
            for (int i = 0; i < bug.length; i++)
                try {
                    db.execSQL(bug[i]);
                } catch (Exception e) {

                }

        }
        if (oldVersion < 5) {
            for (int i = 0; i < AllUpdateLocationV5.length; i++)
                try {
                    db.execSQL(AllUpdateLocationV5[i]);
                } catch (Exception e) {

                }

        }
        if (oldVersion < 6) {
            for (int i = 0; i < AllUpdateLocationV6.length; i++)
                try {
                    db.execSQL(AllUpdateLocationV6[i]);
                } catch (Exception e) {

                }

        }


    }


}