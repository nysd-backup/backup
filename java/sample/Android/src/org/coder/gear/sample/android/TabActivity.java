/**
 * 
 */
package org.coder.gear.sample.android;

import java.util.ArrayList;
import java.util.List;

import org.coder.gear.sample.android.fragment.CameraFragment;
import org.coder.gear.sample.android.fragment.ServiceFragment;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * @author yoshida-n
 *
 */
public class TabActivity extends Activity implements TabListener{
	
	private List<Fragment> managedFragment = new ArrayList<Fragment>();

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_tab);
		
		final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        ServiceFragment sf = new ServiceFragment();
        managedFragment.add(sf);
        actionBar.addTab(actionBar.newTab().setText("SERVICE").setTabListener(this));

        CameraFragment camera = new CameraFragment();
        managedFragment.add(camera);
        actionBar.addTab(actionBar.newTab().setText("CAMERA").setTabListener(this));
	}
	
	/**
	 * @see android.app.ActionBar.TabListener#onTabSelected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
	 */
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Fragment fragment = managedFragment.get(tab.getPosition());
		ft.replace(R.id.content_frag_tab, fragment);
	}

	/**
	 * @see android.app.ActionBar.TabListener#onTabUnselected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
	 */
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		Fragment fragment = managedFragment.get(tab.getPosition());
		ft.remove(fragment);
	}

	/**
	 * @see android.app.ActionBar.TabListener#onTabReselected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
	 */
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}
