/**
 * 
 */
package org.coder.gear.sample.android.service;

import java.io.IOException;

import org.coder.gear.sample.android.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

/**
 * @author yoshida-n
 *
 */
public class MusicPlayerService extends Service {

	private MediaPlayer player;
	
	/**
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();	
		player =  MediaPlayer.create(this, R.raw.kalimba);
    }
	
	
	/**
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return new MusicBinder();
	}
	
	/**
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public boolean onUnbind(Intent intent) {
		stop();
		return false;
	}

	public class MusicBinder extends Binder {
		
		public MusicPlayerService getService() {
			return MusicPlayerService.this;
		}
	}
	
	public void start(){
		if(!player.isPlaying()){
			
			player.start();
		}
	}
	
	public void stop() {
		//フラグメント移動するとstopがきかなくなる！？
		player.stop();		
		try {
			player.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
