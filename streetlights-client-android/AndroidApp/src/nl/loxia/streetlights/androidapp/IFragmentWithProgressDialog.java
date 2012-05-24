package nl.loxia.streetlights.androidapp;

import nl.loxia.streetlights.fragments.ICancelableAsyncTask;
import android.content.Context;

public interface IFragmentWithProgressDialog {

    public abstract void showProgressDialog(Context context, ICancelableAsyncTask task, String msg);

    public abstract void dismissProgressDialog();

}