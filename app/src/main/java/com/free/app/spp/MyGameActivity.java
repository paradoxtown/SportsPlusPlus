package com.free.app.spp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;

public class MyGameActivity extends AppCompatActivity {
    private boolean is_admin = true;
    private List mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_game);
        Button edit_schedule = findViewById(R.id.edit_schedule);
        if (!is_admin) {
            edit_schedule.setVisibility(View.GONE);
        }
        dataHandle();
    }

    private void dataHandle()  {
        mData.add(new MatchDate("2019-05-05"));
        mData.add(new MyGameMatch("1班", "2班", "3:00PM", "篮球场1号框", "21", "20"));
        mData.add(new MyGameMatch("3班", "4班", "7:00PM", "篮球场2号框", "21", "20"));
        mData.add(new MatchDate("2019-05-06"));
        mData.add(new MyGameMatch("4班", "5班", "8:00AM", "篮球场3号框", "-", "-"));
        mData.add(new MyGameMatch("6班", "1班", "9:30AM", "篮球场4号框", "-", "-"));
        mData.add(new MyGameMatch("7班", "3班", "3:00PM", "篮球场5号框", "-", "-"));
        MyGameMatchAdapter myGameMatchAdapter = new MyGameMatchAdapter(mData, this);
        ListView matches = findViewById(R.id.list_my_game_match);
        matches.setAdapter(myGameMatchAdapter);
    }
}


class MyGameMatchAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    private List mData;
    private Context mContext;
    private final int ITEM_NORMAL = 0;
    private final int ITEM_SECTION = 1;
    MyGameMatchAdapter(List mData, Context mContext) {
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
        if (mData.get(position) instanceof MyGameMatch) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_my_game_match,parent,false);
            convertView.setBackgroundColor(Color.WHITE);
            LinearLayout myGameMatchItem = convertView.findViewById(R.id.my_game_match_item);
            TextView teamName1 = convertView.findViewById(R.id.team_name1);
            TextView teamName2 = convertView.findViewById(R.id.team_name2);
            TextView teamScore1 = convertView.findViewById(R.id.team_points1);
            TextView teamScore2 = convertView.findViewById(R.id.team_points2);
            TextView time = convertView.findViewById(R.id.my_game_time);
            TextView place = convertView.findViewById(R.id.my_game_place);

            myGameMatchItem.setBackgroundColor(Color.rgb(240,240,240));
            MyGameMatch a = (MyGameMatch) mData.get(position);
            String p1 = a.getScore1();
            String p2 = a.getScore2();
            teamName1.setText(a.getTeamName1());
            teamName2.setText(a.getTeamName2());
            teamScore1.setText(p1);
            teamScore1.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            teamScore2.setText(p2);
            teamScore2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            time.setText(a.getTime());
            place.setText(a.getPlace());
        }
        else if (itemViewType == ITEM_SECTION) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_date,parent,false);
            convertView.setBackgroundColor(Color.rgb(240, 240, 240));
            TextView dfn = convertView.findViewById(R.id.dfn);
            dfn.setTextColor(Color.BLACK);
            dfn.setTextSize(16);
            MatchDate c = (MatchDate) mData.get(position);
            String date = c.getDate();
            String[] tmp_date = date.split("-");
            date = tmp_date[0] + "年" + tmp_date[1] + "月" + tmp_date[2] + "日";
            dfn.setText(date);
        }
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == ITEM_SECTION;
    }
}