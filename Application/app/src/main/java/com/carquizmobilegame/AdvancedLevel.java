package com.carquizmobilegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

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

public class AdvancedLevel extends AppCompatActivity {

    private final Quiz quiz = new Quiz();
    private Toast toast;
    private String[] carMakes;
    private String carMakeOne;
    private String carMakeTwo;
    private String carMakeThree;
    private List<Integer> previousRandomNumbers = new ArrayList<>();
    private int fails;
    private boolean timerToggled;
    private int randomNumber;
    private Button guessImageButton;
    private CountDownTimer countDownTimer;
    private EditText image_one_edit_text, image_two_edit_text, image_three_edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        timerToggled = getIntent().getBooleanExtra("timerToggled", false);
        fails = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_level);

        Toolbar toolbar = findViewById(R.id.toolbar);

        // Setting the Action Bar as the above Toolbar
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        guessImageButton = findViewById(R.id.identify_image_button);

        carMakes = getResources().getStringArray(R.array.car_makes_array_w_duplicate);

        // Calling the randomlySelectImage to Display a random Car Image

        int randomNumberOne = quiz.randomlySelectImage(findViewById(R.id.random_car_image_1), previousRandomNumbers);
        previousRandomNumbers.add(randomNumberOne);

        int randomNumberTwo = quiz.randomlySelectImage(findViewById(R.id.random_car_image_2), previousRandomNumbers);
        previousRandomNumbers.add(randomNumberTwo);

        int randomNumberThree = quiz.randomlySelectImage(findViewById(R.id.random_car_image_3), previousRandomNumbers);
        previousRandomNumbers.add(randomNumberOne);

        carMakeOne = carMakes[randomNumberOne];

        carMakeTwo = carMakes[randomNumberTwo];

        carMakeThree = carMakes[randomNumberThree];

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (timerToggled)
        {
            getMenuInflater().inflate(R.menu.custom_toolbar, menu);

            menu.add(0, 0, 1, R.string.countdown_second)
                    .setActionView(quiz.getTimer(this, getIntent(), guessImageButton))
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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

        if (timerToggled )

            countDownTimer.cancel();

        super.onDestroy();
    }

    public void checkImageSelected(View view)
    {

    }
}
