package com.example.amit.yadavfoundation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CommunicationAddress extends AppCompatActivity implements View.OnClickListener {
Button communicationnxtbtn_obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_address);
        communicationnxtbtn_obj=findViewById(R.id.communicationnxtbtnid);
        communicationnxtbtn_obj.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.communicationnxtbtnid:
                Intent permanentadd_intent=new Intent(getApplicationContext(),PermanentAddress.class);
                startActivity(permanentadd_intent);
                break;
        }
    }
}
