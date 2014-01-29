/**
 * 
 */
package org.coder.gear.sample.android.fragment;

import java.util.concurrent.atomic.AtomicInteger;

import org.coder.gear.sample.android.R;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * @author yoshida-n
 *
 */
public class GcmFragment extends Fragment{
	
	private static final String SENDER_ID = "380188014792";

	AtomicInteger msgId = new AtomicInteger();
	
	/**
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gcm, container,false);		
		return view;
    }
	
	 /**
	 * @see android.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	public void onActivityCreated(Bundle savedInstanceState) {
		//onCreateViewでイベント設定するとエラーになるようだ
		super.onActivityCreated(savedInstanceState);
	    Button button = (Button) getActivity().findViewById(R.id.start_gcm);
	    button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					  new AsyncTask<Void,String,String>() {
				            @Override
				            protected String doInBackground(Void... params) {
				                String msg = "";
				                try {
				                    Bundle data = new Bundle();
				                        data.putString("my_message", "Hello World");
				                        data.putString("my_action",
				                                "com.google.android.gcm.demo.app.ECHO_NOW");
				                        String id = Integer.toString(msgId.incrementAndGet());
				                        GoogleCloudMessaging.getInstance(getActivity()).send(SENDER_ID + "@gcm.googleapis.com", id, data);
				                        msg = "Sent message";
				                } catch (Exception ex) {
				                    msg = "Error :" + ex.getMessage();
				                }
				                return msg;
				            }

				            @Override
				            protected void onPostExecute(String msg) {
				                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
				            }
				        }.execute(null, null, null);
				}
			});
	 }

}
