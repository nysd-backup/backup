/**
 * 
 */
package org.coder.gear.sample.android.task;

import org.coder.gear.sample.android.MenuActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

/**
 * @author yoshida-n
 *
 */
public class LoginTask extends AsyncTask<String, String, Integer>{
	
	private ProgressDialog progressDialog;
	
	private Activity bindActivity;
	
	public LoginTask(Activity bindActivity ){
		this.bindActivity = bindActivity;
	}
	
	/**
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	protected void onPreExecute(){
		progressDialog = new ProgressDialog(bindActivity);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("処理を実行中しています");
		progressDialog.setCancelable(true);
		progressDialog.setMax(100);
		progressDialog.show();
	}

	/**
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Integer doInBackground(String... params) {
		for(int i = 0 ; i < 4; i++){
			publishProgress(30*(i+1) + "");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	/**
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Integer result) {
		progressDialog.dismiss();
		Intent intent = new Intent(bindActivity,MenuActivity.class);
		bindActivity.startActivity(intent);
	}

}
