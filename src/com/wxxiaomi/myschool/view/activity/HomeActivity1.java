package com.wxxiaomi.myschool.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.wxxiaomi.myschool.ConstantValue;
import com.wxxiaomi.myschool.GlobalParams;
import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.webpage.page.Html_Lib_Main;
import com.wxxiaomi.myschool.bean.webpage.page.Html_Main;
import com.wxxiaomi.myschool.bean.webpage.page.Html_lib_Login;
import com.wxxiaomi.myschool.bean.webpage.request.ResponseData;
import com.wxxiaomi.myschool.engine.LibraryEngineImpl;
import com.wxxiaomi.myschool.engine.OfficeEngineImpl;
import com.wxxiaomi.myschool.view.custom.CircularImageView;
import com.wxxiaomi.myschool.view.custom.LoadingDialog;
import com.wxxiaomi.myschool.view.custom.MyCodeDialog2;
import com.wxxiaomi.myschool.view.custom.MyCodeDialog2.OkButonnListener;
import com.wxxiaomi.myschool.view.fragment.ElectiveCourseFragment;
import com.wxxiaomi.myschool.view.fragment.IndexFragment;
import com.wxxiaomi.myschool.view.fragment.LibBorrowStateFragment1;
import com.wxxiaomi.myschool.view.fragment.ScoreFragment1;
import com.wxxiaomi.myschool.view.fragment.base.BaseFragment;
import com.wxxiaomi.myschool.view.fragment.base.FragmentCallback;

