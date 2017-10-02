package com.unicorn.modem.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.unicorn.modem.R;
import com.unicorn.modem.util.PreferenceHelper;
import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.business_id)
  EditText businessId;
  @BindView(R.id.business_layout)
  LinearLayout businessLayout;
  @BindView(R.id.interval_time)
  EditText intervalTime;
  @BindView(R.id.interval_layout)
  LinearLayout intervalLayout;
  @BindView(R.id.modem_no)
  AppCompatSpinner modemNo;
  @BindView(R.id.modem_layout)
  LinearLayout modemLayout;
  @BindView(R.id.save_button)
  Button saveButton;
  @BindView(R.id.ip)
  EditText ip;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting);
    ButterKnife.bind(this);

    loadData();
    setSupportActionBar(toolbar);
  }

  private void loadData() {
    List<Integer> modemNumber = new ArrayList<>();
    for (int i = 1; i < 10; i++) {
      modemNumber.add(i);
    }
    ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<>(this,
        android.R.layout.simple_spinner_item, modemNumber);
    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    modemNo.setAdapter(dataAdapter);

    String url = PreferenceHelper.getServerUrl();

    ip.setText(url);

    Integer interval = PreferenceHelper.getUpdateInterval();
    intervalTime.setText(String.valueOf(interval));

    Integer bizId = PreferenceHelper.getBizId();
    businessId.setText(String.valueOf(bizId));

    Integer selectedModem = PreferenceHelper.getModemNo();
    modemNo.setSelection(selectedModem-1);
  }

  @OnClick(R.id.save_button)
  public void onViewClicked() {
    if (!validate()) {
      return;
    }
    String bizId = businessId.getText().toString();
    PreferenceHelper.setBizId(Integer.parseInt(bizId));

    Integer selectedModem = (Integer) modemNo.getSelectedItem();
    PreferenceHelper.setModemNo(selectedModem);

    Integer interval = Integer.valueOf(intervalTime.getText().toString());
    PreferenceHelper.setUpdateInterval(interval);

    String url = ip.getText().toString();
    PreferenceHelper.setServerUrl(url);

    Toast.makeText(this, "Setting saved successfully!", Toast.LENGTH_LONG).show();
    finish();
  }

  private boolean validate() {
    if (ip.getText().toString().trim().isEmpty()) {
      Toast.makeText(this, "Server URL can not be empty", Toast.LENGTH_SHORT).show();
      return false;
    }
    if (businessId.getText().toString().trim().isEmpty()) {
      Toast.makeText(this, "Business ID can not be empty", Toast.LENGTH_SHORT).show();
      return false;
    }
    try {
      int interval = Integer.valueOf(intervalTime.getText().toString());
    } catch (NumberFormatException ex) {
      Toast.makeText(this, "Update interval time is not correct", Toast.LENGTH_SHORT).show();
      return false;
    }
    return true;
  }
}
