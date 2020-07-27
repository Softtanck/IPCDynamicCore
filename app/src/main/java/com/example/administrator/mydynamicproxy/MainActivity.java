package com.example.administrator.mydynamicproxy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onResume() {
        super.onResume();
        IDealerRemoteInterface IDealerRemoteInterface = CsdApi.getInterface(IDealerRemoteInterface.class);
        boolean getState = false;
        boolean verifyResult = false;
        if (IDealerRemoteInterface != null) {
            getState = IDealerRemoteInterface.getDealerState();
            verifyResult = IDealerRemoteInterface.verifyDealerState("22222");
        }
        Log.d("Tanck", "Current getState result : " + getState);
        Log.d("Tanck", "Current verifyResult result : " + verifyResult);
    }
}
