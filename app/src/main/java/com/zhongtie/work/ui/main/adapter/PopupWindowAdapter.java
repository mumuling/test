package com.zhongtie.work.ui.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhongtie.work.R;

/**
 * list PopupWindowAdapter
 */
public class PopupWindowAdapter extends BaseAdapter {

    private String[] mCollection;

    public PopupWindowAdapter(String... collection) {
        mCollection = collection;
    }

    @Override
    public int getCount() {
        return mCollection.length;
    }

    @Override

    public Object getItem(int position) {
        return mCollection[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popup, parent, false);
        }

        TextView t = convertView.findViewById(R.id.item_content);
        t.setText(mCollection[position]);
        return convertView;
    }

}

