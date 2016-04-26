package be.heinenbavo.adriunorobot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
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
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.Buffer;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements  ConnectThread.ConnectThreadListener ,StartFragment.OnFragmentInteractionListener{
    BluetoothAdapter myBluetooth;
    BluetoothDevice bluetoothDevice;
    ConnectedThread connectedThread;
    Set<BluetoothDevice> bluetoothDevices;
    DeviceAdapter adapter;
    private boolean isBtConnected = false;
    static UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getPairedDevices();
    }

    private void getPairedDevices(){
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if(myBluetooth == null){
            Toast.makeText(getApplicationContext(),"BLUETHOOTH NOT SUPPORTED",Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            if(!myBluetooth.isEnabled()){
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent,1);
            }
        }

        bluetoothDevices = myBluetooth.getBondedDevices();

        DeviceAdapter bluetoothDeviceArrayAdapter = new DeviceAdapter(this, android.R.layout.simple_spinner_dropdown_item);

        if(bluetoothDevices.size()!=0){

            for(BluetoothDevice device:bluetoothDevices){
                bluetoothDeviceArrayAdapter.add(device);
                Log.e("Bluethooth device",device.toString());
            }

            StartFragment startFragment  = new StartFragment();

            startFragment.setDeviceAdapter(bluetoothDeviceArrayAdapter);
            startFragment.setmListener(this);
            FragmentManager manager = getFragmentManager();

            FragmentTransaction fragmentTx = manager.beginTransaction();

            // The fragment will have the ID of Resource.Id.fragment_container.
            fragmentTx.replace(R.id.fragmentholder, startFragment);

            // Commit the transaction.
            fragmentTx.commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            Toast.makeText(getApplicationContext(),"ENABLE BLUETHOOTH",Toast.LENGTH_SHORT).show();
            finish();
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

    @Override
    public void manageConnectedSocket(BluetoothSocket mmSocket) {
        Toast.makeText(getApplicationContext(),"CONNECTED",Toast.LENGTH_SHORT).show();
        connectedThread= new ConnectedThread(mmSocket);

        ControllFragment fragment = new ControllFragment();
        fragment.setConnectedThread(connectedThread);

        FragmentManager manager = getFragmentManager();

        FragmentTransaction fragmentTx = manager.beginTransaction();

        // The fragment will have the ID of Resource.Id.fragment_container.
        fragmentTx.replace(R.id.fragmentholder, fragment);

        // Commit the transaction.
        fragmentTx.commit();
    }

    @Override
    public void CouldNotConnectToSocket() {
        Toast.makeText(getApplicationContext(),"COULD NOT CONNECT",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ItemSelected(int position) {

        bluetoothDevice = (BluetoothDevice) bluetoothDevices.toArray()[position];
        Log.e("bd selected", bluetoothDevice.getName().toString());
        ConnectThread connectThread = new ConnectThread(this, bluetoothDevice,applicationUUID, myBluetooth);
        connectThread.run();
    }
}
