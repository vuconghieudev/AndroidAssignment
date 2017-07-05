package advanced.android.com.hackernews.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hieu on 29-Jun-17.
 */

public class CreateDatabase extends SQLiteOpenHelper {

    public static String TB_STORIES="STORIES";
    public static String TB_STORIES_ID="ID";
    public static String TB_STORIES_BY="BY";
    public static String TB_STORIES_DESCENDANTS="DESCENDANTS";
    public static String TB_STORIES_IDSTORY="IDSTORY";
    public static String TB_STORIES_SCORE="SCORE";
    public static String TB_STORIES_TIME="TIME";
    public static String TB_STORIES_TITLE="TITLE";
    public static String TB_STORIES_TYPE="TYPE";
    public static String TB_STORIES_KIDS="KIDS";
    public static String TB_STORIES_URL="URL";

    public static String TB_KIDS="KIDS";
    public static String TB_KIDS_ID= "ID";
    public static String TB_KIDS_IDSTORY="IDSTORY";
    public static String TB_KIDS_KID="KID";

    public CreateDatabase(Context context) {
        super(context,"HackerNews",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbSTORIES= "CREATE TABLE "+TB_STORIES+ " ( "+TB_STORIES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TB_STORIES_BY + " TEXT , "
                + TB_STORIES_DESCENDANTS +" INTEGER , "
                + TB_STORIES_IDSTORY + " TEXT ,"
                + TB_STORIES_SCORE + " INTEGER ,"
                + TB_STORIES_TITLE + " TEXT ,"
                + TB_STORIES_KIDS + " INTEGER ,"
                + TB_STORIES_TIME + " TEXT ,"
                + TB_STORIES_TYPE + " TEXT ,"
                + TB_STORIES_URL + " TEXT )";

        String tbKIDS= "CREATE TABLE "+TB_KIDS+ " ( "+TB_KIDS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TB_KIDS_IDSTORY + " TEXT ,"
                + TB_KIDS_KID + " TEXT )";

        db.execSQL(tbSTORIES);
        db.execSQL(tbKIDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }
}
