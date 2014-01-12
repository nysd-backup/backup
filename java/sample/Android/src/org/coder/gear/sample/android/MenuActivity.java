/**
 * 
 */
package org.coder.gear.sample.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author yoshida-n
 *
 */
public class MenuActivity extends Activity{

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		//タブボタン
		Button button = (Button)findViewById(R.id.tabs);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this,TabActivity.class);			
				MenuActivity.this.startActivityForResult(intent,1);
			}
		});
	}
}
