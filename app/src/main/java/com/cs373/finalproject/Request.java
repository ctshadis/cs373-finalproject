package com.cs373.finalproject;

public class Request extends android.app.Activity implements Comparable<Request> {

    private int arrivalTime, trackRequested, sectorRequested;
    private boolean complete;

    public Request(int arrivalTime, int trackRequested, int sectorRequested){
        this.arrivalTime = arrivalTime;
        this.trackRequested = trackRequested;
        this.sectorRequested = sectorRequested;
        this.complete = false;
    }

    public int getArrivalTime(){ return this.arrivalTime; }

    public int getTrackRequested(){ return this.trackRequested; }

    public int getSectorRequested(){ return this.sectorRequested; }

    public boolean getComplete(){ return this.complete; }

    public void setComplete(boolean val){ this.complete = val; }

    @Override
    public String toString(){
        String result = "[Request: Arrival time=" + getArrivalTime() + ", Track=" + getTrackRequested() + ", Sector=" + getSectorRequested() + "]";
        return result;
    }

    @Override
    public int compareTo(Request otherRequest) {
        return Integer.compare(getArrivalTime(), otherRequest.getArrivalTime());
    }
}

