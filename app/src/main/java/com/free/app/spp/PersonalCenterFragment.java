package com.free.app.spp;

import android.content.Intent;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.free.app.spp.justloginregistertest.loginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PersonalCenterFragment extends Fragment {
    private boolean isVisible = true;
    private ListView attentionList;
    private List<TeamItem> teamList = new ArrayList<>();
    private LinearLayout modifyPassword;
    private LinearLayout logout;
    private String userNameContent;
    private JSONArray likedTeam;
    private AllMap allMap = new AllMap();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_center, container, false);
        super.onCreate(savedInstanceState);
        userNameContent = getActivity().getIntent().getStringExtra("UserName");
        LinearLayout attentionLayout = view.findViewById(R.id.attention_teams);
        modifyPassword = view.findViewById(R.id.modify_password);
        logout = view.findViewById(R.id.log_out);
        attentionList = view.findViewById(R.id.attention_list);
        attentionList.setVisibility(View.GONE);
        TextView userName = view.findViewById(R.id.userName);
        TextView description = view.findViewById(R.id.description);
        TextView attentionTeamText = view.findViewById(R.id.attention_teams_text);
        final TextView modifyPwText = view.findViewById(R.id.modify_password_text);
        TextView logoutText = view.findViewById(R.id.log_out_text);
        ImageView userImg = view.findViewById(R.id.userImg);
        userName.setText(userNameContent);
        userName.setTextSize(36);
        description.setText("开发组很懒，这个功能都还没写。");
        attentionTeamText.setTextSize(16);
        modifyPwText.setTextSize(16);
        logoutText.setTextSize(16);

        userImg.setImageResource(R.drawable.person_img);
        MyTeamAdapter adapter = new MyTeamAdapter(teamList, getActivity());
        attentionList.setAdapter(adapter);

        attentionLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getLikedTeam();
            }
        });

        attentionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TeamItem teamItem = teamList.get(position);
                Intent intent = new Intent(getActivity(), TeamActivity.class);
                intent.putExtra("cnName", teamItem.getTeamName());
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), loginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void getLikedTeam() {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                if (isVisible) {
                    teamList.clear();
                    likedTeam = jsonArray;
                    if (likedTeam != null) {
                        for (int i = 0; i < likedTeam.length(); i++) {
                            JSONObject jso = (JSONObject) likedTeam.get(i);
                            jso = (JSONObject) jso.get("team");
                            teamList.add(new TeamItem(
                                    allMap.getCnNameFromCnLocName(jso.getString("球队中文名"))));
                        }
                    isVisible = false;
                    attentionList.setVisibility(View.VISIBLE);
                    modifyPassword.setVisibility(View.GONE);
                    logout.setVisibility(View.GONE);
                } else {
                    modifyPassword.setVisibility(View.VISIBLE);
                    logout.setVisibility(View.VISIBLE);
                    attentionList.setVisibility(View.GONE);
                    isVisible = true;
                }
                }
            }
        });
        http.execute("RequestGet", userNameContent);
    }
}
