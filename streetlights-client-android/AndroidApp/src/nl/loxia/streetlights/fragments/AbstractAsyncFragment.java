package nl.loxia.streetlights.fragments;

import nl.loxia.streetlights.androidapp.IFragmentWithProgressDialog;
import nl.loxia.streetlights.androidapp.ProgressDialogHelper;
import android.app.Fragment;
import android.content.Context;

// Stupid java for not having multiple inheritance of mix-in :(
public abstract class AbstractAsyncFragment extends Fragment implements IFragmentWithProgressDialog {
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
