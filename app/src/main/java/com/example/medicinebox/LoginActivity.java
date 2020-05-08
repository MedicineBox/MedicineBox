package com.example.medicinebox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;


public class LoginActivity extends Activity {

    View clayout_top, clayout_bottom;
    EditText edtId, edtPasswd;
    Button btnLogin, btnRegister;
    String id, passwd, hint;
    boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        clayout_top = findViewById(R.id.login_clayout_top);
        clayout_bottom = findViewById(R.id.login_clayout_bottom);
        edtId = findViewById(R.id.login_edt_id);
        edtPasswd = findViewById(R.id.login_edt_passwd);
        btnLogin = findViewById(R.id.login_btn_login);
        btnRegister = findViewById(R.id.login_btn_register);



//        id EditText 터치 시
        edtId.setOnFocusChangeListener(new View.OnFocusChangeListener() {                   // 포커스 이동
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {                                      // edtId에 focus가 밎춰져 있을 경우
                    edtId.setHint("");                              // hint 제거
                    edtId.setCursorVisible(true);                   // cursor 보임
                } else {                                            // 포커스가 다른곳으로 옮겨졌을 때
                    if(edtId.getText().toString().equals("")) {
                        edtId.setHint("ID");                        // EditText에 내용이 없으면 hint 다시 보이기
                    }
                }
            }
        });

//        password EditText 터치 시
        edtPasswd.setOnFocusChangeListener(new View.OnFocusChangeListener() {               // 포커스 이동
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {                                          // edtPasswd에 focus가 밎춰져 있을 경우
                    edtPasswd.setHint("");                              // hint 제거
                    edtId.setCursorVisible(true);                       // cursor 보임
                } else {                                                // 포커스가 다른곳으로 옮겨졌을 때
                    if(edtPasswd.getText().toString().equals("")) {
                        edtPasswd.setHint("PASSWORD");                  // 내용이 없으면 hint 다시 보이기
                    }
                }
            }
        });

//        editText, 버튼 말고 다른곳 터치시 포커스 옮기고 키보드 내림
        clayout_top.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clayout_top.requestFocus();
                hideKeyboard();
                return true;
            }
        });
        clayout_bottom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clayout_top.requestFocus();
                hideKeyboard();
                return true;
            }
        });

//        login 버튼 터치 시
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = edtId.getText().toString();
                passwd = edtPasswd.getText().toString();

                if(id.equals("") || passwd.equals("")) {
                    Toast.makeText(getApplicationContext(), "ID 또는 Password를 확인해 주세요", Toast.LENGTH_SHORT).show();
                }

                //서버에서 확인해야함
                flag = true;    // 일단 확인했다 치고
                if(flag) {
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);            // MainActivity로 이동
                    Intent intent = new Intent(getApplicationContext(), Device_auth_wifi.class);            // Device_auth_wifi로 이동. 테스틀용
//                    intent.putExtra("id", id);                          // id값 넘김. 일단 없는걸로 치고 테스트
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Sign up page로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);            // MainActivity로 이동
                startActivity(intent);
            }
        });



    }




    public void hideKeyboard() {
        View view = getCurrentFocus();
        if(view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
