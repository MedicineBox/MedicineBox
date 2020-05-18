package com.example.medicinebox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


/*    LinearLayout bHome, bSearch, bSetting;
    ImageButton bBtnHome, bBtnSearch, bBtnSetting;
    View bottomBar;*/

//    firebase 관련
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String id;

    FloatingActionButton btnAddmedi;
    ImageView btnSetting, btnSearch, btnTake, btnTrash;
    TableRow row1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        btnSetting = findViewById(R.id.btnSetting);
        btnSearch = findViewById(R.id.btnSearch);
        btnTake = findViewById(R.id.btnTake);
        btnTrash = findViewById(R.id.btnTrash);
        row1 = findViewById(R.id.row1);
        btnAddmedi = findViewById(R.id.btnAddmedi);

        // 세션 id 받아오기
        id = Session.getUserData(getApplicationContext());

        Toast.makeText(getApplicationContext(),id+"님 안녕하세요!", Toast.LENGTH_SHORT).show();

        //설정
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });


        //검색
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });


        //복용하기
        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 복용할 약 있는 경우
                /*new AlertDialog.Builder(MainActivity.this)
                        .setTitle("복용 하시겠습니까?")
                        .setMessage("약 명")
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
                        .show();*/

                // 복용할 약 없는 경우
                /*new AlertDialog.Builder(MainActivity.this)
                        .setTitle("지금은 복용 시간이 아닙니다.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which){
                                Toast.makeText(getApplicationContext(), "확인 누름", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();*/
            }
        });

        //버리기
        btnTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버릴 약 있는 경우
                /*new AlertDialog.Builder(MainActivity.this)
                        .setTitle("약을 버리시겠습니까?")
                        .setMessage("약 명")
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
                        .show();*/

                // 버릴 약 없는 경우
                /*new AlertDialog.Builder(MainActivity.this)
                        .setTitle("유통기한이 지난 약이 없습니다.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which){
                                Toast.makeText(getApplicationContext(), "확인 누름", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();*/
            }
        });

        //목록에서 의약품 선택
        row1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                startActivity(intent);
            }
        });


        // 보관 의약품 추가
        btnAddmedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMediActivity.class);
                startActivity(intent);
            }
        });

/*
        imgbtnPill1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), AddMedicine.class);
                startActivity(in);
            }
        });
*/

 /*       imgbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> user = new HashMap<>();
                user.put("first", "Ada");                   // 데이터 넣기
                user.put("last", "Lovelace");
                user.put("born", 1815);

//                // firestore 컬렉션 선택
//                db.collection("users")
//                    .add(user)
//                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {          // 데이터 넣기 성공시
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                            Log.d("firestore", "DocumentSnapshot added with ID: " + documentReference.getId());
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {                             // 데이터 넣기 실패시
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w("firestore", "Error adding document", e);
//                        }
//                    });
                // firestore 컬렉션 선택
//                db.collection("users").document("user1")
//                        .set(user)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                 Log.d("firestore", "DocumentSnapshot successfully written!");
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("firestore", "Error writing document", e);
//                            }
//                        })
            }
        });
*/
 /*       imgbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new user with a first, middle, and last name
                Map<String, Object> user = new HashMap<>();
                user.put("first", "Alan");
                user.put("middle", "Mathison");
                user.put("last", "Turing");
                user.put("born", 1912);

                // Add a new document with a generated ID
//                db.collection("users").document()
//                    .add(user)
//                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                            Log.d("firebase", "DocumentSnapshot added with ID: " + documentReference.getId());
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w("firebase", "Error adding document", e);
//                        }
//                    });

            }
        });
*/


//        하단 바 버튼 클릭
  /*      bBtnHome.setOnClickListener(new View.OnClickListener() {
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
        });*/
    }
}
