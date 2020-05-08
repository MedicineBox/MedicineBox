package com.example.medicinebox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SearchMain extends AppCompatActivity {

    LinearLayout bHome, bSearch, bSetting;
    ImageButton bBtnHome, bBtnSearch, bBtnSetting;
    View bottomBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);


   /*     bottomBar = (View) findViewById(R.id.bottomBar_search_main);
        bHome = (LinearLayout) bottomBar.findViewById(R.id.layoutHome);
        bSearch = (LinearLayout) bottomBar.findViewById(R.id.layoutSearch);
        bSetting = (LinearLayout) bottomBar.findViewById(R.id.layoutSetting);
        bBtnHome = (ImageButton) bottomBar.findViewById(R.id.imgbtnHome);
        bBtnSearch = (ImageButton) bottomBar.findViewById(R.id.imgbtnSearch);
        bBtnSetting = (ImageButton) bottomBar.findViewById(R.id.imgbtnSetting);
*/

//        하단 바 버튼 클릭
 /*       bBtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                in.addFlags(in.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
            }
        });
        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                in.addFlags(in.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
            }
        });
        bBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Search 클릭", Toast.LENGTH_SHORT).show();
            }
        });
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Search 클릭", Toast.LENGTH_SHORT).show();
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
*/
    }
}
