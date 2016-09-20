package rks.gentrexha.waiwtw;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

// References:
// http://stackoverflow.com/a/27008913/3841083

@SuppressWarnings("unused")
public class mainActivity extends AppCompatActivity
{
    private int locationRequestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLocationService();
        checkGPSStatus();
    }

    public void btnMoreOnClick(View v)
    {
        TextView txvLocation = (TextView) findViewById(R.id.txvLocationService);
        Intent intMap = new Intent(this, mapActivity.class);
        // Asks for permission to use location services
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, locationRequestCode);
            // locationRequestCode is an app-defined int constant. The callback method gets the result of the request.
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            startActivity(intMap);
        else
        {
            txvLocation.setText(R.string.enableLocation);
        }
    }

    public void btnHistoryOnClick(View v)
    {
        Intent intHistory = new Intent(this, historyActivity.class);
        startActivity(intHistory);
    }

    private void checkLocationService()
    {
        // Asks for permission to use location services
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, locationRequestCode);
            // locationRequestCode is an app-defined int constant. The callback method gets the result of the request.
        }
    }

    private void checkGPSStatus()
    {
        boolean gps_enabled = false;
        boolean network_enabled = false;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try
        {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        catch (Exception ignored)
        {
        }
        try
        {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }
        catch (Exception ignored)
        {
        }
        if ( !gps_enabled && !network_enabled )
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(mainActivity.this);
            dialog.setMessage("Your GPS is disabled, for this application to work it has to be enabled!");
            dialog.setPositiveButton("Enable", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    // This will navigate user to the device location settings screen
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            AlertDialog alert = dialog.create();
            alert.show();
        }
    }
}
