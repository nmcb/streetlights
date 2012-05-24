/*
 **Created By: MBlokhuijzen
 **Date: May 22, 2012, 9:18:12 PM
 */

package nl.loxia.streetlights.androidapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends Activity {
    public static final String PREFS_NAME = "MyPreferences";
    SharedPreferences settings;
    EditText ipAddressText;
    String ipAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu);

        settings = getSharedPreferences(PREFS_NAME, 0);

        // Getting IP Address
        WifiManager myWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
        int ipAddressInt = myWifiInfo.getIpAddress();

        ipAddress = convertIpAddressToString(ipAddressInt);

        // Set TextView to current IP Address
        TextView tv = (TextView) findViewById(R.id.ipAddress);
        tv.setText(ipAddress.substring(0, ipAddress.lastIndexOf(".") + 1));

        ipAddressText = (EditText) findViewById(R.id.ipAddressEdit);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            SharedPreferences.Editor prefsEditor = settings.edit();
            prefsEditor.putString("ip_address", ipAddress.substring(0, ipAddress.lastIndexOf(".") + 1)
                    + ipAddressText.getText().toString());
            prefsEditor.commit();
            Log.d(this.getClass().getName(), "back button pressed");
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
    private String convertIpAddressToString(int ipAddress) {
        return String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));
    }

}
