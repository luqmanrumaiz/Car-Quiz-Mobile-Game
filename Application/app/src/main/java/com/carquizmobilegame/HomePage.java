package com.carquizmobilegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


    }

    public void switchToIdentifyMake(View view)
    {
        Intent intent = new Intent(this, IdentifyCarMake.class);
        startActivity(intent);
    }


}