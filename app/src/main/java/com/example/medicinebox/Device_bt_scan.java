package com.example.medicinebox;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

/*
                    블루투스 스캔을 위한 액티비티
 */

public class Device_bt_scan extends AppCompatActivity {
    private final  static String TAG = Device_bt_scan.class.getSimpleName();

    private boolean mScanning;
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
    private Handler mHandler;


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;           // 0이 아닌 양의 정수 아무거나 해도 됨.
    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
//    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private boolean isPermissionAllowed = false;
    int fineLocationPermission, coarseLocationPermission;

    private List<Map<String, String>> searchedDevices;
    private String macAddress;
    private String wifi, passwd;
    DeviceListAdapter deviceListAdapter;
    private int devicePosition;

    ListView listView;
    Button btnNext;
    ImageButton btnRefresh;
    ProgressBar progressBar;



//    10초동안 스캔 후 멈춤
    private static final long SCAN_PERIOD = 10000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.device_bt_scan);


        deviceListAdapter = new DeviceListAdapter(this);

        listView = findViewById(R.id.auth_listBtScan);
        btnNext = findViewById(R.id.auth_btnScan);
        btnRefresh = findViewById(R.id.auth_btnBtRefresh);
        progressBar = findViewById(R.id.auth_BtProgress);
        mHandler = new Handler();

//        listView.setAdapter(leDeviceListAdapter);
        Intent getIntent = getIntent();
        wifi = getIntent.getStringExtra("wifi");
        passwd = getIntent.getStringExtra("passwd");
        devicePosition = -1;
        searchedDevices = new ArrayList<>();
//        listView.setAdapter(searchedDevices);
        listView.setAdapter(deviceListAdapter);


//        블루투스 브로드캐스트 리시버 등록
        IntentFilter searchFilter = new IntentFilter();
        searchFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        searchFilter.addAction(BluetoothDevice.ACTION_FOUND);
        searchFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mBroadcastReceiver, searchFilter);


//        Toast.makeText(getApplicationContext(), "Wi-Fi : " + wifi + "\n PASSWORD : " + passwd, Toast.LENGTH_SHORT).show();

        macAddress = "";
//        블루투스 활성화
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {                                  // 블루투스 지원하지 않을 경우
            Toast.makeText(getApplicationContext(), "블루투스를 지원하지 않는 단말기입니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else if (!bluetoothAdapter.isEnabled()) {                      // 블루투스가 활성화되지 않았을 경우 활성화 요청
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);             // 블루투스 활성화를 위해 필요한 intent
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);                              // 다른 액티비티를 통해서 블루투스를 활성화 한 후에 결과를 requestCode 변수로 리턴
        }


//        위치 권한이 있는지 있는지 확인. 없다면 -1 반환
        fineLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        coarseLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);

        if(fineLocationPermission == PackageManager.PERMISSION_DENIED || coarseLocationPermission == PackageManager.PERMISSION_DENIED) {            // 권한이 없을 때
            String[] permissions = new String[2];
            permissions[0] = Manifest.permission.ACCESS_COARSE_LOCATION;                            // 사용자에게 요청할 권한
            permissions[1] = Manifest.permission.ACCESS_FINE_LOCATION;
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);                      // 사용자에게 권한 요청. onRequestPermissionsResult로 결과값 보냄.
            Toast.makeText(this, "권한 획득이 필요합니다.", Toast.LENGTH_SHORT).show();
        } else {                                                        // 권한이 있을 때
            isPermissionAllowed = true;
        }

//        이미 권한이 있는 상태에서 블루투스가 켜져있으면 스캔 시작
        if(bluetoothAdapter.isEnabled() && isPermissionAllowed) {
            scanDevice(true);
        }



        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPermissionAllowed) {
//                    Toast.makeText(getApplicationContext(), "BT 다시 스캔", Toast.LENGTH_SHORT).show();
                    deviceListAdapter.clear();
//                    scanLeDevice(true);
                    scanDevice(true);
                } else {
                    String[] permissions = new String[1];
                    permissions[0] = Manifest.permission.ACCESS_COARSE_LOCATION;
                    requestPermissions(permissions, LOCATION_PERMISSION_REQUEST_CODE);
                }
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (devicePosition == -1) {
                    Toast.makeText(getApplicationContext(), "디바이스를 선택해 주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), Device_auth.class);
                    intent.putExtra("wifi", wifi);
                    intent.putExtra("passwd", passwd);
//                    intent.putExtra("macAddress", macAddress);
                    intent.putExtra("device", deviceListAdapter.getDevice(devicePosition));
                    startActivity(intent);
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                devicePosition = position;
//                macAddress = leDeviceListAdapter.getDevice(position).getAddress();
//                device = leDeviceListAdapter.getDevice(position);
            }
        });

    }
