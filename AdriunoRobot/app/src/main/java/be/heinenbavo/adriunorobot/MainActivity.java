package be.heinenbavo.adriunorobot;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Button btnSend;
    private EditText edtText;

    private static final int DISCOVER_DURATION = 300;
    private static final int REQUEST_BLU = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSend = (Button) findViewById(R.id.btnSend);
        edtText = (EditText) findViewById(R.id.edtText);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendViaBluetooth();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void sendViaBluetooth() {

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not supported.", Toast.LENGTH_LONG);
        }
        else {
            //Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);

            //enableBtIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVER_DURATION);

            Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();

            if (devices.size() != 0) {

                for (BluetoothDevice device : devices){

                    if (device.getName().equals("HC-06")){
                        sendDataToPairedDevice(edtText.getText().toString(), device);
                        break;
                    }
                }
            }
            //startActivityForResult(enableBtIntent, REQUEST_BLU);
        }
    }

    private void sendDataToPairedDevice(String message ,BluetoothDevice device) {

        byte[] toSend = message.getBytes();
        try {
            TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            String uuid = tManager.getDeviceId();

            UUID applicationUUID = UUID.fromString(uuid);
            BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord(applicationUUID);
            OutputStream mmOutStream = socket.getOutputStream();
            mmOutStream.write(toSend);
            // Your Data is sent to  BT connected paired device ENJOY.
        } catch (IOException e) {
            Log.e("", "Exception during write", e);
        }
        catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", ex.toString());
        }
    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data){

        if (resultcode == DISCOVER_DURATION && requestcode == REQUEST_BLU){

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");

            File file = new File(Environment.getExternalStorageDirectory(), "plop.txt");
            intent.putExtra(Intent.EXTRA_STREAM, edtText.getText().toString());

            PackageManager pm = getPackageManager();
            List<ResolveInfo> list = pm.queryIntentActivities(intent, 0);

            if (list.size() > 0){
                String packagename = null;
                String classname = null;
                boolean found = false;

                for (ResolveInfo info : list){
                    packagename = info.activityInfo.packageName;

                    if (packagename.equals("com.android.bluetooth")){

                        classname = info.activityInfo.name;

                        found = true;
                        break;
                    }
                }

                if (!found) {
                    Toast.makeText(this, "Bluetooth haven't been found.", Toast.LENGTH_LONG);
                }
                else {
                    //sendDataToPairedDevice(edtText.getText().toString() ,bluetoothDevice);
                    intent.setClassName(packagename, classname);
                    startActivity(intent);
                }
            }
            else {
                Toast.makeText(this, "Bleutooth is canceled", Toast.LENGTH_LONG);
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
