package com.free.app.spp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerResultsIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.halfbit.pinnedsection.PinnedSectionListView;

public class RecorderActivity extends AppCompatActivity {
    ArrayList recordList = new ArrayList<>();
    ArrayList teamAList = new ArrayList<>();
    ArrayList teamBList = new ArrayList<>();
    String match_id;
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText editText6;
    EditText editText7;
    EditText editText8;
    EditText editText9;
    EditText editText10;
    EditText editText11;
    EditText editText12;
    EditText editText13;
    EditText editText14;
    EditText editText15;
    EditText editText16;
    String teamA;
    String teamB;
    ListView players;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);
        AddAllToRecord();
        Intent i = getIntent();
        match_id = i.getStringExtra("id");
        teamA = i.getStringExtra("主场");
        teamB = i.getStringExtra("客场");
        String first = i.getStringExtra("第一节");
        try {
            GETAllplayer();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        editText1 = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);
        editText3 = (EditText)findViewById(R.id.editText3);
        editText4 = (EditText)findViewById(R.id.editText4);
        editText5 = (EditText)findViewById(R.id.editText5);
        editText6 = (EditText)findViewById(R.id.editText6);
        editText7 = (EditText)findViewById(R.id.editText7);
        editText8 = (EditText)findViewById(R.id.editText8);
        editText9 = (EditText)findViewById(R.id.editText9);
        editText10 = (EditText)findViewById(R.id.editText10);
        editText11 = (EditText)findViewById(R.id.editText11);
        editText12 = (EditText)findViewById(R.id.editText12);
        editText13 = (EditText)findViewById(R.id.editText13);
        editText14 = (EditText)findViewById(R.id.editText14);
        editText15 = (EditText)findViewById(R.id.editText15);
        editText16 = (EditText)findViewById(R.id.editText16);
        display();
        players = findViewById(R.id.my_game_players);
        Button updateButton = findViewById(R.id.update_data);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // players.setAdapter(recorderAdapter);
                try {
                    PUTMyMatch();
                    PUTAllplayer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("###############################");
            }
        });
        Button newPlayerButton = (Button)findViewById(R.id.newPlayerButton);
        newPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDialog();
            }
        });
    }
    protected void CreateDialog() {
        LayoutInflater li = LayoutInflater.from(RecorderActivity.this);
        final View v = li.inflate(R.layout.new_player_dialog, null);
        AlertDialog.Builder ad = new AlertDialog.Builder(RecorderActivity.this);
        ad.setIcon(R.drawable.basketball);
        ad.setTitle("新的球员");
        ad.setMessage("请输入新球员的相关信息");
        ad.setView(v);
        ad.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText player_name_edit = (EditText) v.findViewById(R.id.player_name_edit);
                EditText player_team_edit = (EditText) v.findViewById(R.id.player_team_edit);
                EditText player_pos_edit = (EditText) v.findViewById(R.id.player_pos_edit);
                String name = player_name_edit.getText().toString().trim();
                String team = player_team_edit.getText().toString().trim();
                String pos = player_pos_edit.getText().toString().trim();
                RecorderPlayer rp = new RecorderPlayer(name,pos,"","","","","","","","");
                if(team.contentEquals("team1")){
                    team = teamA;
                    teamAList.add(rp);
                }
                else if(team.contentEquals("team2")){
                    team = teamB;
                    teamBList.add(rp);
                }
                else{
                    Toast.makeText(RecorderActivity.this,"请在所属队伍一栏中输入team1或team2！",Toast.LENGTH_LONG).show();
                    return;
                }
                POSTplayer(team,name,pos);
                try {
                    GETAllplayer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).setCancelable(true);
        //设置按钮是否可以按返回键取消,false则不可以取消
        AlertDialog dialog = ad.create();  //创建对话框
        dialog.setCanceledOnTouchOutside(true); //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
        dialog.show();
    }

    public void AddAllToRecord(){
        recordList = new ArrayList<>();
        for(int i = 0;i < teamAList.size();i++)recordList.add(teamAList.get(i));
        for(int i = 0;i < teamBList.size();i++)recordList.add(teamBList.get(i));
    }

    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public void PUTAllplayer() throws JSONException{
        Http <JSONArray> h = new Http<>();
        h.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException, IOException {
                System.out.println();
            }
        });
        JSONArray total = new JSONArray();
        for(int i = 0;i < recordList.size();i++){
            if(recordList.get(i) instanceof RecorderPlayer){
                RecorderPlayer tmp = (RecorderPlayer)recordList.get(i);
                JSONObject player = new JSONObject();
                player.put("号码",tmp.getNumber());
                player.put("id",tmp.getId());
                player.put("得分",tmp.getScore());
                player.put("篮板",tmp.getReb());

                player.put("三分",tmp.getThreePoint());
                player.put("罚球",tmp.getPen());
                player.put("抢断","未记录");
                player.put("助攻",tmp.getAssist());
                player.put("失误",tmp.getFault_());
                total.put(player);


//            player.put("篮板","1");
//            player.put("助攻","1");
//            player.put("三分","1");
//            player.put("罚球","1");
//            player.put("抢断","1");
//            player.put("助攻","1");
//            player.put("失误","1");
//            player.put("号码","1");
            }
        }
        h.execute("PlayerScore",total.toString());
    }
    public void POSTplayer(String team,String name,String pos){
        Http <JSONArray> h = new Http<>();
        h.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException, IOException {
                Toast.makeText(RecorderActivity.this,"成功添加球员",Toast.LENGTH_LONG).show();
            }
        });
        h.execute("POSTPlayer",match_id,team,name,pos);
    }


    public void GETAllplayer() throws JSONException{
        Http <JSONArray> h = new Http<>();
        h.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException, IOException {
                teamAList = new ArrayList<>();
                teamBList = new ArrayList<>();
                teamAList.add("队伍一");
                teamBList.add("队伍二");
                for(int i = 0;i < jsonArray.length();i++){
                    JSONObject j = jsonArray.getJSONObject(i);
                    String id = j.getString("id");
                    String team = j.getString("球队名");
                    String name = j.getString("球员名");
                    String pos = j.getString("位置");
                    String score = j.getString("得分");
                    String reb = j.getString("篮板");
                    String tp = j.getString("三分");
                    String pen = j.getString("罚球");
                    String ass = j.getString("助攻");
                    String fault = j.getString("失误");
                    String num = j.getString("号码");
                    RecorderPlayer rp = new RecorderPlayer(name,pos,num,score,
                            tp,reb,ass,pen,"0",fault);
                    rp.setId(id);
                    if(team.contentEquals(teamA))teamAList.add(rp);
                    else teamBList.add(rp);
                }
                AddAllToRecord();
                RecorderAdapter recorderAdapter = new RecorderAdapter(recordList, RecorderActivity.this);
                players.setAdapter(recorderAdapter);
            }
        });
        h.execute("GetPlayer",match_id);
    }


    public void PUTMyMatch() throws JSONException {
        Http <JSONArray> h = new Http<>();
        h.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException, IOException {
                System.out.println("success");
            }
        });
        String str1 = editText1.getText().toString().trim();
        String str2 = editText2.getText().toString().trim();
        String str3 = editText3.getText().toString().trim();
        String str4 = editText4.getText().toString().trim();
        int totScoreA = Integer.parseInt(str1) + Integer.parseInt(str2) + Integer.parseInt(str3) + Integer.parseInt(str4);
        int overtime = 0;
        String str5 = editText5.getText().toString().trim();
        String str6 = editText6.getText().toString().trim();
        String str7 = editText7.getText().toString().trim();
        String str8 = editText8.getText().toString().trim();

        String str9 = editText9.getText().toString().trim();
        String str10 = editText10.getText().toString().trim();
        String str11 = editText11.getText().toString().trim();
        String str12 = editText12.getText().toString().trim();
        int totScoreB = Integer.parseInt(str9) + Integer.parseInt(str10) + Integer.parseInt(str11) + Integer.parseInt(str12);

        String str13 = editText13.getText().toString().trim();
        String str14 = editText14.getText().toString().trim();
        String str15 = editText15.getText().toString().trim();
        String str16 = editText16.getText().toString().trim();
        if(!isNumeric(str1)||!isNumeric(str2)||!isNumeric(str3)||!isNumeric(str4)||
        !isNumeric(str5)||!isNumeric(str6)||!isNumeric(str7)||!isNumeric(str8)||
                !isNumeric(str9)||!isNumeric(str10)||!isNumeric(str11)||!isNumeric(str12)||
                !isNumeric(str13)||!isNumeric(str14)||!isNumeric(str15)||!isNumeric(str16)){
            Toast.makeText(RecorderActivity.this,"得分处请输入自然数！",Toast.LENGTH_LONG);
        }
        if(!str5.contentEquals("0")){overtime++;totScoreA += Integer.parseInt(str5);totScoreB += Integer.parseInt(str13);}
        if(!str6.contentEquals("0")){overtime++;totScoreA += Integer.parseInt(str6);totScoreB += Integer.parseInt(str14);}
        if(!str7.contentEquals("0")){overtime++;totScoreA += Integer.parseInt(str7);totScoreB += Integer.parseInt(str15);}
        if(!str8.contentEquals("0")){overtime++;;totScoreA += Integer.parseInt(str8);totScoreB += Integer.parseInt(str16);}
        String strA = String.valueOf(totScoreA);String strB = String.valueOf(totScoreB);
        String ot = String.valueOf(overtime);
        JSONObject total = new JSONObject();
        total.put("matchid",match_id);

            total.put("home1",str1);
            total.put("home2",str2);
            total.put("home3",str3);
            total.put("home4",str4);
            total.put("home5",str5);
            total.put("home6",str6);
            total.put("home7",str7);
            total.put("home8",str8);

            total.put("away1",str9);
            total.put("away2",str10);
            total.put("away3",str11);
            total.put("away4",str12);
            total.put("away5",str13);
            total.put("away6",str14);
            total.put("away7",str15);
            total.put("away8",str16);

            total.put("OT",ot);
            total.put("home_total",strA);
            total.put("away_total",strB);

        h.execute("MatchScore",total.toString());
    }
    public void display()
    {
        Intent i = getIntent();
        editText1.setText(i.getStringExtra("主场第一节"));
        editText2.setText(i.getStringExtra("主场第二节"));
        editText3.setText(i.getStringExtra("主场第三节"));
        editText4.setText(i.getStringExtra("主场第四节"));
        editText5.setText(i.getStringExtra("主场加时1"));
        editText6.setText(i.getStringExtra("主场加时2"));
        editText7.setText(i.getStringExtra("主场加时3"));
        editText8.setText(i.getStringExtra("主场加时4"));
        editText9.setText(i.getStringExtra("客场第一节"));
        editText10.setText(i.getStringExtra("客场第二节"));
        editText11.setText(i.getStringExtra("客场第三节"));
        editText12.setText(i.getStringExtra("客场第四节"));
        editText13.setText(i.getStringExtra("客场加时1"));
        editText14.setText(i.getStringExtra("客场加时2"));
        editText15.setText(i.getStringExtra("客场加时3"));
        editText16.setText(i.getStringExtra("客场加时4"));
    }
}

class RecorderAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
     private ArrayList mData;
    private Context mContext;
    private final int ITEM_NORMAL = 0;
    private final int ITEM_SECTION = 1;

    RecorderAdapter(ArrayList mData, Context mContext) {
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof RecorderPlayer) {
            return ITEM_NORMAL;
        } else {
            return ITEM_SECTION;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == ITEM_NORMAL) {
            final RecorderPlayer rp = (RecorderPlayer)mData.get(position);
            ViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_recorder_player, parent, false);
                convertView.setBackgroundColor(Color.WHITE);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            }
            else {
                vh = (ViewHolder)convertView.getTag();
            }
            vh.playerName.clearFocus();
            vh.playerPos.clearFocus();
            vh.playerNum.clearFocus();
            vh.playerScore.clearFocus();
            vh.playerReb.clearFocus();
            vh.playerTp.clearFocus();
            vh.playerAss.clearFocus();
            vh.playerPen.clearFocus();
            vh.playerFoul.clearFocus();
            vh.playerFault.clearFocus();
            if (vh.playerName.getTag() instanceof TextWatcher) {
                vh.playerName.removeTextChangedListener((TextWatcher) (vh.playerName.getTag()));
            }
            if (vh.playerPos.getTag() instanceof TextWatcher) {
                vh.playerPos.removeTextChangedListener((TextWatcher) (vh.playerPos.getTag()));
            }
            if (vh.playerNum.getTag() instanceof TextWatcher) {
                vh.playerNum.removeTextChangedListener((TextWatcher) (vh.playerNum.getTag()));
            }
            if (vh.playerScore.getTag() instanceof TextWatcher) {
                vh.playerScore.removeTextChangedListener((TextWatcher) (vh.playerScore.getTag()));
            }
            if (vh.playerReb.getTag() instanceof TextWatcher) {
                vh.playerReb.removeTextChangedListener((TextWatcher) (vh.playerReb.getTag()));
            }
            if (vh.playerTp.getTag() instanceof TextWatcher) {
                vh.playerTp.removeTextChangedListener((TextWatcher) (vh.playerTp.getTag()));
            }
            if (vh.playerAss.getTag() instanceof TextWatcher) {
                vh.playerAss.removeTextChangedListener((TextWatcher) (vh.playerAss.getTag()));
            }
            if (vh.playerPen.getTag() instanceof TextWatcher) {
                vh.playerPen.removeTextChangedListener((TextWatcher) (vh.playerPen.getTag()));
            }
            if (vh.playerFoul.getTag() instanceof TextWatcher) {
                vh.playerFoul.removeTextChangedListener((TextWatcher) (vh.playerFoul.getTag()));
            }
            if (vh.playerFault.getTag() instanceof TextWatcher) {
                vh.playerFault.removeTextChangedListener((TextWatcher) (vh.playerFault.getTag()));
            }
            vh.playerName.setText(TextUtils.isEmpty(rp.getPlayerName())?"":rp.getPlayerName());
            vh.playerPos.setText(TextUtils.isEmpty(rp.getPosition())?"":rp.getPosition());
            vh.playerNum.setText(TextUtils.isEmpty(rp.getNumber())?"":rp.getNumber());
            vh.playerScore.setText(TextUtils.isEmpty(rp.getScore())?"":rp.getScore());
            vh.playerReb.setText(TextUtils.isEmpty(rp.getReb())?"":rp.getReb());
            vh.playerTp.setText(TextUtils.isEmpty(rp.getThreePoint())?"":rp.getThreePoint());
            vh.playerAss.setText(TextUtils.isEmpty(rp.getAssist())?"":rp.getAssist());
            vh.playerPen.setText(TextUtils.isEmpty(rp.getPen())?"":rp.getPen());
            vh.playerFoul.setText(TextUtils.isEmpty(rp.getFoul())?"":rp.getFoul());
            vh.playerFault.setText(TextUtils.isEmpty(rp.getFault_())?"":rp.getFault_());
            TextWatcher nameWatcher = new MyTextChangeListener() {
                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        rp.setPlayerName(null);
                    } else {
                        rp.setPlayerName(String.valueOf(s));
                    }
                }
            };
            TextWatcher posWatcher = new MyTextChangeListener() {
                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        rp.setPosition(null);
                    } else {
                        rp.setPosition(String.valueOf(s));
                    }
                }
            };
            TextWatcher numWatcher = new MyTextChangeListener() {
                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        rp.setNumber(null);
                    } else {
                        rp.setNumber(String.valueOf(s));
                    }
                }
            };
            TextWatcher scoreWatcher = new MyTextChangeListener() {
                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        rp.setScore(null);
                    } else {
                        rp.setScore(String.valueOf(s));
                    }
                }
            };
            TextWatcher rebWatcher = new MyTextChangeListener() {
                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        rp.setReb(null);
                    } else {
                        rp.setReb(String.valueOf(s));
                    }
                }
            };
            TextWatcher tpWatcher = new MyTextChangeListener() {
                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        rp.setThreePoint(null);
                    } else {
                        rp.setThreePoint(String.valueOf(s));
                    }
                }
            };
            TextWatcher assWatcher = new MyTextChangeListener() {
                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        rp.setAssist(null);
                    } else {
                        rp.setAssist(String.valueOf(s));
                    }
                }
            };
            TextWatcher penWatcher = new MyTextChangeListener() {
                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        rp.setPen(null);
                    } else {
                        rp.setPen(String.valueOf(s));
                    }
                }
            };
            TextWatcher foulWatcher = new MyTextChangeListener() {
                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        rp.setFoul(null);
                    } else {
                        rp.setFoul(String.valueOf(s));
                    }
                }
            };
            TextWatcher faultWatcher = new MyTextChangeListener() {
                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        rp.setFault_(null);
                    } else {
                        rp.setFault_(String.valueOf(s));
                    }
                }
            };
            vh.playerName.addTextChangedListener(nameWatcher);
            vh.playerName.setTag(nameWatcher);
            vh.playerReb.addTextChangedListener(rebWatcher);
            vh.playerReb.setTag(rebWatcher);
            vh.playerScore.addTextChangedListener(scoreWatcher);
            vh.playerScore.setTag(scoreWatcher);
            vh.playerTp.addTextChangedListener(tpWatcher);
            vh.playerTp.setTag(tpWatcher);
            vh.playerPen.addTextChangedListener(penWatcher);
            vh.playerPen.setTag(penWatcher);
            vh.playerAss.addTextChangedListener(assWatcher);
            vh.playerAss.setTag(assWatcher);
            vh.playerPos.addTextChangedListener(posWatcher);
            vh.playerPos.setTag(posWatcher);
            vh.playerFoul.addTextChangedListener(foulWatcher);
            vh.playerFoul.setTag(foulWatcher);
            vh.playerFault.addTextChangedListener(faultWatcher);
            vh.playerFault.setTag(faultWatcher);
            vh.playerNum.addTextChangedListener(numWatcher);
            vh.playerNum.setTag(numWatcher);

        }
        else if (itemViewType == ITEM_SECTION) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_recorder_team, parent, false);
            convertView.setBackgroundColor(Color.rgb(240, 240, 240));
            LinearLayout linearLayout = convertView.findViewById(R.id.recorder_section);
            TextView team1 = convertView.findViewById(R.id.recorder_team);
            String teamName = (String) mData.get(position);
            if (teamName.equals("队伍一")) {
                linearLayout.setBackgroundResource(R.color.team_1_pink);
            }
            else {
                linearLayout.setBackgroundResource(R.color.team_2_blue);
            }
            team1.setText(teamName);
        }
        return convertView;
    }
    class ViewHolder {
        EditText playerName;
        EditText playerPos;
        EditText playerNum;
        EditText playerScore;
        EditText playerTp;
        EditText playerReb;
        EditText playerAss;
        EditText playerPen;
        EditText playerFoul;
        EditText playerFault;
        ViewHolder(View convertView){
            playerName = convertView.findViewById(R.id.player_name_input);
            playerPos = convertView.findViewById(R.id.player_pos_input);
            playerNum = convertView.findViewById(R.id.player_number_input);
            playerScore = convertView.findViewById(R.id.player_score_input);
            playerTp = convertView.findViewById(R.id.player_tp_input);
            playerReb = convertView.findViewById(R.id.player_reb_input);
            playerAss = convertView.findViewById(R.id.player_assist_input);
            playerPen = convertView.findViewById(R.id.player_pen_input);
            playerFoul = convertView.findViewById(R.id.player_foul_input);
            playerFault = convertView.findViewById(R.id.player_fault_input);
        }
    }

    private abstract class MyTextChangeListener implements TextWatcher {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }
    }
    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == ITEM_SECTION;
    }
}

