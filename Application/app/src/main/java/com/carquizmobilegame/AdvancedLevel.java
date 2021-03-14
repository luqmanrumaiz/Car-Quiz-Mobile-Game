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
    private EditText imageOneEditText, imageTwoEditText, imageThreeEditText;

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

        imageOneEditText = findViewById(R.id.random_car_edit_text_1);
        imageTwoEditText = findViewById(R.id.random_car_edit_text_2);
        imageThreeEditText = findViewById(R.id.random_car_edit_text_3);

        guessImageButton = findViewById(R.id.identify_image_button);

        carMakes = getResources().getStringArray(R.array.car_makes_array_w_duplicate);

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
        if (imageOneEditText.getText().toString().equals("") || imageTwoEditText.getText().toString().equals("")
        || imageThreeEditText.getText().toString().equals(""))

            toast = quiz.showToast(false, "Please make sure all Text Boxes are filled !!!", "", this);

        else
        {
            if (imageOneEditText.getText().toString().equals(carMakeOne))
            {
                imageOneEditText.setTextColor(getResources().getColor(R.color.green));
                imageOneEditText.setEnabled(false);
            }
            if (imageTwoEditText.getText().toString().equals(carMakeTwo))
            {
                imageTwoEditText.setTextColor(getResources().getColor(R.color.green));
                imageTwoEditText.setEnabled(false);
            }
            if (imageThreeEditText.getText().toString().equals(carMakeThree))
            {
                imageThreeEditText.setTextColor(getResources().getColor(R.color.green));
                imageThreeEditText.setEnabled(false);
            }

            if (! imageOneEditText.getText().toString().equals(carMakeOne))
            {
                imageOneEditText.setTextColor(getResources().getColor(R.color.red));
            }
            if (! imageTwoEditText.getText().toString().equals(carMakeTwo))
            {
                imageTwoEditText.setTextColor(getResources().getColor(R.color.red));
            }
            if (! imageThreeEditText.getText().toString().equals(carMakeThree))
            {
                imageOneEditText.setTextColor(getResources().getColor(R.color.red));
            }

            if (! (imageOneEditText.getText().toString().equals(carMakeOne)
                    || imageTwoEditText.getText().toString().equals(carMakeTwo)
                    || imageThreeEditText.getText().toString().equals(carMakeThree)))
            {
                fails++;
            }

            if (fails == 3)
            {
                toast = quiz.showToast(false, "You have failed due to 03 Incorrect Guesses","", this);
            }
        }
    }
}
