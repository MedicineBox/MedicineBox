package com.example.medicinebox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    LinearLayout bHome, bSearch, bSetting;
    ImageButton bBtnHome, bBtnSearch, bBtnSetting;
    View bottomBar;

//    firebase 관련
    FirebaseFirestore db = FirebaseFirestore.getInstance();


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
        ImageButton imgbtn1 = (ImageButton) findViewById(R.id.imgbtn1);
        ImageButton imgbtn2 = (ImageButton) findViewById(R.id.imgbtn2);

        imgbtnPill1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), AddMedicine.class);
                startActivity(in);
            }
        });

        imgbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> user = new HashMap<>();
                user.put("first", "Ada");                   // 데이터 넣기
                user.put("last", "Lovelace");
                user.put("born", 1815);

                // firestore 컬렉션 선택
                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {          // 데이터 넣기 성공시
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("firestore", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {                             // 데이터 넣기 실패시
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("firestore", "Error adding document", e);
                            }
                        });
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
