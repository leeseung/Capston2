package com.example.dell.capston;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ToggleButton;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;


import com.androidquery.AQuery;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {
    EditText url;
    Button btn;
    String txt;
    Handler handler = new Handler();
    TextView textview;
    String result = "";
    String display = "";
    Button Button1,Button2,Button3,Button4;
    EditText et_webpage_src;
    NotificationManager mNotificationManager;

    String image_URL;
    String URL;

    String jsonPage;


    String responseDetails;
    String responseData;
    String responseStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        TabHost tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();

        //startActivity(new Intent(this, DialogActivity.class));

        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.settings); // 탭내용에 어떤것이 들어갈것인지
        spec.setIndicator("홈"); // 탭 버튼에 표시되는 텍스트 혹은 아이콘도 가능
        tabs.addTab(spec);
        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.back);
        spec.setIndicator("뒤로가기");
        tabs.addTab(spec);

        tabs.setCurrentTab(0); // 기본설정탭이 0번 인덱스 탭

        url = (EditText) findViewById(R.id.url);
        btn = (Button) findViewById(R.id.connect);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                txt = url.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + txt));
                startActivity(intent);
            }
        });
        Button1=  (Button)findViewById(R.id.Btn1);
        Button2=  (Button)findViewById(R.id.Btn2);
        Button3=  (Button)findViewById(R.id.Btn3);
        Button4=  (Button)findViewById(R.id.Btn4);

        Button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                new JsonLoadingTask1().execute(); //Async스레드를 시작

              /* if (Button1.isChecked()) {

                    //et_webpage_src = (EditText) findViewById(R.id.webpage_src);
                    //sendNotification(responseDetails, "");
                    new JsonLoadingTask1().execute(); //Async스레드를 시작

                }*/

            }
        });

        Button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                new JsonLoadingTask2().execute(); //Async스레드를 시작


            }
        });

        Button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                new JsonLoadingTask3().execute(); //Async스레드를 시작

            }
        });
        Button4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                new JsonLoadingTask4().execute(); //Async스레드를 시작

            }
        });



    }
    private void sendNotification(String Title,String message) {  //알림패널에 나타나게하는 코드
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(Title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());


    }

   public void sendCenterPush(String URL,  String image_URL) {
        sendNotification(URL,image_URL);
        Intent intent = new Intent(getApplicationContext(), DialogActivity.class);
        intent.putExtra("image_URL", image_URL);
       intent.putExtra("URL",URL);
        startActivity(intent);
    }


    public String getJsonText(int chk) {

        StringBuffer sb = new StringBuffer();
        try {

            //주어진 URL 문서의 내용을 문자열로 얻는다.
            switch (chk) {
                case 1:
                    jsonPage = getStringFromUrl("http://59.15.234.45/CapstoneDesign/jsps/testJson.jsp?&Beacon=1");
                    break;
                case 2:
                    jsonPage = getStringFromUrl("http://59.15.234.45/CapstoneDesign/jsps/testJson.jsp?&Beacon=2");
                    break;
                case 3:
                    jsonPage = getStringFromUrl("http://59.15.234.45/CapstoneDesign/jsps/testJson.jsp?&Beacon=3");
                    break;
                case 4:
                    jsonPage = getStringFromUrl("http://59.15.234.45/CapstoneDesign/jsps/testJson.jsp?&Beacon=4");
                    break;
                default:
                    //에러
                    //jsonPage = getStringFromUrl("http://59.15.234.45/CapstoneDesign/jsps/testJson.jsp?&Beacon=1");
            }
            //String jsonPage = getStringFromUrl("http://59.15.234.45/CapstoneDesign/jsps/testJson.jsp?&Beacon=1");

           JSONObject obj = new JSONObject(jsonPage); //
            JSONArray List = obj.getJSONArray("List");

            JSONObject info = List.getJSONObject(0);
            image_URL = info.getString("image_URL");
            //Beacon = info.getString("Beacon");
           // Filename = info.getString("Filename");
            URL = info.getString("URL");


            // sb.append("[ "+responseData+" ]\n");
            sb.append(image_URL + "\n");
           // sb.append(Beacon + "\n");
           // sb.append(Filename + "\n");
            sb.append(URL + "\n");
            sb.append("\n");

            //sendNotification("gggg", responseDetails);
            sendCenterPush(URL,image_URL);



        } catch (Exception e) {
            // TODO: handle exception
        }

        return sb.toString();
    }//getJsonText()----------

    private class JsonLoadingTask1 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strs) {

            return getJsonText(1);

        } // doInBackground : 백그라운드 작업을 진행한다.
        @Override
        protected void onPostExecute(String result) {
            // et_webpage_src.setText(result);
        } // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
    } // JsonLoadingTask

    private class JsonLoadingTask2 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strs) {

            return getJsonText(2);

        } // doInBackground : 백그라운드 작업을 진행한다.
        @Override
        protected void onPostExecute(String result) {
            // et_webpage_src.setText(result);
        } // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
    } // JsonLoadingTask

    private class JsonLoadingTask3 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strs) {

            return getJsonText(3);

        } // doInBackground : 백그라운드 작업을 진행한다.
        @Override
        protected void onPostExecute(String result) {
            // et_webpage_src.setText(result);
        } // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
    } // JsonLoadingTask
    private class JsonLoadingTask4 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strs) {

            return getJsonText(4);

        } // doInBackground : 백그라운드 작업을 진행한다.
        @Override
        protected void onPostExecute(String result) {
            // et_webpage_src.setText(result);
        } // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
    } // JsonLoadingTask
    // getStringFromUrl : 주어진 URL의 문서의 내용을 문자열로 반환

    public String getStringFromUrl(String pUrl){

        BufferedReader bufreader=null;
        HttpURLConnection urlConnection = null;

        StringBuffer page=new StringBuffer(); //읽어온 데이터를 저장할 StringBuffer객체 생성

        try {

            //[Type1]
            /*
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(pUrl));
            InputStream contentStream = response.getEntity().getContent();
            */

            //[Type2]
            URL url= new URL(pUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream contentStream = urlConnection.getInputStream();

            bufreader = new BufferedReader(new InputStreamReader(contentStream,"UTF-8"));
            String line = null;

            //버퍼의 웹문서 소스를 줄단위로 읽어(line), Page에 저장함
            while((line = bufreader.readLine())!=null){
                Log.d("line:",line);
                page.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //자원해제
            try {
                bufreader.close();
                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return page.toString();
    }// getStringFromUrl()-------------------------

    /**
     * Created by lee on 2016-04-14.
     */



}
