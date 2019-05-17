package com.free.app.spp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerResultsIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;

public class RecorderActivity extends AppCompatActivity {
    static List recordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);
        recordList.add("队伍一");
        for (int i = 0; i < 10; i++) {
            recordList.add(new RecorderPlayer());
        }
        recordList.add("队伍二");
        for (int i = 0; i < 10; i++) {
            recordList.add(new RecorderPlayer());
        }
        Intent i = getIntent();
        String first = i.getStringExtra("第一节");
        EditText text1 = (EditText)findViewById(R.id.editText5);
        EditText text2 = (EditText)findViewById(R.id.editText2);
        EditText text3 = (EditText)findViewById(R.id.editText3);
        EditText text4 = (EditText)findViewById(R.id.editText4);

        final RecorderAdapter recorderAdapter = new RecorderAdapter(recordList, this);
        final ListView players = findViewById(R.id.my_game_players);
        players.setAdapter(recorderAdapter);
        Button updateButton = findViewById(R.id.update_data);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // players.setAdapter(recorderAdapter);
                System.out.println("###############################");
            }
        });
    }

    public void PUTMyMatch()
    {
        Http <JSONArray> h = new Http<>();
        h.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException, IOException {
                System.out.println("success");
            }
        });
        h.execute("PUTMyMatch");
    }
}

class RecorderAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    // private List mData;
    private Context mContext;
    private final int ITEM_NORMAL = 0;
    private final int ITEM_SECTION = 1;

    RecorderAdapter(List mData, Context mContext) {
        // this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return RecorderActivity.recordList.size();
    }

    @Override
    public Object getItem(int position) {
        return RecorderActivity.recordList.get(position);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (RecorderActivity.recordList.get(position) instanceof RecorderPlayer) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_recorder_player, parent, false);
            convertView.setBackgroundColor(Color.WHITE);
            RecorderPlayer recorderPlayer = (RecorderPlayer) RecorderActivity.recordList.get(position);
            EditText playerName = convertView.findViewById(R.id.player_name_input);
            EditText playerPos = convertView.findViewById(R.id.player_pos_input);
            EditText playerNum = convertView.findViewById(R.id.player_number_input);
            EditText playerScore = convertView.findViewById(R.id.player_score_input);
            EditText playerTp = convertView.findViewById(R.id.player_tp_input);
            EditText playerReb = convertView.findViewById(R.id.player_reb_input);
            EditText playerAss = convertView.findViewById(R.id.player_assist_input);
            EditText playerPen = convertView.findViewById(R.id.player_pen_input);
            EditText playerFoul = convertView.findViewById(R.id.player_foul_input);
            EditText playerFault = convertView.findViewById(R.id.player_fault_input);
            recorderPlayer.setPlayerName(playerName.getText().toString());
            recorderPlayer.setPosition(playerPos.getText().toString());
            recorderPlayer.setNumber(playerNum.getText().toString());
            recorderPlayer.setScore(playerScore.getText().toString());
            recorderPlayer.setThreePoint(playerTp.getText().toString());
            recorderPlayer.setReb(playerReb.getText().toString());
            recorderPlayer.setAssist(playerAss.getText().toString());
            recorderPlayer.setPen(playerPen.getText().toString());
            recorderPlayer.setFoul(playerFoul.getText().toString());
            recorderPlayer.setFault_(playerFault.getText().toString());
        } else if (itemViewType == ITEM_SECTION) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_recorder_team, parent, false);
            convertView.setBackgroundColor(Color.rgb(240, 240, 240));
            LinearLayout linearLayout = convertView.findViewById(R.id.recorder_section);
            TextView team1 = convertView.findViewById(R.id.recorder_team);
            String teamName = (String) RecorderActivity.recordList.get(position);
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

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == ITEM_SECTION;
    }
}

class RecorderPlayer {
    private String playerName, position, number, score, threePoint, reb, assist, pen, foul, fault_;
    RecorderPlayer() {

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
}
