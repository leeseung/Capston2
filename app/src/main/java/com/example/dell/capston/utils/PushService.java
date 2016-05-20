package com.example.dell.capston.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.dell.capston.PosterFragment;
import com.example.dell.capston.R;
import com.example.dell.capston.TestMainActivity;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PushService extends Service implements BeaconConsumer {
    private BeaconManager beaconManager;

    int count = 0;
    String Majorid;
    String Minorid;
    private Context mContext = null;

    protected String proximityUuid;
    private List<Beacon> beaconList = new ArrayList<>();

    public PushService() {


    }

    @Override
    public void onCreate() {
        Log.d("TAG2", "# Service - onCreate() starts here");

        mContext = getApplicationContext();

        // 실제로 비콘을 탐지하기 위한 비콘매니저 객체를 초기화
        beaconManager = BeaconManager.getInstanceForApplication(this);

        // 여기가 중요한데, 기기에 따라서 setBeaconLayout 안의 내용을 바꿔줘야 하는듯 싶다.
        // 필자의 경우에는 아래처럼 하니 잘 동작했음.
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

        // 비콘 탐지를 시작한다. 실제로는 서비스를 시작하는것.

        //new BeaconBackground().execute();
        beaconManager.bind(this);



    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.


        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    beaconList.clear();
                    for (Beacon beacon : beacons) {
                       beaconList.add(beacon);
                       //handler.sendEmptyMessage(0);
                        handler.sendEmptyMessage(0);
                    }

                }
                //한번만 핸들러가 돌아가게 설정정
			/*	if (count == 0) {

					handler.sendEmptyMessage(0);
					count = 1;
				}*/

            }

        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
        }
    }


    /*****************************************************
     * Handler, Callback, Sub-classes
     ******************************************************/

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            //textView.setText("");

            // ������ ���̵�� �Ÿ��� �����Ͽ� textView�� �ִ´�.
            for (Beacon beacon : beaconList) {
                count = 1;
                //BeaconCheck=true;
                Majorid = beacon.getId2().toString();
                Minorid = beacon.getId3().toString();
                sendNotification("HS비콘","ㅎㅇ");
                //1������
                //sendNotification("HS Beacon","포스터" );
               /* if (Majorid.equals("4660") && Minorid.equals("64002")) {


                    //new BackgroundPush().execute();
                    //new JsonLoadingPush().execute();
                    sendNotification("HS Beacon","공모전 " );

                    //new JsonLoadingTask1().execute();
                    //sendNotification("HS Beacon","공모전 " );
					*//*if((Beacon1Poster.equals(true) ) &&  (Beacon1EmptyClass.equals(true)))
					sendNotification("HS Beacon","포스터, 빈강의실 " );
					else if((Beacon1Poster.equals(true))  &&  (Beacon1EmptyClass.equals(false) ))
						sendNotification("HS Beacon","포스터, 빈강의실 " );
					else if ((Beacon1EmptyClass.equals(true)) && (Beacon1Poster.equals(false)) )
						sendNotification("HS Beacon","빈강의실 " );
					else
						return;*//*
                }*/


				/*else if(Majorid.equals("4660") && Minorid.equals("64001")) {

					//new JsonLoadingTask2().execute();
				}

				else if(Majorid.equals("4660") && Minorid.equals("64001")) {

					//new JsonLoadingTask3().execute();
				}*/


            }

        }
    };

    private void sendNotification(String Title, String message) {  //알림패널에 나타나게하는 코드
        Intent intent = new Intent(this, TestMainActivity.class);
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
}
