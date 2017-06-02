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

import com.unicorn.modem.R;
import com.unicorn.modem.util.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity
{
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        setupSpinner();
        setSupportActionBar(toolbar);
    }

    private void setupSpinner()
    {
        List<Integer> modemNumber = new ArrayList<>();
        for (int i = 1; i < 10; i++)
        {
            modemNumber.add(i);
        }
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, modemNumber);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modemNo.setAdapter(dataAdapter);
    }

    @OnClick(R.id.save_button)
    public void onViewClicked()
    {
        String bizId = businessId.getText().toString();
        PreferenceHelper.setBizId(Integer.parseInt(bizId));

        Integer selectedModem = (Integer) modemNo.getSelectedItem();
        PreferenceHelper.setModemNo(selectedModem);

        Integer interval = Integer.valueOf(intervalTime.getText().toString());
        PreferenceHelper.setUpdateInterval(interval);

        Toast.makeText(this, "Setting saved successfully!", Toast.LENGTH_LONG).show();
        finish();
    }
}
