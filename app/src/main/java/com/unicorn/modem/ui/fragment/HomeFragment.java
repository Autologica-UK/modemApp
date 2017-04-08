package com.unicorn.modem.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unicorn.modem.R;
import com.unicorn.modem.model.db.dao.SMSDaoImpl;
import com.unicorn.modem.model.event.Event;
import com.unicorn.modem.model.event.UpdateEvent;
import com.unicorn.modem.service.impl.SmsServiceImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Arashmidos
 */
public class HomeFragment extends BaseFragment
{
    private static String TAG = HomeFragment.class.getSimpleName();
    private static HomeFragment fragment;
    @BindView(R.id.total_no)
    TextView totalNo;
    @BindView(R.id.sent_no)
    TextView sentNo;
    @BindView(R.id.failed_no)
    TextView failedNo;
    private SmsServiceImpl smsService;
    private SMSDaoImpl smsDao = new SMSDaoImpl();

    public HomeFragment()
    {
    }

    public static HomeFragment getInstance()
    {
        return new HomeFragment();
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

        smsService = new SmsServiceImpl(getActivity());
        smsService.getSmsList();

        totalNo.setText(String.valueOf(smsDao.totalCount()));
        int sentCount = smsDao.sentCount();
        int deliveredCount = smsDao.deliveredCount();
        //TODO: Delivery has problem in distinguish which message is delivered
        sentNo.setText(String.format("%s / %s", sentCount + deliveredCount, deliveredCount + sentCount));
        failedNo.setText(String.valueOf(smsDao.failedCount()));

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
        Log.d(TAG, "database updated");
        if (event instanceof UpdateEvent)
        {
            getActivity().runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    totalNo.setText(String.valueOf(smsDao.totalCount()));
                    int sentCount = smsDao.sentCount();
                    int deliveredCount = smsDao.deliveredCount();
                    //TODO: Update this for delivery bug
                    sentNo.setText(String.format("%s / %s", sentCount + deliveredCount, deliveredCount + sentCount));
                    failedNo.setText(String.valueOf(smsDao.failedCount()));
                }
            });
        }
    }
}
