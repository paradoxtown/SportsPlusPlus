package com.example.a1111.sprots;

import android.content.Intent;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class TeamListFragment extends Fragment {

    private ArrayList<Team> TeamList = new ArrayList<>();
    private static JSONArray ret = null;private TeamAdapter adapter;
    private GridView gridView;TextView tx;private View view;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teamlist,container,false);
        super.onCreate(savedInstanceState);
        getTeamInfo();
        return view;
    }

    private void getTeamInfo() {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray TeamInfo) throws JSONException, IOException {
                ret = TeamInfo;
                initTeams(ret);
            }
        });
        http.execute("GetTeamInfo", "");
    }



    private void initTeams(JSONArray TeamInfo) throws JSONException, IOException {
        for(int i = 0; i < TeamInfo.length(); i++){
            JSONObject j = TeamInfo.getJSONObject(i);
            String EngName1 = j.getString("球队名");
            final String CnName1 = Maps.EngToCn.get(EngName1);

            TeamList.add(new Team(CnName1, EngName1 + ".png"));

        }
        adapter = new TeamAdapter(this.getActivity(),R.layout.team_item, TeamList);
        gridView = view.findViewById(R.id.listview);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Team t = TeamList.get(position);
                Intent intent = new Intent(getActivity(),TeamActivity.class);
                intent.putExtra("123",t.getName());
                startActivity(intent);
            }
        });
    }
}
