package com.carquizmobilegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.TextViewCompat;

import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.os.Bundle;
import android.widget.TextView;
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


        carMakes = getResources().getStringArray(R.array.car_makes_array_w_duplicate);

        // Calling the randomlySelectImage to Display a random Car Image
        int randomNumber = quiz.randomlySelectImage(findViewById(R.id.random_car_image), previousRandomNumbers);

        previousRandomNumbers.add(randomNumber);


        Button identifyButton = findViewById(R.id.identify_button);

        identifyButton.setOnClickListener(v ->
        {
            if (carOptionsSpinner.getSelectedItem().equals(carMakes[randomNumber]))

                toast = quiz.showToast(true,"Correct !!!", getApplicationContext());
            else
                toast = quiz.showToast(true,"Incorrect !!!", getApplicationContext());

            identifyButton.setText("Next");

            identifyButton.setOnClickListener(a ->
            {
                finish();
                startActivity(getIntent());
            });
        });

        carOptionsSpinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.car_makes_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carOptionsSpinner.setAdapter(adapter);

        // Calling the randomlySelectImage to Display a random Car Image
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy()
    {
        if (toast != null)

            toast.cancel();

        if (timerToggled)

            quiz.stopTimer();

        super.onDestroy();
    }
}
