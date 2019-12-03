package com.kazim.myvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class splash extends AppCompatActivity {
TextView textView;
Typeface typeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView textView=findViewById(R.id.textView);
        typeface=Typeface.createFromAsset(getAssets(),"fonts/Pacifico.ttf");
        Thread thread=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1*1000);
                    Intent intent=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(intent);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }
}
