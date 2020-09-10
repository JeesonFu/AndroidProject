package cn.edu.bistu.notebook.note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDB extends SQLiteOpenHelper {

    private final static String DB_NAME = "notesdb";
    private final static int DB_VERSION = 1;

    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_CONTENT = "content";

    //建表，删表
    private final static String SQL_CREATE_DB = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME_DATE + " TEXT NOT NULL,"
            + COLUMN_NAME_CONTENT + " TEXT NOT NULL)";
    private final static String SQL_DELETE_DB = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public NotesDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
