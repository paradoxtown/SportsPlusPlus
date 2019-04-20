package com.example.a1111.sprots;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends ArrayAdapter<Team> {
    private int resourceId;
    private boolean flag = false;
    TeamAdapter(Context context, int textViewResourceId, List<Team> objects, boolean flag)
    {
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
        this.flag = flag;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Team team = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        ImageView TeamImage = view.findViewById(R.id.team_image);
        TextView TeamName = view.findViewById(R.id.team_name);
        InputStream in;
        Bitmap img = null;
        if (!flag) {
            try {
                String fileName = team.getImage();
                if (fileName.contentEquals("76ers.png")) fileName = "p" + fileName;
                in = getContext().getResources().getAssets().open(fileName);
                img = BitmapFactory.decodeStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            TeamImage.setImageBitmap(img);
            TeamName.setText(team.getName());
        }
        else {
            String mark = team.getName();
            switch (mark) {
                case "球员" : TeamImage.setImageResource(R.drawable.boy); break;
                case "数据" : TeamImage.setImageResource(R.drawable.pencil); break;
                case "赛程" : TeamImage.setImageResource(R.drawable.telephone); break;
                case "关注" : TeamImage.setImageResource(R.drawable.heart); break;
                // case "取消关注" : TeamImage.setImageResource(R.drawable.heart); break;
                default:
            }
            TeamName.setText(mark);
        }
        return view;
    }

}
