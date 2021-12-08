package org.techtown.final_exam_project;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Date;

public class DB_helper {

    //변수 선언
    private static final String DATABASE_NAME = "running.db";    //생성될 데베파일 이름
    private static final int DATABASE_VERSION = 2;
    public static SQLiteDatabase mDB;
    public static DatabaseHelper mDBHelper;
    private Context context;

    //데이터베이스헬퍼 정의 (SQLiteOpenHelper 를 상속 받음)
    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //테이블을 만들어라
            db.execSQL(DB_table.CreateDB._CREATE0);
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_table.CreateDB._TABLEWARE);
            onCreate(db);
        }
    }

    //생성자
    public DB_helper(Context context) {
        this.context = context;
    }

    //데베를 사용하기 위한 준비
    //이 메소드를 사용하는 곳으로 예외 책임을 전가(예외 던지기 - throws)
    public DB_helper open() throws SQLException {
        mDBHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create() {
        mDBHelper.onCreate(mDB);
    }

    public void close() {
        mDB.close();
    }

    //데이터 삽입
    public long insertColum(Date date, String running_type, float distance, double average_speed) {
        ContentValues values = new ContentValues();
        values.put(DB_table.CreateDB.DATE, String.valueOf(date));
        values.put(DB_table.CreateDB.RUNNING_TYPE, running_type);
        values.put(DB_table.CreateDB.DISTANCE, distance);
        values.put(DB_table.CreateDB.AVERAGE_SPEED, average_speed);
        return mDB.insert(DB_table.CreateDB._TABLEWARE, null, values);
    }

    //데이터 갱신
    public boolean updateColumn(Date date, String running_type, float distance, double average_speed) {
        ContentValues values = new ContentValues();
        values.put(DB_table.CreateDB.DATE, String.valueOf(date));
        values.put(DB_table.CreateDB.RUNNING_TYPE, running_type);
        values.put(DB_table.CreateDB.DISTANCE, distance);
        values.put(DB_table.CreateDB.AVERAGE_SPEED, average_speed);
        return mDB.update(DB_table.CreateDB._TABLEWARE,  values, null, null) > 0;
    }

    //데이터 전체 삭제
    public void deleteAllColumns() {
        mDB.delete(DB_table.CreateDB._TABLEWARE, null, null);
    }


    //데이터 선택
    public Cursor selectColumns(){
        return mDB.query(DB_table.CreateDB._TABLEWARE,  null, null, null, null, null, null);
    }

    public int selectNameColumns(){
        Cursor c = mDB.rawQuery("SELECT * FROM running", null);
        return c.getCount();
    }

    public static SQLiteDatabase executeRawQuery() {

        mDB = mDBHelper.getReadableDatabase();
        return mDB;

    }
}