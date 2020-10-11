package com.example.assignment1mc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;

import static android.widget.Toast.makeText;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity";

    private static final int VIDEO_CAPTURE = 101;
    private Uri fileUri;
    private SensorManager sensorManager;
    private int RECORD_TIME=45;
    Sensor accelerometer;
    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Initializing Sensor Services");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: Registered accelerometer listener");

        Button symptomButton = (Button)findViewById(R.id.btnsymptoms);
        symptomButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent= new Intent(getApplicationContext(), Main2ActivityNew.class);
                startActivity(intent);
            }

        });


        Button bt1 = (Button) findViewById(R.id.button);
        if(!hasCamera()){
            bt1.setEnabled(false);
        }
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecording();
            }
        });

        final TextView mainTextView= (TextView) findViewById(R.id.textViewMain);

        dbHelper = new DbHelper(this);

        Button buttonHR = (Button) findViewById(R.id.buttonMeasureHR);
        buttonHR.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                makeText(getApplicationContext(),"Calculating Heart Rate... ",Toast.LENGTH_SHORT).show();
                float hr=VideoProcessing.processing();
                dbHelper.addHR((int) hr);
                Log.d(TAG, "HEART_RATE= "+ hr);
                mainTextView.setText("HEART_RATE= "+ hr+" is added to the database");
            }

        });

        Button buttonRR = (Button) findViewById(R.id.buttonRR);
        buttonRR.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                makeText(getApplicationContext(),"Calculating Respiratory Rate... ",Toast.LENGTH_SHORT).show();
                float rr= 0;
                try {
                    rr = RespiratoryRate.processing();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dbHelper.addRR((int) rr);
                Log.d(TAG, "RESPIRATORY_RATE= "+ rr);
                mainTextView.setText("RESPIRATORY_RATE= "+ rr+" is added to the database");
                dbHelper.close();
            }

        });



    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @SuppressLint("ShowToast")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d(TAG, "onSensorChanged: X: "+ sensorEvent.values[0] + " ,Y: " + sensorEvent.values[1]+ " ,Z: "+sensorEvent.values[2]);

    }

    public void startRecording()
    {
        File mediaFile = new
                File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/newVideo.mp4");


        Log.d(TAG, "ACTION_VIDEO_CAPTURED starting to initialize");
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        Log.d(TAG, "ACTION_VIDEO_CAPTURED initialized");
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,5);
        Log.d(TAG, "EXTRA_DURATION_LIMIT");
        fileUri = FileProvider.getUriForFile(
                MainActivity.this,
                "com.example.assignment1mc.provider", mediaFile);
//         fileUri = Uri.fromFile(mediaFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, VIDEO_CAPTURE);
    }

    private boolean hasCamera() {
        if (getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_ANY)){
            return true;
        } else {
            return false;
        }
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        if (requestCode == VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                makeText(this, "Video has been saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                makeText(this, "Video saved",
                        Toast.LENGTH_LONG).show();
            } else {
                makeText(this, "Failed to record video",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
