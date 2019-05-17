package com.free.app.spp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.free.app.spp.justloginregistertest.User;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class MyGameFragment extends Fragment {
    private View view;
    private List<MyGame> mData = new ArrayList<>();
    private List<MyGame> myAdministrationMatch = new ArrayList<>();
    private ListView myGames;
    private String UserName;
    private JSONArray json;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent i = getActivity().getIntent();
        UserName = i.getStringExtra("UserName");
        view = inflater.inflate(R.layout.fragment_my_game, container, false);
        super.onCreate(savedInstanceState);
        myGames = view.findViewById(R.id.my_game_list);
        getAllSchedule();
        myGames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), MyGameActivity.class);
                i.putExtra("123",mData.get(position));
                i.putExtra("UserName",UserName);
                startActivity(i);
            }
        });
        Button createGameButton = view.findViewById(R.id.add_new_game_button);
        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MyGameCreatorActivity.class);
                startActivityForResult(i,100);
            }
        });
        return view;
    }

    private void getAllSchedule(){
        Http <JSONArray> h = new Http<>();
        h.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException, IOException {
                System.out.println("124erw2wef");
                json = jsonArray;
                for(int i = 0;i < json.length();i++){
                    JSONObject j = json.getJSONObject(i);
                    String name = j.getString("名称");
                    String intro = j.getString("简介");
                    String time = j.getString("时间");
                    String id = j.getString("id");
                    mData.add(new MyGame(name,time,intro,id,null));
                }
                MyGameAdapter myGameAdapter = new MyGameAdapter(mData, view.getContext());
                myGames.setAdapter(myGameAdapter);
            }
        });
        h.execute("GetAllSchedule",UserName);
    }

    private void getMySchedule(){
        Http <JSONArray> h = new Http<>();
        h.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException, IOException {
                System.out.println("124erw2wef");
                json = jsonArray;
                for(int i = 0;i < json.length();i++){
                    JSONObject j = json.getJSONObject(i);
                    j = j.getJSONObject("赛程");
                    String name = j.getString("名称");
                    String intro = j.getString("简介");
                    String time = j.getString("创建时间");
                    String id = j.getString("id");
                    myAdministrationMatch.add(new MyGame(name,time,intro,id,null));
                }
            }
        });
        h.execute("GetMySchedule",UserName);
    }

    private void POSTMySchedule(String time,String matchName,String intro,ArrayList<String> admin){
        Http <JSONArray> h = new Http<>();
        h.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException, IOException {
                System.out.println("wffewfdsfe");
            }
        });
        String s = "";
        for (String ss:admin) {
            if(s.contentEquals(""))s = s  + ss;
            else s = s + "+" + ss;
        }
        h.execute("POSTMySchedule",UserName,time,matchName,intro,s);
    }



    public void onActivityResult ( int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //这里要通过请求码来判断数据的来源
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    String name = data.getStringExtra("name");
                    String intro = data.getStringExtra("intro");
                    String date = data.getStringExtra("date");
                    ArrayList<String>admins = data.getStringArrayListExtra("admin");
                    mData.add(new MyGame(name,date,intro,"50",admins));
                    MyGameAdapter myGameAdapter = new MyGameAdapter(mData, view.getContext());
                    myGames.setAdapter(myGameAdapter);
                    POSTMySchedule(date,name,intro,admins);
                }
                break;
            default:
        }
    }
}

class MyGameAdapter extends BaseAdapter {
    private List mData;
    private Context mContext;

    MyGameAdapter(List mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.mygame_item, parent, false);
        TextView gameName = convertView.findViewById(R.id.my_game_name);
        MyGame myGame = (MyGame) mData.get(position);
        gameName.setText(myGame.getGameName());
        LikeButton likeButton = convertView.findViewById(R.id.my_team_star_button);
        likeButton.setLiked(false);
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });
        return convertView;
    }
}
