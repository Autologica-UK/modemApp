package com.unicorn.modem.ui.activity;

import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v13.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.unicorn.modem.R;
import com.unicorn.modem.model.db.SmsStatus;
import com.unicorn.modem.model.db.dao.SMSDaoImpl;
import com.unicorn.modem.reciever.TrackerAlarmReceiver;
import com.unicorn.modem.ui.BottomNavigationViewHelper;
import com.unicorn.modem.ui.fragment.HomeFragment;
import com.unicorn.modem.ui.fragment.SmsListFailedFragment;
import com.unicorn.modem.ui.fragment.SmsListReceivedFragment;
import com.unicorn.modem.ui.fragment.SmsListSentFragment;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();
  private static final int READ_SMS_PERMISSIONS_REQUEST = 1;
  @BindView(R.id.content)
  FrameLayout content;
  @BindView(R.id.navigation)
  BottomNavigationView navigation;
  @BindView(R.id.container)
  LinearLayout container;
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  private Fragment fragment;
  private FragmentManager fragmentManager;
  private SMSDaoImpl smsDao = new SMSDaoImpl();

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
      = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()) {
        case R.id.navigation_home:
          fragment = HomeFragment.getInstance();
          break;
        case R.id.navigation_sent:
          fragment = SmsListSentFragment.getInstance(SmsStatus.SENT);
          break;
        case R.id.navigation_failed:
          fragment = SmsListFailedFragment.getInstance(SmsStatus.FAILED);
          break;
        case R.id.navigation_sms_received:
          fragment = SmsListReceivedFragment.getInstance(SmsStatus.RECEIVED);
          break;
      }
      FragmentTransaction transaction = fragmentManager.beginTransaction();
      transaction.replace(R.id.content, fragment);
//            transaction.addToBackStack(null);
      transaction.commit();
      BottomNavigationViewHelper.disableShiftMode(navigation);
      return true;
    }

  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    if (!checkPermissions()) {
      requestPermissions();
    }
//    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);

    fragmentManager = getSupportFragmentManager();
    fragment = new HomeFragment();
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.add(R.id.content, fragment);
    transaction.addToBackStack(null);
    transaction.commit();
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    BottomNavigationViewHelper.disableShiftMode(navigation);
    new TrackerAlarmReceiver().setAlarm(this);

    setSupportActionBar(toolbar);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.settings:
        startActivity(new Intent(this, SettingActivity.class));

        /*DialogFragment loginDialog = LoginDialogFragment.newInstance();
        loginDialog.show(getSupportFragmentManager(), "login_dialog");*/
    }
    return true;
  }

  private boolean checkPermissions() {
    return PackageManager.PERMISSION_GRANTED == ActivityCompat
        .checkSelfPermission(this, permission.READ_SMS)
        && PackageManager.PERMISSION_GRANTED == ActivityCompat
        .checkSelfPermission(this, permission.SEND_SMS)
        && PackageManager.PERMISSION_GRANTED == ActivityCompat
        .checkSelfPermission(this, permission.RECEIVE_SMS);
  }

  private void requestPermissions() {//TODO Complete
    boolean shouldProvideRationale =
        ActivityCompat.shouldShowRequestPermissionRationale(this, permission.READ_SMS);

    // Provide an additional rationale to the user. This would happen if the user denied the
    // request previously, but didn't check the "Don't ask again" checkbox.

  }
}
