package com.unicorn.modem.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.unicorn.modem.R;
import com.unicorn.modem.model.db.Sms;
import com.unicorn.modem.model.db.SmsStatus;
import com.unicorn.modem.model.db.dao.SMSDaoImpl;
import com.unicorn.modem.model.event.Event;
import com.unicorn.modem.model.event.UpdateEvent;
import com.unicorn.modem.ui.adapter.SmsReceiverAdapter;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 *
 */
public class SmsListReceivedFragment extends BaseFragment {

  private static final String TAG = SmsListReceivedFragment.class.getName();
  @BindView(R.id.list)
  RecyclerView list;
  private long status;
  private SmsReceiverAdapter adapter;
  private SMSDaoImpl smsDao;
  private List<Sms> smsList;

  public SmsListReceivedFragment() {
  }

  public static SmsListReceivedFragment getInstance(SmsStatus sentStatus) {

    SmsListReceivedFragment fragment = new SmsListReceivedFragment();
    Bundle args = new Bundle();
    args.putLong("STATUS", sentStatus.getValue());
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_sms_failed_list, container, false);
    ButterKnife.bind(this, view);

    Bundle args = getArguments();
    if (args != null) {
      status = args.getLong("STATUS");
    }

    list.setHasFixedSize(true);
    list.setLayoutManager(new LinearLayoutManager(getActivity()));
    smsDao = new SMSDaoImpl();
    smsList = smsDao.retrieveAllByStatus(SmsStatus.RECEIVED);
    adapter = new SmsReceiverAdapter(getActivity(), smsList);
    list.setAdapter(adapter);
    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override
  public void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }

  @Subscribe
  public void getMessage(Event event) {
    Log.d(TAG, "database updated");
    if (event instanceof UpdateEvent) {
      refresh();
    }
  }

  private void refresh() {
    smsList = smsDao.retrieveAllByStatus(SmsStatus.RECEIVED);
    adapter.updateList( smsList );
  }
}
