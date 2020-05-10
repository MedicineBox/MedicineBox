package com.example.medicinebox;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DBConn extends AsyncTask<String, String, String> {
    public static String serverUrl = "http://ec2-3-18-185-130.us-east-2.compute.amazonaws.com/node/";

    String sendMsg, receiveMsg;

    HttpURLConnection conn = null;
    BufferedReader reader = null;

    DBConn(String sendmsg) {
        this.sendMsg = sendmsg;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(serverUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-url-urlencoded");
            conn.setRequestMethod("GET");
//            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

//            JSON 형식
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("user_id", "user2");
            jsonObject.accumulate("passwd", "1234");


            conn.connect();                                                 // node.js 연결 수행


            InputStream inputStream = conn.getInputStream();                // 입력 스트림 생성
            reader = new BufferedReader(new InputStreamReader(inputStream));// 속도를 향상시키고 부하를 줄이기 위한 버퍼 선언
            StringBuffer buffer = new StringBuffer();                       // 실제 데이터를 받는 곳

            String line = "";                                               // line 별 String 받기 위한 임시 변수

/*
            node.js 서버로부터 데이터 가져옴
            한줄씩 읽어서 line 변수에 저장한 후에 buffer 변수에 계속 추가해줌.
            다 읽으면 line이 null 이므로 반복문 빠져나감
*/
            while((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            return buffer.toString();                                       // String으로 형변환 해줘야 한다.


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                conn.disconnect();
                    if(reader != null) {
                        reader.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
