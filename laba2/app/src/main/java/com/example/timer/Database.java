package com.example.timer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timer.Sequence.Sequence;
import com.example.timer.Sequence.Timer.Category.Category;
import com.example.timer.Sequence.Timer.Timer;

import java.io.IOException;
import java.util.ArrayList;

public class Database {
    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    Cursor userCursor;

    public Database(Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.openDatabase();
        try {
            dbHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }

    public ArrayList<Sequence> getSequence(){
        ArrayList<Sequence> sequences = new ArrayList<Sequence>();
        try{
            userCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.SEQUENCE_TABLE, null);
        }
        catch (Exception ex){
            Log.d("getSequence", ex.getMessage());
        }
        if(userCursor.moveToFirst()){
            do{
                sequences.add(new Sequence(userCursor.getInt(0),
                        userCursor.getString(1), userCursor.getString(2)));
            }
            while(userCursor.moveToNext());
        }
        userCursor.close();
        return sequences;
    }

    public long addSequence(Sequence sequence){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.SEQUENCE_COLUMN_NAME, sequence.getName());
        cv.put(DatabaseHelper.SEQUENCE_COLUMN_COLOR, sequence.getColor());
        return db.insert(DatabaseHelper.SEQUENCE_TABLE, null, cv);
    }

    public void updateSequence(Sequence sequence){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.SEQUENCE_COLUMN_NAME, sequence.getName());
        cv.put(DatabaseHelper.SEQUENCE_COLUMN_COLOR, sequence.getColor());
        db.update(DatabaseHelper.SEQUENCE_TABLE, cv, DatabaseHelper.SEQUENCE_COLUMN_ID + "=" +
                String.valueOf(sequence.getId()), null);
    }

    public void deleteSequence(Sequence sequence){
        ArrayList<Timer> timers = getTimers(sequence.getId());
        for(Timer item: timers){
            deleteTimer(item.getId());
        }
        db.delete(DatabaseHelper.SEQUENCESTIMER_TABLE, DatabaseHelper.SEQUENCESTIMER_COLUMN_IDSEQUENCE + " = ?", new String[]{String.valueOf(sequence.getId())});
        db.delete(DatabaseHelper.SEQUENCE_TABLE, "id = ?", new String[]{String.valueOf(sequence.getId())});
    }

    public void deleteTimer(long id){
        db.delete(DatabaseHelper.TIMER_TABLE, "id = ?", new String[]{String.valueOf(id)});
    }

    public void deleteTable(){
        db.execSQL("DELETE FROM " + DatabaseHelper.SEQUENCESTIMER_TABLE);
        db.execSQL("DELETE FROM " + DatabaseHelper.SEQUENCE_TABLE);
        db.execSQL("DELETE FROM " + DatabaseHelper.TIMER_TABLE);
        db.execSQL("DELETE FROM " + DatabaseHelper.CATEGORY_TABLE);
    }

    public ArrayList<Timer> getTimers(int idSequence) {
        ArrayList<Timer> timers = new ArrayList<>();
        if(idSequence == -1)
            return timers;
        userCursor = db.rawQuery("SELECT " + DatabaseHelper.SEQUENCESTIMER_COLUMN_IDTIMER +
                " FROM " + DatabaseHelper.SEQUENCESTIMER_TABLE + " WHERE " +
                DatabaseHelper.SEQUENCESTIMER_COLUMN_IDSEQUENCE + " = " + idSequence, null);
        ArrayList<String> idList = new ArrayList<>();
        if (userCursor.moveToFirst()) {
            do {
                idList.add(String.valueOf(userCursor.getInt(0)));
            }
            while (userCursor.moveToNext());
        }
        userCursor.close();

        int i;
        StringBuilder result = new StringBuilder("(");
        for (i = 0; i < idList.size(); i++) {
            result.append(idList.get(i));
            if (i + 1 != idList.size()) {
                result.append(", ");
            }
        }
        result.append(")");
        userCursor = db.rawQuery("Select * FROM " + DatabaseHelper.TIMER_TABLE + " WHERE id IN "
                + result.toString(), null);
        return createTimers(userCursor);
    }

    public ArrayList<Timer> getTimers(){
        userCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TIMER_TABLE, null);
        return createTimers(userCursor);
    }

    private ArrayList<Timer> createTimers(Cursor userCursor){
        ArrayList<Timer> timers = new ArrayList<>();
        int id, idCategory, time;
        if(userCursor.moveToFirst()){
            do{
                id = userCursor.getInt(0);
                idCategory = userCursor.getInt(1);
                time = userCursor.getInt(2);
                timers.add(new Timer(id, getCategory(idCategory), time));
            }
            while(userCursor.moveToNext());
        }
        return timers;
    }
    public void addCategory(Category category){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.CATEGORY_COLUMN_NAME, category.getName());
        db.insert(DatabaseHelper.CATEGORY_TABLE, null, cv);
    }

    public void delCategory(long idCategory){
        Cursor timerCursor = db.rawQuery("SELECT "+ DatabaseHelper.TIMER_COLUMN_ID +" FROM " +
                DatabaseHelper.TIMER_TABLE +" WHERE " + DatabaseHelper.TIMER_COLUMN_NAME +" = " +
                idCategory, null);
        if(timerCursor.moveToFirst()){
            do{
                db.delete(DatabaseHelper.SEQUENCESTIMER_TABLE, DatabaseHelper.SEQUENCESTIMER_COLUMN_IDTIMER +
                                " = ?", new String[]{String.valueOf(timerCursor.getString(0))});
            }while(timerCursor.moveToNext());
        }
        db.delete(DatabaseHelper.TIMER_TABLE, DatabaseHelper.TIMER_COLUMN_NAME + " = ?", new String[]{String.valueOf(idCategory)});
        db.delete(DatabaseHelper.CATEGORY_TABLE, "id = ?", new String[]{String.valueOf(idCategory)});
    }

    public Category getCategory(int idCategory){
        Cursor categoryCursor = db.rawQuery("SELECT " + DatabaseHelper.CATEGORY_COLUMN_NAME
                + " FROM " + DatabaseHelper.CATEGORY_TABLE + " WHERE " + DatabaseHelper.CATEGORY_COLUMN_ID +
                " = " + idCategory, null);
        if(categoryCursor.moveToFirst()){
             return new Category(idCategory, categoryCursor.getString(0));
        }
        return null;
    }

    public ArrayList<Category> getCategory(){
        ArrayList<Category> categories = new ArrayList<>();
        Cursor categoryCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.CATEGORY_TABLE, null);
        if(categoryCursor.moveToFirst()){
            do{
                categories.add(new Category(categoryCursor.getInt(0), categoryCursor.getString(1)));
            }while(categoryCursor.moveToNext());
        }
        return categories;
    }

    public void updateTimer(Timer timer) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.TIMER_COLUMN_TIME, timer.getDuration());
        cv.put(DatabaseHelper.TIMER_COLUMN_NAME, timer.getCategory().getId());
        try{
            db.update(DatabaseHelper.TIMER_TABLE, cv, DatabaseHelper.TIMER_COLUMN_ID + "=" +
                    String.valueOf(timer.getId()), null);
        }
        catch(Exception ex){
            Log.d("UpdateTimer", ex.getMessage());
        }
    }

    public void addTimer(Timer timer, long idSequence){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.TIMER_COLUMN_NAME, timer.getCategory().getId());
        cv.put(DatabaseHelper.TIMER_COLUMN_TIME, timer.getDuration());
        long idTimer = db.insert(DatabaseHelper.TIMER_TABLE, null, cv);
        cv = new ContentValues();
        cv.put(DatabaseHelper.SEQUENCESTIMER_COLUMN_IDSEQUENCE, idSequence);
        cv.put(DatabaseHelper.SEQUENCESTIMER_COLUMN_IDTIMER, idTimer);
        db.insert(DatabaseHelper.SEQUENCESTIMER_TABLE, null, cv);
    }
}