class RecorderPlayer {
    private String playerName, position, number, score, threePoint, reb, assist, pen, foul, fault_,id;
    RecorderPlayer(String pn,String ps,String num,String sc,
                   String tp,String r,String ass,String p,String fou,String fau) {
            playerName = pn;
            position = ps;
            number = num;
            score = sc;
            threePoint = tp;
            reb = r;
            assist = ass;
            pen = p;
            foul = fou;
            fault_ = fau;
            id = "";
    }

    void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    void setPosition(String position) {
        this.position = position;
    }

    void setNumber(String number) {
        this.number = number;
    }

    void setScore(String score) {
        this.score = score;
    }

    void setThreePoint(String threePoint) {
        this.threePoint = threePoint;
    }

    void setReb(String reb) {
        this.reb = reb;
    }

    void setAssist(String assist) {
        this.assist = assist;
    }

    void setPen(String pen) {
        this.pen = pen;
    }

    void setFoul(String foul) {
        this.foul = foul;
    }

    void setFault_(String fault_) {
        this.fault_ = fault_;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPosition() {
        return position;
    }

    public String getNumber() {
        return number;
    }

    public String getScore() {
        return score;
    }

    public String getThreePoint() {
        return threePoint;
    }

    public String getReb() {
        return reb;
    }

    public String getAssist() {
        return assist;
    }

    public String getPen() {
        return pen;
    }

    public String getFoul() {
        return foul;
    }

    public String getFault_() {
        return fault_;
    }

    public String getId() {
        return id;
    }
}
