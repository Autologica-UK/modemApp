package com.unicorn.modem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import com.unicorn.modem.R;
import com.unicorn.modem.ui.fragment.LoginDialogFragment;
import com.unicorn.modem.util.PreferenceHelper;

public class LoginActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    String serverUrl = PreferenceHelper.getServerUrl();
    if (serverUrl.equals("")) {
      DialogFragment loginDialog = LoginDialogFragment.newInstance();
      loginDialog.show(getSupportFragmentManager(), "login_dialog");
    } else {
      startActivity(new Intent(this, MainActivity.class));
    }
  }
}
