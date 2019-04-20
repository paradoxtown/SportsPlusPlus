package com.example.a1111.sprots;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArrangeFragment extends Fragment {



    private View view;
    private static JSONArray ret;
    private List mData = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_arrange,container,false);
        super.onCreate(savedInstanceState);
        getMatch();
//        Context mContext = view.getContext();
//        ListView list_animal = view.findViewById(R.id.list_animal);
//        // View head = inflater.inflate(R.layout.view_header, null, false);
//
//        MatchAdapter mAdapter = new MatchAdapter(mData, mContext);
//        // list_animal.addHeaderView(head);
//        list_animal.setAdapter(mAdapter);
        return view;
    }

    private void getMatch() {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray MatchInfo) throws JSONException, IOException {
                // TeamListFragment.this.initTeams(TeamInfo);
                ret = MatchInfo;
                dataHandle();
            }
        });
        http.execute("GetMatch", "");
    }

    private void dataHandle() throws JSONException {
        String nowDate = null;
        for(int i = 0;i < ret.length();i++){
            JSONObject j = ret.getJSONObject(i);
            String date = j.getString("日期");
            if(nowDate == null || nowDate.contentEquals(date) == false){
                nowDate = date;
                mData.add(new MatchDate(date));
            }
            String id = j.getString("id");
            String host = j.getString("主场球队中文名");
            String guest = j.getString("客场球队中文名");
            String host_points = j.getString("主场总分");
            String guest_points = j.getString("客场总分");
            String host_pic  = Maps.CntoEng.get(host) + ".png";if(host_pic.equals("76ers.png"))host_pic = "p" + host_pic;
            String guest_pic  = Maps.CntoEng.get(guest) + ".png";if(guest_pic.equals("76ers.png"))guest_pic = "p" + guest_pic;
            mData.add(new Match(host,guest,host_points,guest_points,host_pic,guest_pic,id));
        }

        Context mContext = view.getContext();
        ListView list_animal = view.findViewById(R.id.list_animal);
        // View head = inflater.inflate(R.layout.view_header, null, false);

        MatchAdapter mAdapter = new MatchAdapter(mData, mContext);
        // list_animal.addHeaderView(head);
        list_animal.setAdapter(mAdapter);
        list_animal.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Match m = (Match)mData.get(position);
                Intent i = new Intent(getActivity(),MatchActivity.class);
                i.putExtra("123",m.getId());
                startActivity(i);
            }
        });

    }

}
