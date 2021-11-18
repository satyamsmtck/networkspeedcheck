package com.satyam.createnetworkspeedchecklib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.satyam.mynetworkspeedcheck.ITrafficSpeedListener;
import com.satyam.mynetworkspeedcheck.TrafficSpeedMeasurer;
import com.satyam.mynetworkspeedcheck.Utils;

public class MainActivity extends AppCompatActivity {

    private static final boolean SHOW_SPEED_IN_BITS = false;

    private TrafficSpeedMeasurer mTrafficSpeedMeasurer;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.text);

        mTrafficSpeedMeasurer = new TrafficSpeedMeasurer(TrafficSpeedMeasurer.TrafficType.ALL);
        mTrafficSpeedMeasurer.startMeasuring();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTrafficSpeedMeasurer.stopMeasuring();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTrafficSpeedMeasurer.removeListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTrafficSpeedMeasurer.registerListener(mStreamSpeedListener);
    }

    private ITrafficSpeedListener mStreamSpeedListener = new ITrafficSpeedListener() {

        @Override
        public void onTrafficSpeedMeasured(final double upStream, final double downStream) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String upStreamSpeed = Utils.parseSpeed(upStream, SHOW_SPEED_IN_BITS);
                    String downStreamSpeed = Utils.parseSpeed(downStream, SHOW_SPEED_IN_BITS);
                    mTextView.setText("Up Stream Speed: " + upStreamSpeed + "\n" + "Down Stream Speed: " + downStreamSpeed);
                }
            });
        }
    };

}