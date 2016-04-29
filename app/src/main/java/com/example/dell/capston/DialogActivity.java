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
import java.util.ArrayList;

public class DialogActivity extends Activity implements
        OnClickListener {

    private Button mConfirm, mCancel;
    private TextView titleview1,titleview2,titleview3;

    private ImageView imageView1,imageView2,imageView3;

    Bundle B = getIntent().getExtras();
    /*ArrayList<String> image_urlList = B.getStringArrayList("image_urlList");
    ArrayList<String> urlList = B.getStringArrayList("urlList");*/
    ArrayList<String> urlList  = new ArrayList<String>();
    ArrayList<String> image_urlList  = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.push);

        Intent intent = getIntent();


        titleview1 = (TextView) findViewById(R.id.titleView1);
        imageView1 = (ImageView) findViewById(R.id.imageView1);

        titleview2 = (TextView) findViewById(R.id.titleView2);
        imageView2 = (ImageView) findViewById(R.id.imageView2);

        titleview3 = (TextView) findViewById(R.id.titleView3);
        imageView3 = (ImageView) findViewById(R.id.imageView3);


        /*String image_URL = intent.getStringExtra("image_URL");
        //String Beacon = intent.getStringExtra("Beacon");
        // String Filename = intent.getStringExtra("Filename");
        String URL = intent.getStringExtra("URL");*/

        image_urlList = (ArrayList<String>) B
                .get("image_urlList");

        urlList = (ArrayList<String>) B
                .get("urlList");


        titleview1.setText(urlList.get(0));
        Glide.with(this).load(image_urlList.get(0)).into(imageView1);

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

}

