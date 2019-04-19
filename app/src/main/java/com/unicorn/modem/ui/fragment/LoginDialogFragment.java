package com.unicorn.modem.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.unicorn.modem.R;
import com.unicorn.modem.model.event.Event;
import com.unicorn.modem.service.impl.SmsServiceImpl;
import com.unicorn.modem.ui.activity.MainActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Arash 2017-07-11
 */
public class LoginDialogFragment extends DialogFragment implements TextWatcher {

  @BindView(R.id.password_field)
  EditText passwordField;

  @BindView(R.id.inputLayout)
  TextInputLayout inputLayout;

  public static LoginDialogFragment newInstance() {
    return new LoginDialogFragment();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.dialog_setting_login, null);
    ButterKnife.bind(this, view);

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
    String passwordValue = passwordField.getText().toString();

    if (passwordValue.isEmpty()) {
      inputLayout.setError(getString(R.string.error_password_is_empty));
      passwordField.requestFocus();
      return;
    }

    new SmsServiceImpl(getActivity()).getData(passwordValue);
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {

  }

  @Override
  public void afterTextChanged(Editable s) {
    inputLayout.setError(null);
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
    } else {
      inputLayout.setError(getString(R.string.error_invalid_login));
    }
  }
}

