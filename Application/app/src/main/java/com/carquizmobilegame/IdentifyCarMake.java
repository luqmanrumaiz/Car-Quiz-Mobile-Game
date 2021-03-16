package com.carquizmobilegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class IdentifyCarMake extends AppCompatActivity {


    private final Quiz quiz = new Quiz();

    private List<Integer> previousRandomNumbers;

    private Spinner carOptionsSpinner;

    private Toast toast;

    private int randomNumber;

    private boolean timerToggled;

    private Button identifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        previousRandomNumbers = new ArrayList<>();
        timerToggled = getIntent().getBooleanExtra("timerToggled", false);

        if (savedInstanceState == null)
            System.out.println("AA");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_car_make);

        Toolbar toolbar = findViewById(R.id.toolbar);

        // Setting the Action Bar as the above Toolbar
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        ImageView randomlySelectedImage = findViewById(R.id.random_car_image);


        if (savedInstanceState == null)
        {
            // Calling the randomlySelectImage to Display a random Car Image
            randomNumber = quiz.randomlySelectImage(randomlySelectedImage, previousRandomNumbers, false);
        }
        else
        {
            randomlySelectedImage.setImageResource(Quiz.CAR_IMAGES[randomNumber]);
        }

        previousRandomNumbers.add(randomNumber);

        identifyButton = findViewById(R.id.identify_button);

        identifyButton.setOnClickListener(v ->
        {
            if (carOptionsSpinner.getSelectedItem().equals(Quiz.CAR_MAKES[randomNumber]))

                toast = quiz.showToast(true, "Correct !!!", "", this);
            else
                toast = quiz.showToast(false, "Incorrect !!!", Quiz.CAR_MAKES[randomNumber], this);

            quiz.restartRound(this, getIntent(), identifyButton);
        });

        carOptionsSpinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.car_makes_array_no_duplicates, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carOptionsSpinner.setAdapter(adapter);


        // Calling the randomlySelectImage to Display a random Car Image
    }


    private static final String RANDOM_NUMBER = "randomNumber";
    private int mPosition;


    /* https://stackoverflow.com/questions/32283853/android-save-state-on-orientation-change
     * ^
     * |
     * Answered by Androider - (Summary - How to save State when Orientation Changes)
     */

    @Override
    protected void onSaveInstanceState(final Bundle outState)
    {
        super.onSaveInstanceState(outState);
        // Save the state of item position
        outState.putInt(RANDOM_NUMBER, randomNumber);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Read the state of item position
        randomNumber = savedInstanceState.getInt(RANDOM_NUMBER);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        /* https://suragch.medium.com/adding-a-menu-to-the-toolbar-in-android-60d096f9fb89
         * (Summary - How to make a Custom Toolbar)
         */
        getMenuInflater().inflate(R.menu.custom_toolbar, menu);

        if (timerToggled)
        {
            menu.findItem(R.id.ic_timer).setVisible(true);

            TextView timerTextView = quiz.getTimerTextView(this, getIntent(), identifyButton);

            menu.findItem(R.id.timer_text_view).setActionView(timerTextView);
        }
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home)
        {
            // close this activity and return to home page
            finish();
            startActivity(new Intent(this, HomePage.class));
        }

        return super.onOptionsItemSelected(item);
    }

    // Closing the Toast or/and the Countdown Timer when the Activity is destroyed
    @Override
    public void onDestroy() {

        if (toast != null)

            toast.cancel();

        if (timerToggled && quiz.getCountDownTimer() != null)

            quiz.getCountDownTimer().cancel();

        super.onDestroy();
    }
}
