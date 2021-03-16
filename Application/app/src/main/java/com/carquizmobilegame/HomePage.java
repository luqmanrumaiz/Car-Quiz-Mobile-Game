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

    /**
     * This method simply sets the timerToggled to true meaning that
     * @param view
     */
    public void toggleTimer(View view)
    {
        timerToggled = !timerToggled;
    }

    /**
     * This method navigates to 1 of the 4 Game Mode Pages based on the Button that has been clicked
     *
     * @param view
     */
    public void switchToGameMode(View view)
    {
        Intent intent = null;

        /* This if else condition checks whether the Id of the View Component is the same
         * as the Id of all 4 Buttons that indicate the Game Mode to navigate to.
         */

        if (view.getId() == R.id.car_make_button)

            intent = new Intent(this, IdentifyCarMake.class);

        else if (view.getId() == R.id.hints_button)

            intent = new Intent(this, Hints.class);

        else if (view.getId() == R.id.car_image_button)

            intent = new Intent(this, IdentifyCarImage.class);

        else if (view.getId() == R.id.advanced_level)

            intent = new Intent(this, AdvancedLevel.class);


        /* Making the next Intent or in other words the Game Mode know if the timer is toggled or not
         * by putting in the timerToggled boolean with the key as "timerToggle"
         */

        intent.putExtra("timerToggled", timerToggled);
        startActivity(intent);

    }


}