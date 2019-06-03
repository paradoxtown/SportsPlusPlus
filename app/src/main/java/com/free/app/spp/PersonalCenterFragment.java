package com.free.app.spp;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.free.app.spp.justloginregistertest.loginActivity;
import com.free.app.spp.justloginregistertest.modiActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonalCenterFragment extends Fragment {
    private boolean isVisible = true;
    private boolean adminIsVisible = true;
    private boolean offlineIsVisible = true;
    private ListView attentionList;
    private ListView adminOfflineList;
    private ListView attentionOfflineList;

    private List<TeamItem> teamList = new ArrayList<>();
    private List<MyGame> adminMatchList = new ArrayList<>();
    private List<MyGame> likedMatchList = new ArrayList<>();

    private LinearLayout modifyPassword;
    private LinearLayout logout;
    private String userNameContent;
    private JSONArray likedTeam;
    private JSONArray adminOfflineMatch;
    private JSONArray likedOfflineMatch;
    private AllMap allMap = new AllMap();
    private List<String> attentionSet = new ArrayList<>();
    private List<String> adminOfflineSet = new ArrayList<>();
    private List<String> attentionOfflineSet = new ArrayList<>();
    private SharedPreferences.Editor e;
    private LinearLayout attentionLayout;
    private LinearLayout adminOfflineLayout;
    private LinearLayout attentionOfflineLayout;


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_center, container, false);
        super.onCreate(savedInstanceState);
        userNameContent = getActivity().getIntent().getStringExtra("UserName");
        attentionLayout = view.findViewById(R.id.attention_teams);
        adminOfflineLayout = view.findViewById(R.id.my_game_admin_games);
        attentionOfflineLayout = view.findViewById(R.id.my_game_attention_games);
        modifyPassword = view.findViewById(R.id.modify_password);
        logout = view.findViewById(R.id.log_out);
        attentionList = view.findViewById(R.id.attention_list);
        attentionList.setVisibility(View.GONE);
        adminOfflineList = view.findViewById(R.id.my_game_admin_games_list);
        adminOfflineList.setVisibility(View.GONE);
        attentionOfflineList = view.findViewById(R.id.my_game_attention_games_list);
        attentionOfflineList.setVisibility(View.GONE);
        TextView userName = view.findViewById(R.id.userName);
        TextView description = view.findViewById(R.id.description);
        TextView attentionTeamText = view.findViewById(R.id.attention_teams_text);
        final TextView modifyPwText = view.findViewById(R.id.modify_password_text);
        TextView logoutText = view.findViewById(R.id.log_out_text);
        ImageView userImg = view.findViewById(R.id.userImg);
        userName.setText(userNameContent);
        userName.setTextSize(36);
        description.setText(userNameContent + "的个人中心");
        attentionTeamText.setTextSize(16);
        modifyPwText.setTextSize(16);
        logoutText.setTextSize(16);

        userImg.setImageResource(R.drawable.person_img);
