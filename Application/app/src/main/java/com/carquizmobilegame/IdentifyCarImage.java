package com.carquizmobilegame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IdentifyCarImage extends AppCompatActivity {

    private final Quiz quiz = new Quiz();
    private Toast toast;
    private String[] carMakes;
    private String carMake;
    private List<Integer> previousRandomNumbers = new ArrayList<>();
    private int fails;
    private boolean timerToggled;
    private boolean imageOneToggle, imageTwoToggle, imageThreeToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        timerToggled = getIntent().getBooleanExtra("timerToggled", false);
        fails = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_car_image);

        Toolbar toolbar = findViewById(R.id.toolbar);

        // Setting the Action Bar as the above Toolbar
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        carMakes = getResources().getStringArray(R.array.car_makes_array_w_duplicate);

        // Calling the randomlySelectImage to Display a random Car Image

        int randomNumberOne = quiz.randomlySelectImage(findViewById(R.id.random_car_image_1), previousRandomNumbers);
        previousRandomNumbers.add(randomNumberOne);

        int randomNumberTwo = quiz.randomlySelectImage(findViewById(R.id.random_car_image_2), previousRandomNumbers);
        previousRandomNumbers.add(randomNumberTwo);

        int randomNumberThree = quiz.randomlySelectImage(findViewById(R.id.random_car_image_3), previousRandomNumbers);
        previousRandomNumbers.add(randomNumberOne);

        int randomNumber = new Random().nextInt(3) + 1;

        switch (randomNumber)
        {
            case 1:
                carMake = carMakes[randomNumberOne];
                break;

            case 2:
                carMake = carMakes[randomNumberTwo];
                break;

            case 3:
                carMake = carMakes[randomNumberThree];
                break;
        }

        TextView randomCarMake = findViewById(R.id.identify_image_text_view);
        randomCarMake.setText(carMake);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(timerToggled)

            menu.add(0, 0, 1, R.string.countdown_second).
                    setActionView(quiz.getTimer(this)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home)
        {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {

        if (toast != null)

            toast.cancel();

        if (timerToggled)

            quiz.stopTimer();

        super.onDestroy();
    }



    public void selectImage(View view)
    {
        CardView cardViewOne = findViewById(R.id.card_view_identify_image_1);
        CardView cardViewTwo = findViewById(R.id.card_view_identify_image_2);
        CardView cardViewThree = findViewById(R.id.card_view_identify_image_3);

        if (imageOneToggle)

            cardViewOne.setForeground(null);

        else if (imageTwoToggle)

            cardViewTwo.setForeground(null);

        else if (imageThreeToggle)

            cardViewThree.setForeground(null);

        imageOneToggle = false;
        imageTwoToggle = false;
        imageThreeToggle = false;

        if (view.getId() == cardViewOne.getId())
        {
            cardViewOne.setForeground(getResources().getDrawable(R.drawable.card_image_select));
            imageOneToggle = true;
        }
        else if (view.getId() == cardViewTwo.getId())
        {
            cardViewTwo.setForeground(getResources().getDrawable(R.drawable.card_image_select));
            imageTwoToggle = true;
        }
        else if (view.getId() == cardViewThree.getId())
        {
            cardViewThree.setForeground(getResources().getDrawable(R.drawable.card_image_select));
            imageThreeToggle = true;
        }
    }
}
