package com.example.dell.capston;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import com.example.dell.capston.R;
import com.example.dell.capston.contents.MacroManager;
import com.example.dell.capston.service.BlueWaveService;
import com.example.dell.capston.utils.AppSettings;
import com.example.dell.capston.utils.Constants;
import com.example.dell.capston.utils.Logs;
import com.example.dell.capston.utils.PushService;
import com.example.dell.capston.utils.RecycleUtils;

import android.app.ActionBar;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class IntroActivity extends Activity {

	private Context mContext;
	private Timer mRefreshTimer;

	Boolean Beacon1Poster = false;
	Boolean Beacon1EmptyClass = false;

	Boolean Beacon2Poster = false;
	Boolean Beacon2EmptyClass = false;


	/*****************************************************
	 *	 Overrided methods
	 ******************************************************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		new JsonLoadingTask1().execute();
		new JsonLoadingTask2().execute();

		//----- System, Context
		mContext = this;//.getApplicationContext();

		//----- UI
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_intro);


		initialize();

	}

	@Override
	public synchronized void onStart() {
		super.onStart();
	}

	@Override
	public synchronized void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		// Stop the timer
		if(mRefreshTimer != null) {
			mRefreshTimer.cancel();
			mRefreshTimer = null;
		}
		super.onDestroy();
		finalizeActivity();
	}

	@Override
	public void onLowMemory (){
		super.onLowMemory();
		// onDestroy is not always called when applications are finished by Android system.
		finalizeActivity();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			//case R.id.action_discoverable:
			//	return true;

		}
		return false;
	}

	@Override
	public void onBackPressed() {
		//super.onBackPressed();		// TODO: Disable this line to run below code
		finalizeActivity();
		finishActivity();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig){
		// This prevents reload after configuration changes
		super.onConfigurationChanged(newConfig);
	}


	/**
	 * Initialization / Finalization
	 */
	private void initialize() {


		// Check bluetooth
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// If the adapter is null, then Bluetooth is not supported
		if (bluetoothAdapter == null) {
			Toast.makeText(this, getString(R.string.bt_ble_not_supported), Toast.LENGTH_LONG).show();
			return;
		}

		if(!bluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, Constants.REQUEST_ENABLE_BT);
			startBeaconPushManager();
			/*startServiceAndManager();
			reserveActivityChange(2*1000);*/
			//JsonParsing();

			return;
		}
		// 비콘푸쉬 서비스 시작시킴
		startBeaconPushManager();
		startServiceAndManager();
		reserveActivityChange(5 * 1000);
	}



	private void finalizeActivity() {
		RecycleUtils.recursiveRecycle(getWindow().getDecorView());
		System.gc();
	}

	public void finishActivity() {
		finish();
	}

	private void startServiceAndManager() {
		startService(new Intent(this, BlueWaveService.class));
		MacroManager.getInstance(getApplicationContext());		// To make macro manager instance
	}
	private void startBeaconPushManager() {
		startService(new Intent(this, PushService.class)); // To make macro manager instance
	}

	private void reserveActivityChange(long delay) {
		if(mRefreshTimer != null) {
			mRefreshTimer.cancel();
		}
		mRefreshTimer = new Timer();
		mRefreshTimer.schedule(new RefreshTimerTask(), delay);
	}

	/*****************************************************
	 *	Public classes
	 ******************************************************/
	/**
	 * Receives result from external activity
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
			case Constants.REQUEST_ENABLE_BT:
				// When the request to enable Bluetooth returns
				if (resultCode == Activity.RESULT_OK) {
					startServiceAndManager();
					reserveActivityChange(1 * 1000);
				} else {
					// User did not enable Bluetooth or an error occured
					Logs.e("BT is not enabled");
					Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
				}
				break;
		}	// End of switch(requestCode)
	}



	/*****************************************************
	 *	Sub classes
	 ******************************************************/
	private class RefreshTimerTask extends TimerTask {
		public RefreshTimerTask() {}

		public void run() {
			startActivity(new Intent(mContext, TestMainActivity.class));
			finishActivity();
		}
	}

	public String getJsonText(int chk) {



		StringBuffer sb1 = new StringBuffer();
		MyApplication myApplication = MyApplication.instance();

		StringBuffer Monday = new StringBuffer();
		StringBuffer Tuseday = new StringBuffer();
		StringBuffer Wendsday = new StringBuffer();
		StringBuffer Thurday = new StringBuffer();
		StringBuffer Friday = new StringBuffer();
		StringBuffer Saturday = new StringBuffer();

		/*a= "aaaaaa";
		b="bbbbbbbb";*/
		try {

			String chkString = String.valueOf(chk);
			//113.198.80.214 - 101호 서버
			//115.21.3.126 - 예찬노트북

			String jsonPage1 = getStringFromUrl("http://113.198.80.214/CapstoneDesign/jsps/testJson.jsp?&Beacon=1");
			String jsonPage2 = getStringFromUrl("http://113.198.80.214/CapstoneDesign/jsps/testJson.jsp?&Beacon=2");
			String jsonPage3 = getStringFromUrl("http://113.198.80.214/CapstoneDesign/jsps/testJson.jsp?&Beacon=3");
			String jsonPage4 = getStringFromUrl("http://113.198.80.214/CapstoneDesign/jsps/testJson.jsp?&Beacon=4");

			if (chk == 1) {



				JSONObject obj = new JSONObject(jsonPage1);
				JSONArray List = obj.getJSONArray("List");

				for(int i=0;i<List.length();i++ ) {
					JSONObject info = List.getJSONObject(i);
					String TypeCheck = info.getString("Type");
					if(TypeCheck.equals("Poster")) {
						Beacon1Poster = true;
						myApplication.image1 = info.getString("image_URL");
						myApplication.url1 = info.getString("URL");

						JSONObject info1 = List.getJSONObject(1);
						myApplication.image2 = info1.getString("image_URL");
						myApplication.url2 = info1.getString("URL");

						JSONObject info2 = List.getJSONObject(2);
						myApplication.image3 = info2.getString("image_URL");
						myApplication.url3 = info2.getString("URL");

						JSONObject info3 = List.getJSONObject(3);
						myApplication.image4 = info3.getString("image_URL");
						myApplication.url4 = info3.getString("URL");

						JSONObject info4 = List.getJSONObject(4);
						myApplication.image5 = info4.getString("image_URL");
						myApplication.url5 = info4.getString("URL");
					}
					else if(TypeCheck.equals("Url")){
						Beacon1EmptyClass = true;
					}

				}
				/*for (int i = 0; i < List.length(); i++) {
					JSONObject info = List.getJSONObject(i);
					image_URL = info.getString("image_URL");
					URL = info.getString("URL");

					myApplication.urlList.add(URL);
					myApplication.image_urlList.add(image_URL);
				}
*/
				// sb.append("[ "+responseData+" ]\n");
				/*sb1.append(image_URL + "\n");
				// sb.append(Beacon + "\n");
				// sb.append(Filename + "\n");
				sb1.append(URL + "\n");
				sb1.append("\n");*/

				//sendNotification("HS BEACON", "포스터,강의실");


				//sendCenterPush("HS PUSH!" , "gf");

				/*intent1 = new Intent(getApplicationContext(), TestMainActivity.class);
				intent1.putExtra("image_urlList", image_URL);
				intent1.putExtra("urlList", URL);
				startActivity(intent1);
				bundle = new Bundle();
				bundle.putString("image_URL", a);
				bundle.putString("iURL", b);
				posterActivity = new PosterFragment();
				posterActivity.setArguments(bundle);*/


			} else if (chk == 2) {


				//JSONParser parser = new JSONParser();
				JSONObject obj = new JSONObject(jsonPage2);
				//JSONObject obj = (JSONObject) parser.parse(jsonPage2);
				JSONArray parse_List = (JSONArray) obj.getJSONArray("List");
				//리스트 배열
				for(int i=0;i<parse_List.length();i++ ) {

					JSONObject obj1 = (JSONObject) parse_List.getJSONObject(i);
					String TypeCheck = obj1.getString("Type");

					if(TypeCheck.equals("Poster")){
						Beacon2Poster = true;
					}
					//타입이 포스터이면
					else if(TypeCheck.equals("Siganpyo")) {
						Beacon2EmptyClass = true;

						JSONObject Empty_Siganpyo = (JSONObject) obj1.getJSONObject("Empty_Siganpyo");
						JSONArray buildings = (JSONArray) Empty_Siganpyo.getJSONArray("buildings");

						JSONObject buildingsOBJ = (JSONObject) buildings.getJSONObject(0);
						JSONArray classrooms = (JSONArray) buildingsOBJ.getJSONArray("classrooms");

						//잘 나오는지 점검
						Log.e("TAGG", String.valueOf(classrooms.length()));

						//클래스룸 배열열
						for (int j = 0; j < classrooms.length(); j++) {
							JSONObject classroomsOBJ = (JSONObject) classrooms.getJSONObject(j);
							JSONArray periods = (JSONArray) classroomsOBJ.getJSONArray("periods");

							Log.e("TAGG", String.valueOf(periods.length()));

							for (int k = 0; k < periods.length(); k++) {
								JSONObject weekOBJ = (JSONObject) periods.getJSONObject(k);
								String building = weekOBJ.getString("week");

								Log.e("TEMP", building);

								if (building.equals("월")) {
									String MondayClassroom = classroomsOBJ.getString("classroom");
									Monday.append(MondayClassroom + "호 :");

									Log.e("TEMP", String.valueOf(Monday));

									JSONArray times = (JSONArray) weekOBJ.getJSONArray("times");

									Log.e("TAGG", String.valueOf(times.length()));

									for (int l = 0; l < times.length(); l++) {
										JSONObject timesOBJ = (JSONObject) times.getJSONObject(l);
										String TempTime = timesOBJ.getString("time");
										if (l != (times.length() - 1))
											Monday.append(TempTime + "교시, ");
										else
											Monday.append(TempTime + "교시");
									}
									Monday.append("\n");

									Log.e("TEMP", String.valueOf(Monday));

								} else if (building.equals("화")) {
									String MondayClassroom = classroomsOBJ.getString("classroom");
									Tuseday.append(MondayClassroom + "호 :");
									JSONArray times = (JSONArray) weekOBJ.getJSONArray("times");
									for (int l = 0; l < times.length(); l++) {
										JSONObject timesOBJ = (JSONObject) times.getJSONObject(l);
										String TempTime = timesOBJ.getString("time");
										if (l != (times.length() - 1))
											Tuseday.append(TempTime + "교시, ");
										else
											Tuseday.append(TempTime + "교시");
									}
									Tuseday.append("\n");

								} else if (building.equals("수")) {
									String MondayClassroom = classroomsOBJ.getString("classroom");
									Wendsday.append(MondayClassroom + "호 :");
									JSONArray times = (JSONArray) weekOBJ.getJSONArray("times");
									for (int l = 0; l < times.length(); l++) {
										JSONObject timesOBJ = (JSONObject) times.getJSONObject(l);
										String TempTime = timesOBJ.getString("time");
										if (l != (times.length() - 1))
											Wendsday.append(TempTime + "교시, ");
										else
											Wendsday.append(TempTime + "교시");
									}
									Wendsday.append("\n");

								} else if (building.equals("목")) {
									String MondayClassroom = classroomsOBJ.getString("classroom");
									Thurday.append(MondayClassroom + "호 :");
									JSONArray times = (JSONArray) weekOBJ.getJSONArray("times");
									for (int l = 0; l < times.length(); l++) {
										JSONObject timesOBJ = (JSONObject) times.getJSONObject(l);
										String TempTime = timesOBJ.getString("time");
										if (l != (times.length() - 1))
											Thurday.append(TempTime + "교시, ");
										else
											Thurday.append(TempTime + "교시");
									}
									Thurday.append("\n");

								} else if (building.equals("금")) {
									String MondayClassroom = classroomsOBJ.getString("classroom");
									Friday.append(MondayClassroom + "호 :");
									JSONArray times = (JSONArray) weekOBJ.getJSONArray("times");
									for (int l = 0; l < times.length(); l++) {
										JSONObject timesOBJ = (JSONObject) times.getJSONObject(l);
										String TempTime = timesOBJ.getString("time");
										if (l != (times.length() - 1))
											Friday.append(TempTime + "교시, ");
										else
											Friday.append(TempTime + "교시");
									}
									Friday.append("\n");

								} else if (building.equals("토")) {
									String MondayClassroom = classroomsOBJ.getString("classroom");
									Saturday.append(MondayClassroom + "호 :");
									JSONArray times = (JSONArray) weekOBJ.getJSONArray("times");
									for (int l = 0; l < times.length(); l++) {
										JSONObject timesOBJ = (JSONObject) times.getJSONObject(l);
										String TempTime = timesOBJ.getString("time");
										if (l != (times.length() - 1))
											Saturday.append(TempTime + "교시, ");
										else
											Saturday.append(TempTime + "교시");
									}
									Saturday.append("\n");

								}
							/*JSONObject times = (JSONObject) periods.getJSONObject(i);
							JSONArray time = (JSONArray) times.getJSONArray("times");
*/
							}


						}


					}

				}

				myApplication.JINRIMonClassRoom = Monday.toString();

				myApplication.JINRItuseClassRoom = Tuseday.toString();

				myApplication.JINRIwensClassRoom = Wendsday.toString();

				myApplication.JINRIthursClassRoom = Thurday.toString();

				myApplication.JINRIfriClassRoom= Friday.toString();

				myApplication.JINRIsaturClassRoom = Saturday.toString();


			}


		} catch (Exception e) {
			// TODO: handle exception
		}

		return sb1.toString();
	}//getJsonText()----------

	public String getStringFromUrl(String pUrl) {

		BufferedReader bufreader = null;
		HttpURLConnection urlConnection = null;

		StringBuffer page = new StringBuffer(); //�о�� �����͸� ������ StringBuffer��ü ����

		try {

			//[Type1]
            /*
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(pUrl));
            InputStream contentStream = response.getEntity().getContent();
            */

			//[Type2]
			java.net.URL url = new URL(pUrl);
			urlConnection = (HttpURLConnection) url.openConnection();
			InputStream contentStream = urlConnection.getInputStream();

			bufreader = new BufferedReader(new InputStreamReader(contentStream, "UTF-8"));
			String line = null;

			//������ ������ �ҽ��� �ٴ����� �о�(line), Page�� ������
			while ((line = bufreader.readLine()) != null) {
				Log.d("line:", line);
				page.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//�ڿ�����
			try {
				bufreader.close();
				urlConnection.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return page.toString();
	}// getStringFromUrl()-------------------------

	/**
	 * Created by lee on 2016-04-14.
	 */
private class JsonLoadingTask1 extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... strs) {

			return getJsonText(1);

		} // doInBackground : ��׶��� �۾��� �����Ѵ�.

		@Override
		protected void onPostExecute(String result) {
			// et_webpage_src.setText(result);
		} // onPostExecute : ��׶��� �۾��� ���� �� UI �۾��� �����Ѵ�.
	} // JsonLoadingTask

	private class JsonLoadingTask2 extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... strs) {

			return getJsonText(2);

		} // doInBackground : ��׶��� �۾��� �����Ѵ�.

		@Override
		protected void onPostExecute(String result) {
			// et_webpage_src.setText(result);
		} // onPostExecute : ��׶��� �۾��� ���� �� UI �۾��� �����Ѵ�.
	} // JsonLoadingTask

	private class JsonLoadingTask3 extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... strs) {

			return getJsonText(3);

		} // doInBackground : ��׶��� �۾��� �����Ѵ�.

		@Override
		protected void onPostExecute(String result) {
			// et_webpage_src.setText(result);
		} // onPostExecute : ��׶��� �۾��� ���� �� UI �۾��� �����Ѵ�.
	} // JsonLoadingTask

	private class JsonLoadingTask4 extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... strs) {

			return getJsonText(4);

		} // doInBackground : ��׶��� �۾��� �����Ѵ�.

		@Override
		protected void onPostExecute(String result) {
			// et_webpage_src.setText(result);
		} // onPostExecute : ��׶��� �۾��� ���� �� UI �۾��� �����Ѵ�.
	} // JsonLoadingTask
	// getStringFromUrl : �־��� URL�� ������ ������ ���ڿ��� ��ȯ

	private class BackgroundPush extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... strs) {

			//sendNotification("HS Beacon","공모전 " );

			return null;
		} // doInBackground : ��׶��� �۾��� �����Ѵ�.

		@Override
		protected void onPostExecute(String result) {
			// et_webpage_src.setText(result);
		} // onPostExecute : ��׶��� �۾��� ���� �� UI �۾��� �����Ѵ�.
	} // JsonLoadingTask




}
