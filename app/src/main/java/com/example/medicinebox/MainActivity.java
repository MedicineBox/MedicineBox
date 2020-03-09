package com.example.medicinebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

public class MainActivity extends AppCompatActivity {


    LinearLayout bHome, bSearch, bSetting;
    ImageButton bBtnHome, bBtnSearch, bBtnSetting;
    View bottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        bottomBar = (View) findViewById(R.id.bottomBar_main);
        bHome = (LinearLayout) bottomBar.findViewById(R.id.layoutHome);
        bSearch = (LinearLayout) bottomBar.findViewById(R.id.layoutSearch);
        bSetting = (LinearLayout) bottomBar.findViewById(R.id.layoutSetting);
        bBtnHome = (ImageButton) bottomBar.findViewById(R.id.imgbtnHome);
        bBtnSearch = (ImageButton) bottomBar.findViewById(R.id.imgbtnSearch);
        bBtnSetting = (ImageButton) bottomBar.findViewById(R.id.imgbtnSetting);
        ImageButton imgbtnPill1 = (ImageButton)findViewById(R.id.mainPill1);

        imgbtnPill1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), AddMedicine.class);
                startActivity(in);
            }
        });


//        하단 바 버튼 클릭
        bBtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Home 클릭", Toast.LENGTH_SHORT).show();
            }
        });
        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Home 클릭", Toast.LENGTH_SHORT).show();
            }
        });
        bBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), SearchMain.class);
                startActivity(in);
            }
        });
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), SearchMain.class);
                startActivity(in);
            }
        });
        bBtnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Setting 클릭", Toast.LENGTH_SHORT).show();
            }
        });
        bSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Setting 클릭", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
