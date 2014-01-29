/**
 * 
 */
package org.coder.gear.sample.android.receiver;

import org.coder.gear.sample.android.service.GcmIntentService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

/**
 * @author yoshida-n
 *
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
    	Toast.makeText(context, "Message Received", Toast.LENGTH_SHORT).show();
        // Explicitly specify that GcmIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(),
                GcmIntentService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        context.startService(intent.setComponent(comp));
       setResultCode(Activity.RESULT_OK);
    }
}