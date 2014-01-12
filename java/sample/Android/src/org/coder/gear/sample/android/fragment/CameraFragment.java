/**
 * 
 */
package org.coder.gear.sample.android.fragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.coder.gear.sample.android.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @author yoshida-n
 *
 */
public class CameraFragment extends Fragment{
	
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	/**
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_camera, container,false);		
		return view;
    }
	
	 /**
	 * @see android.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	public void onActivityCreated(Bundle savedInstanceState) {
		//onCreateViewでイベント設定するとエラーになるようだ
		super.onActivityCreated(savedInstanceState);
	    Button button = (Button) getActivity().findViewById(R.id.start_camera);
	    button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

					Uri fileUri = getOutputMediaFile(MEDIA_TYPE_IMAGE); // create a file to save the image
				    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

				    // start the image capture Intent
				    startActivityForResult(intent, MEDIA_TYPE_IMAGE);
				}
			});
	 }
	
	/**
	 * @param type
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	private Uri getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "MyCameraApp");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else if(type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "VID_"+ timeStamp + ".mp4");
	    } else {
	        return null;
	    }
	    return Uri.fromFile(mediaFile);
	}
}
