package com.example.amit.yadavfoundation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

public class VarifyphoneNo extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
Toolbar toolbar_obj;
ImageButton nextimgbtn_obj;
    private Spinner spinner;
    private static final String[] paths = {"Choose a country"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varifyphone_no);
        nextimgbtn_obj=findViewById(R.id.nextimgbtnid);
        spinner=findViewById(R.id.countryspinnerid);
        nextimgbtn_obj.setOnClickListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }//end of onCreate method

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nextimgbtnid:
                Intent intent=new Intent(getApplicationContext(),OTPVerify.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i>0){

}else spinner.setSelection(0);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
