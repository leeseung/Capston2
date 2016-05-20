package com.example.dell.capston;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by lee on 2016-05-08.
 */
public class MyApplication extends Application {

    private static MyApplication myApplication;


    public MyApplication(){
        myApplication=this;
    }
    public static MyApplication instance(){
        return myApplication;
    }
    public static String value;

    public static Boolean beacon1Poster;
    public static Boolean beacon1Empty;




    public static String image1;
    public static String image2;
    public static String image3;
    public static String image4;
    public static String image5;


    public static String url1;
    public static String url2;
    public static String url3;
    public static String url4;
    public static String url5;

    public static String Building;

   /* public static ArrayList<String> JINRIMonClassRoom = new ArrayList<String>();
    public static ArrayList<String> JINRItuseClassRoom = new ArrayList<String>();
    public static ArrayList<String> JINRIwensClassRoom = new ArrayList<String>();
    public static ArrayList<String> JINRIthursClassRoom = new ArrayList<String>();
    public static ArrayList<String> JINRIfriClassRoom = new ArrayList<String>();
    public static ArrayList<String> JINRIsaturClassRoom = new ArrayList<String>();*/

    public static String JINRIMonClassRoom;
    public static String JINRItuseClassRoom;
    public static String JINRIwensClassRoom;
    public static String JINRIthursClassRoom;
    public static String JINRIfriClassRoom;
    public static String JINRIsaturClassRoom;



}
