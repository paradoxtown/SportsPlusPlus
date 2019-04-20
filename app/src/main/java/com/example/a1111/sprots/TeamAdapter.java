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
    public TeamAdapter(Context context, int textViewResourceId, List<Team> objects)
    {
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Team team = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        ImageView TeamImage = (ImageView) view.findViewById(R.id.team_image);
        TextView TeamName = (TextView) view.findViewById(R.id.team_name);
        InputStream in = null;
        Bitmap img = null;
        try {
            String fileName = team.getImage();if(fileName.contentEquals("76ers.png"))fileName = "p" + fileName;
            in = getContext().getResources().getAssets().open(fileName);
            img = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TeamImage.setImageBitmap(img);
        TeamName.setText(team.getName());
        return view;
    }

}
