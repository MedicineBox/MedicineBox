package com.example.medicinebox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class EditMediActivity extends AppCompatActivity {

    int daySunVal = 0, dayMonVal = 0, dayTueVal = 0, dayWedVal = 0, dayThuVal = 0, dayFriVal = 0, daySatVal = 0;
    int y, m, d;

    ImageView btnBack;
    LinearLayout layoutDay, layoutCycle, layoutType, layoutTake, lAddTime1, lAddTime2, lAddTime3, lAddTime4, lAddTime5;
    Button btnMediAdd, btnDay, btnCycle, btnStartdate, btnDaySun, btnDayMon, btnDayTue, btnDayWed, btnDayThu, btnDayFri, btnDaySat
            , btnAddTime1, btnAddTime2, btnAddTime3, btnAddTime4, btnAddTime5, btnExpiredate;
    Spinner spinSlot, spinCycle, spinPerDay;

    TextView textType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editmedi_activity);

        btnBack = findViewById(R.id.btnBack);

        spinSlot = findViewById(R.id.spinSlot);

        layoutType = findViewById(R.id.layoutType);
        textType = findViewById(R.id.textType);
        layoutTake = findViewById(R.id.layoutTake);

        btnDaySun = findViewById(R.id.btnDaySun);
        btnDayMon = findViewById(R.id.btnDayMon);
        btnDayTue = findViewById(R.id.btnDayTue);
        btnDayWed = findViewById(R.id.btnDayWed);
        btnDayThu = findViewById(R.id.btnDayThu);
        btnDayFri = findViewById(R.id.btnDayFri);
        btnDaySat = findViewById(R.id.btnDaySat);

        lAddTime1 = findViewById(R.id.layoutAddTime1);
        lAddTime2 = findViewById(R.id.layoutAddTime2);
        lAddTime3 = findViewById(R.id.layoutAddTime3);
        lAddTime4 = findViewById(R.id.layoutAddTime4);
        lAddTime5 = findViewById(R.id.layoutAddTime5);
        btnAddTime1 = findViewById(R.id.btnAddTime1);
        btnAddTime2 = findViewById(R.id.btnAddTime2);
        btnAddTime3 = findViewById(R.id.btnAddTime3);
        btnAddTime4 = findViewById(R.id.btnAddTime4);
        btnAddTime5 = findViewById(R.id.btnAddTime5);

        layoutDay = findViewById(R.id.layoutAddDay);
        layoutCycle = findViewById(R.id.layoutAddCycle);
        btnDay = findViewById(R.id.btnType_Day);
        btnCycle = findViewById(R.id.btnType_Cycle);
        spinCycle = findViewById(R.id.spinnerCycle);
        spinPerDay = findViewById(R.id.spinnerPerDay);
        btnStartdate = findViewById(R.id.btnStartdate);
        btnExpiredate = findViewById(R.id.btnExpiredate);

        btnMediAdd = findViewById(R.id.btnMediAdd);


        //뒤로가기
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
