package com.cs373.finalproject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LOOK extends android.app.Activity{

    private List<Request> reqList = new ArrayList<Request>();
    private final int NUM_SECTORS = 10;


    public LOOK(ArrayList<Request> reqList){
        this.reqList = reqList;
        for(int i = 0; i < reqList.size(); i++){
            reqList.get(i).setComplete(false);
        }
    }

    public double seekTime(int startTrack, int endTrack){
        if(startTrack < endTrack)
            return 12 + 0.1 * (endTrack - startTrack);
        else
            return 12 + 0.1 * (startTrack - endTrack);
    }

    public double searchTime(int startSector, int endSector){
        return .5 * Math.abs(endSector - startSector);
    }

    public int[] getDistances(int currentTrack, double currentTime, boolean movingInward){
        int[] distances = new int[reqList.size()];
        for(int i = 0; i < reqList.size(); i++){
            if(reqList.get(i).getComplete()==false && reqList.get(i).getArrivalTime() <= currentTime) { //job is waiting
                //make sure direction is correct
                if((movingInward && (reqList.get(i).getTrackRequested() >= currentTrack)) || (!movingInward && (reqList.get(i).getTrackRequested() <= currentTrack)))
                    distances[i] = Math.abs(currentTrack - reqList.get(i).getTrackRequested());
                else distances[i]=22222; //wrong direction
            }
            else{
                distances[i] = 11111; // if the request is complete
            }
        }
        return distances;
    }

    public int findMin(int[] arr){
        int min = 0;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] < arr[min] && arr[i] != 11111 && arr[i] != 22222){ // if the element is smaller than the current
                min = i;
            }
        }
        return min;
    }

    public String[] runAlgorithm() {
        boolean movingInward = true; //moving toward HIGHER NUMBERS FOR TRACKS
        double currentTime = 0; // start of alg
        int currentTrack = 0; // start of alg
        int currentSector = 0; // start of alg
        double[] elapsedTimes = new double[reqList.size()];
        double[] seekTimes = new double[reqList.size()];
        double[] searchTimes = new double[reqList.size()];
        double[] transferTimes = new double[reqList.size()];
        //find shortest seek time
        int counter = 0;
        int end = reqList.size();
        while (counter < end) { //iterate through all requests
            int[] distances;
            int next_index;
            distances = getDistances(currentTrack, currentTime, movingInward);
            next_index = findMin(distances);
            if(distances[next_index] == 11111 || distances[next_index] == 22222){
                movingInward = !movingInward;
                distances = getDistances(currentTrack, currentTime, movingInward);
                next_index = findMin(distances);
            }

            double seekTime = seekTime(currentTrack, reqList.get(next_index).getTrackRequested());
            seekTimes[next_index] = seekTime;
            double searchTime = searchTime(currentSector, reqList.get(next_index).getSectorRequested());
            searchTimes[next_index] = searchTime;
            double transferTime = 1.2;
            transferTimes[next_index] = transferTime;
            double elapsed = seekTime + searchTime + transferTime;
            elapsedTimes[next_index] = elapsed;

            //update reqList
            reqList.get(next_index).setComplete(true);

            //update currents
            currentTime = currentTime + elapsed;
            currentTrack = reqList.get(next_index).getTrackRequested();
            currentSector = reqList.get(next_index).getSectorRequested();

            counter++;
        }

        double averageTime;
        double sum = 0;
        for (int k = 0; k < elapsedTimes.length; k++){
            sum = sum + elapsedTimes[k];
        }
        averageTime = sum / elapsedTimes.length;

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
