package com.example.medicinebox;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;
import com.google.protobuf.LazyStringArrayList;

/*
                    블루투스 스캔을 위한 액티비티
 */

public class Device_ble_scan extends Activity {
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

    private BluetoothGatt bluetoothGatt;
    private BluetoothGattCharacteristic readCharacteristic = null;
    private BluetoothGattCharacteristic writeCharacteristic = null;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final String SERVICE = "0000fff0-0000-1000-8000-00805f9b34fb";
    private final String WRITE_UUID = "0000fff1-0000-1000-8000-00805f9b34fb";
    private final String READ_UUID = "0000fff2-0000-1000-8000-00805f9b34fb";
    private boolean isPermissionAllowed = false;
    int permission;

    private String macAddress;
    private String wifi, passwd;
    LeDeviceListAdapter leDeviceListAdapter;

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

        leDeviceListAdapter = new LeDeviceListAdapter(this);

        listView = findViewById(R.id.auth_listBtScan);
        btnNext = findViewById(R.id.auth_btnScan);
        btnRefresh = findViewById(R.id.auth_btnBtRefresh);
        progressBar = findViewById(R.id.auth_BtProgress);
        mHandler = new Handler();

        listView.setAdapter(leDeviceListAdapter);
        Intent getIntent = getIntent();
        wifi = getIntent.getStringExtra("wifi");
        passwd = getIntent.getStringExtra("passwd");

//        Toast.makeText(getApplicationContext(), "Wi-Fi : " + wifi + "\n PASSWORD : " + passwd, Toast.LENGTH_SHORT).show();

        macAddress = "";

//        위치 권한이 있는지 있는지 확인. 없다면 -1 반환
        permission = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);

        if(permission == PackageManager.PERMISSION_DENIED) {            // 궝한이 없을 때
            String[] permissions = new String[1];
            permissions[0] = Manifest.permission.ACCESS_COARSE_LOCATION;                            // 사용자에게 요청할 권한
            requestPermissions(permissions, LOCATION_PERMISSION_REQUEST_CODE);                      // 사용자에게 권한 요청
            Toast.makeText(this, "권한 획득이 필요합니다.", Toast.LENGTH_SHORT).show();
        } else {                                                        // 권한이 있을 때
            isPermissionAllowed = true;
        }

//        블루투스 활성화
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null) {                                  // 블루투스 지원하지 않을 경우
        } else if(!bluetoothAdapter.isEnabled()) {                      // 블루투스가 활성화되지 않았을 경우
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);             // 블루투스 활성화를 위해 필요한 intent
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);                              // 다른 액티비티를 통해서 블루투스를 활성화 한 후에 결과를 requestCode 변수로 리턴
        }

//        이미 권한이 있는 상태에서 블루투스가 켜져있으면 스캔 시작
        if(bluetoothAdapter.isEnabled() && isPermissionAllowed) {
            scanLeDevice(true);
        }


        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isPermissionAllowed) {
                    Toast.makeText(getApplicationContext(), "BT 다시 스캔", Toast.LENGTH_SHORT).show();
                    leDeviceListAdapter.clear();
                    scanLeDevice(true);
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
                if(macAddress.equals("")){
                    Toast.makeText(getApplicationContext(), "디바이스를 선택해 주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), Device_auth.class);
                    intent.putExtra("wifi", wifi);
                    intent.putExtra("passwd", passwd);
                    intent.putExtra("macAddress", macAddress);
                    startActivity(intent);
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                macAddress = leDeviceListAdapter.getDevice(position).getAddress();
            }
        });

    }


//        사용자에게 권한을 요청한 결과가 들어오은 메소드
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if(requestCode == LOCATION_PERMISSION_REQUEST_CODE) {                               // 위치 권한 요청의 응답값인지 체크
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {                      // 권한 획득
            scanLeDevice(true);                                                  // BLE 스캐닝
        } else {                                                                        // 권한 획득 실패
            finish();
        }
    }

}
    private void scanLeDevice(final boolean enable) {
        if(enable) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    stopScanBLE();
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);

            mScanning = true;
            startScanBLE();
        } else {
            mScanning = false;
            stopScanBLE();
        }
        invalidateOptionsMenu();
    }
    private void startScanBLE() {
        bluetoothAdapter.getBluetoothLeScanner().startScan(scanCallback);
        btnRefresh.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Log.d("BT_SCAN", "START!!!!!!!!!");
    }
    private void stopScanBLE() {
        bluetoothAdapter.getBluetoothLeScanner().stopScan(scanCallback);
        btnRefresh.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        Log.d("BT_SCAN", "STOP!!!!!!!!!");
    }

    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);

            BluetoothDevice device = result.getDevice();
            leDeviceListAdapter.addDevice(device);
            leDeviceListAdapter.notifyDataSetChanged();
        }
    };

    private boolean findGattService() {
        List<BluetoothGattService> gattServices = bluetoothGatt.getServices();

        if(gattServices == null)    return false;

        readCharacteristic = null;
        writeCharacteristic = null;

        for(BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();

            if(gattService.getUuid().toString().equals(SERVICE)) {
                List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();

//                Loops through available Characteristics.
                for(BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics){
                    if(gattCharacteristic.getUuid().toString().equals(READ_UUID)){
                        final int charaProp = gattCharacteristic.getProperties();

                        if((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                            try{
                                readCharacteristic = gattCharacteristic;
                                List<BluetoothGattDescriptor> list = readCharacteristic.getDescriptors();
                                Log.d("DDDDD1", "read characteristic found : " + charaProp);

                                bluetoothGatt.setCharacteristicNotification(gattCharacteristic, true);

                                // 리시버 설정
                                BluetoothGattDescriptor descriptor = readCharacteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
                                bluetoothGatt.writeDescriptor(descriptor);

                            } catch(Exception e) {
                                e.printStackTrace();
                                return false;
                            }
                        } else {
                            Log.d("DDDDD1", "read characteristic prop is invlid : " + charaProp);
                        }
                    } else if(gattCharacteristic.getUuid().toString().equalsIgnoreCase(WRITE_UUID)){
                        final int charaProp = gattCharacteristic.getProperties();

                        if((charaProp | BluetoothGattCharacteristic.PROPERTY_WRITE) > 0) {
                            Log.d("DDDDD1", "write characteristic foud : " + charaProp);
                            writeCharacteristic = gattCharacteristic;
                        } else {
                            Log.d("DDDDD1", "write characteristic prop is invalid : " + charaProp);
                        }
                    }
                }
            }
        }
        return true;
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        unregisterReceiver(receiver);
    }
}


