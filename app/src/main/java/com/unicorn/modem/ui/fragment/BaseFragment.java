package com.unicorn.modem.ui.fragment;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import com.unicorn.modem.R;

/**
 * Created by Arashmidos on 2017-03-31
 */
public class BaseFragment extends Fragment {

  private ProgressDialog progressDialog;

  public void showProgressDialog(CharSequence message) {
    if (this.progressDialog == null) {
      this.progressDialog = new ProgressDialog(getActivity());
      this.progressDialog.setIndeterminate(true);
      this.progressDialog.setCancelable(Boolean.FALSE);
    }
    this.progressDialog.setIcon(R.drawable.ic_info_outline_24dp);
    this.progressDialog.setTitle(R.string.message_please_wait);
    this.progressDialog.setMessage(message);
    this.progressDialog.show();
  }

  public void dismissProgressDialog() {
    if (this.progressDialog != null) {
      this.progressDialog.dismiss();
    }
  }
}
