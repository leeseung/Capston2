package com.example.dell.capston;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
    ToggleButton Button1;
    EditText et_webpage_src;
    NotificationManager mNotificationManager;

    String responseDetails;



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
        spec.setIndicator("설정"); // 탭 버튼에 표시되는 텍스트 혹은 아이콘도 가능
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
        Button1=  (ToggleButton)findViewById(R.id.Btn1);
        Button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Button1.isChecked()) {

                    //et_webpage_src = (EditText) findViewById(R.id.webpage_src);
                    //sendNotification(responseDetails, "");
                    new JsonLoadingTask().execute(); //Async스레드를 시작

                }

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

    public void sendCenterPush(String pw,String name) {
        sendNotification(pw, name);
        Intent intent = new Intent(getApplicationContext(), DialogActivity.class);
        intent.putExtra("PW",pw);
        intent.putExtra("NAME",name);
        startActivity(intent);
    }

    public String getJsonText() {

        StringBuffer sb = new StringBuffer();
        try {

            //주어진 URL 문서의 내용을 문자열로 얻는다.
            String jsonPage = getStringFromUrl("http://ajax.googleapis.com/ajax/services/search/images?");

            //읽어들인 JSON포맷의 데이터를 JSON객체로 변환
            JSONObject json = new JSONObject(jsonPage);
           // String responseData = (String) json.get("responseData");
           responseDetails = (String) json.get("responseDetails");
           // sendNotification("gigigi", responseDetails);
            //String id = (String) json.get("id");

           // sb.append("[ "+responseData+" ]\n");
            sb.append(responseDetails + "\n");
            sb.append("\n");

            //sendNotification("gggg", responseDetails);
            sendCenterPush("gggg", responseDetails);




            /*String pw = (String) json.get("pw");
            String name = (String) json.get("name");
            String id = (String) json.get("id");



            sb.append("[ "+pw+" ]\n");
            sb.append(name+"\n");
            sb.append(id+"\n");
            sb.append("\n");

            sendCenterPush(pw, name);*/


           /* //ksk_list의 값은 배열로 구성 되어있으므로 JSON 배열생성
            JSONArray jArr = json.getJSONArray("ksk_list");

            //배열의 크기만큼 반복하면서, ksNo과 korName의 값을 추출함
            for (int i=0; i<jArr.length(); i++){

                //i번째 배열 할당
                json = jArr.getJSONObject(i);

                //ksNo,korName의 값을 추출함
                String ksNo = json.getString("ksNo");
                String korName = json.getString("korName");
                System.out.println("ksNo:"+ksNo+"/korName:"+korName);

                //StringBuffer 출력할 값을 저장
                sb.append("[ "+ksNo+" ]\n");
                sb.append(korName+"\n");
                sb.append("\n");
            }*/

        } catch (Exception e) {
            // TODO: handle exception
        }

        return sb.toString();
    }//getJsonText()----------

    private class JsonLoadingTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strs) {
            return getJsonText();
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

}
