package com.example.medicinebox;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.common.primitives.Bytes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Device_auth extends Activity {

    LinearLayout step1, step2, step3, step4;
    ImageView img1, img2, img3, img4;
    ProgressBar pbar1, pbar2, pbar3, pbar4;


    private final static String TAG = Device_auth.class.getSimpleName();


    private String sendString;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_auth);

        step1 = findViewById(R.id.auth_layout1);
        step2 = findViewById(R.id.auth_layout2);
        step3 = findViewById(R.id.auth_layout3);
        step4 = findViewById(R.id.auth_layout4);
        img1 = findViewById(R.id.auth_imgview1);
        img2 = findViewById(R.id.auth_imgview2);
        img3 = findViewById(R.id.auth_imgview3);
        img4 = findViewById(R.id.auth_imgview4);
        pbar1 = findViewById(R.id.auth_progress1);
        pbar2 = findViewById(R.id.auth_progress2);
        pbar3 = findViewById(R.id.auth_progress3);
        pbar4 = findViewById(R.id.auth_progress4);


//        시작시엔 스텝 2, 3, 4 숨기기
        step2.setVisibility(View.INVISIBLE);
        step3.setVisibility(View.INVISIBLE);
        step4.setVisibility(View.INVISIBLE);


        Intent getIntent = getIntent();
        String wifi_id = getIntent.getStringExtra("wifi_id");
        String wifi_pw = getIntent.getStringExtra("wifi_pw");
        String device_id = getIntent.getStringExtra("device_id");
        sendString = "{ \"device_id\" : \"" + device_id + ", \"wifi_id\" : \"" + wifi_id + "\", \"wifi_pw\" : \"" + wifi_pw + "\" }";



//        Toast.makeText(getApplicationContext(), "Wi-Fi : " + wifi + "\nPASSWORD : " + passwd +"\ndevice : " + deviceName +"\nMACAddr : " + deviceAddr, Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onResume() {
        super.onResume();
//        registerReceiver()
    }



//    진행 단계 업데이트
    private void updateProgressStage(final int step) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (step) {
                    case 1:
                        pbar1.setVisibility(View.GONE);
                        img1.setVisibility(View.VISIBLE);
                        step2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        pbar2.setVisibility(View.GONE);
                        img2.setVisibility(View.VISIBLE);
                        step3.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        pbar3.setVisibility(View.GONE);
                        img3.setVisibility(View.VISIBLE);
                        step4.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        pbar4.setVisibility(View.GONE);
                        img4.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }


























}