//        디바이스 검색이 끝나고 디바이스의 이름이나 찾은 디바이스를 listen한다.
        BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action) {
//                    블루투스 검색 시작
                    case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                        btnRefresh.setVisibility(View.GONE);                                        // 새로고침 버튼 숨김
                        progressBar.setVisibility(View.VISIBLE);                                    // 프로그레스바 보여짐
                        Toast.makeText(getApplicationContext(), "블루투스 검색 시작", Toast.LENGTH_SHORT).show();
                        break;
//                    블루투스 디바이스 찾음
                    case BluetoothDevice.ACTION_FOUND:
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);           // 찾은 디바이스를 객체에 저장
                        deviceListAdapter.addDevice(device);                                      // 디바이스 객체를 어댑터에 추가. 어댑터 통해서 리스트뷰에 띄움
                        deviceListAdapter.notifyDataSetChanged();                                 // 어댑터에 데이터의 변경을 알림. 리스트 목록 갱신.
//                        Toast.makeText(getApplicationContext(), "블루투스 디바이스 찾음", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "블루투스 디바이스 찾음 ");
                        Log.d(TAG, "디바이스 이름 : " + device.getName() + ", 다비이스 주소 : " + device.getAddress());
                        break;
//                    블루투스 검색 종료
                    case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                        btnRefresh.setVisibility(View.VISIBLE);                                     // 새로고침 보여짐
                        progressBar.setVisibility(View.GONE);                                       // 프로그레스바 숨김
                        Toast.makeText(getApplicationContext(), "블루투스 검색 종료", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };




    private void scanDevice(final boolean enable) {
        if(enable) {                                // 블루수트 스캔 시작 요청
            mHandler.postDelayed(new Runnable() {                           // delay 가진 후 run() 실행
                @Override
                public void run() {
                    bluetoothAdapter.cancelDiscovery();                     // 블루투스 검색 종료
                }
            }, SCAN_PERIOD);                                                // SCAN_PERIOD 만큼 기다린 뒤 실행
            bluetoothAdapter.startDiscovery();                              // 블루투스 스캔 시작
        } else {
            bluetoothAdapter.cancelDiscovery();
        }
    }


//        사용자에게 권한을 요청한 결과가 들어오은 메소드
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if(requestCode == LOCATION_PERMISSION_REQUEST_CODE) {                               // 위치 권한 요청의 응답값인지 체크
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                                                                                        // 권한 획득
            scanDevice(true);                                                  // BT 스캐닝
        } else {                                                                        // 권한 획득 실패
//            finish();
            Toast.makeText(getApplicationContext(), "권한 획득 실패", Toast.LENGTH_SHORT).show();
        }
    }

}

//
//    private void scanLeDevice(final boolean enable) {
//        if(enable) {
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mScanning = false;
//                    stopScanBLE();
//                    invalidateOptionsMenu();
//                }
//            }, SCAN_PERIOD);
//
//            mScanning = true;
//            startScanBLE();
//        } else {
//            mScanning = false;
//            stopScanBLE();
//        }
//        invalidateOptionsMenu();
//    }
//    private void startScanBLE() {
//        bluetoothAdapter.getBluetoothLeScanner().startScan(scanCallback);
//        btnRefresh.setVisibility(View.GONE);
//        progressBar.setVisibility(View.VISIBLE);
//        Log.d("BT_SCAN", "START!!!!!!!!!");
//    }
//    private void stopScanBLE() {
//        bluetoothAdapter.getBluetoothLeScanner().stopScan(scanCallback);
//        btnRefresh.setVisibility(View.VISIBLE);
//        progressBar.setVisibility(View.GONE);
//        Log.d("BT_SCAN", "STOP!!!!!!!!!");
//    }
//
//    private ScanCallback scanCallback = new ScanCallback() {
//        @Override
//        public void onScanResult(int callbackType, ScanResult result) {
//            super.onScanResult(callbackType, result);
//
//            BluetoothDevice device = result.getDevice();
//            leDeviceListAdapter.addDevice(device);
//            leDeviceListAdapter.notifyDataSetChanged();
//            if(device.getName() != null)
//                Log.d("DEVICE", device.getName());
//        }
//    };


//    startActivityForResult에 의해 호출됨.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // 블루투스 활성화 시 (창 종료되고 나서)
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {                                                      // 활성화 버튼 누름. 블루투스 활성화됨.
                    Toast.makeText(getApplicationContext(), "블루투스 활성화 됨", Toast.LENGTH_SHORT).show();
                } else if (resultCode == RESULT_CANCELED) {                                         // 취소 버튼 누름. 블루투스 활성화 안됨.
                    Toast.makeText(getApplicationContext(), "블루투스 활성화가 필요합니다.", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        unregisterReceiver(receiver);
    }
}


