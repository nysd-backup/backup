/**
 * 
 */
package org.coder.gear.sample.android.fragment;

import org.coder.gear.sample.android.R;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author yoshida-n
 *
 */
public class DownloadFragment extends Fragment{
	
	private DownloadManager downLoadManager;
	
	private long id = -1;

	/**
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_download, container,false);		
		return view;
    }
	
	/**
	 * @see android.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		downLoadManager = (DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
		Uri.Builder uriBuilder = new Uri.Builder();
		uriBuilder.scheme("http");
		uriBuilder.authority("www.cross-bridge.jp");
		uriBuilder.path("/TechBooster.pdf");
		
		final Request request = new Request(uriBuilder.build());
		request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/temp.pdf");
		request.setTitle("TechBooster");
		request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
		request.setMimeType("application/pdf");
			
		Button start = (Button)getActivity().findViewById(R.id.start_dl_service);
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {							
				Toast.makeText(getActivity(), "Download Start", Toast.LENGTH_SHORT).show();
				id = downLoadManager.enqueue(request);
			}
		});
			
		Button stop = (Button)getActivity().findViewById(R.id.stop_dl_service);
		stop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {								
				Toast.makeText(getActivity(), "Download Cancel", Toast.LENGTH_SHORT).show();
				downLoadManager.remove(id);
			}
		});
		
	}
}
