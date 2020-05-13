package com.example.medicinebox;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


class DeviceListAdapter extends BaseAdapter {
    private ArrayList<BluetoothDevice> mDevices;
    private LayoutInflater mInflater;
    private int mSelectedRadioPosition;

    public DeviceListAdapter() {
        super();
        mDevices = new ArrayList<BluetoothDevice>();
//        mInflater = Device_ble_scan2.this.getLayoutInflater();
//        mInflater = LayoutInflater.from(mContext);
    }

    public DeviceListAdapter(Context context) {
        super();
        mDevices = new ArrayList<BluetoothDevice>();
        mInflater = LayoutInflater.from(context);
    }

    public void addDevice(BluetoothDevice device) {
        if(!mDevices.contains(device)) {
            mDevices.add(device);
        }
    }

    public BluetoothDevice getDevice(int position) {
        return mDevices.get(position);
    }

    public void clear() {
        mDevices.clear();
    }


    @Override
    public int getCount() {
        return mDevices.size();
    }

    @Override
    public Object getItem(int position) {
        return mDevices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mInflater.inflate(R.layout.bt_items, null);

//        Log.d("LISTVIEW", "called getview!!!!!!!!!!");
        //        convertView.setActivated(true);
        convertView.setSelected(true);

        TextView tvName = convertView.findViewById(R.id.btItemName);
        TextView tvAddr = convertView.findViewById(R.id.btItemAddr);

        BluetoothDevice device = mDevices.get(position);
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
