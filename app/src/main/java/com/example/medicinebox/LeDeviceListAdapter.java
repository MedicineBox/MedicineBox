package com.example.medicinebox;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


class LeDeviceListAdapter extends BaseAdapter {
    private ArrayList<BluetoothDevice> mLeDevices;
    private LayoutInflater mInflater;
    private int mSelectedRadioPosition;

    public LeDeviceListAdapter() {
        super();
        mLeDevices = new ArrayList<BluetoothDevice>();
//        mInflater = Device_ble_scan2.this.getLayoutInflater();
//        mInflater = LayoutInflater.from(mContext);
    }

    public LeDeviceListAdapter(Context context) {
        super();
        mLeDevices = new ArrayList<BluetoothDevice>();
        mInflater = LayoutInflater.from(context);
    }

    public void addDevice(BluetoothDevice device) {
        if(!mLeDevices.contains(device)) {
            mLeDevices.add(device);
        }
    }

    public BluetoothDevice getDevice(int position) {
        return mLeDevices.get(position);
    }

    public void clear() {
        mLeDevices.clear();
    }


    @Override
    public int getCount() {
        return mLeDevices.size();
    }

    @Override
    public Object getItem(int position) {
        return mLeDevices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mInflater.inflate(R.layout.bt_items, null);

//        convertView.setActivated(true);
        convertView.setSelected(true);

        TextView tvName = convertView.findViewById(R.id.btItemName);
        TextView tvAddr = convertView.findViewById(R.id.btItemAddr);

        BluetoothDevice device = mLeDevices.get(position);
        final String deviceName = device.getName();
        if(deviceName != null && deviceName.length() > 0) {
            tvName.setText(deviceName);
        } else {
            tvName.setText("Unknown device");
        }
        tvAddr.setText(device.getAddress());

        return convertView;
    }
//
//    public String getAddr(int position) {
//        return mLeDevices.get(position).getAddress();
//    }
}
