package com.wxxiaomi.myschool.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.view.custom.MyMaterialDialog;
import com.wxxiaomi.myschool.view.fragment.IndexFragment;
import com.wxxiaomi.myschool.view.fragment.LeftFragment;
import com.wxxiaomi.myschool.view.fragment.base.BaseFragment;
import com.wxxiaomi.myschool.view.fragment.base.FragmentCallback;

public class HomeActivity extends AppCompatActivity implements
		FragmentCallback{

	private Toolbar toolbar; // ����toolbar
	private ActionBarDrawerToggle mDrawerToggle; // ����toolbar���Ͻǵĵ������˵���ť
	private DrawerLayout drawer_main; // ������໬�����֣���ʵ����������


	private Fragment contentFragment; // ��¼��ǰ����ʹ�õ�fragment
	private boolean isMenuShuffle = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		// tool_NavigationDrawerFragment f = new
		// tool_NavigationDrawerFragment();
		LeftFragment f = new LeftFragment();
		this.getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame_left, f).commit();

		if (savedInstanceState == null) {
			contentFragment = new IndexFragment();
		} else {
			// ȡ��֮ǰ�����contentFragment
			contentFragment = this.getSupportFragmentManager().getFragment(
					savedInstanceState, "contentFragment");
		}
		// ���õ�ǰ��fragment
		this.getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame_main, contentFragment).commit();

		initToolbar();
		// initFragment(savedInstanceState);
		new MyMaterialDialog.Builder(this)
        .setTitle("title")
        .setMessage("message")
        .setNegativeButton("cancel",null)
        .setPositiveButton("ok",null)
//        .set
        .create()
        
        .show();
	}
	

	private void initToolbar() {
		toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		toolbar.setTitle("index"); // �������������setSupportActionBar֮ǰ����Ȼ����Ч
		toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true); // ���÷��ؼ�����
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// Ϊ�����ɣ����������ϽǵĶ�̬ͼ�꣬Ҫʹ������ķ���
		drawer_main = (DrawerLayout) findViewById(R.id.dl_left);
		mDrawerToggle = new ActionBarDrawerToggle(this, drawer_main, toolbar,
				R.string.drawer_open, R.string.drawer_close);
		mDrawerToggle.syncState();
		drawer_main.setDrawerListener(mDrawerToggle);
	}

//	@Override
//	public void menuClick(String menuName) {
//		getSupportActionBar().setTitle(menuName); // �޸�Toolbar�˵�������
//
//		switch (menuName) {
//		case "a":
//			if (indexFragment == null) {
//				// Log.i("wang", "iFragment = new IndexFragment()");
//				indexFragment = new IndexFragment();
//			}
//			switchContent(contentFragment, indexFragment);
//			isMenuShuffle = false;
//			break;
//		case "b":
//			if (testFragment == null) {
//				testFragment = new TestFragment();
//			}
//			switchContent(contentFragment, testFragment);
//			isMenuShuffle = true;
//			break;
//		}
//		invalidateOptionsMenu();
//
//		/**
//		 * �ر���໬���˵�
//		 */
//		drawer_main.closeDrawers();
//	}

	/**
	 * ��fragment�����л�ʱ��������������ʾ�ķ�������fragment�Է�ֹ���ݵ��ظ�����
	 * 
	 * @param from
	 * @param to
	 */
//	public void switchContent(Fragment from, Fragment to) {
//		// if (isFragment != to) {
//		// isFragment = to;
//		// FragmentManager fm = getSupportFragmentManager();
//		// // ��ӽ������ֵĶ���
//		// FragmentTransaction ft = fm.beginTransaction();
//		// if (!to.isAdded()) { // ���ж��Ƿ�add��
//		// ft.hide(from).add(R.id.frame_main, to).commit(); //
//		// ���ص�ǰ��fragment��add��һ����Activity��
//		// } else {
//		// ft.hide(from).show(to).commit(); // ���ص�ǰ��fragment����ʾ��һ��
//		// }
//		// }
//		contentFragment = to;
//		this.getSupportFragmentManager().beginTransaction()
//				.replace(R.id.frame_main, contentFragment).commit();
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_demo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_collect:
			Toast.makeText(this, "action_collect", Toast.LENGTH_LONG).show();
			break;
		case R.id.action_send:
			Toast.makeText(this, "action_send", Toast.LENGTH_LONG).show();
			break;
		case R.id.action_copy_link:
			Toast.makeText(this, "action_copy_link", Toast.LENGTH_LONG).show();
			break;
		case R.id.action_open_by_browser:
			Toast.makeText(this, "action_open_by_browser", Toast.LENGTH_LONG)
					.show();
			break;
		// case R.id.main_toolbar_shuffle:
		// Toast.makeText(this, "main_toolbar_shuffle", Toast.LENGTH_LONG)
		// .show();
		// break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// Log.e("isMenuShuffle",isMenuShuffle + "");
		if (isMenuShuffle) {
			menu.findItem(R.id.action_send).setVisible(true);
		} else {
			menu.findItem(R.id.action_send).setVisible(false);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onFragmentCallback(BaseFragment fragment, int id, Bundle args) {
		// TODO Auto-generated method stub
		
	}

	public void switchFragment(Fragment f, int state) {
		 // �޸�Toolbar�˵�������
		if (state == 0) {
			getSupportActionBar().setTitle(f.getClass().getSimpleName());
			contentFragment = f;
			this.getSupportFragmentManager().beginTransaction()
					.replace(R.id.frame_main, contentFragment).commit();
			invalidateOptionsMenu();

			/**
			 * �ر���໬���˵�
			 */
			drawer_main.closeDrawers();
		}
//		switch (menuName) {
//		case "a":
//			if (indexFragment == null) {
//				// Log.i("wang", "iFragment = new IndexFragment()");
//				indexFragment = new IndexFragment();
//			}
//			switchContent(contentFragment, indexFragment);
//			isMenuShuffle = false;
//			break;
//		case "b":
//			if (testFragment == null) {
//				testFragment = new TestFragment();
//			}
//			switchContent(contentFragment, testFragment);
//			isMenuShuffle = true;
//			break;
//		}
		
		
	}

}
