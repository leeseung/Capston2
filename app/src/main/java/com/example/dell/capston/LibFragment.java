package com.example.dell.capston;

/**
 * Created by lee on 2016-04-29.
 */



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class LibFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private WebView web1, web2, web3;
    private TextView titleview1,titleview2,titleview3;

    private ImageView imageView1,imageView2,imageView3;
    private int mPage;

    public static LibFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        LibFragment fragment = new LibFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lib_push, container, false);

        web1=(WebView) view.findViewById(R.id.Webview1);
        web2=(WebView) view.findViewById(R.id.Webview2);
        web3=(WebView) view.findViewById(R.id.Webview3);


        web1.getSettings().setLoadWithOverviewMode(true);
        web1.getSettings().setUseWideViewPort(true);

        web2.getSettings().setLoadWithOverviewMode(true);
        web2.getSettings().setUseWideViewPort(true);

        web3.getSettings().setLoadWithOverviewMode(true);
        web3.getSettings().setUseWideViewPort(true);

        web1.loadUrl("http://220.67.224.205/domianweb/roomview5.asp?room_no=1");
        web2.loadUrl("http://220.67.224.205/domianweb/roomview5.asp?room_no=2");
        web3.loadUrl("http://220.67.224.205/domianweb/roomview5.asp?room_no=4");

        return view;
    }




/*
    private Button mConfirm, mCancel;*//*
    private TextView titleview1,titleview2,titleview3;

    private ImageView imageView1,imageView2,imageView3;*//*

    private WebView web1, web2, web3;
    *//* Bundle B = getIntent().getExtras();
        *//**//*ArrayList<String> image_urlList = B.getStringArrayList("image_urlList");
    ArrayList<String> urlList = B.getStringArrayList("urlList");*//**//*
    ArrayList<String> urlList  = new ArrayList<String>();
    ArrayList<String> image_urlList  = new ArrayList<String>();*//*

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.lib_push, container, false);
        //Intent intent = getActivity().getIntent();


        web1=(WebView) view.findViewById(R.id.Webview1);
        web2=(WebView) view.findViewById(R.id.Webview2);
        web3=(WebView) view.findViewById(R.id.Webview3);


        web1.getSettings().setLoadWithOverviewMode(true);
        web1.getSettings().setUseWideViewPort(true);

        web2.getSettings().setLoadWithOverviewMode(true);
        web2.getSettings().setUseWideViewPort(true);

        web3.getSettings().setLoadWithOverviewMode(true);
        web3.getSettings().setUseWideViewPort(true);

        web1.loadUrl("http://220.67.224.205/domianweb/roomview5.asp?room_no=1");
        web2.loadUrl("http://220.67.224.205/domianweb/roomview5.asp?room_no=2");
        web3.loadUrl("http://220.67.224.205/domianweb/roomview5.asp?room_no=4");

        return view;
    }*/



    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.lib_push);

        web1=(WebView)findViewById(R.id.Webview1);
        web2=(WebView)findViewById(R.id.Webview2);
        web3=(WebView)findViewById(R.id.Webview3);


        web1.getSettings().setLoadWithOverviewMode(true);
        web1.getSettings().setUseWideViewPort(true);

        web2.getSettings().setLoadWithOverviewMode(true);
        web2.getSettings().setUseWideViewPort(true);

        web3.getSettings().setLoadWithOverviewMode(true);
        web3.getSettings().setUseWideViewPort(true);

        web1.loadUrl("http://220.67.224.205/domianweb/roomview5.asp?room_no=1");
        web2.loadUrl("http://220.67.224.205/domianweb/roomview5.asp?room_no=2");
        web3.loadUrl("http://220.67.224.205/domianweb/roomview5.asp?room_no=4");
        

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
*/

}
