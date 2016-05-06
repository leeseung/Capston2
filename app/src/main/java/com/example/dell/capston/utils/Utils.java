/*
 * Copyright (C) 2014 The Retro Watch - Open source smart watch project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.dell.capston.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.dell.capston.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;


public class Utils {
	private static Context mContext;
	
	private static final String TAG = "Utils";  
	
	private static String[] mMacroDistanceString = null;
	private static String[] mMacroWorksString = null;
	private static String[] mScanStatusString = null;

	
	
	/**
	 * This initializer must be called before using Utils class
	 * @param c
	 */
	public static void initialize(Context c) {
		mContext = c;
		// Load strings
		mMacroDistanceString = mContext.getResources().getStringArray(R.array.array_macro_distance);
		mMacroWorksString = mContext.getResources().getStringArray(R.array.array_macro_works);
		mScanStatusString = mContext.getResources().getStringArray(R.array.array_scan_status);
	}
	
	
	//============================================================
	// Launching external app
	//============================================================
	public static final void launchBrowser(String uriString) {
		if(uriString == null || uriString.length() < 1 
				|| (!uriString.contains("http://") && !uriString.contains("https://"))
				) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		Uri u = Uri.parse(uriString);
		i.setData(u);
		mContext.startActivity(i);
	}
	
	
	//============================================================
	// App string resource
	//============================================================
	public static String getDistanceString(int index) {
		if(mMacroDistanceString == null || index < 0 || index > mMacroDistanceString.length - 1) {
			return null;
		} else {
			return mMacroDistanceString[index];
		}
	}
	
	public static String getWorkTypeString(int index) {
		if(mMacroWorksString == null || index < 0 || index > mMacroWorksString.length - 1) {
			return null;
		} else {
			return mMacroWorksString[index];
		}
	}
	
	public static final int SCAN_STRING_BT_OFF = 0;
	public static final int SCAN_STRING_SCANNING = 1;
	public static final int SCAN_STRING_SCAN_FINISHED = 2;
	public static String getScanString(int index) {
		if(mScanStatusString == null || index < 0 || index > mScanStatusString.length - 1) {
			return null;
		} else {
			return mScanStatusString[index];
		}
	}
	
	
	//============================================================
	// Directory, File handling
	//============================================================
    public static void initFileDirectory(String path)
	{
		File directory = new File(path);
		if( !directory.exists() ) {
			directory.mkdirs();
		}
	}
	
	public static void deleteDirectory(String path) {
		if(path==null) return;
		if(Utils.isFileExist(new File(path))) {
			deleteFileDirRecursive(path);
		}
	}

	public static void deleteFileDirRecursive(String path) {
		File file = new File(path);
		File[] childFileList = file.listFiles();
		for(File childFile : childFileList)
		{
			if(childFile.isDirectory()) {
				deleteFileDirRecursive(childFile.getAbsolutePath());
			}
			else {
				childFile.delete();
			}
		}
		file.delete();
	}
	
	public static boolean checkFileExists(String directory, String filename)
	{
		if(directory==null || filename==null)
			return false;
		File kFile = new File(directory+"/"+filename);
		if(kFile != null) {
			return kFile.exists();
		}
		return false;
	}
	
	public static File makeDirectory(String dir_path){
        File dir = new File(dir_path);
        if (!dir.exists())
        {
            dir.mkdirs();
            Log.i(TAG , "!dir.exists");
        }else{
            Log.i(TAG , "dir.exists");
        }
 
        return dir;
    }
 
    public static File makeFile(File dir , String file_path){
        File file = null;
        boolean isSuccess = false;
        if(dir.isDirectory()){
            file = new File(file_path);
            if(file!=null&&!file.exists()){
                Log.i(TAG , "!file.exists");
                try {
                    isSuccess = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally{
                    Log.i(TAG, "??��??�� ???? = " + isSuccess);
                }
            }else{
                Log.i(TAG , "file.exists");
            }
        }
        return file;
    }

    public static String getAbsolutePath(File file){
        return ""+file.getAbsolutePath();
    }

    public static boolean deleteFile(File file){
        boolean result;
        if(file!=null&&file.exists()){
            file.delete();
            result = true;
        }else{
            result = false;
        }
        return result;
    }

    public static boolean isFile(File file){
        boolean result;
        result = file != null && file.exists() && file.isFile();
        return result;
    }

    public static boolean isDirectory(File dir){
        boolean result;
        result = dir != null && dir.isDirectory();
        return result;
    }

    public static boolean isFileExist(File file){
        boolean result;
        result = file != null && file.exists();
        return result;
    }

    public static boolean reNameFile(File file , File new_name){
        boolean result;
        result = file != null && file.exists() && file.renameTo(new_name);
        return result;
    }

    public static String[] getList(File dir){
        if(dir!=null&&dir.exists())
            return dir.list();
        return null;
    }

    public static boolean writeFile(File file , byte[] file_content){
        boolean result;
        FileOutputStream fos;

        if(file!=null&&file_content!=null){
            try {
                fos = new FileOutputStream(file);
                try {
                    fos.write(file_content);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            result = true;
        }else{
        	Logs.d(TAG, "##### writeFile :: file is null or file does not exists or content is null ");
            result = false;
        }
        return result;
    }

    public static String readFile(File file){
    	StringBuilder sb = new StringBuilder();
        int readcount=0;

        if( file!=null && file.exists() ){
            try {
                FileInputStream fis = new FileInputStream(file);
                Reader in = new InputStreamReader(fis, "UTF-8");
                readcount = (int)file.length();
                char[] tempByte = new char[readcount];
                in.read(tempByte);
//                readcount = (int)file.length();
//                byte[] buffer = new byte[readcount];
//                fis.read(buffer);
                fis.close();
                sb.append(tempByte);
            } catch (Exception e) {
                e.printStackTrace();
                Logs.d(TAG, "##### writeFile :: Exception while FILE IO ");
            }
        } else {
        	Logs.d(TAG, "##### writeFile :: file is null or file does not exists or content is null ");
        }
        return sb.toString();
    }

    public static boolean copyFile(File file , String save_file){
        boolean result;
        if(file!=null&&file.exists()){
            try {
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream newfos = new FileOutputStream(save_file);
                int readcount=0;
                byte[] buffer = new byte[1024];
                while((readcount = fis.read(buffer,0,1024))!= -1){
                    newfos.write(buffer,0,readcount);
                }
                newfos.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            result = true;
        }else{
            result = false;
        }
        return result;
    }

	//============================================================
	// URL, File name
	//============================================================

	// url (with extension)
	public static String convertUrlToFileName(String url)
	{
		String name = new File(url).getName();
		return name;
	}

	// TODO: url ���� (without extension)
	public static String convertUrlToFileNameWithoutExt(String url)
	{
		String name = new File(url).getName();
		// TODO
		return name;
	}
	
	
	public static final String REG_EXP_IMAGE_URL = "(?i)http://[a-zA-Z0-9_.\\-%&=?!:;@\"'/]*(?i)(.gif|.jpg|.png|.jpeg)";
	
    // TODO: Not working correctly
    public static List<String> getImageURL(String str) {
		Pattern nonValidPattern = Pattern.compile(REG_EXP_IMAGE_URL);

		List<String> result = new ArrayList<String>();
		Matcher matcher = nonValidPattern.matcher(str);
		while (matcher.find()) {
			result.add(matcher.group(0));
			break;
		}
		return result;
	}
	
    public static Comparator<Object> KeyStringSort = new Comparator<Object>() {
        public int compare(Object s1, Object s2) {
            String ss1 = (String)s1;
            String ss2 = (String)s2;
            return (-1) * ss2.compareTo(ss1);
        }
    };
    
    public static Bitmap getResizedBitmapFromFile(String filePath, int screenW, int screenH, float resizeRatio) 
	{
		//----- Load image as small as possible to reduce memory overhead
        Bitmap pic = null;		
        try 
        {
        	File tempFile = new File(filePath);
        	if( isFileExist(tempFile) == false ) return null;
        	
            // Decode image size
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            
            InputStream is = new FileInputStream(filePath);
            BitmapFactory.decodeStream(is, null, options);
    		is.close();

    		// Target bitmap size
    		int smallerW = (screenW > screenH ? screenH : screenW);	// Get screen width
    		int bitmapW = options.outWidth;			// image size Width
    		int bitmapH = options.outHeight;		// image size Height
    		int imageW = (int)( (float)smallerW * resizeRatio );	// Target bitmap width : Resize image according to resize ratio
    		int imageH = (imageW * bitmapH / bitmapW);	// Target bitmap height
            
            int scale = 1;
            int scaleW = bitmapW / imageW;
//            int scaleH = bitmapH / imageH;
            while( scaleW / 2 >= 1 ) {
            	scale = scale * 2;
            	scaleW = scaleW / 2;
            }

            if (scale > 1) {
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                options = new BitmapFactory.Options();
                options.inSampleSize = scale;
                
                InputStream is2 = new FileInputStream(filePath);
                pic = BitmapFactory.decodeStream(is2, null, options);
                is2.close();

                // resize to desired dimensions
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(pic, imageW, imageH, true);
                pic.recycle();
                pic = scaledBitmap;

                System.gc();
            } else {
            	InputStream is2 = new FileInputStream(filePath);
                pic = BitmapFactory.decodeStream(is2);
                is2.close();
            }

        } catch (Exception e) {
            pic = null;
        }
        return pic;		
	}

    
	//============================================================
	// Regular expression
	//============================================================
	
	public static String removeSpecialChars(String str){       
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
		str =str.replaceAll(match, " ");
		return str;
	}
	
	
	//============================================================
	// String utils
	//============================================================
	
	public static String byteArrayToString(byte[] ba) {
		StringBuilder hex = new StringBuilder(ba.length * 2);
		for (byte b : ba)
			hex.append(String.format("%02X ", b));

		return hex.toString();
	}
	
	
}
