/**
 * 
 */
package org.coder.gear.sample.android.service;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.IntentService;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * ダウンロード用のサービス、IntentServiceなので終了したら勝手に止まる。
 * 
 * @author yoshida-n
 *
 */
public class DownloadService extends IntentService{

	public DownloadService() {
		super("DownloadService");	
	}

	/**
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent(Intent arg0) {
		String url="http://www.google.co.jp/";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        HttpResponse res = null;
 
        try {           
            res = httpClient.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        //終わったらステータス通知
        NotificationManager mManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);           
        Intent intent = new Intent(Intent.ACTION_VIEW);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);        
        Builder builder = new Notification.Builder(getApplicationContext()).setContentIntent(pi);
        Notification n = builder.setContentText("Download Complete :" + res.getStatusLine().getStatusCode()).setContentText("DOWNLOAD").build();
        mManager.notify(1, n);
	}

}