//        MyTeamAdapter adapter = new MyTeamAdapter(teamList, getActivity());
//        attentionList.setAdapter(adapter);
//        AttentionGameAdapterInPersonalCenter adp
//                = new AttentionGameAdapterInPersonalCenter(matchList, getActivity());
//        attentionOfflineList.setAdapter(adp);
        SharedPreferences sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        e = sp.edit();
        attentionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLikedTeam();
            }
        });

        adminOfflineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOfflineAdminTeam();
            }
        });

        attentionOfflineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOfflineLikedTeam();
            }
        });

        attentionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TeamItem teamItem = teamList.get(position);
                isLiked(teamItem);
            }
        });

        adminOfflineList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyGame myGame = adminMatchList.get(position);
                isAdmin(myGame);
            }
        });

        attentionOfflineList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyGame myGame = likedMatchList.get(position);
                isOfflineAttention(myGame);
            }
        });

        modifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), modiActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), loginActivity.class);
                e.clear().apply();
                startActivity(intent);
            }
        });

        return view;
    }

    private void isLiked(final TeamItem t) {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                String cnName = t.getTeamName();
                System.out.println(cnName + " " + userNameContent);
                boolean isLiked = false;
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jso = (JSONObject) jsonArray.get(i);
                        jso = (JSONObject) jso.get("team");
                        String cnName2 = allMap.getCnNameFromCnLocName(jso.getString("球队中文名"));
                        if (cnName.equals(cnName2)) {
                            isLiked = true;
                            break;
                        }
                    }
                }
                Intent intent = new Intent(getActivity(), TeamActivity.class);
                intent.putExtra("cnName", cnName);
                intent.putExtra("isLiked", isLiked);
                intent.putExtra("UserName", userNameContent);
                startActivity(intent);
            }
        });
        http.execute("RequestGet", userNameContent);
    }

    private void isAdmin(final MyGame myGame) {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException, IOException {
                String gameName = myGame.getGameName();
                System.out.println(gameName + " " + userNameContent);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject j = jsonArray.getJSONObject(i);
                    j = j.getJSONObject("赛程");
                    String name = j.getString("名称");
                    if (gameName.equals(name)) {
                        myGame.setIs_administrator();
                    }
                }
                Intent intent = new Intent(getActivity(), MyGameActivity.class);
                intent.putExtra("MyGame", myGame);
                intent.putExtra("UserName", userNameContent);
                startActivity(intent);
            }
        });
        http.execute("GetMySchedule", userNameContent);
    }

    private void isOfflineAttention(final MyGame myGame) {
        Intent intent = new Intent(getActivity(), MyGameActivity.class);
        intent.putExtra("MyGame", myGame);
        intent.putExtra("UserName", userNameContent);
        startActivity(intent);
    }

    private void getLikedTeam() {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                if (isVisible) {
                    teamList.clear();
                    likedTeam = jsonArray;
                    attentionSet.clear();
                    if (likedTeam != null) {
                        for (int i = 0; i < likedTeam.length(); i++) {
                            JSONObject jso = (JSONObject) likedTeam.get(i);
                            jso = (JSONObject) jso.get("team");
                            String cN = allMap.getCnNameFromCnLocName(jso.getString("球队中文名"));
                            if (!attentionSet.contains(cN)) {
                                teamList.add(new TeamItem(cN));
                                attentionSet.add(cN);
                            }
                        }
                        MyTeamAdapter adapter = new MyTeamAdapter(teamList, getActivity());
                        attentionList.setAdapter(adapter);
                        isVisible = false;
                        offlineIsVisible = true;
                        adminIsVisible = true;
                        adminOfflineLayout.setVisibility(View.GONE);
                        attentionOfflineLayout.setVisibility(View.GONE);
                        adminOfflineList.setVisibility(View.GONE);
                        attentionList.setVisibility(View.VISIBLE);
                        modifyPassword.setVisibility(View.GONE);
                        logout.setVisibility(View.GONE);
                    } else {
                        modifyPassword.setVisibility(View.VISIBLE);
                        logout.setVisibility(View.VISIBLE);
                        adminOfflineLayout.setVisibility(View.VISIBLE);
                        attentionOfflineLayout.setVisibility(View.VISIBLE);
                        attentionList.setVisibility(View.GONE);
                        isVisible = true;
                    }
                } else {
                    modifyPassword.setVisibility(View.VISIBLE);
                    logout.setVisibility(View.VISIBLE);
                    attentionList.setVisibility(View.GONE);
                    adminOfflineLayout.setVisibility(View.VISIBLE);
                    attentionOfflineLayout.setVisibility(View.VISIBLE);
                    isVisible = true;
                }
            }
        });
        http.execute("RequestGet", userNameContent);
    }

    private void getOfflineLikedTeam() {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException, IOException {
                if (offlineIsVisible) {
                    likedMatchList.clear();
                    attentionOfflineSet.clear();
                    likedOfflineMatch = jsonArray;
                    System.out.println(likedMatchList.toString());
                    if (likedMatchList != null) {
                        for (int i = 0; i < likedOfflineMatch.length(); i ++) {
                            JSONObject j = jsonArray.getJSONObject(i);
                            j = j.getJSONObject("赛程");
                            String name = j.getString("名称");
                            String intro = j.getString("简介");
                            String time = j.getString("创建时间");
                            String id = j.getString("id");
                            if (!attentionOfflineSet.contains(name)) {
                                likedMatchList.add(new MyGame(name, time, intro, id));
                                attentionOfflineSet.add(name);
                            }
                        }
                        AttentionGameAdapterInPersonalCenter attentionOfflineAdp =
                                new AttentionGameAdapterInPersonalCenter(likedMatchList, getActivity());
                        attentionOfflineList.setAdapter(attentionOfflineAdp);
                        attentionOfflineList.setVisibility(View.VISIBLE);
                        attentionList.setVisibility(View.GONE);
                        modifyPassword.setVisibility(View.GONE);
                        logout.setVisibility(View.GONE);
                        offlineIsVisible = false;
                        isVisible = true;
                        adminIsVisible = true;
                    } else {
                        isVisible = true;
                        adminIsVisible = true;
                        offlineIsVisible = true;
                        adminOfflineLayout.setVisibility(View.VISIBLE);
                        modifyPassword.setVisibility(View.VISIBLE);
                        logout.setVisibility(View.VISIBLE);
                        attentionOfflineList.setVisibility(View.GONE);
                    }
                } else {
                    modifyPassword.setVisibility(View.VISIBLE);
                    logout.setVisibility(View.VISIBLE);
                    attentionOfflineList.setVisibility(View.GONE);
                    isVisible = true;
                    adminIsVisible = true;
                    offlineIsVisible = true;
                }
            }
        });
        http.execute("GetSubforgame", userNameContent);
    }

    private void getOfflineAdminTeam() {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                if (adminIsVisible) {
                    adminMatchList.clear();
                    adminOfflineSet.clear();
                    adminOfflineMatch = jsonArray;
                    System.out.println(adminOfflineMatch.toString());
                    if (adminOfflineMatch != null) {
                        for (int i = 0; i < adminOfflineMatch.length(); i++) {
                            JSONObject j = jsonArray.getJSONObject(i);
                            j = j.getJSONObject("赛程");
                            String name = j.getString("名称");
                            String intro = j.getString("简介");
                            String time = j.getString("创建时间");
                            String id = j.getString("id");
                            if (!adminOfflineSet.contains(name)) {
                                adminMatchList.add(new MyGame(name, time, intro, id));
                                adminOfflineSet.add(name);
                            }
                        }
                        AttentionGameAdapterInPersonalCenter adp
                                = new AttentionGameAdapterInPersonalCenter(adminMatchList, getActivity());
                        adminOfflineList.setAdapter(adp);
                        adminOfflineList.setVisibility(View.VISIBLE);
                        attentionOfflineLayout.setVisibility(View.GONE);
                        attentionOfflineList.setVisibility(View.GONE);
                        attentionList.setVisibility(View.GONE);
                        isVisible = true;
                        logout.setVisibility(View.GONE);
                        modifyPassword.setVisibility(View.GONE);
                        adminIsVisible = false;
                        offlineIsVisible = true;
                    } else {
                        isVisible = true;
                        adminIsVisible = true;
                        offlineIsVisible = true;
                        modifyPassword.setVisibility(View.VISIBLE);
                        logout.setVisibility(View.VISIBLE);
                        attentionOfflineLayout.setVisibility(View.VISIBLE);
                        adminOfflineList.setVisibility(View.GONE);
                    }
                } else {
                    modifyPassword.setVisibility(View.VISIBLE);
                    logout.setVisibility(View.VISIBLE);
                    attentionOfflineLayout.setVisibility(View.VISIBLE);
                    adminOfflineList.setVisibility(View.GONE);
                    adminIsVisible = true;
                }
            }
        });
        http.execute("GetMySchedule", userNameContent);
    }
}

class AttentionGameAdapterInPersonalCenter extends BaseAdapter {
    private List mData;
    private Context mContext;

    AttentionGameAdapterInPersonalCenter(List mData, Context mContext) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.simple_layout, parent, false);
        TextView gameName = convertView.findViewById(R.id.something);
        final MyGame myGame = (MyGame) mData.get(position);
        gameName.setText(myGame.getGameName());
        return convertView;
    }
}
