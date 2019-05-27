package com.free.app.spp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;

import static android.app.Activity.RESULT_OK;

public class MyGameFragment extends Fragment {
    /*
     This fragment builds the screen of "my game" general screen.
     It includes create-button, "my game" list.
     */
    private View view;
    private List<MyGame> mData = new ArrayList<>();
    private List<MyGame> myAdministrationMatchList = new ArrayList<>();
    private List<MyGame> myLikedMatchList = new ArrayList<>();
    private ListView myGames;
    private String UserName;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent i = getActivity().getIntent();
        UserName = i.getStringExtra("UserName");
        view = inflater.inflate(R.layout.fragment_my_game, container, false);
        super.onCreate(savedInstanceState);
        myGames = view.findViewById(R.id.my_game_list);
        getLikedGame();
        getMySchedule();
        getAllSchedule();

        myGames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), MyGameActivity.class);
                i.putExtra("123", mData.get(position));
                i.putExtra("UserName", UserName);
                startActivity(i);
            }
        });

        FButton createGameButton = view.findViewById(R.id.add_new_game_button);
        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MyGameCreatorActivity.class);
                i.putExtra("UserName", UserName);
                startActivityForResult(i, 100);
            }
        });

        RefreshLayout refreshLayout = view.findViewById(R.id.my_game_fragment_refresh);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                getLikedGame();
                getMySchedule();
                getAllSchedule();
                refreshlayout.finishRefresh(2000);
            }
        });
        return view;
    }

    private void getAllSchedule() {
        Http<JSONArray> h = new Http<>();
        h.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException, IOException {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject j = jsonArray.getJSONObject(i);
                    String name = j.getString("名称");
                    String intro = j.getString("简介");
                    String time = j.getString("时间");
                    String id = j.getString("id");
                    MyGame newGame = new MyGame(name, time, intro, id);
                    for (MyGame mg : myLikedMatchList) {
                        if (mg.getId().contentEquals(id)) newGame.setIs_liked(true);
                    }
                    for (MyGame mg : myAdministrationMatchList) {
                        if (mg.getId().contentEquals(id)) newGame.setIs_administrator();
                    }
                    mData.add(newGame);
                }
                MyGameAdapter myGameAdapter = new MyGameAdapter(mData, view.getContext(), UserName);
                myGames.setAdapter(myGameAdapter);
            }
        });
        h.execute("GetAllSchedule", UserName);
    }

    private void getMySchedule() {
        Http<JSONArray> h = new Http<>();
        h.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException, IOException {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject j = jsonArray.getJSONObject(i);
                    j = j.getJSONObject("赛程");
                    String name = j.getString("名称");
                    String intro = j.getString("简介");
                    String time = j.getString("创建时间");
                    String id = j.getString("id");
                    myAdministrationMatchList.add(new MyGame(name, time, intro, id));
                }
            }
        });
        h.execute("GetMySchedule", UserName);
    }

    private void POSTMySchedule(String time, String matchName, String intro, ArrayList<String> admin) {
        Http<JSONArray> h = new Http<>();
        h.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException, IOException {
                System.out.println("wffewfdsfe");
            }
        });
        StringBuilder s = new StringBuilder();
        for (String ss : admin) {
            if (s.toString().contentEquals("")) s.append(ss);
            else s.append("+").append(ss);
        }
        h.execute("POSTMySchedule", UserName, time, matchName, intro, s.toString());
    }

    private void getLikedGame() {
        Http<JSONArray> h = new Http<>();
        h.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject j = jsonArray.getJSONObject(i);
                    j = j.getJSONObject("赛程");
                    String name = j.getString("名称");
                    String intro = j.getString("简介");
                    String time = j.getString("创建时间");
                    String id = j.getString("id");
                    myLikedMatchList.add(new MyGame(name, time, intro, id));
                }

            }
        });
        h.execute("GetSubforgame", UserName);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //这里要通过请求码来判断数据的来源
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra("name");
                String intro = data.getStringExtra("intro");
                String date = data.getStringExtra("date");
                ArrayList<String> admins = data.getStringArrayListExtra("admin");
                POSTMySchedule(date, name, intro, admins);
                mData.add(new MyGame(name, date, intro, "50"));
                MyGameAdapter myGameAdapter = new MyGameAdapter(mData, view.getContext(), UserName);
                myGames.setAdapter(myGameAdapter);
            }
        }
    }
}

class MyGameAdapter extends BaseAdapter {
    private List mData;
    private Context mContext;
    private String UserName;

    MyGameAdapter(List mData, Context mContext, String userName) {
        this.mData = mData;
        this.mContext = mContext;
        this.UserName = userName;
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

    private void POSTAllSchedule(String schedule_id) {
        Http<JSONArray> h = new Http<>();
        h.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException, IOException {
                System.out.println("Successfully fix attention!");
            }
        });
        h.execute("POSTAllSchedule", schedule_id, UserName);
    }

    private void POSTSubforgame(String schedule_id) {
        Http<JSONArray> h = new Http<>();
        h.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException, IOException {
                System.out.println("Successfully cancel attention!");
            }
        });
        h.execute("POSTSubforgame", schedule_id, UserName);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.mygame_item, parent, false);
        TextView gameName = convertView.findViewById(R.id.my_game_name);
        final MyGame myGame = (MyGame) mData.get(position);
        gameName.setText(myGame.getGameName());
        LikeButton likeButton = convertView.findViewById(R.id.my_team_star_button);
        if (myGame.getIs_liked() || myGame.getIs_administrator()) likeButton.setLiked(true);
        else likeButton.setLiked(false);
        if (!myGame.getIs_administrator()) likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Toast.makeText(mContext, "成功关注", Toast.LENGTH_LONG).show();
                POSTAllSchedule(myGame.getId());
                myGame.setIs_liked(true);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if (!myGame.getIs_administrator()) {
                    Toast.makeText(mContext, "成功取消关注", Toast.LENGTH_LONG).show();
                    POSTSubforgame(myGame.getId());
                    myGame.setIs_liked(false);
                }
            }
        });
        return convertView;
    }
}
