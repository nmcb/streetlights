/*
**Created By: MBlokhuijzen
**Date: May 25, 2012, 9:17:43 AM
*/

package nl.loxia.streetlights.fragments;


import nl.loxia.streetlights.androidapp.R;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class SettingsFragment extends Fragment {
    SharedPreferences settings;
    private Activity activity;
    Context context;
    ListRoadsFragment listRoadsFragment;
    
    EditText ipAddressText;
    EditText portNumberText;
    
    String ipAddress;
    String portNumber;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = getActivity();
    }
    
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        
        //TextView ipAddressTextView = (TextView)view.findViewById(R.id.ipAddress);
        //ipAddressTextView.setText(ipAddress.substring(0, ipAddress.lastIndexOf(".") + 1));
        
//        ipAddressText = (EditText)getActivity().findViewById(R.id.ipAddressEdit);
//        portNumberText = (EditText)getActivity().findViewById(R.id.portNumberEdit);
        
//        ipAddress = ipAddressText.getText().toString();
//        portNumber = portNumberText.getText().toString();
        
        return view;
    }
}
