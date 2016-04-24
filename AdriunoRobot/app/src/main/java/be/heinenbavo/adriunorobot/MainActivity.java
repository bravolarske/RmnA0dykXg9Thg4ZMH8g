package be.heinenbavo.adriunorobot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.io.OutputStreamWriter;
import java.nio.Buffer;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Button btnSend, btnConnect;
    private EditText edtText;
    String address = null;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private static final int DISCOVER_DURATION = 300;
    private static final int REQUEST_BLU = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnConnect = (Button) findViewById(R.id.btnConnect);
        btnSend = (Button) findViewById(R.id.btnSend);
        edtText = (EditText) findViewById(R.id.edtText);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendViaBluetooth();
            }
        });

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Set<BluetoothDevice> devices = myBluetooth.getBondedDevices();

                if (devices.size() != 0) {

                    for (BluetoothDevice device : devices){

//                        if (device.getName().equals("HC-06")){
//                            sendDataToPairedDevice(edtText.getText().toString(), device);
//
//                            t
//                            myBluetooth = BluetoothAdapter.getDefaultAdapter();
//                            BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(device.getAddress());
//                            btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(applicationUUID);//create a RFCOMM (SPP) connection
//                            BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
//                            btSocket.connect();
//                            break;
//                        }
                    }
                }


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
//wordt uitgevoerd
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not supported.", Toast.LENGTH_LONG);
        }
        else {

            Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();

            if (devices.size() != 0) {

                for (BluetoothDevice device : devices){

                    if (device.getName().equals("HC-06")){
                        sendDataToPairedDevice(edtText.getText().toString(), device);
                        break;
                    }
                }
            }
        }
    }

    private void sendDataToPairedDevice(String message ,BluetoothDevice device) {

        try {
            myBluetooth = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(device.getAddress());
            btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(applicationUUID);//create a RFCOMM (SPP) connection
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
            btSocket.connect();

            btSocket.getOutputStream().write(message.getBytes());

        } catch (IOException e) {
            Log.e("", "Exception during write", e);
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
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
