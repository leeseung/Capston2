package com.example.dell.capston;

/**
 * Created by lee on 2016-05-07.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class DietFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE3";

    private WebView web1;
    private int mPage;

    public static DietFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        DietFragment fragment = new DietFragment();
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
        View view = inflater.inflate(R.layout.diet_push, container, false);

        web1 = (WebView) view.findViewById(R.id.Webview1);



        web1.getSettings().setLoadWithOverviewMode(true);
        web1.getSettings().setUseWideViewPort(true);

        web1.loadUrl("http://www.hansung.ac.kr/web/www/life_03_01_t1");


        return view;
    }
}

