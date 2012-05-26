/*
 **Created By: MBlokhuijzen
 **Date: May 22, 2012, 9:18:12 PM
 */

package nl.loxia.streetlights.androidapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;

public class SettingsActivity extends Activity {
    public static final String PREFS_NAME = "MyPreferences";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu);
        
        // Getting IP Address
//        WifiManager myWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
//        int ipAddressInt = myWifiInfo.getIpAddress();
//
//        ipAddress = convertIpAddressToString(ipAddressInt);
      
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {   
//            ipAddress = getFragmentManager().findFragmentById(R.id.ipAddressEdit).toString();
//            
//            portNumber = getFragmentManager().findFragmentById(R.id.portNumberEdit).toString();
//            
//            SharedPreferences.Editor prefsEditor = settings.edit(); 
//            prefsEditor.putString("ip_address", "http://" + ipAddress.substring(0, ipAddress.lastIndexOf(".") + 1)
//                    + ipAddressText.getText().toString());
//            prefsEditor.putString("port_number", ":" +portNumberText.getText().toString());
//            prefsEditor.commit();

//            Log.d(this.getClass().getName(), "IP Address: " +settings.getString("ip_address", null)+ ", PortNumber: " +settings.getString("port_number", null));
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Use this method to convert the WifiInfo.getIpAddress() to a readable IP Address
     * 
     * @param ipAddress
     *            you received from WifiInfo()
     * @return IP Address
     */
//    private String convertIpAddressToString(int ipAddress) {
//        return String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
//                (ipAddress >> 24 & 0xff));
//    }  
}
