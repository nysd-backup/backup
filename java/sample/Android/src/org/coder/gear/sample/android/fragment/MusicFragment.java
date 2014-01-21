/**
 * 
 */
package org.coder.gear.sample.android.fragment;

import org.coder.gear.sample.android.R;
import org.coder.gear.sample.android.service.MusicPlayerService;
import org.coder.gear.sample.android.service.MusicPlayerService.MusicBinder;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author yoshida-n
 *
 */
public class MusicFragment extends Fragment{

	private MusicServiceConnection conn = new MusicServiceConnection();
	
	private MusicPlayerService service = null;
	
	private boolean bound = false;
	
	/**
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_music, container,false);		
		return view;
    }
	
	/**
	 * @see android.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		Button start = (Button)getActivity().findViewById(R.id.start_service);
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(bound){
					service.start();
				}else{
					Toast.makeText(getActivity(),"not bound",Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		Button stop = (Button)getActivity().findViewById(R.id.stop_service);
		stop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(bound){
					service.stop();
				}else{
					Toast.makeText(getActivity(),"not bound",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	/**
	 * @see android.app.Fragment#onStart()
	 */
	public void onStart(){
		super.onStart();
		Intent intent = new Intent(getActivity(),MusicPlayerService.class);
		getActivity().bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}
	
	/**
	 * @see android.app.Fragment#onPause()
	 */
	public void onStop(){
		super.onStop();
		getActivity().unbindService(conn);
	}
	
	public class MusicServiceConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Toast.makeText(getActivity(), "Connect", Toast.LENGTH_SHORT).show();
			MusicFragment.this.service = ((MusicBinder)service).getService();
			MusicFragment.this.bound = true;			
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Toast.makeText(getActivity(), "Disconnect", Toast.LENGTH_SHORT).show();
			service = null;
			MusicFragment.this.bound = false;
		}
		
	}
}
