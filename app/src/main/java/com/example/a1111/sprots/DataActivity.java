package com.example.a1111.sprots;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataActivity extends AppCompatActivity {

    private String name;
    private static JSONArray ret;
    private ArrayList <TeamData> A = new ArrayList<>();
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        this.setTitle("球队数据");
        Toast.makeText(DataActivity.this,"data",Toast.LENGTH_SHORT).show();
        Intent i = getIntent();
        name = i.getStringExtra("123");
        GetTeamInfo();
    }

    private void GetTeamInfo() {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray teamInfo) throws JSONException {
                ret = teamInfo;
                Handle(ret);
            }
        });
        http.execute("GetTeamInfo", "");
    }
        private void Handle(JSONArray e) throws JSONException {
            for(int i = 0;i < e.length();i++){
                JSONObject j = e.getJSONObject(i);
                String cn_name = j.getString("球队中文名");
                if(cn_name.contentEquals(Maps.CntoFull.get(name))){
                    String coach = j.getString("主教练");
                    String intro = j.getString("介绍");
                    JSONObject assist = j.getJSONObject("场均助攻");
                    JSONObject loss = j.getJSONObject("场均失分");
                    JSONObject turnover = j.getJSONObject("场均失误");
                    JSONObject points = j.getJSONObject("场均得分");
                    JSONObject rebounds = j.getJSONObject("场均篮板");
                    String start_year = j.getString("进入NBA");
                    String CN_name = j.getString("球队中文名");
//                    A.add(new TeamData("技术统计",""));
//                    A.add(new TeamData("",""));
//                    A.add(new TeamData("",""));
                    A.add(new TeamData("场均助攻",""));
                    A.add(new TeamData("排名" , assist.getString("排名") ));
                    A.add(new TeamData("数值" , assist.getString("数值") ));
                    A.add(new TeamData("场均失误",""));
                    A.add(new TeamData("排名" , turnover.getString("排名") ));
                    A.add(new TeamData("数值" , turnover.getString("数值") ));
                    A.add(new TeamData("场均得分",""));
                    A.add(new TeamData("排名" , points.getString("排名") ));
                    A.add(new TeamData("数值" , points.getString("数值") ));
                    A.add(new TeamData("场均篮板",""));
                    A.add(new TeamData("排名" , rebounds.getString("排名") ));
                    A.add(new TeamData("数值" , rebounds.getString("数值") ));
                    // A.add(new TeamData("进入NBA:" , start_year));
                    GridView g = findViewById(R.id.team_data_grid);
                    TeamDataAdapter adp = new TeamDataAdapter(A,this);
                    g.setAdapter(adp);
                }
            }
        }

}

class TeamData{
    private String data_info;
    private String data_value;
    TeamData(String di,String dv){
        data_info = di;
        data_value = dv;
    }

    String getData_info() {
        return data_info;
    }

    String getData_value() {
        return data_value;
    }
}

class TeamDataAdapter extends BaseAdapter{
    private ArrayList<TeamData> mList;
    private Context mContext;

    TeamDataAdapter(ArrayList<TeamData> mList,
                               Context mContext) {
        super();
        this.mList = mList;
        this.mContext = mContext;
    }
    public int getCount() {
        if (mList == null) {
            return 0;
        } else {
            return this.mList.size();
        }
    }
    @Override
    public Object getItem(int position) {
        if (mList == null) {
            return null;
        } else {
            return this.mList.get(position);
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.team_data_grid_item, null);
            holder.description = convertView.findViewById(R.id.team_data_item_description);
            holder.value = convertView.findViewById(R.id.team_data_item_value);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (this.mList != null) {
            TeamData i = this.mList.get(position);
            if (holder.description != null) {
                holder.description.setText(i.getData_info());
                holder.description.setTextSize(12);
                holder.description.setTextColor(Color.GRAY);
                holder.value.setText(i.getData_value());
                holder.value.setTextSize(20);
                holder.value.setTextColor(Color.BLACK);
                TextPaint textPaint = holder.value.getPaint();
                textPaint.setFakeBoldText(true);
            }
        }
        return convertView;
    }

    private class ViewHolder
    {
        TextView description;
        TextView value;
    }
}


