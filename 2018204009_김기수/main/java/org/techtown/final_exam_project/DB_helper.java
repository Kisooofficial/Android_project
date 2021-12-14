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
    private static final String DATABASE_NAME = "running.db"; //생성될 데이터베이스파일 이름
    private static final int DATABASE_VERSION = 2; //생성되는 데이터베이스의 버전
    public static SQLiteDatabase mDB; // 데이터베이스 생성과 변경을 해주는 SQLiteDataBase 생성
    public static DatabaseHelper mDBHelper; // SQLiteOpenHelper를 상속받는 DatabaseHelper 객체 생성
    private Context context;

    //데이터베이스헬퍼 정의 (SQLiteOpenHelper 를 상속 받음)
    private class DatabaseHelper extends SQLiteOpenHelper {

        //생성자
        public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //테이블 생성 쿼리
            db.execSQL(DB_table.CreateDB._CREATE0);
        }


        @Override
        // 데이터베이스 버전을 업그레이드 하는 경우
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_table.CreateDB._TABLEWARE);
            onCreate(db);
        }
    }

    //생성자
    public DB_helper(Context context) {
        this.context = context;
    }

    //데이터베이스를 사용하기 위한 준비
    //이 메소드를 사용하는 곳으로 예외 책임을 전가(예외 던지기 - throws)
    public DB_helper open() throws SQLException {
        mDBHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase(); // 데이터베이스를 쓸 수 있도록 메서드 실행
        return this;
    }

    public void create() {
        mDBHelper.onCreate(mDB);
    } // 데이터베이스 테이블 생성

    public void close() {
        mDB.close();
    } //데이터베이스 사용 종료

    //데이터 삽입
    public long insertColum(Date date, String running_type, float distance, double average_speed) {
        //값 집합을 저장하도록 도와주는 ContentValues 객체 생성
        ContentValues values = new ContentValues();
        //measuring 클래스에서 넘어오는 값들의 집합을 모두 넣는다
        values.put(DB_table.CreateDB.DATE, String.valueOf(date));
        values.put(DB_table.CreateDB.RUNNING_TYPE, running_type);
        values.put(DB_table.CreateDB.DISTANCE, distance);
        values.put(DB_table.CreateDB.AVERAGE_SPEED, average_speed);
        return mDB.insert(DB_table.CreateDB._TABLEWARE, null, values);
    }

    //데이터 갱신
    public boolean updateColumn(Date date, String running_type, float distance, double average_speed) {
        //ContentValue 이용하여 러닝 기록 데이터 수정
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
    
    public static SQLiteDatabase executeRawQuery() {

        mDB = mDBHelper.getReadableDatabase();
        return mDB;

    }
}