package nl.loxia.streetlights.androidapp;

import android.app.Activity;
import android.content.Context;

// Stupid java for not having multiple inheritance of mix-in :(
public abstract class AbstractAsyncActivity extends Activity implements IActivityWithProgressDialog {
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
