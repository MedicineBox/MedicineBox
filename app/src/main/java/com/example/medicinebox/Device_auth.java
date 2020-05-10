package com.example.medicinebox;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public class Device_auth extends Activity {

    LinearLayout step1, step2, step3, step4;
    ImageView img1, img2, img3, img4;
    ProgressBar pbar1, pbar2, pbar3, pbar4;



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
        if(bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {                     // 블루투스가 활성화 안되어 있다면
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);     // 블루투스 활성화를 위해 필요한 intent
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);                      // 다른 액티비티를 통해서 블루투스를 활성화 한 후에 결과를 requestCode 변수로 리턴
        }


        Intent getIntent = getIntent();
        String wifi = getIntent.getStringExtra("wifi");
        String passwd = getIntent.getStringExtra("passwd");
        String macAddresss = getIntent.getStringExtra("macAddress");

        Toast.makeText(getApplicationContext(), "Wi-Fi : " + wifi + "\nPASSWORD : " + passwd +"\nMACAddr : " + macAddresss, Toast.LENGTH_SHORT).show();



/*
        //인증 완료 후
        Intent intent = new Intent(getApplicationContext(), Splash_auth.class);
        startActivity(intent);
*/

    }
}
