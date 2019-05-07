package com.free.app.spp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class SerialArrangeActivity extends AppCompatActivity {


    ArrayList<ArrangeListItem> A = new ArrayList<>();
    ListView list;
    ArrangeListAdapter adp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serialarrange);

        FloatingActionButton fab = findViewById(R.id.fab);
        list = (ListView) findViewById(R.id.ArrangeList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SerialArrangeActivity.this, NewArrangeActivity.class);
                startActivityForResult(i, 1);
            }
        });

        Button saveSchedule = findViewById(R.id.save_schedule);
        saveSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SerialArrangeActivity.this, MyGameActivity.class);
                startActivity(i);
            }
        });
    }
    protected void onActivityResult ( int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //这里要通过请求码来判断数据的来源
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    ArrangeListItem getData = (ArrangeListItem)data.getSerializableExtra("123");
                    A.add(getData);
                    adp = new ArrangeListAdapter(A,this);
                    list.setAdapter(adp);
                }
                break;
            default:
        }
    }
}

class ArrangeListAdapter extends BaseAdapter{

    private ArrayList<ArrangeListItem> mList;
    private Context mContext;
    ArrangeListAdapter(ArrayList<ArrangeListItem> mList,
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
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.arrangelist_item, null);
            holder.text1 = convertView.findViewById(R.id.text1);
            holder.text2 = convertView.findViewById(R.id.text2);
            holder.text3 = convertView.findViewById(R.id.text3);
            holder.text4 = convertView.findViewById(R.id.text4);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (this.mList != null) {
            ArrangeListItem i = this.mList.get(position);
            if (holder.text1 != null) {
                holder.text1.setText(i.getTeamA());
                holder.text2.setText(i.getTeamB());
                holder.text3.setText(i.getPos());
                holder.text4.setText(i.getTime());
//                holder.text1.setTextSize(12);
//                holder.text1.setTextColor(Color.GRAY);
            }
        }
        return convertView;
    }

     class ViewHolder
    {
        TextView text1;
        TextView text2;
        TextView text3;
        TextView text4;
    }
}

