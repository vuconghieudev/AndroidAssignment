package advanced.android.com.hackernews.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import advanced.android.com.hackernews.DAO.StoriesDAO;
import advanced.android.com.hackernews.DTO.StoriesDTO;
import advanced.android.com.hackernews.MainActivity;
import advanced.android.com.hackernews.R;

/**
 * Created by Hieu on 30-Jun-17.
 */

public class StoriesAdapter extends BaseAdapter implements View.OnClickListener{
    Context context ;
    int layout;
    List<StoriesDTO> storiesDTOList;
    StoriesDAO storiesDAO;
    viewHolderStories viewHolderStories;


    public  StoriesAdapter(Context context , int layout, List<StoriesDTO> storiesDTOList){
        this.context = context;
        this.layout = layout;
        this.storiesDTOList = storiesDTOList;
        storiesDAO = new StoriesDAO(context);
    }
    public void appendList(List<StoriesDTO>pList) {
        if (storiesDTOList== null) {
            storiesDTOList= new ArrayList<>();
        }
        if (pList != null) {
            storiesDTOList.addAll(pList);
            notifyDataSetChanged();
        }
    }
    @Override
    public int getCount() {
        return storiesDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return storiesDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return storiesDTOList.get(position).getId();
    }
    public class viewHolderStories{
        TextView txtSTT,txtScore,txtTitle,txtNameURL,txtDatime,txtByUser,txtCountUserComment;
        ImageButton btnComment,btnBrower;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view ==null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderStories =  new viewHolderStories();
            view = inflater.inflate(R.layout.item_main_layout,parent,false);
            viewHolderStories.txtSTT = (TextView) view.findViewById(R.id.txtSTT);
            viewHolderStories.txtScore = (TextView) view.findViewById(R.id.txtScore);
            viewHolderStories.txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            viewHolderStories.txtNameURL = (TextView) view.findViewById(R.id.txtNameURL);
            viewHolderStories.txtDatime = (TextView) view.findViewById(R.id.txtDatime);
            viewHolderStories.txtByUser = (TextView) view.findViewById(R.id.txtByUser);
            viewHolderStories.txtCountUserComment = (TextView) view.findViewById(R.id.txtCountUserComment);
            viewHolderStories.btnComment = (ImageButton) view.findViewById(R.id.btnComment);
            viewHolderStories.btnBrower = (ImageButton) view.findViewById(R.id.btnBrower);
            viewHolderStories.btnBrower.setOnClickListener(this);
            viewHolderStories.btnComment.setOnClickListener(this);
            view.setTag(viewHolderStories);
        }else {
            viewHolderStories = (viewHolderStories) view.getTag();
        }
        StoriesDTO storiesDTO = storiesDTOList.get(position);
        viewHolderStories.txtSTT.setText(String.valueOf(storiesDTO.getId()));
        viewHolderStories.txtScore.setText(String.valueOf(storiesDTO.getScore()));
        viewHolderStories.txtTitle.setText(storiesDTO.getTitle());
        viewHolderStories.txtNameURL.setText(storiesDTO.getUrl());
        viewHolderStories.txtDatime.setText(storiesDTO.getTime());
        viewHolderStories.txtByUser.setText(storiesDTO.getBy());
        viewHolderStories.txtCountUserComment.setText(String.valueOf(storiesDTO.getKids()));

        viewHolderStories.btnBrower.setTag(position);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBrower:
                int position = (int) viewHolderStories.btnBrower.getTag();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(storiesDTOList.get(position).getUrl()));
                ((MainActivity)context).startActivity(intent);
                break;
            case R.id.btnComment:
                break;
        }
    }
}
