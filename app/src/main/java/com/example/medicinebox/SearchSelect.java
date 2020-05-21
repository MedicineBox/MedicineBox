package com.example.medicinebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchSelect extends AppCompatActivity {

    TextView mediName, mediEffect;
    ImageView btnBack, btnHome, mediPhoto;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_select);

        btnBack = findViewById(R.id.btnBack);
        btnHome = findViewById(R.id.btnHome);

        mediName = findViewById(R.id.mediName);
        mediEffect = findViewById(R.id.mediEffect);
        mediPhoto = findViewById(R.id.mediPhoto);

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

        // 검색 결과에서 선택한 값 넘겨받음
        Intent intent = getIntent();
        final String name = intent.getExtras().getString("name");
        mediName.setText(name);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL imgurl = new URL(getXmlData(name));
                    Log.i("imgurl", String.valueOf(imgurl));
                    //Log.i("count", String.valueOf(count));
                    /*if (imgurl.equals(0)) {
                        SearchSelect.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                            @Override
                            public void run() {
                                mediPhoto.setImageResource(R.drawable.search_logo);
                            }
                        });
                    } else {*/
                        InputStream is = imgurl.openStream();
                        final Bitmap bm = BitmapFactory.decodeStream(is);
                        SearchSelect.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                            @Override
                            public void run() {
                                mediPhoto.setImageBitmap(bm);
                            }
                        });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    String getXmlData(String word){

        StringBuffer buffer = new StringBuffer();

        Log.i("word",word);
        //String name = URLEncoder.encode(str);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
        String key= "NNlPSgKdYOzfOQTW0r5g1EGKaq43lm%2FmEwfx%2Fyi09i6FtGJCPyG1fB2yIEvE4lCLfxs7X6O%2FXtZ89jslBzW%2BWw%3D%3D";
        String queryUrl="http://apis.data.go.kr/1470000/MdcinGrnIdntfcInfoService/getMdcinGrnIdntfcInfoList?"+"ServiceKey="+key+"&item_name="+word;
        Log.i("url",queryUrl);
        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();
            Log.i("eventType", String.valueOf(eventType));

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        //buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기
                        if(tag.equals("item"));// 첫번째 검색결과
                        else if(tag.equals("ITEM_IMAGE")){
                            //buffer.append("큰제품이미지 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            //buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("CLASS_NAME")){
                            //buffer.append("분류명 : ");
                            xpp.next();
                            mediEffect.setText(xpp.getText());
                            //buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            //buffer.append("\n");//줄바꿈 문자 추가
                        }


                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("item"))
                            //buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
            e.printStackTrace();
        }

        //buffer.append("파싱 끝\n");
        return buffer.toString();//StringBuffer 문자열 객체 반환

    }//getXmlData method....
}
