package com.example.amit.yadavfoundation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
Button communitybtn_obj,matrimonybtn_obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        communitybtn_obj=findViewById(R.id.communitybtnid);
        matrimonybtn_obj=findViewById(R.id.matrimonybtnid);
        communitybtn_obj.setOnClickListener(this);
        matrimonybtn_obj.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.communitybtnid:
                Intent community_intent=new Intent(getApplicationContext(),CommunicationAddress.class);
                startActivity(community_intent);
                break;

        }
    }
}
