package org.techtown.final_exam_project;

import android.provider.BaseColumns;

public class DB_table {
    //데이터베이스 테이블 구성(데이터베이스의 틀 구성)
    public static final class CreateDB implements BaseColumns {
        public static final String _TABLEWARE = "running";     //테이블 이름
        public static final String DATE = "date";
        public static final String DISTANCE = "distance";
        public static final String AVERAGE_SPEED = "average_speed";
        public static final String RUNNING_TYPE = "running";
        public static final String _CREATE0 = "create table if not exists "+ _TABLEWARE +"("
                +_ID+" integer primary key autoincrement, "
                +RUNNING_TYPE+" text not null, "
                +DATE+" text not null , "
                +DISTANCE+"  not null ,"
                +AVERAGE_SPEED+" not null); ";
    }
}
