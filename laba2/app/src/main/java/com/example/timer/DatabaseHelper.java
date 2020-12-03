package com.example.timer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH;
    private static String DB_NAME = "Timer.db";
    private static final int SCHEMA = 3;

    public static final String SEQUENCE_TABLE = "Sequences";
    public static final String SEQUENCE_COLUMN_ID = "id";
    public static final String SEQUENCE_COLUMN_NAME = "name";
    public static final String SEQUENCE_COLUMN_COLOR = "color";
    private boolean mNeedUpdate = false;

    public static final String TIMER_TABLE = "TIMER";
    public static final String TIMER_COLUMN_ID = "id";
    public static final String TIMER_COLUMN_NAME = "idCategory";
    public static final String TIMER_COLUMN_TIME = "time";

    public static final String SEQUENCESTIMER_TABLE = "SequencesTimers";
    public static final String SEQUENCESTIMER_COLUMN_IDSEQUENCE = "idSequence";
    public static final String SEQUENCESTIMER_COLUMN_IDTIMER = "idTimer";

    public static final String CATEGORY_TABLE = "Category";
    public static final String CATEGORY_COLUMN_ID = "id";
    public static final String CATEGORY_COLUMN_NAME = "name";

    private Context context;

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, SCHEMA);
        this.context = context;
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/" + DB_NAME;
        copyDataBase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQLiteDatabase openDatabase(){
        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH);
        return dbFile.exists();
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = context.getAssets().open(DB_NAME);
        //InputStream mInput = mContext.getResources().openRawResource(R.raw.info);
        OutputStream mOutput = new FileOutputStream(DB_PATH);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }
}