public class HomeActivity1 extends AppCompatActivity implements
		FragmentCallback, OnClickListener {
	private Toolbar toolbar; // ����toolbar
	private ActionBarDrawerToggle mDrawerToggle; // ����toolbar���Ͻǵĵ������˵���ť
	private DrawerLayout drawer_main; // ������໬�����֣���ʵ����������
	private NavigationView navigationView;

	private Fragment contentFragment; // ��¼��ǰ����ʹ�õ�fragment
	private boolean isMenuShuffle = false;
	private CircularImageView iv_head;
	private Html_Main main;
	private MainChangeListener lis;
	private CharSequence title = "��ҳ";
	private TextView header_tv;

	/**
	 * ͼ��ݵ�¼ҳ��bean
	 */
	private Html_lib_Login html_lib_login;
	/**
	 * ͼ�����ҳ��bean
	 */
	private Html_Lib_Main html_lib_main;
	private LibMainChangeListener libLis;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home1);
		navigationView = (NavigationView) findViewById(R.id.navigation);
		toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		drawer_main = (DrawerLayout) findViewById(R.id.dl_left);
		iv_head = (CircularImageView) navigationView.getHeaderView(0)
				.findViewById(R.id.iv_head);
		header_tv = (TextView) navigationView.getHeaderView(0).findViewById(
				R.id.header_tv);
		initFragment(savedInstanceState);
		initToolBar();
		initDrawer();
		initData();
	}

	/**
	 * ��ʼ���໬��
	 */
	private void initDrawer() {
		mDrawerToggle = new ActionBarDrawerToggle(this, drawer_main, toolbar,
				R.string.drawer_open, R.string.drawer_close);
		mDrawerToggle.syncState();
		drawer_main.setDrawerListener(mDrawerToggle);
		iv_head.setOnClickListener(this);
		initDrawerHeader();
	}

	/**
	 * ��ʼ���໬����ͷ��
	 */
	private void initDrawerHeader() {
		if (ConstantValue.isLogin) {
			BitmapUtils bitmapUtil = new BitmapUtils(this);
			bitmapUtil.display(iv_head, ConstantValue.LOTTERY_URI
					+ GlobalParams.gloUserInfo.userInfo.pic);
			header_tv.setText(GlobalParams.gloUserInfo.userInfo.name);
		} else {
			header_tv.setText("��¼");
			header_tv.setOnClickListener(this);
		}

	}

	/**
	 * ��ʼ��fragment
	 * 
	 * @param savedInstanceState
	 */
	private void initFragment(Bundle savedInstanceState) {
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
	}

	/**
	 * ��ʼ��toolbar
	 */
	private void initToolBar() {
		// �������������setSupportActionBar֮ǰ����Ȼ����Ч
		toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
		toolbar.setTitle(title);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("��ҳ");
		getSupportActionBar().setHomeButtonEnabled(true); // ���÷��ؼ�����
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	}

	private void setToolBarTitle(String title) {
		getSupportActionBar().setTitle(title);
	}

	private MenuItem currentItem;

	/**
	 * ��ʼ�����ݣ����ּ�����ť�¼�
	 */
	private void initData() {
		currentItem = navigationView.getMenu().findItem(R.id.drawer_home);
		currentItem.setChecked(true);
		navigationView
				.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected(MenuItem menuItem) {
						if (currentItem.getItemId() != menuItem.getItemId()) {
							boolean isLogin = checkLogin();
							if (isLogin) {
								if (changFragmentById(menuItem)) {
									currentItem.setChecked(false);
									menuItem.setChecked(true);
									currentItem = menuItem;
								}

							}
						}
						drawer_main.closeDrawers();
						return true;
					}

				});
	}

	private boolean changFragmentById(MenuItem menuItem) {
		boolean f = true;
		switch (menuItem.getItemId()) {
		case R.id.drawer_score:
			setToolBarTitle("�ɼ���ѯ");
			switchFragment(new ScoreFragment1(), 0);
			break;
		case R.id.drawer_home:
			setToolBarTitle("��ҳ");
			switchFragment(new IndexFragment(), 0);
			break;
		case R.id.drawer_classform:
			break;
		case R.id.drawer_elective:
			setToolBarTitle("ѡ�����");
			switchFragment(new ElectiveCourseFragment(), 0);
			break;
		case R.id.drawer_lib_borrow_state:
			setToolBarTitle("�������");
			switchFragment(new LibBorrowStateFragment1(), 0);
			break;
		case R.id.drawer_lib_search:
			Intent intent = new Intent(HomeActivity1.this,
					LibSearchActivity.class);
			startActivity(intent);
			f = false;
		default:
			break;
		}
		return f;
	}

	boolean flag;

	public boolean checkLogin() {
		if (ConstantValue.isLogin) {
			flag = true;
		} else {
			Log.i("wang", "����checkLogin->ConstantValue.isLogin=false");
			new AlertDialog.Builder(this)
					.setMessage("��ǰδ��¼,�Ƿ�����ǰȥ��¼?")
					.setPositiveButton(
							"�ǵ�",
							new android.content.DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Intent intent = new Intent(
											HomeActivity1.this,
											LoginActivity.class);
									startActivityForResult(intent,
											ConstantValue.LOGINACTIVITY);
								}
							})
					.setNegativeButton(
							"ȡ��",
							new android.content.DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									flag = false;
								}
							}).setCancelable(false).create().show();
		}
		return flag;
	}

	/**
	 * ��fragment�����л�ʱ��������������ʾ�ķ�������fragment�Է�ֹ���ݵ��ظ�����
	 * 
	 * @param from
	 * @param to
	 */
	// public void switchContent(Fragment from, Fragment to) {
	// // if (isFragment != to) {
	// // isFragment = to;
	// // FragmentManager fm = getSupportFragmentManager();
	// // // ��ӽ������ֵĶ���
	// // FragmentTransaction ft = fm.beginTransaction();
	// // if (!to.isAdded()) { // ���ж��Ƿ�add��
	// // ft.hide(from).add(R.id.frame_main, to).commit(); //
	// // ���ص�ǰ��fragment��add��һ����Activity��
	// // } else {
	// // ft.hide(from).show(to).commit(); // ���ص�ǰ��fragment����ʾ��һ��
	// // }
	// // }
	// contentFragment = to;
	// this.getSupportFragmentManager().beginTransaction()
	// .replace(R.id.frame_main, contentFragment).commit();
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
		case android.R.id.home:
			drawer_main.openDrawer(GravityCompat.START);
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (isMenuShuffle) {
			menu.findItem(R.id.action_send).setVisible(true);
		} else {
			menu.findItem(R.id.action_send).setVisible(false);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onFragmentCallback(BaseFragment fragment, int id, Bundle args) {
		switch (id) {
		case ConstantValue.OfficeNoLogin:
			// ����ϵͳ��δ��¼
			loginOfficeByNet(GlobalParams.gloUserInfo.userInfo.officeUserInfo.username,
					GlobalParams.gloUserInfo.userInfo.officeUserInfo.password);
			break;
		case 0:
			// office��ַ����
			handOfficeOutTime();
			break;
		case ConstantValue.LIBNOLOGIN:
			// ͼ���δ��¼
			LibLoginByNet();
			break;
		default:
			break;
		}
	}

	/**
	 * ��¼����ϵͳ��ȡmain
	 * 
	 * @param username
	 * @param password
	 */
	private void loginOfficeByNet(final String username, final String password) {
		final LoadingDialog dialog = new LoadingDialog(this).builder()
				.setMessage("���ڵ�¼..").show();
		new AsyncTask<String, Void, ResponseData<Html_Main>>() {
			@Override
			protected ResponseData<Html_Main> doInBackground(String... params) {
				OfficeEngineImpl impl = new OfficeEngineImpl();
//				Log.i("wang", "username=" + username + "--psd=" + password);
				return impl.getOfficeMainHtml2BeanByOne(username, password);
			}

			@Override
			protected void onPostExecute(ResponseData<Html_Main> result) {
				dialog.dismiss();
				if (result.isSuccess()) {

					main = result.getObj();
					lis.change(main);
				} else {
					// ��ȡʧ��
				}
				super.onPostExecute(result);
			}
		}.execute();

	}

	/**
	 * ��¼ͼ��ݻ�ȡmain������libLis֪ͨ
	 */
	private void LibLoginByNet() {
		final LoadingDialog loodingDialog = new LoadingDialog(
				HomeActivity1.this).builder();
		loodingDialog.setMessage("���ڻ�ȡ����....");
		final AlertDialog errorDialog = new AlertDialog.Builder(this)
				.setPositiveButton("ȷ��", null).setTitle("����").create();
		// ��ʾdialog
		final MyCodeDialog2 dialog2 = new MyCodeDialog2(this).builder();
		dialog2.show();
		// final MyCodeDialog dialog = new MyCodeDialog(this).builder();
		// ��ȡͼ��ݵ�¼ҳ���bean,���а���piccode
		new AsyncTask<String, Void, ResponseData<Html_lib_Login>>() {
			@Override
			protected ResponseData<Html_lib_Login> doInBackground(
					String... params) {
				LibraryEngineImpl engine = new LibraryEngineImpl();
				return engine.getLibLoginPageAndCodePic();
			}

			@Override
			protected void onPostExecute(ResponseData<Html_lib_Login> result) {
				if (result.isSuccess()) {

					html_lib_login = result.getObj();
					LibLogin();
				} else {
					// ���»�ȡ��ַʧ��
				}
				super.onPostExecute(result);
			}

			/**
			 * ��ȡ��ͼ��ݵ�¼ҳ���bean��ִ�е�¼����
			 */
			private void LibLogin() {
				dialog2.setImage(html_lib_login.getPicCode());
				dialog2.setOnOkButtonListener(new OkButonnListener() {
					@Override
					public void onClick(String input) {
						loodingDialog.show();
						login(input);
					}

					private void login(final String input) {
						new AsyncTask<String, Void, ResponseData<Html_Lib_Main>>() {

							@Override
							protected ResponseData<Html_Lib_Main> doInBackground(
									String... params) {
								LibraryEngineImpl engine = new LibraryEngineImpl();
								return engine.Login(html_lib_login,
										"131110199", "987987987", input);
							}

							@Override
							protected void onPostExecute(
									ResponseData<Html_Lib_Main> result) {
								loodingDialog.dismiss();
								if (result.isSuccess()) {
									html_lib_main = result.getObj();
									libLis.change(html_lib_main);
									dialog2.dismiss();
								} else {
									// ��¼ʧ��:ԭ������֤���������˺��������
									errorDialog.setMessage(result.getError());
									errorDialog.show();
								}
								super.onPostExecute(result);
							}
						}.execute();
					}
				});

			}
		}.execute();
	}

	private void handOfficeOutTime() {
		new AsyncTask<String, Void, ResponseData<Html_Main>>() {
			@Override
			protected ResponseData<Html_Main> doInBackground(String... params) {
				OfficeEngineImpl impl = new OfficeEngineImpl();
				return impl.getOfficeMainHtml2BeanByOne(main.getUsername(),
						main.getPassword());
			}

			@Override
			protected void onPostExecute(ResponseData<Html_Main> result) {
				if (result.isSuccess()) {
					main = result.getObj();
					lis.change(main);
				} else {
					// ���»�ȡ��ַʧ��
				}
				super.onPostExecute(result);
			}
		}.execute();

	}

	public void switchFragment(Fragment f, int state) {
		// �޸�Toolbar�˵�������
		if (state == 0) {
			// getSupportActionBar().setTitle(f.getClass().getSimpleName());
			contentFragment = f;
			this.getSupportFragmentManager().beginTransaction()
					.replace(R.id.frame_main, contentFragment).commit();
			invalidateOptionsMenu();
			/**
			 * �ر���໬���˵�
			 */
			drawer_main.closeDrawers();
		}
		// switch (menuName) {
		// case "a":
		// if (indexFragment == null) {
		// // Log.i("wang", "iFragment = new IndexFragment()");
		// indexFragment = new IndexFragment();
		// }
		// switchContent(contentFragment, indexFragment);
		// isMenuShuffle = false;
		// break;
		// case "b":
		// if (testFragment == null) {
		// testFragment = new TestFragment();
		// }
		// switchContent(contentFragment, testFragment);
		// isMenuShuffle = true;
		// break;
		// }

	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.iv_head:
			if(ConstantValue.isLogin){
				intent = new Intent(this, UserInfoActivity.class);
				startActivity(intent);
			}else{
				intent = new Intent(this, LoginActivity.class);
				startActivityForResult(intent, ConstantValue.LOGINACTIVITY);
			}
			break;
		case R.id.header_tv:
			intent = new Intent(this, LoginActivity.class);
			startActivityForResult(intent, ConstantValue.LOGINACTIVITY);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case ConstantValue.LOGINACTIVITY:
			// ��¼�ɹ��������
			initDrawerHeader();
			// changFragmentByMenuId(currentNavigationItemid);
			break;

		default:
			break;
		}
	}

	public Html_Lib_Main getLibMain() {
		return html_lib_main;
	}

	public Html_Main getOfficeHtmlMain() {
		return main;
	}

	public interface MainChangeListener {
		void change(Html_Main main);
	}

	public void setMainChangeListener(MainChangeListener lis) {
		this.lis = lis;
	}

	public interface LibMainChangeListener {
		void change(Html_Lib_Main html_lib_main);
	}

	public void setLibMainChangeListener(LibMainChangeListener libLis) {
		this.libLis = libLis;
	}

}
