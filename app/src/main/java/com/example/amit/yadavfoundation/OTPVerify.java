package com.example.amit.yadavfoundation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OTPVerify extends AppCompatActivity implements View.OnClickListener {

    Button button_obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpverify);
        button_obj=findViewById(R.id.otpverifybtnid);
        button_obj.setOnClickListener(this);
    }//end of onCreate method

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.otpverifybtnid:
                Intent intent=new Intent(getApplicationContext(),UserCurrentLocation.class);
                startActivity(intent);
                break;
        }//end of switch case
    }//end 0f onClick method
}//end of main class
