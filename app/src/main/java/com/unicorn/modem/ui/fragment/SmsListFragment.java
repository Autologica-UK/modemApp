package com.unicorn.modem.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unicorn.modem.R;
import com.unicorn.modem.model.db.SmsStatus;
import com.unicorn.modem.model.event.Event;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class SmsListFragment extends BaseFragment
{
    @BindView(R.id.title)
    TextView title;
    private long status;

    public SmsListFragment()
    {

    }

    public static SmsListFragment getInstance(SmsStatus sentStatus)
    {
        SmsListFragment fragment = new SmsListFragment();
        Bundle args = new Bundle();
        args.putLong("STATUS", sentStatus.getValue());
        fragment.setArguments(args);

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
        View view = inflater.inflate(R.layout.fragment_sms_list, container, false);
        ButterKnife.bind(this, view);

        Bundle args = getArguments();
        if (args != null)
        {
            status = args.getLong("STATUS");
        }

        title.setText(String.valueOf(status));

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

    @Override
    public void onResume()
    {
        super.onResume();
        title.setText(String.valueOf(status));
    }
}
