package com.example.a1111.sprots;

import android.content.Intent;
import android.graphics.Color;
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

import com.example.a1111.sprots.justloginregistertest.loginActivity;

import java.util.ArrayList;
import java.util.List;

public class PersonalCenterFragment extends Fragment {
    private boolean isVisible = true;
    private ListView attentionList;
    private List<TeamItem> teamList = new ArrayList<>();
    private LinearLayout modifyPassword;
    private LinearLayout logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_center, container, false);
        super.onCreate(savedInstanceState);
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
        userName.setText("paradox");
        userName.setTextSize(36);
        description.setText("test,test,test,test");
        attentionTeamText.setTextSize(16);
        modifyPwText.setTextSize(16);
        logoutText.setTextSize(16);

        userImg.setImageResource(R.drawable.person_img);

        teamList.add(new TeamItem("湖人"));
        teamList.add(new TeamItem("马刺"));
        teamList.add(new TeamItem("公牛"));
        teamList.add(new TeamItem("雄鹿"));
        teamList.add(new TeamItem("独行侠"));
        MyTeamAdapter adapter = new MyTeamAdapter(teamList, getActivity());
        attentionList.setAdapter(adapter);

        attentionLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (isVisible) {
                    isVisible = false;
                    attentionList.setVisibility(View.VISIBLE);
                    modifyPassword.setVisibility(View.GONE);
                    logout.setVisibility(View.GONE);
                }
                else {
                    modifyPassword.setVisibility(View.VISIBLE);
                    logout.setVisibility(View.VISIBLE);
                    attentionList.setVisibility(View.GONE);
                    isVisible = true;
                }
            }
        });

        attentionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TeamItem teamItem = teamList.get(position);
                Intent intent = new Intent(getActivity(), TeamActivity.class);
                intent.putExtra("123", teamItem.getTeamName());
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

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View viewOfList = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            viewOfList = listAdapter.getView(i, viewOfList, listView);
            if (i == 0)
                viewOfList.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            viewOfList.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += viewOfList.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        assert viewOfList != null;
        LinearLayout personCenterLayout = viewOfList.findViewById(R.id.personCenterItems);
        ScrollView scrollView = viewOfList.findViewById(R.id.scroll_center);
        personCenterLayout.getLayoutParams().height += params.height;
        scrollView.getLayoutParams().height += params.height;
    }
}
