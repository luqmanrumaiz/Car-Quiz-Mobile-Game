package com.carquizmobilegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class IdentifyCarMake extends AppCompatActivity {

    private final Quiz quiz = new Quiz();
    private List<Integer> previousRandomNumbers = new ArrayList<>();
    private String[] carMakes;
    // A Spinner is a Widget that creates a Dropdown Menu that is used
    private Spinner carOptionsSpinner;
    private Toast toast;
    private boolean timerToggled;
    private Button identifyButton;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        timerToggled = getIntent().getBooleanExtra("timerToggled", false);

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


        carMakes = getResources().getStringArray(R.array.car_makes_array);

        // Calling the randomlySelectImage to Display a random Car Image
        int randomNumber = quiz.randomlySelectImage(findViewById(R.id.random_car_image), previousRandomNumbers);

        previousRandomNumbers.add(randomNumber);

        identifyButton = findViewById(R.id.identify_button);

        identifyButton.setOnClickListener(v ->
        {
            if (carOptionsSpinner.getSelectedItem().equals(carMakes[randomNumber]))

                toast = quiz.showToast(true,"Correct !!!","", this);
            else
                toast = quiz.showToast(false,"Incorrect !!!",carMakes[randomNumber], this);

            quiz.submitToChangeButton(this, getIntent(), identifyButton);
        });

        carOptionsSpinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.car_makes_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carOptionsSpinner.setAdapter(adapter);

        // Calling the randomlySelectImage to Display a random Car Image
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (timerToggled)
        {
            getMenuInflater().inflate(R.menu.custom_toolbar, menu);

            menu.add(0, 0, 1, R.string.countdown_second)
                    .setActionView(quiz.getTimerTextView(this, getIntent(), identifyButton))
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
            // close this activity and return to home page
            finish();
            startActivity(new Intent(this, HomePage.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy()
    {
        if (toast != null)

            toast.cancel();

        if (timerToggled)

            countDownTimer.cancel();
        super.onDestroy();
    }
}
