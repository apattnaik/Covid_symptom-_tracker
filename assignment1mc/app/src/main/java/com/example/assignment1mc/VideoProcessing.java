package com.example.assignment1mc;


import android.graphics.Bitmap;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.media.MediaMetadataRetriever;
import java.util.ArrayList;
import java.util.Arrays;

public class VideoProcessing {

    public static float processing() {

        @SuppressLint("SdCardPath") String uri = "/sdcard/FingertipVideo.mp4";
        ArrayList<Float> bitmapArray = new ArrayList<>();
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(uri);
        int fps = 30000000;
        int frameRate = fps;
        int frameCount = 0;
        float sum = (float) 0.0;
        int pixel;
        int FRAME_WINDOW = 5;
        int beats = 10;
        int MAX_FRAMES = 20;
        Log.d("", "<<<<FRAMES BEING EXTRACTED FROM UPLOADED VIDEO>>>>!");

        while (frameCount <= MAX_FRAMES) {
            Bitmap bmFrame = mediaMetadataRetriever.getFrameAtTime(frameRate, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            if (bmFrame == null)
                break;
            frameRate += 1000000;

            //extracting the red channel from bitmap frames
            for (int x = 0; x < bmFrame.getWidth(); x++) {
                for (int y = 0; y < bmFrame.getHeight(); y++) {
                    pixel = bmFrame.getPixel(x, y);
                    int redValue = Color.red(pixel);
                    sum += redValue;
                }
            }

            bitmapArray.add(sum / (bmFrame.getWidth() * bmFrame.getHeight()));
            sum = 0;
            frameCount++;

            Log.d("", "FRAME EXTRACTION COMPLETE!");
        }

        ArrayList<Float> movingAvgArray = SimpleMovingAverage.simpleMovingAverage(bitmapArray);

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

        sum /= 4;
        float heartRate = (sum / zc.size()) * 12;
        return  heartRate * beats;

    }
}
