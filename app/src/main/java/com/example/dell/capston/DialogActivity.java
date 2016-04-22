package com.example.dell.capston;

/**
 * Created by dell on 2016-04-02.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DialogActivity extends Activity implements
        OnClickListener {

    private Button mConfirm, mCancel;
    private TextView titleview;

    private  ImageView imageView;

    private AQuery aq;
    private ImageView img;

    private BitMap b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.push);
        aq = new AQuery( this );
        b = new BitMap();
        Intent intent = getIntent();
        String image_URL = intent.getStringExtra("image_URL");
        //String Beacon = intent.getStringExtra("Beacon");
       // String Filename = intent.getStringExtra("Filename");
        String URL = intent.getStringExtra("URL");

        titleview = (TextView) findViewById(R.id.titleView);
        img = (ImageView)findViewById(R.id.imageView);



        //img = (ImageView) findViewById(R.id.ImageView);
        //titleview.setText("파일경로 = " + FilePath);
       // titleview1.setText("비콘번호 = " + Beacon);
       // titleview2.setText("파일이름 = " + Filename);
       //titleview.setImageBitmap(b.getBitURL("http://image.slidesharecdn.com/androidstudio-130904110409-/95/android-studio-1-638.jpg?cb=1378292683"));
        titleview.setText(URL);
        Glide.with(this).load(image_URL).into(imageView);
        //aq.id( titleview ).image(FilePath);



        setContent();
    }

    private void setContent() {


        mConfirm = (Button) findViewById(R.id.btnConfirm);
        mCancel = (Button) findViewById(R.id.btnCancel);

        mConfirm.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirm:
                this.finish();
                break;
            case R.id.btnCancel:
                this.finish();
                break;
            default:
                break;
        }
    }
    public class BitMap {
        public Bitmap getBitURL(String src){
            HttpURLConnection connection = null;
            try {
                URL url = new URL(src);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (connection != null) connection.disconnect();
            }
        }
    }



}

