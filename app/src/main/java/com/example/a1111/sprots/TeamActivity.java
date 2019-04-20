package com.example.a1111.sprots;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class TeamActivity extends AppCompatActivity {
    // private ArrayList<String>items = new ArrayList<>();
    private ArrayList<Team> items = new ArrayList<>();
    private AllMap mp = new AllMap();
    private JSONArray ret;
    private String name_c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        items.add(new Team("球员", ""));
        items.add(new Team("数据", ""));
        //items.add(new Team("赛程", ""));
        //items.add(new Team("关注", ""));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        this.setTitle("选项");
        GetTeamInfo();
        Intent i = getIntent();
        name_c = i.getStringExtra("123");
        TextView name_cn = findViewById(R.id.team_cn);
        name_cn.setText(Maps.CntoFull.get(name_c));
        name_cn.setTextSize(25);
        name_cn.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        name_cn.setTextColor(Color.WHITE);
        TextView name_eng = findViewById(R.id.team_eng);
        name_eng.setText(Maps.CntoEng.get(name_c));
        name_eng.setTextSize(20);
        name_eng.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        name_eng.setTextColor(Color.WHITE);
        GridView itemsView = findViewById(R.id.itemsview);
        ImageView img = findViewById(R.id.team_img);
        img.setImageResource(mp.getDrawableId(name_c));
        // ArrayAdapter<String> adp = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);
        TeamAdapter adp = new TeamAdapter(TeamActivity.this, R.layout.team_item, items, true);
        itemsView.setAdapter(adp);
        itemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                String name = intent.getStringExtra("123");
                Team item = items.get(position);
                switch (item.getName()){
                    case "球员": intent = new Intent(TeamActivity.this,StaffActivity.class);break;
                    case "数据": intent = new Intent(TeamActivity.this,DataActivity.class);break;
                    //case "赛程": intent = new Intent(TeamActivity.this,ArrangeActivity.class);break;
                    //case "关注": items.get(position).setName("取消关注");
                    // case "取消关注": items.get(position).setName("关注");
                    default:
                }
                intent.putExtra("123",name);
                startActivity(intent);
            }
        });
        //Intent intent = new Intent(TeamActivity.this,staffActivity.class);
    }

    private void GetTeamInfo() {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray TeamInfo) throws JSONException{
                // TeamListFragment.this.initTeams(TeamInfo);
                ret = TeamInfo;
               Handle();
            }
        });
        http.execute("GetTeamInfo", "");
    }

    private void Handle() throws JSONException {
        Intent in = getIntent();
        for(int i = 0;i < ret.length();i++){
            JSONObject j = ret.getJSONObject(i);
            if(j.getString("球队名").contentEquals(Maps.CntoEng.get(name_c))){
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
