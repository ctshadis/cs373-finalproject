package com.cs373.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

public class MainActivity extends AppCompatActivity {
    private int num_requests = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Slider slider = (Slider) findViewById(R.id.slider1);

        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(Slider slider, float value, boolean fromUser) {
                num_requests = (int) value;
            }
        });
    }

    public void defaultRequests(View v){
        ArrayList<Request> reqList = new ArrayList<Request>();

        Request defrequest1 = new Request(0, 50, 9);
        Request defrequest2 = new Request(2, 135, 6);
        Request defrequest3 = new Request(3, 20, 2);
        Request defrequest4 = new Request(4, 25, 1);
        Request defrequest5 = new Request(8, 200, 9);
        Request defrequest6 = new Request(17, 175, 5);
        Request defrequest7 = new Request(18, 180, 3);
        Request defrequest8 = new Request(20, 99, 4);
        Request defrequest9 = new Request(29, 73, 5);
        Request defrequest10 = new Request(82, 199, 9);

        reqList.add(defrequest1);
        reqList.add(defrequest2);
        reqList.add(defrequest3);
        reqList.add(defrequest4);
        reqList.add(defrequest5);
        reqList.add(defrequest6);
        reqList.add(defrequest7);
        reqList.add(defrequest8);
        reqList.add(defrequest9);
        reqList.add(defrequest10);

        fill(reqList);
    }

    public void fill(ArrayList<Request> reqList) {
        FCFS fcfs = new FCFS(reqList);
        String[] fcfsresults = fcfs.runAlgorithm();
        String fcfsavg = fcfsresults[0];
        String fcfssd = fcfsresults[1];
        String fcfsvar = fcfsresults[2];

        SSTF sstf = new SSTF(reqList);
        String[] sstfresults = sstf.runAlgorithm();
        String sstfavg = sstfresults[0];
        String sstfsd = sstfresults[1];
        String sstfvar = sstfresults[2];

        SCAN scan = new SCAN(reqList);
        String[] scanresults = scan.runAlgorithm();
        String scanavg = scanresults[0];
        String scansd = scanresults[1];
        String scanvar = scanresults[2];

        LOOK look = new LOOK(reqList);
        String[] lookresults = look.runAlgorithm();
        String lookavg = lookresults[0];
        String looksd = lookresults[1];
        String lookvar = lookresults[2];

        CSCAN cscan = new CSCAN(reqList);
        String[] cscanresults = cscan.runAlgorithm();
        String cscanavg = cscanresults[0];
        String cscansd = cscanresults[1];
        String cscanvar = cscanresults[2];

        CLOOK clook = new CLOOK(reqList);
        String[] clookresults = clook.runAlgorithm();
        String clookavg = clookresults[0];
        String clooksd = clookresults[1];
        String clookvar = clookresults[2];

        NSTEPSCAN nstep = new NSTEPSCAN(reqList);
        String[] nstepresults = nstep.runAlgorithm();
        String nstepavg = nstepresults[0];
        String nstepsd = nstepresults[1];
        String nstepvar = nstepresults[2];

        TextView fcfsavgoutputbox = (TextView) findViewById(R.id.fcfsavgoutput);
        TextView sstfavgoutputbox = (TextView) findViewById(R.id.sstfavgoutput);
        TextView scanavgoutputbox = (TextView) findViewById(R.id.scanavgoutput);
        TextView lookavgoutputbox = (TextView) findViewById(R.id.lookavgoutput);
        TextView clookavgoutputbox = (TextView) findViewById(R.id.clookavgoutput);
        TextView cscanavgoutputbox = (TextView) findViewById(R.id.cscanavgoutput);
        TextView nstepavgoutputbox = (TextView) findViewById(R.id.nstepavgoutput);

        TextView fcfsstdevoutputbox = (TextView) findViewById(R.id.fcfsstdevoutput);
        TextView sstfstdevoutputbox = (TextView) findViewById(R.id.sstfstdevoutput);
        TextView scanstdevoutputbox = (TextView) findViewById(R.id.scanstdevoutput);
        TextView lookstdevoutputbox = (TextView) findViewById(R.id.lookstdevoutput);
        TextView clookstdevoutputbox = (TextView) findViewById(R.id.clookstdevoutput);
        TextView cscanstdevoutputbox = (TextView) findViewById(R.id.cscanstdevoutput);
        TextView nstepstdevoutputbox = (TextView) findViewById(R.id.nstepstdevoutput);

        TextView fcfsvaroutputbox = (TextView) findViewById(R.id.fcfsvaroutput);
        TextView sstfvaroutputbox = (TextView) findViewById(R.id.sstfvaroutput);
        TextView scanvaroutputbox = (TextView) findViewById(R.id.scanvaroutput);
        TextView lookvaroutputbox = (TextView) findViewById(R.id.lookvaroutput);
        TextView clookvaroutputbox = (TextView) findViewById(R.id.clookvaroutput);
        TextView cscanvaroutputbox = (TextView) findViewById(R.id.cscanvaroutput);
        TextView nstepvaroutputbox = (TextView) findViewById(R.id.nstepvaroutput);

        fcfsavgoutputbox.setText(fcfsavg + " ms");
        sstfavgoutputbox.setText(sstfavg + " ms");
        scanavgoutputbox.setText(scanavg + " ms");
        lookavgoutputbox.setText(lookavg + " ms");
        cscanavgoutputbox.setText(cscanavg + " ms");
        clookavgoutputbox.setText(clookavg + " ms");
        nstepavgoutputbox.setText(nstepavg + " ms");

        fcfsstdevoutputbox.setText(fcfssd + " ms");
        sstfstdevoutputbox.setText(sstfsd + " ms");
        scanstdevoutputbox.setText(scansd + " ms");
        lookstdevoutputbox.setText(looksd + " ms");
        cscanstdevoutputbox.setText(cscansd + " ms");
        clookstdevoutputbox.setText(clooksd + " ms");
        nstepstdevoutputbox.setText(nstepsd + " ms");

        fcfsvaroutputbox.setText(fcfsvar + " ms");
        sstfvaroutputbox.setText(sstfvar + " ms");
        scanvaroutputbox.setText(scanvar + " ms");
        lookvaroutputbox.setText(lookvar + " ms");
        cscanvaroutputbox.setText(cscanvar + " ms");
        clookvaroutputbox.setText(clookvar + " ms");
        nstepvaroutputbox.setText(nstepvar + " ms");

        TextView requests = (TextView) findViewById(R.id.requestwindow);
        String inputText = "";

        for(int i = 0; i < reqList.size(); i++){
            inputText += reqList.get(i).toString() + "\n";
        }
        requests.setText(inputText);
    }

    public void randomize(View view){
        ArrayList<Request> reqList = new ArrayList<Request>();

        for(int i = 0; i < num_requests; i++){
            int trackRequested = getRandomNumberUsingNextInt(0, 255);
            int sectorRequested = getRandomNumberUsingNextInt(0, 9);
            int arrivalTime = getRandomNumberUsingNextInt(0, 300);
            Request request = new Request(arrivalTime, trackRequested, sectorRequested);
            reqList.add(request);
        }
        Collections.sort(reqList);
        fill(reqList);
    }

    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}