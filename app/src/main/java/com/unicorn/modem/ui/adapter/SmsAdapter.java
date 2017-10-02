package com.unicorn.modem.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.unicorn.modem.R;
import com.unicorn.modem.model.db.Sms;
import java.util.List;

/**
 * Created by Arashmidos on 2017-04-03.
 */

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.MyViewHolder> {

  private final Context context;
  private final LayoutInflater mInflater;
  private List<Sms> smsList;

  public SmsAdapter(Context context, List<Sms> smsList) {
    this.smsList = smsList;
    this.context = context;
    this.mInflater = LayoutInflater.from(context);

  }

  @Override
  public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
    View view = mInflater.inflate(R.layout.list_item_main, parent, false);
    MyViewHolder holder = new MyViewHolder(view);
    return holder;
  }

  @Override
  public void onBindViewHolder(MyViewHolder viewHolder, int position) {
    Sms s = smsList.get(position);
    viewHolder.setData(s, position);
  }

  @Override
  public int getItemCount() {
    return smsList.size();
  }

  public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.sent_date)
    TextView sentDateTv;
    @BindView(R.id.rec_no)
    TextView recNoTv;
    @BindView(R.id.msg)
    TextView msgTv;
    @BindView(R.id.msg_id)
    TextView msgId;
    private Sms current;
    private int pos;
    private Long id;

    public MyViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    public void setData(Sms sms, int position) {
      this.current = sms;
      this.pos = position;
      this.id = sms.getId();
      sentDateTv.setText(sms.getUpdateDateTime());
      recNoTv.setText(sms.getRecordNo());
      msgTv.setText(sms.getMsg());
      msgId.setText(String.valueOf(sms.getMsgId()));
    }
  }
}
