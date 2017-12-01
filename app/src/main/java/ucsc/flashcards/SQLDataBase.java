package ucsc.flashcards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kohl Grunt on 11/11/2017.
 */

public class SQLDataBase extends SQLiteOpenHelper {

    // Singleton Implementation variables
    private static SQLDataBase sInstance;

    public static final String DATABASE_NAME = "Student.db";
    public static final String DATABASE_TABLE = "table_name";

    // There will be 3 tables in this database, set up in a one-to-many configuration
    public static final String CLASS_TABLE = "class_table";
    public static final String CHAPTER_TABLE = "chapter_table";
    public static final String CARD_TABLE = "card_table";

    // Class table column names
    public static final String ClassID = "CLASSID"; //column 0
    public static final String ClassName = "CLASSNAME";

    // Chapter table column names
    public static final String ChapterID = "CHAPTERID";
    public static final String ClassMany  = "CLASSMANY";
    public static final String ChapterName = "CHAPTERNAME";

    // Flashcard table column names
    public static final String CardID = "CARDID";
    public static final String ChapterMany = "CHAPTERMANY";
    public static final String FC_Front = "FCFront";
    public static final String FC_Back = "FCBack";
    public static final String FC_Diff = "FCDiff"; // Difficulty of the flashcard



    public SQLDataBase(Context context){ // is this supposed to be public?
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create class table
        db.execSQL("create table " + CLASS_TABLE + " (" +
                ClassID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ClassName + " TEXT)");
        // create chapter table
        db.execSQL("create table " + CHAPTER_TABLE + " (" +
                ChapterID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ClassMany + " INTEGER," +
                //"FOREIGN KEY (" + ClassMany + ") REFERENCES " + CLASS_TABLE + " (" + ClassID + ")" + // connects the two tables
                //"ON DELETE SET NULL," +
                ChapterName + " TEXT)");  // create card table
        db.execSQL("create table " + CARD_TABLE + " (" +
                CardID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ChapterMany + " INTEGER," +
                //"FOREIGN KEY (" + ChapterMany + ") REFERENCES " + CHAPTER_TABLE + " (" + ChapterID + ")" + // connects the two tables
                //"ON DELETE SET NULL," +
                FC_Front + " TEXT," +
                FC_Back + " TEXT," +
                //FC_Order + "," +
                FC_Diff + " INTEGER)");
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    // Singleton Implementation
    public static synchronized SQLDataBase getInstance(Context context){
        if(sInstance == null){
            sInstance = new SQLDataBase(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // MAke this do something, I guess\
        onCreate(db);
    }





    public boolean insertClass(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ClassName, name);
        long result = db.insert(CLASS_TABLE, null, contentValues);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }


    public boolean insertChapter(String name, int parentClassID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ClassMany, parentClassID);
        contentValues.put(ChapterName, name);
        long result = db.insert(CHAPTER_TABLE, null, contentValues);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }


    public boolean insertCard(String front, String back, int parentChapterID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ChapterMany, parentChapterID);
        contentValues.put(FC_Front, front);
        contentValues.put(FC_Back, back);
        contentValues.put(FC_Diff, 5); // just setting base difficulty to 5 (highest difficulty)
        long result = db.insert(CARD_TABLE, null, contentValues);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }





    public Cursor getClasses() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + CLASS_TABLE, null);
    }

    public Cursor getChapters(int parentClassID) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT " + ChapterID + "," + ChapterName +
                            " FROM " + CHAPTER_TABLE +
                            " WHERE " + ClassMany + " = '" + parentClassID + "'", null);
    }

    public Cursor getCards(int parentChapterID) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT " + FC_Front + "," + FC_Back + "," + FC_Diff + "," + CardID +
                " FROM " + CARD_TABLE +
                " WHERE " + ChapterMany + " = '" + parentChapterID + "'", null);
    }


    // Change the difficulty of each card
    public boolean changeDiff(boolean correct, int cardID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor cursor = db.rawQuery("SELECT " + FC_Diff +
                " FROM " + CARD_TABLE +
                " WHERE " + CardID + " = '" + cardID + "'", null);
        int difficulty;
        if(cursor.moveToNext()){
            difficulty = cursor.getInt(0);
        } else {
            return(false);
        }
        // 0 < Difficulty <= 5
        // Increment if user gets card right or wrong and if the difficulty is still in the range
        try{
            if (correct && (difficulty > 0)) {
                difficulty--;
            } else if(difficulty <= 5){
                difficulty++;
            }
            db.execSQL("UPDATE " + CARD_TABLE +
                    " SET " + FC_Diff + " = " + difficulty +
                    " WHERE " + CardID + " = " + cardID);
            return(true);
        } catch (SQLException mSQLException) {
            return(false);
        }
    }



    public void deleteClass(int classID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + CLASS_TABLE + " WHERE " + ClassID + " = " + classID);
    }

    public void deleteChapter(int chapterID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + CHAPTER_TABLE + " WHERE " + ChapterID + " = " + chapterID);
    }

    public void deleteCard(int cardID){
        System.out.println("OUT::::::::::"+cardID);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + CARD_TABLE + " WHERE " + CardID + " = " + cardID);
    }





    public void ClearDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + CLASS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CHAPTER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CARD_TABLE);
        // create class table
        db.execSQL("create table " + CLASS_TABLE + " (" +
                ClassID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ClassName + " TEXT)");
        db.execSQL("create table " + CHAPTER_TABLE + " (" +
                ChapterID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ClassMany + " INTEGER," +
                ChapterName + " TEXT)");//," +
        //"FOREIGN KEY (" + ClassID + ") REFERENCES (" + CLASS_TABLE + "(" + ClassID + ")))"); // connects the two tables
        // create card table
        db.execSQL("create table " + CARD_TABLE + " (" +
                CardID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ChapterMany + " INTEGER," +
                FC_Front + " TEXT," +
                FC_Back + " TEXT," +
                //FC_Order + "," +
                FC_Diff + " INTEGER)");
    }
}
