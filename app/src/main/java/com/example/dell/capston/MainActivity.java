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
import android.os.Message;
import android.os.RemoteException;
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

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity implements BeaconConsumer {
    EditText url;
    Button btn;
    String txt;
    //Handler handler = new Handler();
    TextView textview;
    String result = "";
    String display = "";
    Button Button1,Button2,Button3,Button4;
    EditText et_webpage_src;
    NotificationManager mNotificationManager;

    String image_URL;
    String URL;

    String jsonPage;

    String Majorid;
    String Minorid;

    int count=0;


    private BeaconManager beaconManager;


    protected String proximityUuid;
    // 감지된 비콘들을 임시로 담을 리스트
    private List<Beacon> beaconList = new ArrayList<>();

   /* ArrayList<String> urlList  = new ArrayList<String>();
    ArrayList<String> image_urlList  = new ArrayList<String>();*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 실제로 비콘을 탐지하기 위한 비콘매니저 객체를 초기화
        beaconManager = BeaconManager.getInstanceForApplication(this);
        //extView = (TextView)findViewById(R.id.Textview);

        // 여기가 중요한데, 기기에 따라서 setBeaconLayout 안의 내용을 바꿔줘야 하는듯 싶다.
        // 필자의 경우에는 아래처럼 하니 잘 동작했음.
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

        // 비콘 탐지를 시작한다. 실제로는 서비스를 시작하는것.
        beaconManager.bind(this);

}


    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            //textView.setText("");

            // 비콘의 아이디와 거리를 측정하여 textView에 넣는다.
            for(Beacon beacon : beaconList){

                Majorid=beacon.getId2().toString();
                Minorid=beacon.getId3().toString();
                //1번비콘
                if(Majorid.equals("4660") && Minorid.equals("64001")) {
                    //textView.append("1번 ID : " + beacon.getId2() + " 2번 ID : " + beacon.getId3() + " / " + "Distance : " + Double.parseDouble(String.format("%.3f", beacon.getDistance())) + "m\n");
                    new JsonLoadingTask1().execute();
                }
                //2번비콘
                else if(Majorid.equals("4660") && Minorid.equals("64001")) {
                    //textView.append("1번 ID : " + beacon.getId2() + " 2번 ID : " + beacon.getId3() + " / " + "Distance : " + Double.parseDouble(String.format("%.3f", beacon.getDistance())) + "m\n");
                    new JsonLoadingTask2().execute();
                }
                //3번비콘
                else if(Majorid.equals("4660") && Minorid.equals("64001")) {
                    //textView.append("1번 ID : " + beacon.getId2() + " 2번 ID : " + beacon.getId3() + " / " + "Distance : " + Double.parseDouble(String.format("%.3f", beacon.getDistance())) + "m\n");
                    new JsonLoadingTask3().execute();
                }





            }
            if(count==1) {

                //handler.removeMessages(0);
                // 자기 자신을 1초마다 호출
                //handler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    };


    private void sendNotification(String Title,String message) {  //알림패널에 나타나게하는 코드
        //상단알림화면
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

        // 푸쉬화면
       /* Intent intent1 = new Intent(getApplicationContext(), DialogActivity.class);
        intent1.putExtra("image_URL", image_URL);
        intent1.putExtra("URL",URL);
        startActivity(intent1);*/

    }

   public void sendCenterPush(String URL,  String image_URL) {
        sendNotification(URL, image_URL);
        /*Intent intent = new Intent(getApplicationContext(), DialogActivity.class);
        intent.putExtra("image_URL", image_URL);
       intent.putExtra("URL",URL);
        startActivity(intent);*/
    }


    public String getJsonText(int chk) {

        ArrayList<String> urlList  = new ArrayList<String>();
        ArrayList<String> image_urlList  = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        try {

            String chkString = String.valueOf(chk);

            jsonPage = getStringFromUrl("http://59.15.234.45/CapstoneDesign/jsps/testJson.jsp?&Beacon=" + chkString);
            /*//주어진 URL 문서의 내용을 문자열로 얻는다.
            switch (chk) {
                case 1:
                    jsonPage = getStringFromUrl("http://59.15.234.45/CapstoneDesign/jsps/testJson.jsp?&Beacon=" + chkString);
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
            }*/
            //String jsonPage = getStringFromUrl("http://59.15.234.45/CapstoneDesign/jsps/testJson.jsp?&Beacon=1");



           JSONObject obj = new JSONObject(jsonPage); //
            JSONArray List = obj.getJSONArray("List");

            for(int i=0;i<List.length();i++) {
                JSONObject info = List.getJSONObject(i);
                image_URL = info.getString("image_URL");
                URL = info.getString("URL");

                urlList.add(URL);
                image_urlList.add(image_URL );
            }

            // sb.append("[ "+responseData+" ]\n");
            sb.append(image_URL + "\n");
           // sb.append(Beacon + "\n");
           // sb.append(Filename + "\n");
            sb.append(URL + "\n");
            sb.append("\n");

            //sendNotification("gggg", responseDetails);
            sendCenterPush("제목","내용");

            Intent intent1 = new Intent(getApplicationContext(), DialogActivity.class);
            intent1.putExtra("image_urlList", image_urlList);
            intent1.putExtra("urlList", urlList);
            startActivity(intent1);


        } catch (Exception e) {
            // TODO: handle exception
        }

        return sb.toString();
    }//getJsonText()----------

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            // 비콘이 감지되면 해당 함수가 호출된다. Collection<Beacon> beacons에는 감지된 비콘의 리스트가,
            // region에는 비콘들에 대응하는 Region 객체가 들어온다.
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    beaconList.clear();
                    for (Beacon beacon : beacons) {
                        beaconList.add(beacon);
                    }
                    // handler.sendEmptyMessage(0);

                }
                if(count==0){
                    handler.sendEmptyMessage(0);
                    count=1;
                }

            }

        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }

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
