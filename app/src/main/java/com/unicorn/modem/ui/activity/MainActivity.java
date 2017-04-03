package com.unicorn.modem.ui.activity;

import android.app.Activity;
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
import com.unicorn.modem.model.db.dao.SMSDaoImpl;
import com.unicorn.modem.model.event.UpdateEvent;
import com.unicorn.modem.reciever.TrackerAlarmReceiver;
import com.unicorn.modem.ui.fragment.HomeFragment;
import com.unicorn.modem.ui.fragment.SmsListSentFragment;
import com.unicorn.modem.util.Constant;
import com.unicorn.modem.util.DateConverter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.unicorn.modem.util.Constant.ACTION_SMS_DELIVERED;
import static com.unicorn.modem.util.Constant.ACTION_SMS_SENT;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.container)
    LinearLayout container;

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
                    fragment = new HomeFragment();
                    break;
                case R.id.navigation_dashboard:
                    fragment = SmsListSentFragment.getInstance(SmsStatus.SENT);
                    break;
                case R.id.navigation_notifications:
                    fragment = SmsListSentFragment.getInstance(SmsStatus.FAILED);
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

                String msgId = "";
                if (intent != null)
                {
                    msgId = intent.getStringExtra(Constant.SMS_ID);
                    if (msgId != null && !msgId.equals(""))
                    {
                        Long id = Long.valueOf(msgId);
                        Sms sms = smsDao.retrieve(id);
                        sms.setStatus(error ? SmsStatus.FAILED.getValue() : SmsStatus.SENT.getValue());
                        sms.setUpdateDateTime(DateConverter.getCurrentDate());
                        smsDao.update(sms);
                    }
                }
                Log.d(TAG, "Msg " + msgId + " delivery report :" + message);
            }
        }, new IntentFilter(ACTION_SMS_SENT));
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

                String msgId = "";
                if (intent != null)
                {
                    msgId = intent.getStringExtra(Constant.SMS_ID);
                    if (msgId != null && !msgId.equals(""))
                    {
                        Long id = Long.valueOf(msgId);
                        Sms sms = smsDao.retrieve(id);
                        sms.setStatus(error ? SmsStatus.FAILED.getValue() : SmsStatus.DELIVERED.getValue());
                        sms.setUpdateDateTime(DateConverter.getCurrentDate());
                        smsDao.update(sms);
                    }
                }
                EventBus.getDefault().post(new UpdateEvent());
                Log.d(TAG, "Msg delivery report :" + message);
            }
        }, new IntentFilter(ACTION_SMS_DELIVERED));

     /*   Sms s = new Sms(123L, 1, "+989367592641", "Hello Vahid from ModemApp");
        s.setId(2L);
        sendMsg(s);*/

        new TrackerAlarmReceiver().setAlarm(this);
    }
}
