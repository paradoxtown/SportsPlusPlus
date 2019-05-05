package com.free.app.spp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;

public class DataActivity extends AppCompatActivity {

    private String name;
    private static JSONArray ret;
    private ArrayList <TeamData> A = new ArrayList<>();
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        this.setTitle("球队数据");
        Toast.makeText(DataActivity.this,"data",Toast.LENGTH_SHORT).show();
        Intent i = getIntent();
        name = i.getStringExtra("cnName");
        System.out.println(name);
        GetTeamInfo();
    }

    private void GetTeamInfo() {
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray teamInfo) throws JSONException {
                ret = teamInfo;
                Handle(ret);
            }
        });
        http.execute("GetTeamInfo", "");
    }
    private void Handle(JSONArray e) throws JSONException {
        for(int i = 0; i < e.length(); i++){
            JSONObject j = e.getJSONObject(i);
            String cn_name = j.getString("球队中文名");
            if(cn_name.contentEquals(Maps.CntoFull.get(name))){
                String coach = j.getString("主教练");
                String intro = j.getString("介绍");
                JSONObject assist = j.getJSONObject("场均助攻");
                JSONObject loss = j.getJSONObject("场均失分");
                JSONObject turnover = j.getJSONObject("场均失误");
                JSONObject points = j.getJSONObject("场均得分");
                JSONObject rebounds = j.getJSONObject("场均篮板");
                String start_year = j.getString("进入NBA");
                String CN_name = j.getString("球队中文名");
                TextView teamCoachName = findViewById(R.id.team_coach_name);
                TextView enterYear = findViewById(R.id.enter_year);
                TextView teamInfo = findViewById(R.id.team_info_content);
                teamCoachName.setText(coach);
                enterYear.setText(start_year);
                teamInfo.setText(intro);

                ColumnChartView chart = findViewById(R.id.chart);
                List<String> title = new ArrayList<>();

                List<Integer> color = new ArrayList<>();

                List<AxisValue> axisXValues = new ArrayList<>();

                List<Column> columns = new ArrayList<>();

                title.add("场均助攻");
                title.add("场均失误");
                title.add("场均得分");
                title.add("场均篮板");

                color.add(Color.parseColor("#D81B60"));
                color.add(Color.parseColor("#00574B"));
                color.add(Color.parseColor("#00bfff"));
                color.add(Color.parseColor("#2F4F4F"));

                for (int t = 0; t < 4; t ++){
                    JSONObject jsonData = j.getJSONObject(title.get(t));
                    float data = 30 - Float.parseFloat(jsonData.getString("排名"));
                    axisXValues.add(new AxisValue(t).setLabel(title.get(t) + ": " + jsonData.getString("数值")));
                    Column column = new Column();
                    List<SubcolumnValue> mPointValues = new ArrayList<>();
                    mPointValues.add(new SubcolumnValue(data, color.get(t)));
                    column.setValues(mPointValues);
                    column.setHasLabels(true);
                    column.setHasLabelsOnlyForSelected(false);
                    columns.add(column);
                }
                ColumnChartData columnChartData = new ColumnChartData(columns);
                Axis axisBottom = new Axis(axisXValues);
                axisBottom.setHasLines(false);
                // axisBottom.setLineColor(Color.parseColor("#ff0000"));
                // axisBottom.setTextColor(Color.parseColor("#666666"));
                axisBottom.setTextSize(10);
                axisBottom.setHasTiltedLabels(true);
                axisBottom.setTextColor(Color.BLACK);
                axisBottom.setMaxLabelChars(10);
                axisBottom.setHasSeparationLine(true);
                columnChartData.setAxisXBottom(axisBottom);
                Axis axisLeft = new Axis();
                axisLeft.setHasLines(false);
                axisLeft.setHasTiltedLabels(true);
                axisLeft.setTextColor(Color.BLACK);
                // axisLeft.setTextColor(Color.parseColor("#666666"));
                columnChartData.setAxisYLeft(axisLeft);
                columnChartData.setFillRatio(0.7f);

                chart.setInteractive(false);
                chart.setColumnChartData(columnChartData);
            }
        }
    }
}

class TeamData{
    private String data_info;
    private String data_value;
    TeamData(String di,String dv){
        data_info = di;
        data_value = dv;
    }

    String getData_info() {
        return data_info;
    }

    String getData_value() {
        return data_value;
    }
}

class TeamDataAdapter extends BaseAdapter{
    private ArrayList<TeamData> mList;
    private Context mContext;

    TeamDataAdapter(ArrayList<TeamData> mList,
                               Context mContext) {
        super();
        this.mList = mList;
        this.mContext = mContext;
    }
    public int getCount() {
        if (mList == null) {
            return 0;
        } else {
            return this.mList.size();
        }
    }
    @Override
    public Object getItem(int position) {
        if (mList == null) {
            return null;
        } else {
            return this.mList.get(position);
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.team_data_grid_item, null);
            holder.description = convertView.findViewById(R.id.team_data_item_description);
            holder.value = convertView.findViewById(R.id.team_data_item_value);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (this.mList != null) {
            TeamData i = this.mList.get(position);
            if (holder.description != null) {
                holder.description.setText(i.getData_info());
                holder.description.setTextSize(12);
                holder.description.setTextColor(Color.GRAY);
                holder.value.setText(i.getData_value());
                holder.value.setTextSize(20);
                holder.value.setTextColor(Color.BLACK);
                TextPaint textPaint = holder.value.getPaint();
                textPaint.setFakeBoldText(true);
            }
        }
        return convertView;
    }

    private class ViewHolder
    {
        TextView description;
        TextView value;
    }
}


