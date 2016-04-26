package be.heinenbavo.adriunorobot;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ControllFragment extends Fragment {
    public void setConnectedThread(ConnectedThread connectedThread) {
        this.connectedThread = connectedThread;
    }

    boolean autonoom=false;
    private ConnectedThread connectedThread;
    public ControllFragment() {
        // Required empty public constructor
    }

    private Button btnUp,btnDown,btnLeft,btnRight,btnState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_controll, container, false);
        btnDown = (Button) v.findViewById(R.id.btnDown);
        btnUp = (Button) v.findViewById(R.id.btnUp);
        btnLeft = (Button) v.findViewById(R.id.btnLeft);
        btnRight = (Button) v.findViewById(R.id.btnRight);

        btnState = (Button) v.findViewById(R.id.btnState);

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             connectedThread.write("s".getBytes());
            }

        });
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectedThread.write("s".getBytes());
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectedThread.write("q".getBytes());
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectedThread.write("d".getBytes());
            }
        });

        btnState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(autonoom==false){
                    btnState.setText("AUTONOOM");
                    connectedThread.write("o".getBytes());
                    autonoom=true;
                }
                else{
                    btnState.setText("MANUAL");
                    connectedThread.write("m".getBytes());
                    autonoom=false;
                }
            }
        });
        if(autonoom==false){
            btnState.setText("AUTONOOM");
        }
        else{
            btnState.setText("MANUAL");
        }
        return v;
    }
}
