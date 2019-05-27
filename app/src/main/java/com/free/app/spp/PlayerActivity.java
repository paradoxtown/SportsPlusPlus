package com.free.app.spp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity {
    private DataInfoSet A;
    private static JSONArray ret;
    @SuppressLint("HandlerLeak")
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            if(msg.what == 1){
//                ImageView img = (ImageView)findViewById(R.id.playerimage);
//                img.setImageBitmap((Bitmap)msg.obj);
//            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Intent i = getIntent();
        player p = (player) i.getSerializableExtra("player");
        this.setTitle(p.getChiName());
        GetPlayerImage(p.getChiName());
        GetPlayerCareer(p);
    }

    private void GetPlayerImage(String playerName) {
        LoadImg<Bitmap> http = new LoadImg<>();
        http.setListener(new LoadImg.OnResponseListener<Bitmap>() {
            @Override
            public void onResponse(Bitmap playerImage) {
                Message message = handler.obtainMessage();
                message.what = 1;
                message.obj = playerImage;
                handler.sendMessage(message);
            }
        });
        http.execute("GetPlayerImage", playerName);
    }

    private void GetPlayerCareer(final player p) {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray PlayerCareer) throws JSONException {
                // TeamListFragment.this.initTeams(TeamInfo);
                ret = PlayerCareer;
                Handle(p);
            }
        });
        http.execute("GetPlayerCareer", p.getNumber());
    }

    private void Handle(player p) throws JSONException {
        ArrayList<DataInfoSet> setArray = new ArrayList<>();
        ListView playerList = findViewById(R.id.playerview);
        TextView name_player = findViewById(R.id.name_player);
        name_player.setText(p.getChiName());
        TextView pos_player = findViewById(R.id.pos_player);
        pos_player.setText(p.getPos());
        TextView school_player = findViewById(R.id.school_player);
        school_player.setText(p.getSchool());
        TextView nation_player = findViewById(R.id.nation_player);
        nation_player.setText(p.getNationality());
        TextView money_player = findViewById(R.id.money_player);
        money_player.setText(p.getSalary());
        TextView weight_player = findViewById(R.id.weight_player);
        weight_player.setText(p.getWeight());
        TextView birth_player = findViewById(R.id.birthday_player);
        birth_player.setText(p.getBirth());
        TextView height_player = findViewById(R.id.height_player);
        height_player.setText(p.getHeight());
        TextView draft_player = findViewById(R.id.draft_player);
        draft_player.setText(p.getDraft());
        TextView compact_player = findViewById(R.id.compact_player);
        compact_player.setText(p.getContract());
        for (int i = 0; i < ret.length(); i++) {
            JSONObject j = ret.getJSONObject(i);
            switch (i) {
                case 0:
                    A = new DataInfoSet("本赛季常规赛平均数据");
                    break;
                case 1:
                    A = new DataInfoSet("生涯季后赛平均数据");
                    break;
                case 2:
                    A = new DataInfoSet("生涯常规赛平均数据");
                    break;
                default:
                    break;
            }
            newItem("三分", j.getString("三分"));
            newItem("助攻", j.getString("助攻"));
            newItem("命中率", j.getString("命中率"));
            newItem("场次", j.getString("场次"));
            newItem("失误", j.getString("失误"));
            newItem("得分", j.getString("得分"));
            newItem("投篮", j.getString("投篮"));
            newItem("抢断", j.getString("抢断"));
            newItem("时间", j.getString("时间"));
            newItem("犯规", j.getString("犯规"));
            newItem("盖帽", j.getString("盖帽"));
            newItem("篮板", j.getString("篮板"));
            newItem("罚球", j.getString("罚球"));
            setArray.add(A);
        }
        NewListViewAdapter adp = new NewListViewAdapter(setArray, this);
        playerList.setAdapter(adp);
    }

    public void newItem(String a, String b) {
        A.add(new DataInfo(a, b));
    }
}

class DataGridView extends GridView {
    public DataGridView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

class DataInfoSet {
    private String description;
    private List<DataInfo> infoList;

    DataInfoSet(String d) {
        description = d;
        infoList = new ArrayList<>();
    }

    String getDescription() {
        return description;
    }

    List<DataInfo> getInfoList() {
        return infoList;
    }

    public void add(DataInfo i) {
        infoList.add(i);
    }
}

class DataInfo {
    private String dataDescription;
    private String data;

    DataInfo(String dd, String d) {
        dataDescription = dd;
        data = d;
    }

    String getData() {
        return data;
    }

    String getDataDescription() {
        return dataDescription;
    }
}

class DataGridViewAdapter extends BaseAdapter {
    private List<DataInfo> mList;
    private Context mContext;

    DataGridViewAdapter(List<DataInfo> mList,
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
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.player_data_item, null);
            holder.data_info = convertView.findViewById(R.id.player_item_name);
            holder.data_item = convertView.findViewById(R.id.player_item_data);
            holder.wrap = convertView.findViewById(R.id.player_info_wrap);
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
                holder.data_item.setTextSize(18);
                holder.data_item.setTextColor(Color.BLACK);
                TextPaint textPaint = holder.data_item.getPaint();
                textPaint.setFakeBoldText(true);
            }
            if ((position % 2 == 0 && (position / 4) % 2 == 0)
                    || (position % 2 == 1 && (position / 4) % 2 == 1)) {
                holder.wrap.setBackgroundColor(holder.wrap.getResources().getColor(R.color.nation_player_color));
            }
        }
        return convertView;
    }

    private class ViewHolder {
        LinearLayout wrap;
        TextView data_info;
        TextView data_item;
    }

}

class NewListViewAdapter extends BaseAdapter {
    private List<DataInfoSet> mList;
    private Context mContext;

    NewListViewAdapter(List<DataInfoSet> mList,
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.player_data_list_item, null);
            holder.gridView = convertView.findViewById(R.id.player_data_grid);
            holder.description = convertView.findViewById(R.id.data_type_description);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (this.mList != null) {
            if (holder.gridView != null) {
                DataInfoSet d = this.mList.get(position);
                DataGridViewAdapter gridViewAdapter = new DataGridViewAdapter(d.getInfoList(), mContext);
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


