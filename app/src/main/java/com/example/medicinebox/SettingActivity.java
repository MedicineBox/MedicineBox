package com.example.medicinebox;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SettingActivity extends AppCompatActivity {

    ImageView btnBack, btnHome;
    Switch switchAlarm;
    TextView btnAccount, btnLogout, btnLeave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);

        btnBack = findViewById(R.id.btnBack);
        btnHome = findViewById(R.id.btnHome);
        switchAlarm = findViewById(R.id.switchAlarm);
        btnAccount = findViewById(R.id.btnAccount);
        btnLogout = findViewById(R.id.btnLogout);
        btnLeave = findViewById(R.id.btnLeave);

        //뒤로가기
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //홈
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //알림 설정
        switchAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.i("알림","활성화");
                } else {
                    Log.i("알림","비활성화");
                }
            }
        });

        //내정보수정
        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(intent);
            }
        });

        //로그아웃
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.clearUserData(getApplicationContext());                                     // 사용자 데이터 삭제
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);           // login activity로 이동
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);   // 새로운 액티비티 태스크? 만들고 이미 있던 스택 삭제
                startActivity(intent);
            }
        });

        //탈퇴
        btnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LeaveActivity.class);
                startActivity(intent);
            }
        });
    }
}
