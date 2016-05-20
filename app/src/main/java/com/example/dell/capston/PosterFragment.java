package com.example.dell.capston;

/**
 * Created by dell on 2016-04-02.
 */
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PosterFragment extends Fragment {
    public static final String ARG_PAGE = "PAGE";

    Context context;
    private TextView titleview1,titleview2,titleview3,titleview4,titleview5;

    private ImageView imageView1,imageView2,imageView3,imageView4,imageView5;
    private int mPage;
    String temp;
    String A;
    String B;
    ArrayList<String> UrlList = new ArrayList<String>();
    ArrayList<String> Image_urlList = new ArrayList<String>();
   /* private ArrayList<String> urlList = new ArrayList<String>();
    private ArrayList<String> image_urlList = new ArrayList<String>();*/


    public static PosterFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PosterFragment fragment = new PosterFragment();
        fragment.setArguments(args);
        return fragment;

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* Bundle bundle =  savedInstanceState;
        bundle = getArguments();
        image_URL = bundle.getString("image_URL");
       URL = bundle.getString("URL");*/
       // mPage = getArguments().getInt(ARG_PAGE);
        /*Bundle bundle = getArguments();
        String image_URL = bundle.getString("image_URL");
        String URL = bundle.getString("URL");*/
        /*UrlList= ((TestMainActivity) getActivity()).urlList.get(0);
        Image_urlList= ((TestMainActivity) getActivity()).image_urlList;*/

        /*MyApplication myApplication = MyApplication.instance();
        temp = myApplication.value;*/


    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.poster_push, container, false);
        //Intent intent1 = getActivity().getIntent();
        //B = getActivity().getIntent().getExtras();
       // Bundle bundle = getArguments();
        /*Bundle bundle = getArguments();
        String image_URL = bundle.getString("image_URL");
        String URL = bundle.getString("URL");*/
        MyApplication myApplication = MyApplication.instance();
        //temp = myApplication.value;


        titleview1 = (TextView) view.findViewById(R.id.titleView1);
        imageView1 = (ImageView) view.findViewById(R.id.imageView1);

        titleview2 = (TextView) view.findViewById(R.id.titleView2);
        imageView2 = (ImageView) view.findViewById(R.id.imageView2);

        titleview3 = (TextView) view.findViewById(R.id.titleView3);
        imageView3 = (ImageView) view.findViewById(R.id.imageView3);

        titleview4 = (TextView) view.findViewById(R.id.titleView4);
        imageView4 = (ImageView) view.findViewById(R.id.imageView4);

        titleview5 = (TextView) view.findViewById(R.id.titleView5);
        imageView5 = (ImageView) view.findViewById(R.id.imageView5);

       /* MyApplication myApplication = MyApplication.instance();
        temp = myApplication.value;*/

        /*
        String image_URL = intent1.getStringExtra("image_urlList");
        //String Beacon = intent.getStringExtra("Beacon");
        // String Filename = intent.getStringExtra("Filename");
        String URL = intent1.getStringExtra("urlList");*/

       /* image_urlList = (ArrayList<String>) B
        .get("image_urlList");

        urlList = (ArrayList<String>) B
                .get("urlList");*/


        titleview1.setText(myApplication.url1);
        Glide.with(this).load(myApplication.image1).into(imageView1);

        titleview2.setText(myApplication.url2);
        Glide.with(this).load(myApplication.image2).into(imageView2);

        titleview3.setText(myApplication.url3);
        Glide.with(this).load(myApplication.image3).into(imageView3);

        titleview4.setText(myApplication.url4);
        Glide.with(this).load(myApplication.image4).into(imageView4);

        titleview5.setText(myApplication.url5);
        Glide.with(this).load(myApplication.image5).into(imageView5);


        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://" + (String) titleview1.getText()));
                startActivity(myIntent);

            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://"+(String) titleview2.getText()));
                startActivity(myIntent);

            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://"+(String) titleview3.getText()));
                startActivity(myIntent);

            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://"+(String) titleview4.getText()));
                startActivity(myIntent);

            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://"+(String) titleview5.getText()));
                startActivity(myIntent);

            }
        });

        return view;
    }


}
    /*private Button mConfirm, mCancel;
    private TextView titleview1,titleview2,titleview3;

    private ImageView imageView1,imageView2,imageView3;

    Bundle B;
   ArrayList<String> image_urlList = B.getStringArrayList("image_urlList");
    ArrayList<String> urlList = B.getStringArrayList("urlList");*//*
    ArrayList<String> urlList  = new ArrayList<String>();
    ArrayList<String> image_urlList  = new ArrayList<String>();
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                // TODO Auto-generated method stub
                View view = inflater.inflate(R.layout.poster_push, container, false);
                Intent intent = getActivity().getIntent();
                //B = getActivity().getIntent().getExtras();

                titleview1 = (TextView) view.findViewById(R.id.titleView1);
                imageView1 = (ImageView) view.findViewById(R.id.imageView1);

                titleview2 = (TextView) view.findViewById(R.id.titleView2);
                imageView2 = (ImageView) view.findViewById(R.id.imageView2);

                titleview3 = (TextView) view.findViewById(R.id.titleView3);
                imageView3 = (ImageView) view.findViewById(R.id.imageView3);


                String image_URL = intent.getStringExtra("image_urlList");
                //String Beacon = intent.getStringExtra("Beacon");
                // String Filename = intent.getStringExtra("Filename");
                String URL = intent.getStringExtra("urlList");

                *//*image_urlList = (ArrayList<String>) B
                .get("image_urlList");

                urlList = (ArrayList<String>) B
                        .get("urlList");
*//*

                titleview1.setText(image_URL);
                Glide.with(this).load(URL).into(imageView1);

                titleview2.setText(image_URL);
                Glide.with(this).load(URL).into(imageView2);

                titleview3.setText(image_URL);
                Glide.with(this).load(URL).into(imageView3);

                return view;
                */








            /*@Override
            protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.poster_push);

        Intent intent = getIntent();


        titleview1 = (TextView) findViewById(R.id.titleView1);
        imageView1 = (ImageView) findViewById(R.id.imageView1);

        titleview2 = (TextView) findViewById(R.id.titleView2);
        imageView2 = (ImageView) findViewById(R.id.imageView2);

        titleview3 = (TextView) findViewById(R.id.titleView3);
        imageView3 = (ImageView) findViewById(R.id.imageView3);


        String image_URL = intent.getStringExtra("image_urlList");
        //String Beacon = intent.getStringExtra("Beacon");
        // String Filename = intent.getStringExtra("Filename");
        String URL = intent.getStringExtra("urlList");

        *//**//*image_urlList = (ArrayList<String>) B
                .get("image_urlList");

        urlList = (ArrayList<String>) B
                .get("urlList");*//**//*


        titleview1.setText(image_URL);
        Glide.with(this).load(URL).into(imageView1);

        *//**//*titleview2.setText(image_URL);
        Glide.with(this).load(URL).into(imageView2);

        titleview3.setText(image_URL);
        Glide.with(this).load(URL).into(imageView3);*//**//*

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
    }*/

/*
}

*/
