package com.example.assignment1mc;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

public class RespiratoryRate {

    public static float processing() throws IOException {

        int FRAME_WINDOW=5;
        float sum = (float) 0.0;
        int beats=10;

//        @SuppressLint("SdCardPath") String uri = "/sdcard/CSVBreathe27V1.csv";//174, 14.5
        @SuppressLint("SdCardPath") String uri = "/sdcard/CSVBreathe44.csv"; // 161, 13
//        @SuppressLint("SdCardPath") String uri = "/sdcard/CSVBreathe19.csv"; //176, 14.66
//          File file= new File (uri);
//        File dir = Environment.getExternalStorageDirectory();
//        File yourFile = new File(dir, "/sdcard/CSVBreathe19.csv");
        Log.d(TAG, "INSIDE RESP_RATE");

        FileReader reader= new FileReader(uri);
        BufferedReader br = new BufferedReader(reader);
        ArrayList<Float> data= new ArrayList<>();
        String line;
        while((line=br.readLine()) != null){
            data.add(Float.parseFloat(line));
        }

        ArrayList<Float> movingAvgArray = SimpleMovingAverage.simpleMovingAverage(data);

        Float[] simpleMovingAvgArray = new Float[movingAvgArray.size()];
        simpleMovingAvgArray = movingAvgArray.toArray(simpleMovingAvgArray);

        ArrayList<Float[]> result = new ArrayList<>();

        for (int frame = 0; frame <= simpleMovingAvgArray.length - FRAME_WINDOW; frame += FRAME_WINDOW) {
            Float[] newArray = Arrays.copyOfRange(simpleMovingAvgArray, frame, frame + FRAME_WINDOW);
            result.add(newArray);
        }

        ArrayList<Integer> zc = new ArrayList<>();

        for (int i = 0; i < result.size(); i++) {
            int zeroCrossings = InvokeZeroCrossing.invokeZeroCrossing(result.get(i));
            zc.add(zeroCrossings);
        }

        for (int i = 0; i < zc.size(); i++) {
            sum += zc.get(i);
        }

        sum /= 2;
//        float rr = (sum / zc.size()) * 12;
        float rr = (sum / zc.size());
        return  rr * beats;



    }
}
