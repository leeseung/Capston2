package com.example.dell.capston.interfaces;

public interface IBtManagerListener {
	int CALLBACK_BT_XXX = 1;
	
	void OnBtManagerCallback(int msgType, int arg0, int arg1, String arg2, String arg3, Object arg4);
}
