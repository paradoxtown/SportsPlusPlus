package com.free.app.spp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;

public class RecorderActivity extends AppCompatActivity {
    List recordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);
        recordList.add("队伍一");
        for (int i = 0; i < 10; i ++) {
            recordList.add(new RecorderPlayer());
        }
        recordList.add("队伍二");
        for (int i = 0; i < 10; i ++) {
            recordList.add(new RecorderPlayer());
        }
        RecorderAdapter recorderAdapter = new RecorderAdapter(recordList, this);
        ListView players = findViewById(R.id.my_game_players);
        players.setAdapter(recorderAdapter);
    }
}

class RecorderAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    private List mData;
    private Context mContext;
    private final int ITEM_NORMAL = 0;
    private final int ITEM_SECTION = 1;

    RecorderAdapter(List mData, Context mContext) {
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof RecorderPlayer) {
            return ITEM_NORMAL;
        }
        else {
            return ITEM_SECTION;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == ITEM_NORMAL) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_recorder_player, parent,false);
            convertView.setBackgroundColor(Color.WHITE);
        }
        else if (itemViewType == ITEM_SECTION) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_recorder_team, parent,false);
            convertView.setBackgroundColor(Color.rgb(240, 240, 240));
            TextView team1 = convertView.findViewById(R.id.recorder_team);
            String teamName = (String) mData.get(position);
            team1.setText(teamName);
        }
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == ITEM_SECTION;
    }
}

class RecorderPlayer {

    RecorderPlayer() {

    }
}
