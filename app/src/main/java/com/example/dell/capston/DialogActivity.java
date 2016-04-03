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
import android.widget.TextView;

public class DialogActivity extends Activity implements
        OnClickListener {

    private Button mConfirm, mCancel;
    private TextView titleview,textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.push);


        Intent intent = getIntent();
        String pw = intent.getStringExtra("PW");
        String name = intent.getStringExtra("NAME");
        titleview = (TextView) findViewById(R.id.TitleView);
        textview = (TextView) findViewById(R.id.TextView);

        titleview.setText(pw);
        titleview.setText(name);

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
