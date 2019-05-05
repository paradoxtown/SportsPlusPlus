package com.free.app.spp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class TeamActivity extends AppCompatActivity {
    private ArrayList<Team> items = new ArrayList<>();
    private AllMap mp = new AllMap();
    private JSONArray ret;
    private String cnName, enName, userName;
    private LikeButton likeButton;
    boolean isLiked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        items.add(new Team("球员", ""));
        items.add(new Team("数据", ""));
        //items.add(new Team("赛程", ""));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        this.setTitle("选项");
        GetTeamInfo();
        Intent i = getIntent();
        likeButton = findViewById(R.id.star_button);
        cnName = i.getStringExtra("cnName");
        enName = mp.getEnNameFromCnName(cnName);
        userName = i.getStringExtra("UserName");
        isLiked = i.getBooleanExtra("isLiked", false);
        System.out.println("isLiked in Activity " + isLiked);
        likeButton.setLiked(isLiked);
        TextView name_cn = findViewById(R.id.team_cn);
        name_cn.setText(Maps.CntoFull.get(cnName));
        name_cn.setTextSize(25);
        name_cn.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        name_cn.setTextColor(Color.WHITE);
        TextView name_eng = findViewById(R.id.team_eng);
        name_eng.setText(Maps.CntoEng.get(cnName));
        name_eng.setTextSize(20);
        name_eng.setTextColor(Color.WHITE);
        GridView itemsView = findViewById(R.id.itemsview);
        ImageView img = findViewById(R.id.team_img);
        img.setImageResource(mp.getDrawableId(cnName));
        TeamAdapter adp = new TeamAdapter(TeamActivity.this, R.layout.team_item, items, true);
        itemsView.setAdapter(adp);
        itemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                String name = intent.getStringExtra("cnName");
                Team item = items.get(position);
                switch (item.getName()) {
                    case "球员":
                        intent = new Intent(TeamActivity.this, StaffActivity.class);
                        break;
                    case "数据":
                        intent = new Intent(TeamActivity.this, DataActivity.class);
                        break;
                    //case "赛程": intent = new Intent(TeamActivity.this,ArrangeActivity.class);break;
                    default:
                }
                intent.putExtra("cnName", name);
                startActivity(intent);
            }
        });

        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                postLikedTeam();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteLikedTeam();
            }
        });
    }

    private void postLikedTeam() {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                if (String.valueOf(jsonArray.getJSONObject(0).get("result")).equals("1")) {
                    Toast.makeText(TeamActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TeamActivity.this, "关注失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        http.execute("RequestPost", userName, cnName);
    }

    private void deleteLikedTeam() {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException, IOException {
                if (String.valueOf(jsonArray.getJSONObject(0).get("result")).equals("1")) {
                    Toast.makeText(TeamActivity.this, "取关成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TeamActivity.this, "取关失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        http.execute("RequestDelete", userName, cnName);
    }

    private void GetTeamInfo() {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray TeamInfo) throws JSONException {
            // TeamListFragment.this.initTeams(TeamInfo);
            ret = TeamInfo;
            Handle();
            }
        });
        http.execute("GetTeamInfo", "");
    }

    private void Handle() throws JSONException {
        Intent in = getIntent();
        for (int i = 0; i < ret.length(); i++) {
            JSONObject j = ret.getJSONObject(i);
            if (j.getString("球队名").contentEquals(Maps.CntoEng.get(cnName))) {
                String intro = j.getString("介绍");
                TextView team_intro = findViewById(R.id.team_intro);
                team_intro.setText(intro);
                break;
            }
        }
    }

}

//class TeamFragmentPagerAdapter extends FragmentPagerAdapter
//{
//    private final int PAGER_COUNT = 4;
//    private Fragment f1 = null;
//    private Fragment f2 = null;
//    private Fragment f3 = null;
//    private Fragment f4 = null;
//
//    public TeamFragmentPagerAdapter(FragmentManager fm) {
//        super(fm);
//        myFragment1 = new MyFragment1();
//        myFragment2 = new MyFragment2();
//        myFragment3 = new MyFragment3();
//        myFragment4 = new MyFragment4();
//    }
//}
