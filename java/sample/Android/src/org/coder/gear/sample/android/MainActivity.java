package org.coder.gear.sample.android;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicInteger;

import org.coder.gear.sample.android.task.LoginTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class MainActivity extends Activity {
	
	 public static final String EXTRA_MESSAGE = "message";
	 public static final String PROPERTY_REG_ID = "registration_id";
	 private static final String PROPERTY_APP_VERSION = "appVersion";
	 private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    /**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     * 
     * Google ProjectのプロジェクトID
     */	 
    String SENDER_ID = "380188014792";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "GCMDemo";

    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;
	
    String regid;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//google-play-services_libをプロジェクトインポートして、ビルドパスに追加する必要がある（通常のプロジェクト参照ではなくAndroidプロジェクト参照にする必要がある）
		//google-play-services.jarを参照LIBに加えても意味ない
		
		Button button = (Button)findViewById(R.id.login);
		final EditText user = (EditText)findViewById(R.id.user);
		final EditText pass = (EditText)findViewById(R.id.password);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if( user.getText().toString().equals(pass.getText().toString())){
					LoginTask task = new LoginTask(MainActivity.this);
					task.execute(user.toString(),pass.toString());
				}else {
					Builder dialog = new AlertDialog.Builder(MainActivity.this);
					dialog.setTitle("error").setMessage("login failed").setCancelable(true).setOnCancelListener(new OnCancelListener() {
						
						@Override
						public void onCancel(DialogInterface dialog) {
							dialog.dismiss();							
						}
					});
					dialog.show();
				}
			}
		});
		
		 if (checkPlayServices()) {			 
			 gcm = GoogleCloudMessaging.getInstance(this);
	         regid = getRegistrationId(this); 	    
	         Toast.makeText(this, "RegistrationId is " + regid, Toast.LENGTH_SHORT).show();
	         if (regid.isEmpty()) {
	                registerInBackground();
	         }else{
	        	 //RegistrationIdは変化するのでサーバに登録しなおす必要がある。
	        	 File regId = new File(Environment.getExternalStoragePublicDirectory(
	   	              Environment.DIRECTORY_PICTURES), "regid.txt");
	        	 try{
	        		 BufferedWriter writer = new BufferedWriter(new FileWriter(regId));
	        		 writer.write(regid);
	        		 writer.flush();
	        		 writer.close();
	        	 }catch(Exception e){
	        		 Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
	        	 }
	         }
		  }
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	/**
	 * @return
	 */
	private boolean checkPlayServices() {		
		try{
		    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		    if (resultCode != ConnectionResult.SUCCESS) {
		        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
		            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
		                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
		        } else {
		        	Toast.makeText(this, "This Device is not supported ", Toast.LENGTH_LONG).show();
		            finish();
		        }
		        return false;
		    }
		}catch(Exception e){
			Toast.makeText(this, "Play Service Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
	    return true;
	}
	
	private String getRegistrationId(Context context) {		
		//SharedPreferencesはローカルから値を取得するためのもののようだ
	    final SharedPreferences prefs = getGCMPreferences(context);	    
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    
	    //アプリバージョンがあがると空になるらしい
	    if (registrationId.isEmpty()) {
	    	Toast.makeText(this, "Reg Id not Found ", Toast.LENGTH_LONG).show();
	        return "";
	    }
	    // Check if app was updated; if so, it must clear the registration ID
	    // since the existing regID is not guaranteed to work with the new
	    // app version.
	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    Toast.makeText(this, "Get App Version :" + registeredVersion, Toast.LENGTH_LONG).show();
	    
	    int currentVersion = getAppVersion(context);
	    if (registeredVersion != currentVersion) {
	    	 Toast.makeText(this, "App Version Changed ", Toast.LENGTH_LONG).show();
	        return "";
	    }
	    return registrationId;
	}
	
	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
	    // This sample app persists the registration ID in shared preferences, but
	    // how you store the regID in your app is up to you.
	    return getSharedPreferences(MainActivity.class.getSimpleName(),
	            Context.MODE_PRIVATE);
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager()
	                .getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
	
	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration ID and app versionCode in the application's
	 * shared preferences.
	 */
	private void registerInBackground() {
	    new AsyncTask<Void,String,String>() {
	        @Override
	        protected String doInBackground(Void... params) {
	            String msg = "";
	            try {
	                if (gcm == null) {
	                    gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
	                }
	               
	                regid = gcm.register(SENDER_ID);
	                msg = "Device registered, registration ID=" + regid;

	                // You should send the registration ID to your server over HTTP,
	                // so it can use GCM/HTTP or CCS to send messages to your app.
	                // The request to your server should be authenticated if your app
	                // is using accounts.
	                sendRegistrationIdToBackend();

	                // For this demo: we don't need to send it because the device
	                // will send upstream messages to a server that echo back the
	                // message using the 'from' address in the message.

	                // Persist the regID - no need to register again.
	                storeRegistrationId(getApplicationContext(), regid);
	            } catch (Exception ex) {
	            	StringWriter writer = new StringWriter();
	            	PrintWriter pw = new PrintWriter(writer);
	            	ex.printStackTrace(pw);
	                msg = msg + "Error :" + writer.toString();
	                // If there is an error, don't just keep trying to register.
	                // Require the user to click a button again, or perform
	                // exponential back-off.
	            }
	            return msg;
	        }

	        @Override
	        protected void onPostExecute(String msg) {
	        	 Toast.makeText(getApplication(), msg, Toast.LENGTH_LONG).show();
	        }
	    }.execute(null, null, null);
	    
	}
	
	/**
	 * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
	 * or CCS to send messages to your app. Not needed for this demo since the
	 * device sends upstream messages to a server that echoes back the message
	 * using the 'from' address in the message.
	 */
	private void sendRegistrationIdToBackend() {
	    // Your implementation here.
	}
	
	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 *
	 * @param context application's context.
	 * @param regId registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {		
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	    SharedPreferences.Editor editor = prefs.edit();	
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    editor.commit();
	}
}
