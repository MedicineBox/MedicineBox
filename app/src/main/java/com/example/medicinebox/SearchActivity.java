package com.example.medicinebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ImageView btnBack, btnHome, btnSearch;
    TextView editSearch;
    ListView searchList;

    boolean inname = false, initem = false;
    String item_name = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        btnBack = findViewById(R.id.btnBack);
        btnHome = findViewById(R.id.btnHome);
        btnSearch = findViewById(R.id.btnSearch);

        editSearch = findViewById(R.id.editSearch);
        searchList = findViewById(R.id.searchList);

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

        //검색
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });


        // 메인에서 검색한 값 가져오기
        Intent intent = getIntent();
        final String word = intent.getExtras().getString("search");
        editSearch.setText(word);

        final List<String> list = new ArrayList<>();

        AsyncTask.execute(new Runnable() {        // 비동기 방식으로 해야된됨. 안그럼 잘 안됨.
            @Override
            public void run() {
                ArrayList<String> resultArray = new ArrayList<>();
                try {
                    resultArray = search(word);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //resultArray = search(word);
                Log.i("resultArray", String.valueOf(resultArray));
                int i;
                for (i=0; i < resultArray.size(); i++) {
                    list.add(resultArray.get(i));
                }


                SearchActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                    @Override
                    public void run() {
                        ArrayAdapter adapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, list);
                        searchList.setAdapter(adapter);
                    }
                });

                /*Log.d("IN ASYNC", String.valueOf(flag));
                if(flag) {
                    //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    //startActivity(intent);
                }*/
            }
        });
    }


    private ArrayList<String> search(String word) throws JSONException {

        REST_API search = new REST_API("search");

        String json = "{\"search\" : \"" + word + "\"}";               // json에서 변수명도 큰따옴표로 감싸야함.

        String result = search.post(json);

        ArrayList<String> searchArray = new ArrayList<>();

        Log.d("ACCOUNTload", "result : " + result); //쿼리 결과값
        JSONArray jsonArray = new JSONArray(result);
        for(int i = 0 ; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String mediName = jsonObject.getString("medi_name");
            searchArray.add(mediName);
        }
        Log.i("searchArray", String.valueOf(searchArray));

//        Log.d("LOGIN", "result : " + result);
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("LOGIN", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            SearchActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(SearchActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("LOGIN", "FAIL!!!!!");
            SearchActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(SearchActivity.this, "검색결과 없음", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        } else {
            Log.d("LOGIN", "SUCCESS!!!!!");
            return searchArray;
        }

        return null;
    }
}
