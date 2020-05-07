package com.example.medicinebox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BtListAdapter extends BaseAdapter {

    private Context mContext = null;
    private ArrayList<String> list;
    LayoutInflater mLayoutInflater = null;
//    private RecyclerView.ViewHolder mViewHolder;


    public BtListAdapter(Context mContext, ArrayList<String> data) {
        this.mContext = mContext;
        this.list = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.spinner_item1, null);

        TextView textView = view.findViewById(R.id.spinneritemTextview);

        textView.setText(list.get(position));

        return view;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
