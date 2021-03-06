package com.free.app.spp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gjiazhe.multichoicescirclebutton.MultiChoicesCircleButton;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.halfbit.pinnedsection.PinnedSectionListView;

public class MyGameActivity extends AppCompatActivity {
    private boolean is_admin = true;
    private ArrayList mData = new ArrayList<>();
    private ListView matches;
    private String UserName;
    private String schedule_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_game);
        Intent i = getIntent();
        MyGame mg = (MyGame) i.getSerializableExtra("MyGame");
        is_admin = mg.getIs_administrator();
        UserName = i.getStringExtra("UserName");
        schedule_id = mg.getId();
        setMultiSelect();
        GetMyMatch();
        TextView myGameName = findViewById(R.id.my_match_name);
        TextView myGameIntro = findViewById(R.id.my_match_intro);
        myGameName.setText(mg.getGameName());
        myGameIntro.setText(mg.getIntro());
        RefreshLayout refreshLayout = findViewById(R.id.my_game_activity_refresh);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                GetMyMatch();
                refreshlayout.finishRefresh(2000);
            }
        });
        dataHandle();
    }

    void setMultiSelect() {
        final MultiChoicesCircleButton.Item item1 = new MultiChoicesCircleButton.Item("Exit", getResources().getDrawable(R.drawable.icon1), 30);

        final MultiChoicesCircleButton.Item item2 = new MultiChoicesCircleButton.Item("Add Match", getResources().getDrawable(R.drawable.icon2), 90);

        final MultiChoicesCircleButton.Item item3 = new MultiChoicesCircleButton.Item("Delete Game", getResources().getDrawable(R.drawable.icon3), 150);

        List<MultiChoicesCircleButton.Item> buttonItems = new ArrayList<>();
        buttonItems.add(item1);
        buttonItems.add(item2);
        buttonItems.add(item3);

        MultiChoicesCircleButton multiChoicesCircleButton = findViewById(R.id.multiChoicesCircleButton);
        multiChoicesCircleButton.setButtonItems(buttonItems);

        multiChoicesCircleButton.setOnSelectedItemListener(new MultiChoicesCircleButton.OnSelectedItemListener() {
            @Override
            public void onSelected(MultiChoicesCircleButton.Item item, int index) {
                if (item == item1) {
                    finish();
                } else if (item == item2) {
                    if (is_admin) {
                        CreateDialog();
                    } else {
                        Toast.makeText(MyGameActivity.this, "您没有权限", Toast.LENGTH_LONG).show();
                    }
                } else if (item == item3) {
                    if (is_admin) {
                        deleteGame();
                    } else {
                        Toast.makeText(MyGameActivity.this, "您没有权限", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    void deleteGame() {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                if (jsonArray.getJSONObject(0).getInt("result") == 1){
                    Intent intent = getIntent();
                    System.out.println("game_id " + schedule_id);
                    intent.putExtra("game_id", schedule_id);
                    // TODO: delete
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    Toast.makeText(MyGameActivity.this, "删除失败", Toast.LENGTH_LONG).show();
                }
            }
        });
        http.execute("DeleteSchedule", schedule_id);
    }

    protected void dataHandle() {
        MyGameMatchAdapter myGameMatchAdapter = new MyGameMatchAdapter(mData, this, is_admin);
        matches = findViewById(R.id.list_my_game_match);
        matches.setAdapter(myGameMatchAdapter);
        matches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if (!(mData.get(position) == MatchDate.class)) {
                    if (is_admin) {
                        intent = new Intent(MyGameActivity.this, RecorderActivity.class);
                    } else {
                        intent = new Intent(MyGameActivity.this, OfflineMatchActivity.class);
                    }
                    try {
                        PassData(intent, (MyGameMatch) mData.get(position));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);
                }
            }
        });
    }

    protected void PassData(Intent i, MyGameMatch t) throws JSONException {
        JSONObject j = t.getJs();
        Iterator<String> keys = j.keys();
        while (keys.hasNext()) {
            String teams = keys.next();
            String teamsInfo = j.optString(teams);
            i.putExtra(teams, teamsInfo);
        }
        i.putExtra("schedule_id", schedule_id);
    }

    protected void POSTMyMatch(String time, String date, String pos, String host, String guest) {
        Http<JSONArray> h = new Http<>();
        h.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                System.out.println("success");
            }
        });
        h.execute("POSTMyMatch", schedule_id, time, date, pos, host, guest);
    }

    @SuppressWarnings("unchecked")
    protected void GetMyMatch() {
        Http<JSONArray> h = new Http<>();
        h.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
            mData = new ArrayList<>();
            System.out.println("Successfully obtained");
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject j = jsonArray.getJSONObject(i);
                        String date = j.getString("日期");
                        String time = j.getString("时间");
                        String pos = j.getString("地点");
                        String id = j.getString("id");
                        String teamA = j.getString("主场");
                        String teamB = j.getString("客场");
                        String scoreA = j.getString("主场总分");
                        String scoreB = j.getString("客场总分");
                        mData.add(new MyGameMatch(teamA, teamB, date, time, pos, scoreA, scoreB, j, id));
                    }
                    MyGameMatchAdapter adp = new MyGameMatchAdapter(mData, MyGameActivity.this, is_admin);
                    matches.setAdapter(adp);
                }
            }
        });
        h.execute("GetMyMatch", schedule_id, "");
    }

    public boolean match(String A, String std) {
        Pattern p = Pattern.compile(std);
        Matcher m = p.matcher(A);
        return m.matches();
    }

    public boolean isLegalDate(String d) {
        String[] ss = d.split("-");
        int year = Integer.parseInt(ss[0]);
        int month = Integer.parseInt(ss[1]);
        int day = Integer.parseInt(ss[2]);
        if ((year % 4 == 0 && year % 100 != 0) || (year % 100 == 0 && year % 400 == 0)) {
            if (month == 2 && day > 29) return false;
        } else {
            if (month == 2 && day > 28) return false;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day > 30) return false;
        }
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            if (day > 31) return false;
        }
        Calendar cal = Calendar.getInstance();
        int nowyear = cal.get(Calendar.YEAR);
        int nowmonth = cal.get(Calendar.MONTH) + 1;
        int nowday = cal.get(Calendar.DAY_OF_MONTH);
        if (year < nowyear) return false;
        if (month < nowmonth) return false;
        return month < nowmonth || day >= nowday;
    }

    protected void CreateDialog() {
        LayoutInflater li = LayoutInflater.from(MyGameActivity.this);
        final View v = li.inflate(R.layout.new_arrange_dialog, null);
        AlertDialog.Builder ad = new AlertDialog.Builder(MyGameActivity.this);
        ad.setIcon(R.drawable.basketball);
        ad.setTitle("新的赛程安排");
        ad.setMessage("请输入新赛程的相关信息");
        ad.setView(v);
        ad.setNegativeButton("取消", null);
        ad.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText time_edit = v.findViewById(R.id.time_edit);
                EditText date_edit = v.findViewById(R.id.date_edit);
                EditText teamA_edit = v.findViewById(R.id.teamA_edit);
                EditText teamB_edit = v.findViewById(R.id.teamB_edit);
                EditText pos_edit = v.findViewById(R.id.pos_edit);
                String time = time_edit.getText().toString().trim();
                String date = date_edit.getText().toString().trim();
                String teamA = teamA_edit.getText().toString().trim();
                String teamB = teamB_edit.getText().toString().trim();
                String pos = pos_edit.getText().toString().trim();
                boolean mark = true;
                if (time.contentEquals("") || date.contentEquals("")
                        || teamA.contentEquals("") || teamB.contentEquals("") || pos.contentEquals("")) {
                    Toast.makeText(MyGameActivity.this, "请输入完整的比赛信息！", Toast.LENGTH_LONG).show();
                    return;
                }
                if (teamA.contentEquals(teamB)) {
                    Toast.makeText(MyGameActivity.this, "两个队伍的队伍名不能相同！", Toast.LENGTH_LONG).show();
                    return;
                }
                if (teamA.length() > 10 || teamB.length() > 10) {
                    Toast.makeText(MyGameActivity.this, "比赛队伍的名字不能超过10个字符", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pos.length() > 10) {
                    Toast.makeText(MyGameActivity.this, "比赛地点的名字不能超过10个字符", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!match(time, "^(([0-1][0-9])|(2[0-4])):[0-5][0-9]$")) {
                    Toast.makeText(MyGameActivity.this, "请正确输入形如HH:MM的时间格式", Toast.LENGTH_LONG).show();
                    mark = false;
                }
                if (!match(date, "^[0-9]{4}\\-((0[1-9])|(1[0-2]))\\-[0-9]{2}$")) {
                    Toast.makeText(MyGameActivity.this, "请正确输入形如YYYY-MM-DD的日期格式", Toast.LENGTH_LONG).show();
                    mark = false;
                }
                if (!match(date, "^[0-9]{4}\\-((0[1-9])|(1[0-2]))\\-[0-9]{2}$") && !isLegalDate(date)) {
                    Toast.makeText(MyGameActivity.this, "请正确输入合法的日期", Toast.LENGTH_LONG).show();
                    mark = false;
                }
                if (mark) {
                    Toast.makeText(MyGameActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                    POSTMyMatch(time, date, pos, teamA, teamB);
                    GetMyMatch();
                }
            }
        }).setCancelable(true);
        //设置按钮是否可以按返回键取消,false则不可以取消
        AlertDialog dialog = ad.create();  //创建对话框
        dialog.setCanceledOnTouchOutside(true); //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
        dialog.show();
    }
}


class MyGameMatchAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    private ArrayList mData;
    private Context mContext;
    private final int ITEM_NORMAL = 0;
    private final int ITEM_SECTION = 1;
    private boolean isAdmin = false;

    MyGameMatchAdapter(ArrayList mData, Context mContext, boolean isAdmin) {
        this.mData = mData;
        this.mContext = mContext;
        this.isAdmin = isAdmin;
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
        } else {
            return ITEM_SECTION;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void deleteMatch(MyGameMatch myGameMatch) {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException, IOException {
                if (jsonArray.getJSONObject(0).getInt("result") == 1) {
                    Toast.makeText(mContext, "删除成功", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(mContext, "删除失败", Toast.LENGTH_LONG).show();
                }
            }
        });
        http.execute("DeleteMatch", myGameMatch.getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == ITEM_NORMAL) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_my_game_match, parent, false);
            convertView.setBackgroundColor(Color.WHITE);
            LinearLayout myGameMatchItem = convertView.findViewById(R.id.my_game_match_item);
            TextView teamName1 = convertView.findViewById(R.id.team_name1);
            TextView teamName2 = convertView.findViewById(R.id.team_name2);
            TextView teamScore1 = convertView.findViewById(R.id.team_points1);
            TextView teamScore2 = convertView.findViewById(R.id.team_points2);
            TextView time = convertView.findViewById(R.id.my_game_time);
            TextView place = convertView.findViewById(R.id.my_game_place);

            myGameMatchItem.setBackgroundColor(Color.rgb(240, 240, 240));
            final MyGameMatch a = (MyGameMatch) mData.get(position);
            String p1 = a.getScore1();
            String p2 = a.getScore2();
            teamName1.setText(a.getTeamName1());
            teamName1.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            teamName2.setText(a.getTeamName2());
            teamName2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            teamScore1.setText(p1);
            teamScore1.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            teamScore2.setText(p2);
            teamScore2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            time.setText(a.getTime());
            place.setText(a.getPlace());

            final TextView deleteOneMatch = convertView.findViewById(R.id.delete_match);
            if (isAdmin) {
                deleteOneMatch.setVisibility(View.VISIBLE);
            }
            else {
                deleteOneMatch.setVisibility(View.GONE);
            }
            deleteOneMatch.setFocusable(false);
            deleteOneMatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteMatch(a);
                }
            });
        } else if (itemViewType == ITEM_SECTION) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_date, parent, false);
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