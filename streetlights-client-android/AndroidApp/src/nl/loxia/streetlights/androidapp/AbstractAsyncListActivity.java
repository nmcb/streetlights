package nl.loxia.streetlights.androidapp;

import android.app.ListActivity;
import android.app.ProgressDialog;

public abstract class AbstractAsyncListActivity extends ListActivity {
    private ProgressDialog progressDialog;
    private boolean destroyed = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyed = true;
    }

    public void showProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
        }

        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && !destroyed) {
            progressDialog.dismiss();
        }
    }
}
