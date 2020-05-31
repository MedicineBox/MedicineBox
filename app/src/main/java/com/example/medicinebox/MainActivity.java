package com.example.medicinebox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {


/*    LinearLayout bHome, bSearch, bSetting;
    ImageButton bBtnHome, bBtnSearch, bBtnSetting;
    View bottomBar;*/

//    firebase 관련
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String id, device_ip;

    TextView editSearch;
    TextView slotNum1, slotNum2, slotNum3, slotNum4, slotNum5, slotNum6, slotNum7;
    TextView mediNum1, mediNum2, mediNum3, mediNum4, mediNum5, mediNum6, mediNum7;
    TextView mediName1, mediName2, mediName3, mediName4, mediName5, mediName6, mediName7;
    TextView todayChk1, todayChk2, todayChk3, todayChk4, todayChk5, todayChk6, todayChk7;
    ImageView mediPhoto1, mediPhoto2, mediPhoto3, mediPhoto4, mediPhoto5, mediPhoto6, mediPhoto7;
    FloatingActionButton btnAddmedi;
    ImageView btnSetting, btnSearch, btnTake, btnTrash;
    TableRow row1, row2, row3, row4, row5, row6, row7;
    View baselayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        editSearch = findViewById(R.id.editSearch);
        btnSetting = findViewById(R.id.btnSetting);
        btnSearch = findViewById(R.id.btnSearch);
        btnTake = findViewById(R.id.btnTake);
        btnTrash = findViewById(R.id.btnTrash);

        row1 = findViewById(R.id.row1);
        row2 = findViewById(R.id.row2);
        row3 = findViewById(R.id.row3);
        row4 = findViewById(R.id.row4);
        row5 = findViewById(R.id.row5);
        row6 = findViewById(R.id.row6);
        row7 = findViewById(R.id.row7);

        slotNum1 = findViewById(R.id.slotNum1);
        slotNum2 = findViewById(R.id.slotNum2);
        slotNum3 = findViewById(R.id.slotNum3);
        slotNum4 = findViewById(R.id.slotNum4);
        slotNum5 = findViewById(R.id.slotNum5);
        slotNum6 = findViewById(R.id.slotNum6);
        slotNum7 = findViewById(R.id.slotNum7);

        mediNum1 = findViewById(R.id.mediNum1);
        mediNum2 = findViewById(R.id.mediNum2);
        mediNum3 = findViewById(R.id.mediNum3);
        mediNum4 = findViewById(R.id.mediNum4);
        mediNum5 = findViewById(R.id.mediNum5);
        mediNum6 = findViewById(R.id.mediNum6);
        mediNum7 = findViewById(R.id.mediNum7);

        mediName1 = findViewById(R.id.mediName1);
        mediName2 = findViewById(R.id.mediName2);
        mediName3 = findViewById(R.id.mediName3);
        mediName4 = findViewById(R.id.mediName4);
        mediName5 = findViewById(R.id.mediName5);
        mediName6 = findViewById(R.id.mediName6);
        mediName7 = findViewById(R.id.mediName7);

        mediPhoto1 = findViewById(R.id.mediPhoto1);
        mediPhoto2 = findViewById(R.id.mediPhoto2);
        mediPhoto3 = findViewById(R.id.mediPhoto3);
        mediPhoto4 = findViewById(R.id.mediPhoto4);
        mediPhoto5 = findViewById(R.id.mediPhoto5);
        mediPhoto6 = findViewById(R.id.mediPhoto6);
        mediPhoto7 = findViewById(R.id.mediPhoto7);

        todayChk1 = findViewById(R.id.todayChk1);
        todayChk2 = findViewById(R.id.todayChk2);
        todayChk3 = findViewById(R.id.todayChk3);
        todayChk4 = findViewById(R.id.todayChk4);
        todayChk5 = findViewById(R.id.todayChk5);
        todayChk6 = findViewById(R.id.todayChk6);
        todayChk7 = findViewById(R.id.todayChk7);


        btnAddmedi = findViewById(R.id.btnAddmedi);
        baselayout = findViewById(R.id.main_baselayout);

        // 세션 id 받아오기
        id = Session.getUserID(getApplicationContext());
        device_ip = Session.getDeviceIP(getApplicationContext());

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
                intent.putExtra("search",editSearch.getText().toString()); //검색어 검색 결과화면으로 넘겨주기
                startActivity(intent);
                editSearch.setText("");
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

        //보관의약품 조회
        //오늘 요일
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("EE");
        TimeZone time = TimeZone.getTimeZone("Asia/Seoul");
        format.setTimeZone(time);
        final String day = format.format(today);
        Log.i("day", day);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<String> timeloadArray = timeload(id, day);
                    Log.i("timeloadArray", String.valueOf(timeloadArray));

                    // 푸시알림
                    int k;
                    for (k = 0; k < timeloadArray.size(); k++) {
                        new AlarmHATT(getApplicationContext()).Alarm(timeloadArray.get(k));
                    }

                    ArrayList<String> takeloadArray = takeload(id, day);
                    Log.i("takeloadArray", String.valueOf(takeloadArray));

                    ArrayList<String> storeloadArray = storeload(id);
                    Log.i("storeloadArray", String.valueOf(storeloadArray));

                    String num1 = mediName1.getText().toString();
                    String num2 = mediName2.getText().toString();
                    String num3 = mediName3.getText().toString();
                    String num4 = mediName4.getText().toString();
                    String num5 = mediName5.getText().toString();
                    String num6 = mediName6.getText().toString();

                    int i;
                    for (i=0; i < takeloadArray.size(); i++) {
                        if (takeloadArray.get(i).equals(num1)) {
                            todayChk1.setText("V");
                        } else if (takeloadArray.get(i).equals(num2)) {
                            todayChk2.setText("V");
                        } else if (takeloadArray.get(i).equals(num3)) {
                            todayChk3.setText("V");
                        } else if (takeloadArray.get(i).equals(num4)) {
                            todayChk4.setText("V");
                        } else if (takeloadArray.get(i).equals(num5)) {
                            todayChk5.setText("V");
                        } else if (takeloadArray.get(i).equals(num6)) {
                            todayChk6.setText("V");
                        }
                    }

                    int j;
                    for (j=0; j < storeloadArray.size(); j++) {
                        //list.add(storeloadArray.get(i));
                        if (storeloadArray.get(j).equals("1")) {
                            String num = mediName1.getText().toString();
                            final String name = mediload(num);
                            MainActivity.this.runOnUiThread(new Runnable() {                                     // UI 쓰레드에서 실행
                                @Override
                                public void run() {
                                    row1.setVisibility(View.VISIBLE);
                                    mediName1.setText(name);

                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                URL imgurl = new URL("http://ec2-3-34-54-94.ap-northeast-2.compute.amazonaws.com:8080/project/medicine/img/"+name+".png");
                                                Log.i("imgurl", String.valueOf(imgurl));
                                                InputStream is = imgurl.openStream();
                                                final Bitmap bm = BitmapFactory.decodeStream(is);
                                                MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                                                    @Override
                                                    public void run() {
                                                        mediPhoto1.setImageBitmap(bm);
                                                    }
                                                });

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    thread.start();
                                }
                            });

                        } else if (storeloadArray.get(j).equals("2")) {
                            String num = mediName2.getText().toString();
                            final String name = mediload(num);
                            MainActivity.this.runOnUiThread(new Runnable() {                                     // UI 쓰레드에서 실행
                                @Override
                                public void run() {
                                    row2.setVisibility(View.VISIBLE);
                                    mediName2.setText(name);

                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                URL imgurl = new URL("http://ec2-3-34-54-94.ap-northeast-2.compute.amazonaws.com:8080/project/medicine/img/"+name+".png");
                                                Log.i("imgurl", String.valueOf(imgurl));
                                                InputStream is = imgurl.openStream();
                                                final Bitmap bm = BitmapFactory.decodeStream(is);
                                                MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                                                    @Override
                                                    public void run() {
                                                        mediPhoto2.setImageBitmap(bm);
                                                    }
                                                });

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    thread.start();
                                }
                            });
                        } else if (storeloadArray.get(j).equals("3")) {
                            String num = mediName3.getText().toString();
                            final String name = mediload(num);
                            MainActivity.this.runOnUiThread(new Runnable() {                                     // UI 쓰레드에서 실행
                                @Override
                                public void run() {
                                    row3.setVisibility(View.VISIBLE);
                                    mediName3.setText(name);

                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                URL imgurl = new URL("http://ec2-3-34-54-94.ap-northeast-2.compute.amazonaws.com:8080/project/medicine/img/"+name+".png");
                                                Log.i("imgurl", String.valueOf(imgurl));
                                                InputStream is = imgurl.openStream();
                                                final Bitmap bm = BitmapFactory.decodeStream(is);
                                                MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                                                    @Override
                                                    public void run() {
                                                        mediPhoto3.setImageBitmap(bm);
                                                    }
                                                });

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    thread.start();
                                }
                            });
                        } else if (storeloadArray.get(j).equals("4")) {
                            String num = mediName4.getText().toString();
                            final String name = mediload(num);
                            MainActivity.this.runOnUiThread(new Runnable() {                                     // UI 쓰레드에서 실행
                                @Override
                                public void run() {
                                    row4.setVisibility(View.VISIBLE);
                                    mediName4.setText(name);

                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                URL imgurl = new URL("http://ec2-3-34-54-94.ap-northeast-2.compute.amazonaws.com:8080/project/medicine/img/"+name+".png");
                                                Log.i("imgurl", String.valueOf(imgurl));
                                                InputStream is = imgurl.openStream();
                                                final Bitmap bm = BitmapFactory.decodeStream(is);
                                                MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                                                    @Override
                                                    public void run() {
                                                        mediPhoto4.setImageBitmap(bm);
                                                    }
                                                });

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    thread.start();
                                }
                            });
                        } else if (storeloadArray.get(j).equals("5")) {
                            String num = mediName5.getText().toString();
                            final String name = mediload(num);
                            MainActivity.this.runOnUiThread(new Runnable() {                                     // UI 쓰레드에서 실행
                                @Override
                                public void run() {
                                    row5.setVisibility(View.VISIBLE);
                                    mediName5.setText(name);

                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                URL imgurl = new URL("http://ec2-3-34-54-94.ap-northeast-2.compute.amazonaws.com:8080/project/medicine/img/"+name+".png");
                                                Log.i("imgurl", String.valueOf(imgurl));
                                                InputStream is = imgurl.openStream();
                                                final Bitmap bm = BitmapFactory.decodeStream(is);
                                                MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                                                    @Override
                                                    public void run() {
                                                        mediPhoto5.setImageBitmap(bm);
                                                    }
                                                });

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    thread.start();
                                }
                            });
                        } else if (storeloadArray.get(j).equals("6")) {
                            String num = mediName6.getText().toString();
                            final String name = mediload(num);
                            MainActivity.this.runOnUiThread(new Runnable() {                                     // UI 쓰레드에서 실행
                                @Override
                                public void run() {
                                    row6.setVisibility(View.VISIBLE);
                                    mediName6.setText(name);

                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                URL imgurl = new URL("http://ec2-3-34-54-94.ap-northeast-2.compute.amazonaws.com:8080/project/medicine/img/"+name+".png");
                                                Log.i("imgurl", String.valueOf(imgurl));
                                                InputStream is = imgurl.openStream();
                                                final Bitmap bm = BitmapFactory.decodeStream(is);
                                                MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                                                    @Override
                                                    public void run() {
                                                        mediPhoto6.setImageBitmap(bm);
                                                    }
                                                });

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    thread.start();
                                }
                            });
                        } else if (storeloadArray.get(j).equals("7")) {
                            String num = mediName7.getText().toString();
                            final String name = mediload(num);
                            MainActivity.this.runOnUiThread(new Runnable() {                                     // UI 쓰레드에서 실행
                                @Override
                                public void run() {
                                    row7.setVisibility(View.VISIBLE);
                                    mediName7.setText(name);

                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                URL imgurl = new URL("http://ec2-3-34-54-94.ap-northeast-2.compute.amazonaws.com:8080/project/medicine/img/"+name+".png");
                                                Log.i("imgurl", String.valueOf(imgurl));
                                                InputStream is = imgurl.openStream();
                                                final Bitmap bm = BitmapFactory.decodeStream(is);
                                                MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                                                    @Override
                                                    public void run() {
                                                        mediPhoto7.setImageBitmap(bm);
                                                    }
                                                });

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    thread.start();
                                }
                            });
                        }
                    }

                    } catch (JSONException ex) {
                    ex.printStackTrace();
                }

            }
        });


       //목록에서 의약품 선택
        row1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                intent.putExtra("num",mediNum1.getText().toString());
                intent.putExtra("name",mediName1.getText().toString());
                intent.putExtra("slot",slotNum1.getText().toString());
                startActivity(intent);
            }
        });

        row2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                intent.putExtra("num",mediNum2.getText().toString());
                intent.putExtra("name",mediName2.getText().toString());
                intent.putExtra("slot",slotNum2.getText().toString());
                startActivity(intent);
            }
        });

        row3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                intent.putExtra("num",mediNum3.getText().toString());
                intent.putExtra("name",mediName3.getText().toString());
                intent.putExtra("slot",slotNum3.getText().toString());
                startActivity(intent);
            }
        });

        row4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                intent.putExtra("num",mediNum4.getText().toString());
                intent.putExtra("name",mediName4.getText().toString());
                intent.putExtra("slot",slotNum4.getText().toString());
                startActivity(intent);
            }
        });

        row5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                intent.putExtra("num",mediNum5.getText().toString());
                intent.putExtra("name",mediName5.getText().toString());
                intent.putExtra("slot",slotNum5.getText().toString());
                startActivity(intent);
            }
        });

        row6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                intent.putExtra("num",mediNum6.getText().toString());
                intent.putExtra("name",mediName6.getText().toString());
                intent.putExtra("slot",slotNum6.getText().toString());
                startActivity(intent);
            }
        });

        row7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                intent.putExtra("num",mediNum7.getText().toString());
                intent.putExtra("name",mediName7.getText().toString());
                intent.putExtra("slot",slotNum7.getText().toString());
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


        baselayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                baselayout.setFocusable(true);
                baselayout.requestFocus();
                return true;
            }
        });



        Toast.makeText(getApplicationContext(), "Device IP : " + device_ip, Toast.LENGTH_SHORT).show();

    }


