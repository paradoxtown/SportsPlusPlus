package com.free.app.spp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView.PinnedSectionListAdapter;

public class MatchAdapter extends BaseAdapter implements PinnedSectionListAdapter {
    private List mData;
    private Context mContext;
    private final int ITEM_NORMAL = 0;
    private final int ITEM_SECTION = 1;
    MatchAdapter(List mData, Context mContext) {
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
        if (mData.get(position) instanceof Match) {
            return ITEM_NORMAL;
        }
        else {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_match,parent,false);
            convertView.setBackgroundColor(Color.WHITE);
            LinearLayout teamItem = convertView.findViewById(R.id.team_item);
            TextView host_name = convertView.findViewById(R.id.host_name);
            TextView guest_name = convertView.findViewById(R.id.guest_name);
            TextView host_points = convertView.findViewById(R.id.host_points);
            TextView guest_points = convertView.findViewById(R.id.guest_points);
            ImageView host_image = convertView.findViewById(R.id.host_image);
            ImageView guest_image = convertView.findViewById(R.id.guest_image);

            teamItem.setBackgroundColor(Color.rgb(240,240,240));
            Match a = (Match) mData.get(position);
            String hp = a.getHost_points();
            String gp = a.getGuest_points();
            host_name.setText(a.getHost_name());
            guest_name.setText(a.getGuest_name());
            host_points.setText(a.getHost_points());
            host_points.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            guest_points.setText(a.getGuest_points());
            guest_points.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            if (Integer.parseInt(hp) > Integer.parseInt(gp)) {
                host_points.setTextColor(Color.BLACK);
            }
            else {
                guest_points.setTextColor(Color.BLACK);
            }
            InputStream img = null;
            try {
                img = mContext.getResources().getAssets().open(a.getHost_image());
            }
            catch(Exception e){
                e.printStackTrace();
            }
            host_image.setImageBitmap(BitmapFactory.decodeStream(img));
            try {
                img = mContext.getResources().getAssets().open(a.getGuest_image());
            }
            catch(Exception e){
                e.printStackTrace();
            }
            guest_image.setImageBitmap(BitmapFactory.decodeStream(img));
            try {
                img.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (itemViewType == ITEM_SECTION) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_date,parent,false);
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
