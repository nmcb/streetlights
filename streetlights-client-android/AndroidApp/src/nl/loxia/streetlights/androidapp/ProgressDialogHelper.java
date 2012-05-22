package nl.loxia.streetlights.androidapp;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogHelper {
    public ProgressDialog progressDialog;

    public void show(Context context, String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }

        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public void dismiss(boolean destroyed) {
        if (progressDialog != null && !destroyed) {
            progressDialog.dismiss();
        }
    }
}