package nl.loxia.streetlights.androidapp;

import nl.loxia.streetlights.fragments.ICancelableAsyncTask;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class ProgressDialogHelper {
    private ProgressDialog progressDialog;

    public void show(Context context, final ICancelableAsyncTask task, String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    task.cancel();
                }
            });
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