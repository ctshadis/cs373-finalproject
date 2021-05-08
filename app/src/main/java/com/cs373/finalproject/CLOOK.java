package com.cs373.finalproject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CLOOK extends android.app.Activity{

    private List<Request> reqList = new ArrayList<Request>();
    private final int NUM_SECTORS = 10;


    public CLOOK(ArrayList<Request> reqList){
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

    public int[] getDistances(int currentTrack, double currentTime){
        int[] distances = new int[reqList.size()];
        for(int i = 0; i < reqList.size(); i++){
            if(reqList.get(i).getComplete()==false && reqList.get(i).getArrivalTime() <= currentTime) { //job is waiting

                if(((reqList.get(i).getTrackRequested() >= currentTrack)))
                    distances[i] = (reqList.get(i).getTrackRequested() - currentTrack);
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

    public int checkForMatch(int currentTrack, double currentTime){
        int result = -1;
        for(int i = 0; i < reqList.size(); i++){
            if(currentTrack == reqList.get(i).getTrackRequested() && currentTime >= reqList.get(i).getArrivalTime() && !reqList.get(i).getComplete()){
                result = i;
            }
        }
        return result;
    }

    public String[] runAlgorithm() {

        double currentTime = 0;
        double currentSeekTime = 0;
        int currentTrack = 0;
        int currentSector = 0;

        double[] elapsedTimes = new double[reqList.size()];
        double[] endTimes = new double[reqList.size()];
        double[] seekTimes = new double[reqList.size()];
        double[] searchTimes = new double[reqList.size()];
        double[] filled = new double[reqList.size()];

        int[] distances;
        int reqsComplete = 0;

        //Loop until all complete
        while(reqsComplete < reqList.size()){
            int match = checkForMatch(currentTrack, currentTime); //returns index in reqList of match -- -1 if none
            if(match >= 0){ //match found
                currentTime = currentTime + 12;
                currentSeekTime = currentSeekTime + 12;
                seekTimes[match] = currentSeekTime;
                currentTime = currentTime + searchTime(currentSector, reqList.get(match).getSectorRequested());
                searchTimes[match] = searchTime(currentSector, reqList.get(match).getSectorRequested());
                currentTime = currentTime + 1.2;
                endTimes[reqsComplete] = currentTime;
                filled[reqsComplete] = reqList.get(match).getTrackRequested();
                reqList.get(match).setComplete(true);
                currentSeekTime = 0; //reset seek timer
                reqsComplete++;

                currentTrack = reqList.get(match).getTrackRequested();
                currentSector = reqList.get(match).getSectorRequested();
            }
            else{ // no match
                currentTime += 0.1;
                currentSeekTime += 0.1;
                currentTrack += 1;

                distances = getDistances(currentTrack, currentTime);
                int next_index = findMin(distances);
                if(distances[next_index] == 11111 || distances[next_index] == 22222){// if the only distances are 11111 (complete) or 22222 (other direction)
                    currentTrack = 0;
                    distances = getDistances(currentTrack, currentTime);
                    next_index = findMin(distances);
                    currentTrack = reqList.get(next_index).getTrackRequested();
                }
            }
        }


        for(int i = endTimes.length-1; i > 0; i--){
            elapsedTimes[i] = endTimes[i] - endTimes[i-1];
        }
        elapsedTimes[0] = endTimes[0] - 0;

        double averageTime = 0;
        double sum = 0;
        for(int i = 0; i < reqList.size(); i++){
            sum += elapsedTimes[i];
        }
        averageTime = sum / reqList.size();

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
