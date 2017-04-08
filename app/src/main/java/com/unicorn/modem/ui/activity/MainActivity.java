package com.unicorn.modem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.unicorn.modem.R;
import com.unicorn.modem.model.db.Sms;
import com.unicorn.modem.model.db.SmsStatus;
import com.unicorn.modem.model.db.dao.SMSDaoImpl;
import com.unicorn.modem.reciever.TrackerAlarmReceiver;
import com.unicorn.modem.ui.fragment.HomeFragment;
import com.unicorn.modem.ui.fragment.SmsListFailedFragment;
import com.unicorn.modem.ui.fragment.SmsListSentFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = MainActivity.class.getSimpleName();
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
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.navigation_home:
                    fragment = HomeFragment.getInstance();
                    break;
                case R.id.navigation_dashboard:
                    fragment = SmsListSentFragment.getInstance(SmsStatus.SENT);
                    break;
                case R.id.navigation_notifications:
                    fragment = SmsListFailedFragment.getInstance(SmsStatus.FAILED);
                    break;
            }
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content, fragment);
//            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();
        fragment = new HomeFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        new TrackerAlarmReceiver().setAlarm(this);

        setSupportActionBar(toolbar);
    }

    public void createTempData()
    {
        SMSDaoImpl smsDao = new SMSDaoImpl();

        for (int i = 0; i < 10; i++)
        {
            Sms sms = new Sms(i * 10L, i, "+9899912312" + i, "Hello unknown people" + i);
            sms.setStatus(SmsStatus.PENDING.getValue());

            long id = smsDao.create(sms);
            Log.d(TAG, "Temp SMS Created with id" + id);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.settings:
                startActivity(new Intent(this, SettingActivity.class));
        }
        return true;
    }

}
