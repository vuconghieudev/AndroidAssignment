package advanced.android.com.hackernews.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import advanced.android.com.hackernews.DTO.KidsDTO;
import advanced.android.com.hackernews.Database.CreateDatabase;

/**
 * Created by Hieu on 29-Jun-17.
 */

public class KidsDAO {
    SQLiteDatabase database;
    public KidsDAO(Context context){
        CreateDatabase createDatabase=  new CreateDatabase(context);
        database = createDatabase.open();

    }
    public void AddKid(KidsDTO kidsDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_KIDS_IDSTORY,kidsDTO.getIdStory());
        contentValues.put(CreateDatabase.TB_KIDS_KID,kidsDTO.getKid());

        database.insert(CreateDatabase.TB_KIDS,null,contentValues);
    }
}
