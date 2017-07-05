package advanced.android.com.hackernews.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import advanced.android.com.hackernews.DTO.StoriesDTO;
import advanced.android.com.hackernews.Database.CreateDatabase;

/**
 * Created by Hieu on 29-Jun-17.
 */

public class StoriesDAO {
    SQLiteDatabase database;
    public StoriesDAO (Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }
    public void AddStory(StoriesDTO storiesDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_STORIES_BY,storiesDTO.getBy());
        contentValues.put(CreateDatabase.TB_STORIES_DESCENDANTS,storiesDTO.getDescendants());
        contentValues.put(CreateDatabase.TB_STORIES_IDSTORY,storiesDTO.getIdStory());
        contentValues.put(CreateDatabase.TB_STORIES_SCORE,storiesDTO.getScore());
        contentValues.put(CreateDatabase.TB_STORIES_TIME,storiesDTO.getTime());
        contentValues.put(CreateDatabase.TB_STORIES_TITLE,storiesDTO.getTitle());
        contentValues.put(CreateDatabase.TB_STORIES_TYPE,storiesDTO.getType());
        contentValues.put(CreateDatabase.TB_STORIES_KIDS,storiesDTO.getKids());
        contentValues.put(CreateDatabase.TB_STORIES_URL,storiesDTO.getUrl());

        database.insert(CreateDatabase.TB_STORIES,null,contentValues);

    }

    public ArrayList<StoriesDTO> GetData(){
        ArrayList<StoriesDTO> storiesDTOarr = new ArrayList<>();
        String query = " SELECT * FROM "+ CreateDatabase.TB_STORIES;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            StoriesDTO storiesDTO = new StoriesDTO();
            storiesDTO.setId(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_STORIES_ID)));
            storiesDTO.setBy(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_STORIES_BY)));
            storiesDTO.setDescendants(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_STORIES_DESCENDANTS)));
            storiesDTO.setIdStory(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_STORIES_IDSTORY)));
            storiesDTO.setScore(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_STORIES_SCORE)));
            storiesDTO.setKids(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_STORIES_KIDS)));
            storiesDTO.setTime(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_STORIES_TIME)));
            storiesDTO.setTitle(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_STORIES_TITLE)));
            storiesDTO.setUrl(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_STORIES_URL)));
            storiesDTO.setType(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_STORIES_TYPE)));
            storiesDTOarr.add(storiesDTO);
            cursor.moveToNext();
        }
        return storiesDTOarr;
    }
    public boolean checkExistData(){
        String query = " SELECT * FROM "+CreateDatabase.TB_STORIES;
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.getCount()!=0){
            return true;
        }else {
            return false;
        }

    }
}
