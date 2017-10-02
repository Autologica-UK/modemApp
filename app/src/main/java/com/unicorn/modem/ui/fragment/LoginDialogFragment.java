package com.unicorn.modem.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.unicorn.modem.R;
import com.unicorn.modem.model.event.Event;
import com.unicorn.modem.service.impl.SmsServiceImpl;
import com.unicorn.modem.ui.activity.MainActivity;
import com.unicorn.modem.ui.activity.SettingActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Arash 2017-07-11
 */
public class LoginDialogFragment extends DialogFragment implements TextWatcher {

  public static final String TAG = LoginDialogFragment.class.getSimpleName();
  @BindView(R.id.username_field)
  EditText usernameField;
  @BindView(R.id.password_field)
  EditText passwordField;
  @BindView(R.id.error_message)
  TextView errorMessage;

  public static LoginDialogFragment newInstance() {
    return new LoginDialogFragment();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.dialog_setting_login, null);
    ButterKnife.bind(this, view);

    usernameField.addTextChangedListener(this);
    passwordField.addTextChangedListener(this);
    getDialog().setTitle(R.string.login_to_setting);

    return view;
  }

  @OnClick({R.id.login_btn, R.id.cancel_btn})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.login_btn:
        doLogin();
        break;
      case R.id.cancel_btn:
        LoginDialogFragment.this.dismiss();
        getActivity().finish();
        break;
    }
  }

  private void doLogin() {
    String usernameValue = usernameField.getText().toString();
    String passwordValue = passwordField.getText().toString();

    /*if (usernameValue.isEmpty()) {
      errorMessage.setText(R.string.error_username_is_empty);
      errorMessage.setVisibility(View.VISIBLE);
      usernameField.requestFocus();
      return;
    }*/

    if (passwordValue.isEmpty()) {
      errorMessage.setText(R.string.error_password_is_empty);
      errorMessage.setVisibility(View.VISIBLE);
      passwordField.requestFocus();
      return;
    }


    new SmsServiceImpl(getActivity()).getData(passwordValue);

    /*if (usernameValue.equalsIgnoreCase("admin") && passwordValue.equals("25852")) {

      startActivity(new Intent(getActivity(), SettingActivity.class));

      LoginDialogFragment.this.dismiss();*/
  /*  } else {
      errorMessage.setText(R.string.error_invalid_login);
      errorMessage.setVisibility(View.VISIBLE);
    }*/
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {

  }

  @Override
  public void afterTextChanged(Editable s) {
    errorMessage.setVisibility(View.GONE);
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
    if (event.getStatusCode() == 200) {
      startActivity(new Intent(getActivity(), MainActivity.class));
    }else{
      errorMessage.setText(R.string.error_invalid_login);
      errorMessage.setVisibility(View.VISIBLE);
    }
  }
}

