package com.unicorn.modem.ui.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.unicorn.modem.R;
import com.unicorn.modem.model.db.Sms;
import com.unicorn.modem.model.db.SmsStatus;
import com.unicorn.modem.ui.fragment.HomeFragment;
import com.unicorn.modem.ui.fragment.SmsListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{
    private static final String ACTION_SMS_SENT = "com.unicorn.modem.action.SMS_SENT";
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.container)
    LinearLayout container;

    private Fragment fragment;
    private FragmentManager fragmentManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.navigation_dashboard:
                    fragment = SmsListFragment.getInstance(SmsStatus.SENT);
                    break;
                case R.id.navigation_notifications:
                    fragment = SmsListFragment.getInstance(SmsStatus.FAILED);
                    break;
            }
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content, fragment).commit();
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
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content, fragment).commit();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        registerReceiver(new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                String message = null;
                boolean error = true;
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        message = "Message sent!";
                        error = false;
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        message = "Error.";
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        message = "Error: No service.";
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        message = "Error: Null PDU.";
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        message = "Error: Radio off.";
                        break;
                }

                Log.d(TAG, "Msg delivery report :" + message);
            }
        }, new IntentFilter(ACTION_SMS_SENT));
    }

    public void sendMsg(Sms sms)
    {
        String msg = sms.getMsg();
        String to = sms.getRecordNo();
        SmsManager smsManager = SmsManager.getDefault();
        List<String> messages = smsManager.divideMessage(msg);
        for (String message : messages)
        {
            smsManager.sendTextMessage(to, null, message,
                    PendingIntent.getBroadcast(this, 0, new Intent(ACTION_SMS_SENT), 0), null);
        }
    }
}
