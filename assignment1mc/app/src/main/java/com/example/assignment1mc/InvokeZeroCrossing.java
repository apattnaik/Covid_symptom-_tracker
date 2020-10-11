package com.example.assignment1mc;

public class InvokeZeroCrossing {

    // Function that returns true if num is
    // greater than both arr[i] and arr[j]
    static boolean isPeak(Float arr[], int n, float num,
                          int i, int j)
    {

        // If num is smaller than the element
        // on the left (if exists)
        if (i >= 0 && arr[i] > num)
        {
            return false;
        }

        // If num is smaller than the element
        // on the right (if exists)
        if (j < n && arr[j] > num)
        {
            return false;
        }
        return true;
    }

    // Function that returns true if num is
    // smaller than both arr[i] and arr[j]
    static boolean isTrough(Float arr[], int n, float num,
                            int i, int j)
    {

        // If num is greater than the element
        // on the left (if exists)
        if (i >= 0 && arr[i] < num)
        {
            return false;
        }

        // If num is greater than the element
        // on the right (if exists)
        if (j < n && arr[j] < num)
        {
            return false;
        }
        return true;
    }

    static int peaksTroughs(Float arr[], int n)
    {
        int peakCount=0;
        int troughCount=0;

        // For every element
        for (int i = 0; i < n; i++)
        {

            // If the current element is a peak
            if (isPeak(arr, n, arr[i], i - 1, i + 1))
            {
                peakCount+=1;
            }
            else{
                // If the current element is a trough
                if (isTrough(arr, n, arr[i], i - 1, i + 1)) {
                    troughCount+=1;
                }
            }
        }


        return (troughCount+peakCount);

    }

    public static int invokeZeroCrossing(Float[] resultElement){
        return peaksTroughs(resultElement, resultElement.length);
    }
}