//    키보드 숨김
    public void hideKeyboard() {
        View view = getCurrentFocus();
        if(view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private ArrayList<String> takeload(String id, String day) throws JSONException {
        REST_API takeload = new REST_API("takeload");

        String json = "{\"id\" : \"" + id + "\", \"day\" : \"" + day + "\"}";

        String result = takeload.post(json);

        ArrayList<String> takeArray = new ArrayList<>();

        Log.d("takeload", "result : " + result);
        JSONArray jsonArray = new JSONArray(result);

        for(int i = 0 ; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String medi_num = jsonObject.getString("medi_num");
            takeArray.add(medi_num);
        }

        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("Takeload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("Takeload", "FAIL!!!!!");
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "보관의약품이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        } else if(result != null) {
            Log.d("Takeload", "SUCCESS!!!!!");
            return takeArray;
        }

        return null;
    }

    private ArrayList<String> timeload(String id, String day) throws JSONException {
        REST_API timeload = new REST_API("timeload");

        String json = "{\"id\" : \"" + id + "\", \"day\" : \"" + day + "\"}";

        String result = timeload.post(json);

        ArrayList<String> timeArray = new ArrayList<>();

        Log.d("timeload", "result : " + result);
        JSONArray jsonArray = new JSONArray(result);

        for(int i = 0 ; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String take_time = jsonObject.getString("take_time");
            timeArray.add(take_time);
        }

        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("timeload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("timeload", "FAIL!!!!!");
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "보관의약품이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        } else if(result != null) {
            Log.d("timeload", "SUCCESS!!!!!");
            return timeArray;
        }

        return null;
    }

    private ArrayList<String> storeload(String id) throws JSONException {

        REST_API storeload = new REST_API("storeload");

        String json = "{\"id\" : \"" + id + "\"}";

        String result = storeload.post(json);

        ArrayList<String> storeArray = new ArrayList<>();

        Log.d("STOREload", "result : " + result); //쿼리 결과값

        JSONArray jsonArray = new JSONArray(result);
        for(int i = 0 ; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String storage_slot = jsonObject.getString("storage_slot");
            String medi_num = jsonObject.getString("medi_num");
            storeArray.add(storage_slot);

            if (storage_slot.equals("1")) {
                slotNum1.setText(storage_slot);
                mediNum1.setText(medi_num);
                mediName1.setText(medi_num);
            }
            else if (storage_slot.equals("2")) {
                slotNum2.setText(storage_slot);
                mediNum2.setText(medi_num);
                mediName2.setText(medi_num);
            }
            else if (storage_slot.equals("3")) {
                slotNum3.setText(storage_slot);
                mediNum3.setText(medi_num);
                mediName3.setText(medi_num);
            }
            else if (storage_slot.equals("4")) {
                slotNum4.setText(storage_slot);
                mediNum4.setText(medi_num);
                mediName4.setText(medi_num);
            }
            else if (storage_slot.equals("5")) {
                slotNum5.setText(storage_slot);
                mediNum5.setText(medi_num);
                mediName5.setText(medi_num);
            }
            else if (storage_slot.equals("6")) {
                slotNum6.setText(storage_slot);
                mediNum6.setText(medi_num);
                mediName6.setText(medi_num);
            }
            else if (storage_slot.equals("7")) {
                slotNum7.setText(storage_slot);
                mediNum7.setText(medi_num);
                mediName7.setText(medi_num);
            }
        }


        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("STOREload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("STOREload", "FAIL!!!!!");
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "보관의약품이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        } else if(result != null) {
            Log.d("STOREload", "SUCCESS!!!!!");
            return storeArray;
        }

        return null;
    }

    private String mediload(String num) throws JSONException {

        REST_API mediload = new REST_API("mediload");

        String json = "{\"num\" : \"" + num + "\"}";

        String result = mediload.post(json);
        Log.d("MEDIload", "result : " + result); //쿼리 결과값
        String medi_name = null;
        JSONArray jsonArray = new JSONArray(result);
        for(int i = 0 ; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            medi_name = jsonObject.getString("medi_name");
        }


        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("MEDIload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("MEDIload", "FAIL!!!!!");
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "보관의약품이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        } else if(result != null) {
            Log.d("MEDIload", "SUCCESS!!!!!");
            return medi_name;
        }

        return null;
    }

    //푸시 알림
    public class AlarmHATT {
        private Context context;
        public AlarmHATT(Context context) {
            this.context=context;
        }
        public void Alarm(String time) {
            String[] split = time.split(":");

            int hour = Integer.parseInt(split[0]);
            int min = Integer.parseInt(split[1]);

            Log.i("hour", String.valueOf(hour));
            Log.i("min", String.valueOf(min));

            AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(MainActivity.this, BroadcastD.class);

            PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            //알람시간 calendar에 set해주기
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), hour, min, 0);

            //알람 예약
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        }
    }
}
