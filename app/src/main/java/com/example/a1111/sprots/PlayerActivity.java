package com.example.a1111.sprots;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerActivity extends AppCompatActivity {
    private DataInfoSet A ;
    private static JSONArray ret;
    private static ArrayList<DataInfoSet> setArray = new ArrayList<DataInfoSet>();
    private  Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                ImageView img = (ImageView)findViewById(R.id.playerimage);
                img.setImageBitmap((Bitmap)msg.obj);
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Intent i = getIntent();
        player p = (player)i.getSerializableExtra("123");
        this.setTitle(p.getChiName());
        GetPlayerImage(p.getChiName());
        GetPlayerCareer(p);
    }

    private void GetPlayerImage(String playerName) {
        LoadImg<Bitmap> http = new LoadImg<>();
        http.setListener(new LoadImg.OnResponseListener<Bitmap>() {
            @Override
            public void onResponse(Bitmap playerImage) throws  IOException {
                System.out.println("123");
                Message message = handler.obtainMessage();
                message.what = 1;
                message.obj=playerImage;
                handler.sendMessage(message);
            }
        });
        http.execute("GetPlayerImage",playerName);
    }

    private void GetPlayerCareer(final player p) {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray PlayerCareer) throws JSONException, IOException {
                // TeamListFragment.this.initTeams(TeamInfo);
                ret = PlayerCareer;
                Handle(p);
            }
        });
        http.execute("GetPlayerCareer", p.getNumber());
    }
    private void Handle(player p) throws JSONException {
        setArray = new ArrayList<>();
        DataGridView playerView = (DataGridView) findViewById(R.id.player_data_grid);
        ListView playerList = (ListView)findViewById(R.id.playerview);
        A = new DataInfoSet("基本信息");
        newItem("姓名" , p.getChiName());newItem("位置:" , p.getPos());
        newItem("体重" , p.getWeight());newItem("合同:" , p.getContract());
        newItem("国籍" , p.getNationality());newItem("学校:" , p.getSchool());
        newItem("序号" , p.getNumber());newItem("本赛季薪金:" , p.getSalary());
        newItem("生日" , p.getBirth());newItem("身高:" , p.getHeight());
        newItem("选秀" , p.getDraft());
        setArray.add(A);
        for(int i = 0;i < ret.length();i++){
            JSONObject j = ret.getJSONObject(i);
            switch (i) {
                case 0:
                    A = new DataInfoSet("本赛季常规赛平均数据");
                    break;
                case 1:
                    A = new DataInfoSet("生涯常规赛平均数据");
                    break;
                case 2:
                    A = new DataInfoSet("生涯季后赛平均数据");
                    break;
                default:
                    break;
            }
            newItem("三分" , j.getString("三分"));
            newItem("助攻" , j.getString("助攻"));
            newItem("命中率" , j.getString("命中率"));
            newItem("场次" , j.getString("场次"));
            newItem("失误" , j.getString("失误"));
            newItem("得分" , j.getString("得分"));
            newItem("投篮" , j.getString("投篮"));
            newItem("抢断" , j.getString("抢断"));
            newItem("时间" , j.getString("时间"));
            newItem("犯规" , j.getString("犯规"));
            newItem("盖帽" , j.getString("盖帽"));
            newItem("篮板" , j.getString("篮板"));
            newItem("罚球" , j.getString("罚球"));
            setArray.add(A);
        }
        NewListViewAdapter adp = new NewListViewAdapter(setArray,this);
        playerList.setAdapter(adp);
    }

    public void newItem(String a, String b){
        A.add(new DataInfo(a,b));
    }
}
class DataGridView extends GridView{
    public DataGridView(android.content.Context context,android.util.AttributeSet attrs) {
        super(context, attrs);
    }
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
class DataInfoSet{
    private String description;
    private List<DataInfo> infoList;
    public DataInfoSet(String d){
        description = d;
        infoList = new ArrayList<DataInfo>() ;
    }

    public String getDescription() {
        return description;
    }

    public List<DataInfo> getInfoList() {
        return infoList;
    }
    public void add(DataInfo i){
        infoList.add(i);
    }
}

class DataInfo{
    private String dataDescription;
    private String data;
    public DataInfo(String dd,String d)
    {
        dataDescription = dd;data = d;
    }
    public String getData() {
        return data;
    }
    public String getDataDescription() {
        return dataDescription;
    }
}

class DataGridViewAdapter extends BaseAdapter{
    private List<DataInfo> mList;
    private Context mContext;

    public DataGridViewAdapter(List<DataInfo> mList,
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

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.player_data_item, null);
            holder.data_info = (TextView) convertView.findViewById(R.id.player_item_name);
            holder.data_item = (TextView) convertView.findViewById(R.id.player_item_data);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (this.mList != null) {
            DataInfo i = this.mList.get(position);
            if (holder.data_info != null) {
                holder.data_info.setText(i.getDataDescription());
                holder.data_info.setTextSize(12);
                holder.data_info.setTextColor(Color.GRAY);
                holder.data_item.setText(i.getData());
                holder.data_item.setTextSize(20);
                holder.data_item.setTextColor(Color.BLACK);
                TextPaint textPaint = holder.data_item.getPaint();
                textPaint.setFakeBoldText(true);
            }
        }
        return convertView;
    }

    private class ViewHolder
    {
        TextView data_info;
        TextView data_item;
    }

}

class NewListViewAdapter extends BaseAdapter {
    private List<DataInfoSet> mList;
    private Context mContext;

    public NewListViewAdapter(List<DataInfoSet> mList,
                                   Context mContext) {
        super();
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.player_data_list_item, null);
            holder.gridView = (GridView) convertView.findViewById(R.id.player_data_grid);
            holder.description = (TextView) convertView.findViewById(R.id.data_type_description);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (this.mList != null) {
            if (holder.gridView != null) {
                DataInfoSet d = this.mList.get(position);
                DataGridViewAdapter gridViewAdapter = new DataGridViewAdapter(d.getInfoList(),mContext);
                holder.gridView.setAdapter(gridViewAdapter);
                holder.description.setText(d.getDescription());
                holder.description.setTextSize(18);
            }
        }
        return convertView;
    }

    private class ViewHolder {
        GridView gridView;
        TextView description;
    }
}


