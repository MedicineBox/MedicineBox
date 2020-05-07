package com.example.medicinebox;

import android.app.Activity;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import androidx.annotation.Nullable;

import com.google.protobuf.LazyStringArrayList;

/*
                    블루투스 스캔을 위한 액티비티
 */

public class Device_ble_scan extends Activity {
    private boolean mScanning;
    private Handler handler;
    private static final int REQUEST_ENABLE_BT = 10; // 블루투스 활성화 상태
    private BluetoothAdapter bluetoothAdapter; // 블루투스 어댑터
    private Set<BluetoothDevice> devices; // 블루투스 디바이스 데이터 셋
    private BluetoothDevice bluetoothDevice; // 블루투스 디바이스
    private BluetoothSocket bluetoothSocket = null; // 블루투스 소켓
    private OutputStream outputStream = null; // 블루투스에 데이터를 출력하기 위한 출력 스트림
    private InputStream inputStream = null; // 블루투스에 데이터를 입력하기 위한 입력 스트림
    private Thread workerThread = null; // 문자열 수신에 사용되는 쓰레드
    private byte[] readBuffer; // 수신 된 문자열을 저장하기 위한 버퍼
    private int readBufferPosition; // 버퍼 내 문자 저장 위치

    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private String macAddress;
    private String wifi, passwd;
    ArrayList<String> deviceNames;

    ListView listView;
    Button btnNext;
//
////    10초동안 스캔 후 멈춤
//    private static final long SCAN_PERIOD = 10000;
//
//    private void scanLeDevice(final boolean enable) {
//        if(enable) {
////
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mScanning = false;
//                    bluetoothAdapter.stopLeScan(BluetoothAdapter.LeScanCallback);
//                    BluetoothLeScanner#startScan()
//                }
//            }, SCAN_PERIOD);
//
//            mScanning = true;
//            bluetoothAdapter.startLeScan(BluetoothAdapter.LeScanCallback);
//        } else {
//            mScanning = false;
//            bluetoothAdapter.stopLeScan(BluetoothAdapter.LeScanCallback);
//        }
//    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.device_bt_scan);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
        deviceNames = new ArrayList<>();

        listView = findViewById(R.id.auth_listBtScan);
        btnNext = findViewById(R.id.auth_btnScan);

        String temp = "sample1";
        deviceNames.add(temp);

        BtListAdapter btAdapter = new BtListAdapter(this, deviceNames);
        listView.setAdapter(btAdapter);

        Intent getIntent = getIntent();
        wifi = getIntent.getStringExtra("wifi");
        passwd = getIntent.getStringExtra("passwd");

        Toast.makeText(getApplicationContext(), "Wi-Fi : " + wifi + "\n PASSWORD : " + passwd, Toast.LENGTH_SHORT).show();

        macAddress = "123";
        deviceNames.add("sample2");


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Device_auth.class);
                intent.putExtra("wifi", wifi);
                intent.putExtra("passwd", passwd);
                intent.putExtra("macAddress", macAddress);
                startActivity(intent);
            }
        });


//        블루투스 활성화
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null) {                                  // 블루투스 지원하지 않을 경우
        } else if(!bluetoothAdapter.isEnabled()) {                      // 블루투스가 활성화되지 않았을 경우
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);             // 블루투스 활성화를 위해 필요한 intent
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);                              // 다른 액티비티를 통해서 블루투스를 활성화 한 후에 결과를 requestCode 변수로 리턴
        }

//        bluetoothAdapter.startDiscovery();

/*
//        다른 디바이스가 현재 휴대폰을 검색
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);       // 디바이스 검색 시간을 300초로 설정
        startActivity(discoverableIntent);                                                          // 사용자 권한 요청 창띄움. allow 누르면 검색 시작.
*/

//        디바이스 연결


    }

//    Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {                   // 디바이스 찾았을 때, BluetoothDevice 불러옴. intent 통해 정보 가져옴.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();             // 디바이스 MAC주소
                devices.add(device);
                deviceNames.add(deviceName);
            }
        }
    };

    private void startScan() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(receiver);
    }
}
