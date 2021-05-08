package com.cs373.finalproject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math.*;

public class FCFS extends android.app.Activity{
    private List<Request> reqList = new ArrayList<Request>();
    private final int NUM_SECTORS = 10;

    public FCFS(ArrayList<Request> reqList){
        this.reqList = reqList;
    }

    public double seekTime(int startTrack, int endTrack){
        if(startTrack < endTrack)
            return 12 + 0.1 * (endTrack - startTrack);
        else
            return 12 + 0.1 * (startTrack - endTrack);
    }

    public double searchTime(int startSector, int endSector){
        if(startSector > endSector)
            return .5 * ((NUM_SECTORS - startSector)+ endSector);
        else
            return .5* Math.abs(endSector - startSector);
    }

    public String[] runAlgorithm(){
        double currentTime = 0;
        int currentTrack = 0;
        int currentSector = 0;
        double offset = 0;
        double[] elapsedTimes = new double[reqList.size()];

        for(int i = 0; i < reqList.size(); i++){
            // calculate seek time using previous disk position
            // calculate rotational latency (.5 * num of sectors)
            // add 1.2 ms
            if(reqList.get(i).getArrivalTime() > currentTime){
                offset = reqList.get(i).getArrivalTime() - currentTime; // how long before it catches up
                currentTime = reqList.get(i).getArrivalTime();

            }
            double seekTime = seekTime(currentTrack, reqList.get(i).getTrackRequested());
            double searchTime = searchTime(currentSector, reqList.get(i).getSectorRequested());
            double transferTime = 1.2;
            double elapsed = seekTime + searchTime + transferTime + offset;
            elapsedTimes[i] = elapsed;
            currentTrack = reqList.get(i).getTrackRequested();
            currentSector = reqList.get(i).getSectorRequested();
            currentTime = currentTime + elapsed;
        }

        double averageTime;
        double sum = 0;
        for (int k = 0; k < elapsedTimes.length; k++){
            sum = sum + elapsedTimes[k];
        }

        averageTime = sum / elapsedTimes.length;
        for (int j = 0; j < elapsedTimes.length; j++){
            //System.out.println("Elapsed Time Request " + j +": " + elapsedTimes[j] + " ms");
        }

        double standardDeviation = 0;
        double standardSum = 0;
        for(int f = 0; f < elapsedTimes.length; f++){
            double error = elapsedTimes[f] - averageTime;
            double errorsquared = error * error;
            standardSum += errorsquared;
        }
        double variance = standardSum / (elapsedTimes.length - 1);
        standardDeviation = Math.sqrt(variance);

        String avg = String.format("%.2f", averageTime);
        String sd = String.format("%.2f", standardDeviation);
        String var = String.format("%.2f", variance);

        String[] result = new String[]{avg, sd, var};
        return result;
    }
}

