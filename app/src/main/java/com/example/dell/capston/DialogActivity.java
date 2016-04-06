package com.example.dell.capston;

/**
 * Created by dell on 2016-04-02.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

public class DialogActivity extends Activity implements
        OnClickListener {

    private Button mConfirm, mCancel;
    private TextView titleview;
    private AQuery aq;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.push);
        aq = new AQuery( this );

        Intent intent = getIntent();
        String pw = intent.getStringExtra("PW");
        String name = intent.getStringExtra("NAME");
        titleview = (TextView) findViewById(R.id.TitleView);
        img = (ImageView) findViewById(R.id.ImageView);

        titleview.setText(pw);
        aq.id( img ).image("http://image.slidesharecdn.com/androidstudio-130904110409-/95/android-studio-1-638.jpg?cb=1378292683");

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
