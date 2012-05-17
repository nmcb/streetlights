package nl.loxia.streetlights.androidapp;

import android.app.ListActivity;
import android.content.Context;

public abstract class AbstractAsyncListActivity extends ListActivity implements IActivityWithProgressDialog {
    private final ProgressDialogHelper progressDialog = new ProgressDialogHelper();
    private boolean destroyed = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyed = true;
    }

    @Override
    public void showProgressDialog(Context context, String msg) {
        progressDialog.show(context, msg);
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss(destroyed);
    }
}
