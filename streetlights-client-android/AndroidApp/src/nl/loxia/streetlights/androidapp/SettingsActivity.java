/*
 **Created By: MBlokhuijzen
 **Date: May 22, 2012, 9:18:12 PM
 */

package nl.loxia.streetlights.androidapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

public class SettingsActivity extends Activity {
    Context context;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu);
    }
    
    //TODO use this method to check for a valid IP, problem is that the IP Address is not set until the Fragment goes into a pause state. 
    //      So you will always get a NullPointerException if you check before that
    public boolean validIpAddress(String ipAddress) {
        String[] ipValues = ipAddress.split(".");
        List<Integer> ipNumbers = new ArrayList<Integer>();
        boolean validIp = true;
        
        for(String ips : ipValues) {
            ipNumbers.add(Integer.parseInt(ips));
        }
        
        for(int ipNumber : ipNumbers) {
            if(ipNumber > 255) {
                validIp = false;
                Toast.makeText(context, "Not a valid IP Address", Toast.LENGTH_LONG);
                break;
            }
            else {
                //Do nothing, the value is valid.
            }
        }
        return validIp;     
    }
}
