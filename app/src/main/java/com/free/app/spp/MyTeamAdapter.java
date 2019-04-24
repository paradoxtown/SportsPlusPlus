package com.free.app.spp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyTeamAdapter extends BaseAdapter {
    private List mData;
    private Context mContext;
    MyTeamAdapter(List mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount(){
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.team_item_list, parent, false);
        TextView teamName = convertView.findViewById(R.id.team_name);
        ImageView teamImg = convertView.findViewById(R.id.team_img);
        TeamItem teamItem = (TeamItem) mData.get(position);
        teamName.setText(teamItem.getTeamName());
        teamImg.setImageResource(teamItem.getImgId());
        return convertView;
    }
}

class TeamItem {
    private String teamName;
    private int imgId;
    private AllMap allMap = new AllMap();

    TeamItem(String teamName) {
        this.teamName = teamName;
    }

    int getImgId () {
        return allMap.getDrawableId(teamName);
    }

    String getTeamName() {
        return teamName;
    }
}
