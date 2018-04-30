package com.example.amit.yadavfoundation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

public class Splash extends AppCompatActivity {
Window mWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        mWindow = getWindow(); mWindow.getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            final Thread thread_obj = new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(3000);
                                Intent intent = new Intent(getApplicationContext(), VarifyphoneNo.class);
                                startActivity(intent);
                                finish();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }//end of catch
                        }//end of run method7
                    }//end of Runnable
            ); //end of Thread
            thread_obj.start();
        }//end of else

    }//end of oncreate method
