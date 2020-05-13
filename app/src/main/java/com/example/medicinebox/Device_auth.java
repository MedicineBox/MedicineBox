package com.example.medicinebox;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Device_auth extends Activity {

    LinearLayout step1, step2, step3, step4;
    ImageView img1, img2, img3, img4;
    ProgressBar pbar1, pbar2, pbar3, pbar4;


    private final static String TAG = Device_auth.class.getSimpleName();

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

    private final UUID uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private final String SERVICE = "0000fff0-0000-1000-8000-00805f9b34fb";
    private final String WRITE_UUID = "0000fff1-0000-1000-8000-00805f9b34fb";
    private final String READ_UUID = "0000fff2-0000-1000-8000-00805f9b34fb";

//    BluetoothLeService bluetoothLeService;

    private boolean mConnected = false;
    private BluetoothGatt bluetoothGatt;
    private BluetoothGattCharacteristic readCharacteristic = null;
    private BluetoothGattCharacteristic writeCharacteristic = null;
//    private BluetoothLe
//    private BluetoothDevice device;
    private String deviceName;
    private String deviceAddr;


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

//        BLUETOOTH 설정
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
//        블루투스 활성화
//        if(bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {                     // 블루투스가 활성화 안되어 있다면
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);     // 블루투스 활성화를 위해 필요한 intent
//            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);                      // 다른 액티비티를 통해서 블루투스를 활성화 한 후에 결과를 requestCode 변수로 리턴
//        }

        Intent getIntent = getIntent();
        String wifi = getIntent.getStringExtra("wifi");
        String passwd = getIntent.getStringExtra("passwd");
//        String macAddress = getIntent.getStringExtra("macAddress");
        bluetoothDevice = getIntent.getParcelableExtra("device");
        deviceName = bluetoothDevice.getName();
        deviceAddr = bluetoothDevice.getAddress();

//        bluetoothLeService = new BluetoothLeService();

//        AsyncTask asyncTask = new
         if(bluetoothDevice != null) {
             BtConnect btConnect = new BtConnect();
             btConnect.execute();
         }
//        Toast.makeText(getApplicationContext(), "Wi-Fi : " + wifi + "\nPASSWORD : " + passwd +"\ndevice : " + deviceName +"\nMACAddr : " + deviceAddr, Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onResume() {
        super.onResume();
//        registerReceiver()
    }

    private class BtConnect extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
//                블루투스 소켓 생성
                bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
                bluetoothSocket.connect();
//               데이터 받기 위해 인풋 스트림 생성
                inputStream = bluetoothSocket.getInputStream();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "블루투스 연결을 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            updateProgressStage(1);
        }
    }


    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            bluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
//            if(!bluetoothLeService.initialize()) {
//                Log.e(TAG, "Unable to initialize Bluetooth");
//                finish();
//            }

//            bluetoothLeService.connect(deviceAddr);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
//            bluetoothLeService = null;
        }
    };

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
//            if(BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)){                            // gatt서버 연결됨
//                mConnected = true;
//                updateProgressStage(1);
//            } else if(BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {                 // gatt서버 연결 끊김
//                mConnected = false;
//            }
        }
    };


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
