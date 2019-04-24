package com.free.app.spp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class NaviActivity extends AppCompatActivity {

    private Fragment f1,f2,f3,f4;
    private FragmentTransaction transaction = getFragmentManager().beginTransaction();

    public void hideAllFragment(FragmentTransaction transaction){
        if(f1!=null){
            transaction.hide(f1);
        }
        if(f2!=null){
            transaction.hide(f2);
        }
        if(f3!=null){
            transaction.hide(f3);
        }
        if(f4!=null){
            transaction.hide(f4);
        }

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            transaction = getFragmentManager().beginTransaction();
            hideAllFragment(transaction);
            switch (item.getItemId()) {
                case R.id.arrange_list:
                    if(f1 == null){
                        f1 = new ArrangeFragment();
                        transaction.add(R.id.frag_frame,f1);
                    }
                    else{
                        transaction.show(f1);
                    }
                    transaction.commit();
                    return true;
                case R.id.team_list:
                    if(f2 == null){
                    f2 = new TeamListFragment();
                    transaction.add(R.id.frag_frame,f2);
                }
                else{
                    transaction.show(f2);
                }
                    transaction.commit();
                    return true;
                case R.id.my_match:
                    if (f3 != null) {
                        transaction.show(f3);
                    }
                    transaction.commit();
                    return true;
                case R.id.personal_center:
                    if(f4 == null){
                        f4 = new PersonalCenterFragment();
                        transaction.add(R.id.frag_frame,f4);
                    }
                    else{
                        transaction.show(f4);
                    }
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        f1 = new ArrangeFragment();
        transaction.add(R.id.frag_frame,f1);transaction.commit();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}


