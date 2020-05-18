package com.example.medicinebox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class StoreActivity extends AppCompatActivity {

    ImageView btnBack, btnMenu;
    FloatingActionButton btnPilladd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity);

        btnBack = findViewById(R.id.btnBack);
        btnMenu = findViewById(R.id.btnMenu);
        btnPilladd = findViewById(R.id.btnPilladd);

        //뒤로가기
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //메뉴 버튼
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(StoreActivity.this , btnMenu);

                MenuInflater inf = popup.getMenuInflater();
                inf.inflate(R.menu.menu, popup.getMenu());
                popup.show();

                //PopupMenu popup = new PopupMenu(getApplicationContext(),v);
                //getMenuInflater().inflate(R.menu.menu,popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.store_edit:
                                Toast.makeText(getApplication(),"수정하기",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), EditMediActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.store_delete:
                                new AlertDialog.Builder(StoreActivity.this)
                                        .setTitle("정말 삭제하시겠습니까?")
                                        .setMessage("삭제 후 약을 모두 꺼내야 합니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which){
                                                Toast.makeText(getApplicationContext(), "확인 누름", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which){
                                                Toast.makeText(getApplicationContext(), "취소 누름", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .show();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
            }
        });

        //약 추가
       btnPilladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "잠금이 해제되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        TabHost tabHost = findViewById(R.id.tabHost) ;
        tabHost.setup();

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        TabHost.TabSpec ts1 = tabHost.newTabSpec("Tab Spec 1") ;
        ts1.setContent(R.id.tab1) ;
        ts1.setIndicator("의약품 정보") ;
        tabHost.addTab(ts1)  ;

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        TabHost.TabSpec ts2 = tabHost.newTabSpec("Tab Spec 2") ;
        ts2.setContent(R.id.tab2) ;
        ts2.setIndicator("복용 정보") ;
        tabHost.addTab(ts2) ;


    }
}
