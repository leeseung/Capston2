package com.example.dell.capston.interfaces;

public interface IAdapterListener {
	int CALLBACK_MACRO_TOGGLE = 1;
	int CALLBACK_MACRO_EDIT = 2;
	int CALLBACK_MACRO_DELETE = 3;
	int CALLBACK_MACRO_LAYOUT_EDIT = 11;
	
	int CALLBACK_BEACON_MAKE_UUID = 101;
	int CALLBACK_BEACON_MAKE_MAJOR = 102;
	int CALLBACK_BEACON_MAKE_MACRO = 103;
	int CALLBACK_BEACON_FORM_DIALOG = 104;	// Show beacon name dialog
	int CALLBACK_INSERT_EDIT_BEACON = 105;	// Remember beacon info
	int CALLBACK_DELETE_BEACON = 106;	// Delete beacon info
	
	int CALLBACK_CLOSE = 1000;
	
	void OnAdapterCallback(int msgType, int arg0, int arg1, String arg2, String arg3, Object arg4);
}
