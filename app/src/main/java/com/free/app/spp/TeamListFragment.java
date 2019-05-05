package com.free.app.spp;

import android.content.Intent;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TeamListFragment extends Fragment {

    private ArrayList<Team> TeamList = new ArrayList<>();
    private static JSONArray ret = null;private TeamAdapter adapter;
    private GridView gridView;
    TextView tx;
    private View view;
    private String userName;
    private AllMap mp = new AllMap();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teamlist,container,false);
        super.onCreate(savedInstanceState);
        userName = getActivity().getIntent().getStringExtra("UserName");
        initTeams();
        return view;
    }

    private void initTeams() {
        String[] teamEnNameList = Maps.teamEnNameList;
        for (String EngName1 : teamEnNameList) {
            final String CnName1 = Maps.EngToCn.get(EngName1);
            TeamList.add(new Team(CnName1, EngName1 + ".png"));
        }
        adapter = new TeamAdapter(this.getActivity(), R.layout.team_item, TeamList, false);
        gridView = view.findViewById(R.id.listview);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isLiked(TeamList.get(position));
            }
        });
    }

    private void isLiked(final Team t) {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                String cnName = t.getName();
                System.out.println(cnName + " " +userName);
                boolean isLiked = false;
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jso = (JSONObject) jsonArray.get(i);
                        jso = (JSONObject) jso.get("team");
                        String cnName2 = mp.getCnNameFromCnLocName(jso.getString("球队中文名"));
                        if (cnName.equals(cnName2)){
                            isLiked = true;
                            System.out.println("isLiked " + isLiked);
                            break;
                        }
                    }
                }
                Intent intent = new Intent(getActivity(), TeamActivity.class);
                intent.putExtra("cnName", cnName);
                intent.putExtra("isLiked", isLiked);
                intent.putExtra("UserName", userName);
                startActivity(intent);
            }
        });
        http.execute("RequestGet", userName);
    }
}
