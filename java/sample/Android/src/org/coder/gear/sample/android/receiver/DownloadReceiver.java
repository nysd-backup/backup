/**
 * 
 */
package org.coder.gear.sample.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author yoshida-n
 *
 */
public class DownloadReceiver extends BroadcastReceiver{

	/**
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "raises : " +  intent.getAction(), Toast.LENGTH_LONG).show();
	}

}
