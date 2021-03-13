package com.carquizmobilegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {

    private boolean timerToggled;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void toggleTimer(View view)
    {
        timerToggled = !timerToggled;
    }

    public void switchToIdentifyMake(View view)
    {
        Intent intent = null;

        if (view.getId() == R.id.car_make_button)

            intent = new Intent(this, IdentifyCarMake.class);

        else if (view.getId() == R.id.hints_button)

            intent = new Intent(this, Hints.class);

        else if (view.getId() == R.id.car_image_button)

            intent = new Intent(this, IdentifyCarImage.class);

        else if (view.getId() == R.id.advanced_level)

            intent = new Intent(this, AdvancedLevel.class);


        if (intent != null)
        {
            intent.putExtra("timerToggled", timerToggled);
            startActivity(intent);
        }
    }


}