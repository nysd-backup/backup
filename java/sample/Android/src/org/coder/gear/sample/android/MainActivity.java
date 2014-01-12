package org.coder.gear.sample.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
