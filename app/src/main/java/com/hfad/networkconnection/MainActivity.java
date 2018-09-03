package com.hfad.networkconnection;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    final static String IPADDRESS = "192.168.0.102";
    boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onStart() {
        super.onStart();


        Thread pingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    success = pingRequest("/system/bin/ping -c 1 " + IPADDRESS);
                }
            }
        });

        pingThread.start();
    }

    private boolean pingRequest(String Instr){

        Runtime runtime = Runtime.getRuntime();
        try
        {
            Process  mIpAddrProcess = runtime.exec(Instr);
            int mExitValue = mIpAddrProcess.waitFor();

            BufferedReader stdInput;
            stdInput = new BufferedReader(new InputStreamReader(mIpAddrProcess.getInputStream()));

            String s;
            String res = "";
            while ((s = stdInput.readLine()) != null) {
                res += s + "\n";
            }
            Log.d("pingmsg", res);

            mIpAddrProcess.destroy();

            if(mExitValue==0){
                return true;
            }else{
                return false;
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();

        }
        catch (IOException e)
        {
            e.printStackTrace();

        }
        return false;
    }
}
