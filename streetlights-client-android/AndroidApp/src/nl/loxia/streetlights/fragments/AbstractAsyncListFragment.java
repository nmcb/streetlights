package nl.loxia.streetlights.fragments;

import nl.loxia.streetlights.androidapp.IFragmentWithProgressDialog;
import nl.loxia.streetlights.androidapp.ProgressDialogHelper;
import android.app.ListFragment;
import android.content.Context;

public abstract class AbstractAsyncListFragment extends ListFragment implements IFragmentWithProgressDialog {
    private final ProgressDialogHelper progressDialog = new ProgressDialogHelper();
    private boolean destroyed = false;

    @Override
    public void onDestroy() {
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
