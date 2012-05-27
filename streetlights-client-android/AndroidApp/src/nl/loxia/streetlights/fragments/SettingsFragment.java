/*
**Created By: MBlokhuijzen
**Date: May 25, 2012, 9:17:43 AM
*/

package nl.loxia.streetlights.fragments;


import nl.loxia.streetlights.androidapp.R;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class SettingsFragment extends Fragment {
    private Activity activity;
    Context context;

    String ipAddress;
    String portNumber;
    String wifiIp;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = getActivity();
    }
   
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        
        return view;
    }
    
    @Override
    public void onPause() {
        super.onPause();
        
        EditText ipAddressText = (EditText)activity.findViewById(R.id.ipAddressEdit);
        EditText portNumberText = (EditText)activity.findViewById(R.id.portNumberEdit);
        
        ipAddress = ipAddressText.getText().toString();
        portNumber = portNumberText.getText().toString();
        

        Settings.saveSetting(getActivity(), "ip_address", "http://"+ipAddress);
        Settings.saveSetting(activity, "port_number", ":"+portNumber);
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
}
