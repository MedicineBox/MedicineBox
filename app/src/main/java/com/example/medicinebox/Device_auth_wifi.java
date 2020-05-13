package com.example.medicinebox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class Device_auth_wifi extends Activity {

    EditText edtWifi, edtPasswd;
    String wifi, passwd;
    Button btnNext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_auth_wifi);


        edtWifi = findViewById(R.id.auth_edtWifi);
        edtPasswd = findViewById(R.id.auth_edtPasswd);
        btnNext = findViewById(R.id.auth_btnWifi);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifi = edtWifi.getText().toString();
                passwd = edtPasswd.getText().toString();

                Intent intent = new Intent(getApplicationContext(), Device_bt_scan.class);
                intent.putExtra("wifi", wifi);
                intent.putExtra("passwd", passwd);
                startActivity(intent);
            }
        });

    }
}
