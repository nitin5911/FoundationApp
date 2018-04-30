package com.example.amit.yadavfoundation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NetworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        getSupportFragmentManager().beginTransaction().replace(R.id.networkcontainerid,new Network_Frag())
                .addToBackStack(null).commit();
    }//end of onCreate method
}//end of main class
