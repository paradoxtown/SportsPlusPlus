package com.free.app.spp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class StaffActivity extends AppCompatActivity {

    private static JSONArray ret;
    private ArrayList<StaffItem> A = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        Intent intent = getIntent();
        String name = intent.getStringExtra("cnName");
        String eng_name = Maps.CntoEng.get(name);
        GetPlayerInfo(eng_name);
    }

    private void GetPlayerInfo(String teamName) {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray PlayerInfo){
                // TeamListFragment.this.initTeams(TeamInfo);
                ret = PlayerInfo;
                getInfo();
            }
        });
        http.execute("GetPlayerInfo", teamName);
    }
    public void getInfo()
    {
        Map<String,Object> mp = new HashMap<>();
        mp.put("playerName","姓名");
        mp.put("playerNumber","号码");
        mp.put("playerPosition","场上位置");
        mp.put("playerSalary","薪水");
        mp.put("player",null);

        try{
            for(int i = 0;i < ret.length();i++) {
                JSONObject obj = ret.getJSONObject(i);
                String ChiName = "无信息";if(obj.has("中文名"))ChiName= obj.getString("中文名");
                String pos = "无信息";if(obj.has("位置"))pos = obj.getString("位置");
                String weight = "无信息";if(obj.has("体重"))weight = obj.getString("体重");
                String contract = "无信息";if(obj.has("合同"))contract = obj.getString("合同");
                String nationality = "无信息";if(obj.has("国籍"))nationality = obj.getString("国籍");
                String school = "无信息";if(obj.has("学校"))school = obj.getString("学校");
                String number = "无信息";if(obj.has("序号"))number = obj.getString("序号");
                String salary = "无信息";if(obj.has("本赛季薪金"))salary = obj.getString("本赛季薪金");
                String team = "无信息";if(obj.has("球队"))team = obj.getString("球队");
                String birth = "无信息";if(obj.has("生日"))birth = obj.getString("生日");
                String EngName = "无信息";if(obj.has("英文名"))EngName = obj.getString("英文名");
                String height = "无信息";if(obj.has("身高"))height = obj.getString("身高");
                String draft = "无信息";if(obj.has("选秀"))draft = obj.getString("选秀");
                player p = new player(ChiName,pos,weight,contract,nationality,school,number,salary,team,birth,EngName,height,draft);
                System.out.println(pos);int begin,end;String num,po;
                if(pos.contains("（")){begin = pos.indexOf("（"); end = pos.indexOf("）");num = pos.substring(begin + 1,end);po = pos.substring(0,begin);}
                else{po = pos;num = "无信息";}
                A.add(new StaffItem(num,po,salary,p));
            }
            ListView StaffView = findViewById(R.id.staffview);
            StaffAdapter adp = new StaffAdapter(A,this);
            StaffView.setAdapter(adp);
            StaffView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(StaffActivity.this,PlayerActivity.class);
                    StaffItem mp = A.get(position);
                    player p = mp.getPlayer();
                    intent.putExtra("player",p);
                    startActivity(intent);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

class StaffItem {
    private String playerNumber, playerPosition, playerSalary;
    private player player;

    StaffItem(String pn, String pp, String ps, player p){
        this.playerNumber = pn;
        this.playerPosition = pp;
        this.playerSalary = ps;
        this.player = p;
    }

    player getPlayer() {
        return this.player;
    }

    String getPlayerNumber() {
        return playerNumber;
    }

    String getPlayerPosition() {
        return playerPosition;
    }

    String getPlayerSalary() {
        return playerSalary;
    }
}

class StaffAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<StaffItem> mList;

    StaffAdapter(ArrayList<StaffItem>mList,Context mContext){
        this.mContext = mContext;
        this.mList = mList;
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

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.player_list_item, null);
            holder.pic = convertView.findViewById(R.id.player_pic);
            holder.name = convertView.findViewById(R.id.player_name);
            holder.number = convertView.findViewById(R.id.player_number);
            holder.pos = convertView.findViewById(R.id.player_position);
            holder.salary = convertView.findViewById(R.id.player_salary);
            convertView.setTag(holder);
        }
        if(this.mList != null){
            holder = (ViewHolder)convertView.getTag();
            StaffItem i = mList.get(position);
            holder.name.setText(i.getPlayer().getChiName());
            holder.number.setText(i.getPlayerNumber());
            holder.pos.setText(i.getPlayerPosition());
            holder.salary.setText(i.getPlayerSalary());
            GetPlayerImage(i.getPlayer().getChiName(),holder.pic);
        }
        return convertView;
    }

    private void GetPlayerImage(String playerName,final ImageView i) {
        LoadImg<Bitmap> http = new LoadImg<>();
        http.setListener(new LoadImg.OnResponseListener<Bitmap>() {
            @Override
            public void onResponse(Bitmap playerImage){
                i.setImageBitmap(playerImage);
            }
        });
        http.execute("GetPlayerImage",playerName);
    }

    class ViewHolder{
        ImageView pic;
        TextView name;
        TextView number;
        TextView pos;
        TextView salary;
    }

}

