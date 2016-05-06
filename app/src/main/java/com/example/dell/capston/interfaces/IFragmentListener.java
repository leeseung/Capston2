package com.example.dell.capston.interfaces;

public interface IFragmentListener {
	int CALLBACK_MACRO_TOGGLE = 1;
	int CALLBACK_MACRO_EDIT = 2;
	int CALLBACK_MACRO_DELETE = 3;
	
	int CALLBACK_BEACON_MAKE_UUID = 101;
	int CALLBACK_BEACON_MAKE_MAJOR = 102;
	int CALLBACK_BEACON_MAKE_MACRO = 103;
	
	int CALLBACK_RUN_IN_BACKGROUND = 201;
	int CALLBACK_SCAN_INTERVAL = 202;
	
	int CALLBACK_CLOSE = 1000;
	
	int CALLBACK_REQUEST_SCAN_CLASSIC = 10001;
	int CALLBACK_REQUEST_SCAN_BLE = 10002;
	
	void OnFragmentCallback(int msgType, int arg0, int arg1, String arg2, String arg3, Object arg4);
}
