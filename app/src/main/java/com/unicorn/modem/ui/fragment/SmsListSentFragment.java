package com.unicorn.modem.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unicorn.modem.R;
import com.unicorn.modem.model.db.Sms;
import com.unicorn.modem.model.db.SmsStatus;
import com.unicorn.modem.model.db.dao.SMSDaoImpl;
import com.unicorn.modem.model.event.Event;
import com.unicorn.modem.ui.adapter.SmsAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class SmsListSentFragment extends BaseFragment
{
    @BindView(R.id.sent_list)
    RecyclerView sentList;
    private long status;
    private SmsAdapter adapter;
    private SMSDaoImpl smsDao;
    private List<Sms> smsList;

    public SmsListSentFragment()
    {

    }

    public static SmsListSentFragment getInstance(SmsStatus sentStatus)
    {
        SmsListSentFragment fragment = new SmsListSentFragment();
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
        View view = inflater.inflate(R.layout.fragment_sms_sent_list, container, false);
        ButterKnife.bind(this, view);

        Bundle args = getArguments();
        if (args != null)
        {
            status = args.getLong("STATUS");
        }

        sentList.setHasFixedSize(true);
        sentList.setLayoutManager(new LinearLayoutManager(getActivity()));
        smsDao = new SMSDaoImpl();
        smsList = smsDao.retrieveAllByStatus(SmsStatus.SENT);
        adapter = new SmsAdapter(getActivity(),smsList);
        sentList.setAdapter(adapter);
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

    }

}
