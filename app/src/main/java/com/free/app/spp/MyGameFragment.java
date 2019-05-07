package com.free.app.spp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.List;

public class MyGameFragment extends Fragment {
    private View view;
    private List<MyGame> mData = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_game, container, false);
        super.onCreate(savedInstanceState);
        mData.add(new MyGame("6系篮球赛"));
        mData.add(new MyGame("21系篮球赛"));
        mData.add(new MyGame("2系篮球赛"));
        mData.add(new MyGame("3系篮球赛"));
        mData.add(new MyGame("18系篮球赛"));
        mData.add(new MyGame("4系篮球赛"));
        mData.add(new MyGame("5系篮球赛"));
        mData.add(new MyGame("1系篮球赛"));
        mData.add(new MyGame("9系篮球赛"));
        mData.add(new MyGame("15系篮球赛"));
        mData.add(new MyGame("16系篮球赛"));
        mData.add(new MyGame("11系篮球赛"));
        ListView myGames = view.findViewById(R.id.my_game_list);
        MyGameAdapter myGameAdapter = new MyGameAdapter(mData, view.getContext());
        myGames.setAdapter(myGameAdapter);
        myGames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), MyGameActivity.class);
                startActivity(i);
            }
        });
        Button createGameButton = view.findViewById(R.id.add_new_game_button);
        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MyGameCreatorActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}

class MyGameAdapter extends BaseAdapter {
    private List mData;
    private Context mContext;

    MyGameAdapter(List mData, Context mContext) {
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.mygame_item, parent, false);
        TextView gameName = convertView.findViewById(R.id.my_game_name);
        MyGame myGame = (MyGame) mData.get(position);
        gameName.setText(myGame.getGameName());
        LikeButton likeButton = convertView.findViewById(R.id.my_team_star_button);
        likeButton.setLiked(false);
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });
        return convertView;
    }
}
