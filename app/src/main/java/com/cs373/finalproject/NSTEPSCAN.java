package com.cs373.finalproject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class NSTEPSCAN extends android.app.Activity{

    private List<Request> reqList = new ArrayList<Request>();
    private final int NUM_SECTORS = 10;
    private final int NUM_TRACKS = 256;


    public NSTEPSCAN(ArrayList<Request> reqList){
        this.reqList = reqList;
        for(int i = 0; i < reqList.size(); i++){
            reqList.get(i).setComplete(false);
        }
    }


    public double searchTime(int startSector, int endSector){
        return .5 * Math.abs(endSector - startSector);
    }



    public String[] runAlgorithm() {
        // it's like an elevator
        // move a track, it takes 0.1 ms. Constant time of 12 every time a request is serviced
        // cycle between 0 and 255
        // NEW REQUESTS HELD WHEN ARM STARTS MOVING
        int currentTrack = 0;
        double currentTime = 0;
        int currentSector = 0;
        double timeCutOff = 0;
        boolean movingInward = true; //toward higher-numbered tracks
        boolean exit = false;
        double[] endTimes = new double[reqList.size()];
        int [] filled = new int[reqList.size()];
        double[] seekTimes = new double[reqList.size()];
        double[] searchTimes = new double[reqList.size()];
        double currentSeek=0;
        int count = 0; // how many complete
        while (!exit) {


            for(int i = 0; i < reqList.size(); i++){
                if((reqList.get(i).getTrackRequested() == currentTrack) && (reqList.get(i).getArrivalTime() <= timeCutOff) && (reqList.get(i).getComplete() == false)){
                    currentTime += 12 + searchTime(currentSector,reqList.get(i).getSectorRequested()) + 1.2;
                    searchTimes[count] = searchTime(currentSector,reqList.get(i).getSectorRequested());
                    currentSeek += 12;
                    seekTimes[count] = currentSeek;
                    currentSeek = 0;
                    endTimes[count] = currentTime;
                    filled[count] = reqList.get(i).getTrackRequested();
                    reqList.get(i).setComplete(true);
                    count++;
                    currentSector = reqList.get(i).getSectorRequested();
                }
            }

            if (movingInward) {
                currentTrack += 1;
                currentTime += 0.1;
                currentSeek += 0.1;
            } else if (!movingInward) {
                currentTrack -= 1;
                currentTime += 0.1;
                currentSeek += 0.1;
            }

            //reverse directions at boundaries
            if (currentTrack == 0 || currentTrack == 255) { //reverse directions
                movingInward = !movingInward;
                timeCutOff = currentTime;
            }

            if(count == reqList.size()){ // are we done?
                exit = true;
            }

        }
        double[] elapsedTimes = new double[reqList.size()];
        for(int i = endTimes.length-1; i > 0; i--){
            elapsedTimes[i] = endTimes[i] - endTimes[i-1];

        }
        elapsedTimes[0] = endTimes[0] - 0;
        double averageTime = 0;
        double sum = 0;
        for(int k = 0; k < endTimes.length; k++){
            sum += elapsedTimes[k];
        }
        averageTime = sum / endTimes.length;

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
