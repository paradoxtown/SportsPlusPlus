package com.free.app.spp;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OfflineMatchActivity extends AppCompatActivity {
    private static JSONObject matchRet = null;
    private static JSONArray playerRet = null;
    private static String schedule_id;
    private static String match_id;
    private String home_team;
    private String away_team;
    private AllMap allMap = new AllMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        match_id = b.getString("id");
        schedule_id = b.getString("schedule_id");
        home_team = b.getString("主场");
        away_team = b.getString("客场");
        getMyMatch(schedule_id, match_id);
        getPlayerSummary(match_id);

        RefreshLayout refreshLayout = findViewById(R.id.container);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                getMyMatch(schedule_id, match_id);
                getPlayerSummary(match_id);
                refreshlayout.finishRefresh(2000);
            }
        });
//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadMore(2000);
//            }
//        });
    }

    private void getMyMatch(String schedule_id, String match_id) {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray games) throws JSONException, IOException {
                matchRet = games.getJSONObject(0);
                OfflineMatchActivity.this.setHeadView();
                OfflineMatchActivity.this.setSumTableConfig();
            }
        });
        http.execute("GetMyMatch", schedule_id, match_id);
    }

    private void getPlayerSummary(String id) {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray games) throws JSONException {
                playerRet = games;
                OfflineMatchActivity.this.setTeamTableConfig(playerRet);
            }
        });
        http.execute("GetPlayer", id);
    }

    void setHeadView() throws JSONException {
        away_team = matchRet.getString("客场");
        home_team = matchRet.getString("主场");
        setTextView(matchRet.getString("客场总分"), matchRet.getString("主场总分"));
    }

    private void setTextView(String score1, String score2) {
        TextView textView_vs = findViewById(R.id.textView_vs);
        TextView textView_score1 = findViewById(R.id.textView_score1);
        TextView textView_score2 = findViewById(R.id.textView_score2);
        textView_vs.setTextColor(Color.WHITE);
        textView_score1.setText(score1);
        textView_score2.setText(score2);
        textView_score1.setTextColor(Color.WHITE);
        textView_score2.setTextColor(Color.WHITE);
        textView_vs.setTextSize(25);
        textView_vs.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView_score1.setTextSize(25);
        textView_score1.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView_score2.setTextSize(25);
        textView_score2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    private List<GameItem> setSumList() throws JSONException {
        List<GameItem> list = new ArrayList<>();
        list.add(new GameItem(matchRet.getString("客场"), matchRet.getString("客场第一节"),
                matchRet.getString("客场第二节"), matchRet.getString("客场第三节"), matchRet.getString("客场第四节"),
                matchRet.getString("客场加时1"), matchRet.getString("客场加时2"), matchRet.getString("客场加时3"),
                matchRet.getString("客场加时4"), matchRet.getString("客场总分")));
        list.add(new GameItem(matchRet.getString("主场"), matchRet.getString("主场第一节"),
                matchRet.getString("主场第二节"), matchRet.getString("主场第三节"), matchRet.getString("主场第四节"),
                matchRet.getString("主场加时1"), matchRet.getString("主场加时2"), matchRet.getString("主场加时3"),
                matchRet.getString("主场加时4"), matchRet.getString("主场总分")));
        return list;
    }

    private TableData initSumTableData(List<GameItem> list) throws JSONException {
        final Column<String> nameCol = new Column<>("球队", "team_name");
        final Column<String> sec1Col = new Column<>("第1节", "sec1");
        final Column<String> sec2Col = new Column<>("第2节", "sec2");
        final Column<String> sec3Col = new Column<>("第3节", "sec3");
        final Column<String> sec4Col = new Column<>("第4节", "sec4");
        final Column<String> totCol = new Column<>("总分", "total");
        nameCol.setFixed(true);
        if (!matchRet.getString("客场加时1").equals("0")) {
            final Column<String> extra1Col = new Column<>("加时一", "extra1");
            if (!matchRet.getString("客场加时2").equals("0")) {
                final Column<String> extra2Col = new Column<>("加时二", "extra2");
                if (!matchRet.getString("客场加时3").equals("0")) {
                    final Column<String> extra3Col = new Column<>("加时三", "extra3");
                    if (!matchRet.getString("客场加时4").equals("0")) {
                        final Column<String> extra4Col = new Column<>("加时四", "extra4");
                        return new TableData<>("总览", list, nameCol, sec1Col, sec2Col, sec3Col, sec4Col, extra1Col, extra2Col, extra3Col, extra4Col, totCol);
                    } else {
                        return new TableData<>("总览", list, nameCol, sec1Col, sec2Col, sec3Col, sec4Col, extra1Col, extra2Col, extra3Col, totCol);
                    }
                } else {
                    return new TableData<>("总览", list, nameCol, sec1Col, sec2Col, sec3Col, sec4Col, extra1Col, extra2Col, totCol);
                }
            } else {
                return new TableData<>("总览", list, nameCol, sec1Col, sec2Col, sec3Col, sec4Col, extra1Col, totCol);
            }
        }
        return new TableData<>("总览", list, nameCol, sec1Col, sec2Col, sec3Col, sec4Col, totCol);
    }

    private void setSumTableConfig() throws JSONException {
        WindowManager wm = this.getWindowManager();
        SmartTable table_sum = findViewById(R.id.table_sum);
        TableData sumTableData = initSumTableData(setSumList());
        table_sum.setTableData(sumTableData);
        table_sum.getConfig().setMinTableWidth(wm.getDefaultDisplay().getWidth());
        table_sum.getConfig().setColumnTitleStyle(new FontStyle(36, Color.WHITE));
        table_sum.getConfig().setContentStyle(new FontStyle(36, Color.GRAY));
        table_sum.getConfig().setTableTitleStyle(new FontStyle(36, Color.BLACK));
        table_sum.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(Color.BLACK));
        table_sum.getConfig().setContentCellBackgroundFormat(new ICellBackgroundFormat<CellInfo>() {
            @Override
            public void drawBackground(Canvas canvas, Rect rect, CellInfo cellInfo, Paint paint) {
                if (cellInfo.row % 2 == 0) {
                    paint.setColor(Color.argb(20, 88, 88, 88));
                    canvas.drawRect(rect, paint);
                }
            }

            @Override
            public int getTextColor(CellInfo cellInfo) {
                return 0;
            }
        });
        table_sum.getConfig().setShowXSequence(false);
        table_sum.getConfig().setShowYSequence(false);
    }

    private TableData initTableData(List<PlayerItem> list, String home_away) {
        final Column<String> nameCol = new Column<>("姓名", "name");
        final Column<String> posCol = new Column<>("位置", "pos");
//        final Column<String> shotCol = new Column<>("投篮", "shots");
        final Column<String> tPointsCol = new Column<>("三分", "threePoints");
        final Column<String> pShotCol = new Column<>("罚球", "penaltyShots");
        final Column<String> rebCol = new Column<>("篮板", "rebs");
        final Column<String> assistsCol = new Column<>("助攻", "assists");
//        final Column<String> foulsCol = new Column<>("犯规", "fouls");
        final Column<String> faultsCol = new Column<>("失误", "faults");
        final Column<String> scoreCol = new Column<>("得分", "score");
        nameCol.setFixed(true);
        return new TableData<>(home_away, list, nameCol, posCol, tPointsCol,
                pShotCol, rebCol, assistsCol,
                faultsCol, scoreCol);
    }

    private PlayerItem getPlayerItemObject(JSONObject player) throws JSONException {
        return new PlayerItem(player.getString("球员名"), player.getString("位置"),
                "", "未记录",
                player.getString("三分"), player.getString("罚球"),
                "", "",
                player.getString("篮板"), player.getString("助攻"),
                "未记录", player.getString("抢断"),
                player.getString("失误"), "无信息",
                player.getString("得分"), "");
    }

    private void setTeamTableConfig(JSONArray data) throws JSONException {
        List<PlayerItem> home_list = new ArrayList<>();
        List<PlayerItem> away_list = new ArrayList<>();
        for (int i = 0; i < data.length(); i++) {
            JSONObject playerData = data.getJSONObject(i);
            if (playerData.getString("球队名").equals(home_team)) {
                home_list.add(getPlayerItemObject(playerData));
            } else away_list.add(getPlayerItemObject(playerData));
        }
        SmartTable table1 = findViewById(R.id.table1);
        SmartTable table2 = findViewById(R.id.table2);
        TableData tableData1 = initTableData(away_list, "客场");
        TableData tableData2 = initTableData(home_list, "主场");
        table1.setTableData(tableData1);
        table2.setTableData(tableData2);
        table1.getConfig().setColumnTitleStyle(new FontStyle(36, Color.WHITE));
        table1.getConfig().setContentStyle(new FontStyle(36, Color.GRAY));
        table1.getConfig().setTableTitleStyle(new FontStyle(36, Color.BLACK));
        table1.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(Color.BLACK));
        table1.getConfig().setContentCellBackgroundFormat(new ICellBackgroundFormat<CellInfo>() {
            @Override
            public void drawBackground(Canvas canvas, Rect rect, CellInfo cellInfo, Paint paint) {
                if (cellInfo.row % 2 == 0) {
                    paint.setColor(Color.argb(20, 88, 88, 88));
                    canvas.drawRect(rect, paint);
                }
            }

            @Override
            public int getTextColor(CellInfo cellInfo) {
                return 0;
            }
        });
        table2.getConfig().setColumnTitleStyle(new FontStyle(36, Color.WHITE));
        table2.getConfig().setContentStyle(new FontStyle(36, Color.GRAY));
        table2.getConfig().setTableTitleStyle(new FontStyle(36, Color.BLACK));
        table2.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(Color.BLACK));
        table2.getConfig().setContentCellBackgroundFormat(new ICellBackgroundFormat<CellInfo>() {
            @Override
            public void drawBackground(Canvas canvas, Rect rect, CellInfo cellInfo, Paint paint) {
                if (cellInfo.row % 2 == 0) {
                    paint.setColor(Color.argb(20, 88, 88, 88));
                    canvas.drawRect(rect, paint);
                }
            }

            @Override
            public int getTextColor(CellInfo cellInfo) {
                return 0;
            }
        });
        table1.getConfig().setShowXSequence(false);
        table1.getConfig().setShowYSequence(false);
        table2.getConfig().setShowXSequence(false);
        table2.getConfig().setShowYSequence(false);
    }
}
