package com.unicorn.modem.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unicorn.modem.R;
import com.unicorn.modem.model.db.SmsStatus;
import com.unicorn.modem.model.event.Event;
import com.unicorn.modem.service.impl.SmsServiceImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 *
 */
public class HomeFragment extends BaseFragment
{
    private static HomeFragment fragment;
    private SmsServiceImpl smsService;

    public HomeFragment()
    {

    }

    public static HomeFragment getInstance()
    {

        if (fragment == null)
        {
            fragment = new HomeFragment();
        }

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        smsService = new SmsServiceImpl();
        smsService.getSmsList();

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void getMessage(Event event)
    {
        dismissProgressDialog();
    }
}
