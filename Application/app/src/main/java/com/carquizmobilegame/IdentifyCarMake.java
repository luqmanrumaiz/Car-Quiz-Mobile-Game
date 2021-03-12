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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class IdentifyCarMake extends AppCompatActivity {

    // A Spinner is a Widget that creates a Dropdown Menu that is used
    private Spinner carOptionsSpinner;

    // A List of all Car Makes without duplicate Car Names
    private List<String> carMakes;

    private final Quiz quiz = new Quiz();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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

        int randomNumber = quiz.randomlySelectImage(findViewById(R.id.random_car_image));

        Button identifyButton = findViewById(R.id.identify_button);

        identifyButton.setOnClickListener(v ->
        {
            quiz.showToast(carOptionsSpinner.getSelectedItem().equals(
                    carMakes.get(randomNumber)), getApplicationContext());
        });

        List<String> randomCarNames = Arrays.asList(getResources().getStringArray(R.array.car_names_array));
        carMakes = new ArrayList<>();

        for (int i = 0; i < randomCarNames.size(); i++)
        {
            String carName = randomCarNames.get(i);
            String carMake = carName.substring(0, carName.indexOf(' '));
            carMakes.add(carMake);
        }

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
        TextView timerTextView = new TextView(this);
        TextViewCompat.setTextAppearance(timerTextView, R.style.WhiteSmallText);

        timerTextView.setTypeface(timerTextView.getTypeface(), Typeface.BOLD);

        new CountDownTimer(20000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                timerTextView.setText( "" + millisUntilFinished / 1000);
            }

            public void onFinish()
            {
                quiz.showToast(false, getApplicationContext());
            }
        }.start();

        menu.add(0, 0, 1, R.string.countdown_second).
                setActionView(timerTextView).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

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
}
