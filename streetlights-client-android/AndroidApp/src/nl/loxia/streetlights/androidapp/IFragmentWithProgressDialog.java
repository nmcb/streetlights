package nl.loxia.streetlights.androidapp;

import android.content.Context;

public interface IFragmentWithProgressDialog {

    public abstract void showProgressDialog(Context context, String msg);

    public abstract void dismissProgressDialog();

}