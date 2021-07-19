package com.byted.camp.todolist.db;

import android.provider.BaseColumns;

public final class TodoContract  {

    //  1. 定义创建数据库以及升级的操作

    public static final String CREATE_TABLE_SQL=String.format("create table %s " +
            "( %s INTEGER PRIMARY KEY AUTOINCREMENT ," +
            "  %s INTEGER, " +
            "  %s INTEGER, " +
            "  %s TEXT, " +
            "  %s INTEGER);",TodoNote.TABLE_NAME,TodoNote._ID,TodoNote.COLUMN_DATE,TodoNote.COLUMN_STATE,TodoNote.COLUMN_CONTENT,TodoNote.COLUMN_PRIORITY);

    public static final String ADD_PRIORITY_COLUMN_SQL=String.format("alter table %s add %s INTEGER;",TodoNote.TABLE_NAME,TodoNote.COLUMN_PRIORITY);


    public static final String SORT_ORDER_PRIORITY_AND_ID=String.format(" %s desc, %s asc",TodoNote.COLUMN_PRIORITY,TodoNote._ID);
    private TodoContract() {
    }

    public static class TodoNote implements BaseColumns {
        //  2.此处定义表名以及列明
        public static final String TABLE_NAME="todo_note";
        public static final String COLUMN_DATE="date";
        public static final String COLUMN_STATE="state";
        public static final String COLUMN_CONTENT="content";
        public static final String COLUMN_PRIORITY="priority";


    }

}
