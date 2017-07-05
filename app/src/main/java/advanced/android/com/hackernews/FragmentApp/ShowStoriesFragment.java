package advanced.android.com.hackernews.FragmentApp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import advanced.android.com.hackernews.Adapter.StoriesAdapter;
import advanced.android.com.hackernews.DAO.StoriesDAO;
import advanced.android.com.hackernews.DTO.StoriesDTO;
import advanced.android.com.hackernews.R;

/**
 * Created by Hieu on 29-Jun-17.
 */

public class ShowStoriesFragment extends Fragment {
    String UrlTopStories=  "https://hacker-news.firebaseio.com/v0/topstories.json";
    String url_start_story = "https://hacker-news.firebaseio.com/v0/item/";
    String url_end_story=".json";
    StoriesDAO storiesDAO;


    ListView listView;
    StoriesAdapter storiesAdapter;
    ArrayList<StoriesDTO> arrayS;

    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_stories_layout,container,false);
        storiesDAO =  new StoriesDAO(getActivity());



        listView = (ListView) view.findViewById(R.id.list_view_show_stories);
        progressDialog = new ProgressDialog(getActivity());

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);


        boolean checkData = storiesDAO.checkExistData();
        if(checkData){
            arrayS = storiesDAO.GetData();
            storiesAdapter = new StoriesAdapter(getActivity(),R.layout.item_main_layout,arrayS);
            listView.setAdapter(storiesAdapter);
            storiesAdapter.notifyDataSetChanged();
        }else {
            new DownldStories().execute();
            arrayS = storiesDAO.GetData();
            storiesAdapter = new StoriesAdapter(getActivity(),R.layout.item_main_layout,arrayS);
            listView.setAdapter(storiesAdapter);
            storiesAdapter.notifyDataSetChanged();
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(), "onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(getActivity(), "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity(), "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getActivity(), "onPasue", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getActivity(), "onDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        Toast.makeText(getActivity(), "onStop", Toast.LENGTH_SHORT).show();
    }

    class DownldStories extends AsyncTask<Void,String,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String update = values[0];
            progressDialog.setMessage("Loading +"+update);
        }

        @Override
        protected Void doInBackground(Void... params) {
            String resArray="";
            HttpURLConnection connection = null;
            InputStream inputStream =null;
            InputStreamReader inputStreamReader = null;
            int index= 0;
            try {
                URL urlID= new URL(UrlTopStories);
                connection = (HttpURLConnection) urlID.openConnection();
                inputStream= connection.getInputStream();
                inputStreamReader =new InputStreamReader(inputStream);
                int data =inputStreamReader.read();

                while (data!=-1){
                    char c = (char) data;
                    resArray +=c;
                    data = inputStreamReader.read();
                }

                JSONArray jsonArray =new JSONArray(resArray);

                for (int i = 0 ; i<= jsonArray.length();i++){
                    String id = jsonArray.getString(i);
                    URL urlArline =new URL(url_start_story+ id + url_end_story);
                    connection = (HttpURLConnection) urlArline.openConnection();
                    inputStream = connection.getInputStream();
                    inputStreamReader = new InputStreamReader(inputStream);
                    String jsonObjectString = "";
                    data = inputStreamReader.read();
                    while (data!=-1){
                        char c = (char) data;
                        jsonObjectString +=c;
                        data = inputStreamReader.read();
                    }
                    JSONObject jsonObject = new JSONObject(jsonObjectString);


                    StoriesDTO storiesDTO = new StoriesDTO();
                    String idStory="";
                    if(jsonObject.has("id")){
                        idStory = jsonObject.getString("id");
                        storiesDTO.setIdStory(idStory);
                    }
                    if(jsonObject.has("by")){
                        storiesDTO.setBy(jsonObject.getString("by"));
                    }
                    if(jsonObject.has("descendants")){
                        storiesDTO.setDescendants(jsonObject.getInt("descendants"));
                    }
                    if(jsonObject.has("score")){
                        storiesDTO.setScore(jsonObject.getInt("score"));
                    }
                    if(jsonObject.has("time")){
                        storiesDTO.setTime(jsonObject.getString("time"));
                    }
                    if(jsonObject.has("title")){
                        storiesDTO.setTitle(jsonObject.getString("title"));
                    }if(jsonObject.has("type")){
                        storiesDTO.setType(jsonObject.getString("type"));
                    }
                    if (jsonObject.has("url")){
                        storiesDTO.setUrl(jsonObject.getString("url"));
                    }
                    if(jsonObject.has("kids")){
                        if(jsonObject.has("kids")){
                            JSONArray jsonArrayKids =  jsonObject.getJSONArray("kids");
                            int leng = jsonArrayKids.length();
                            storiesDTO.setKids(leng);
                            Log.d("sum",leng+"");
                        }else {
                            storiesDTO.setKids(0);
                            Log.d("sum",0+"");
                        }

                    }
                    publishProgress(index + " articles");
                    index++;
                    storiesDAO.AddStory(storiesDTO);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();
        }


    }

}
