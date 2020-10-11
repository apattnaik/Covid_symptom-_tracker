package com.example.assignment1mc;

import java.util.*;

public class SimpleMovingAverage {

    // queue used to store list so that we get the average
    private final Queue<Float> Dataset = new LinkedList<Float>();
    private final int period;
    private float sum;

    // constructor to initialize period
    public SimpleMovingAverage(int period)
    {
        this.period = period;
    }

    // function to add new data in the
    // list and update the sum so that
    // we get the new mean
    public void addData(float num)
    {
        sum += num;
        Dataset.add(num);

        // Updating size so that length
        // of data set should be equal
        // to period as a normal mean has
        if (Dataset.size() > period)
        {
            sum -= Dataset.remove();
        }
    }

    // function to calculate mean
    public float getMean()
    {
        return sum / period;
    }

    public static ArrayList<Float> simpleMovingAverage(ArrayList<Float> bitmapArray){

        //Assigning Period
        int per = 21;
        SimpleMovingAverage obj = new SimpleMovingAverage(per);

        ArrayList<Float> movingAvgArray= new ArrayList<>();
        Iterator<Float> i = bitmapArray.iterator();
        while(i.hasNext()){
            obj.addData(i.next());
            movingAvgArray.add(obj.getMean());
        }

        return movingAvgArray;
    }
}
