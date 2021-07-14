package ir.tdaapp.diako.shaar.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ir.tdaapp.diako.shaar.DataBase.DataBaseHelper;
import ir.tdaapp.diako.shaar.ETC.User;

import java.io.File;
import java.io.IOException;

public class DBAdapter {

    protected static final String TAG = "DataAdapter";
    private final Context mContext;
    private SQLiteDatabase mDb;
    private final DataBaseHelper mDbHelper;

    private OnDatabaseUpdate onDatabaseUpdate;

    public DBAdapter(Context context) {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public DBAdapter(Context context, OnDatabaseUpdate onDatabaseUpdate) {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
        this.onDatabaseUpdate = onDatabaseUpdate;
    }

    private DBAdapter createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    private boolean removeDatabase() {
        boolean isRemoved;
        File file = new File(mDbHelper.getDB_PATH());

        if (!file.exists())
            return true;

        isRemoved = file.delete();

        if (file.exists()) {
            removeDatabase();
        }

        return isRemoved;
    }

    public void updateDatabase() {
        User.UserModel model = new User(mContext).getUserInfo();
        onDatabaseUpdate.onUserInfoSaved(model);

        removeDatabase();
        onDatabaseUpdate.onDatabaseDeleted();

        createDatabase();
        onDatabaseUpdate.onDatabaseCreated();
    }

    public DBAdapter open() throws SQLException {
        try {

            mDbHelper.openDataBase();
//            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();

        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>" + mSQLException.toString());
            throw mSQLException;
        }

        return this;
    }

    public void close() {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        String filePath = database.getPath();
        mDbHelper.close();
    }

    public Cursor getData(String Query) {
        try {
            Cursor mCur = mDb.rawQuery(Query, null);
            if (mCur != null) {
                mCur.moveToNext();
            }
            return mCur;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString());
            // throw mSQLException;
            return null;
        }
    }

    public Cursor executeQuery(String Q) {
        final DBAdapter mDbHelper = new DBAdapter(mContext);
        mDbHelper.createDatabase();

        mDbHelper.open();
        final Cursor _Cursor = mDbHelper.getData(Q);
        mDbHelper.close();
        return _Cursor;
    }

    public int getVersion() {
        return mDbHelper.getVer();
    }

    public interface OnDatabaseUpdate {
        void onUserInfoSaved(User.UserModel model);

        void onDatabaseDeleted();

        void onDatabaseCreated();
    }

}