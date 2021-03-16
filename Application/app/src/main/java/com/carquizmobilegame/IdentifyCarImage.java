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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IdentifyCarImage extends AppCompatActivity {

    private final Quiz quiz = new Quiz();

    private Toast toast;
    private String carMake;
    private List<Integer> previousRandomNumbers = new ArrayList<>();
    private int fails;
    private boolean timerToggled;
    private int randomNumber;
    private boolean imageOneToggle, imageTwoToggle, imageThreeToggle;
    private CardView cardViewOne, cardViewTwo, cardViewThree;
    private Button guessImageButton;
    private CountDownTimer countDownTimer;
    private boolean countDownFinished;
    private TextView timerTextView;

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

        guessImageButton = findViewById(R.id.identify_image_button);

        cardViewOne = findViewById(R.id.card_view_identify_image_1);
        cardViewTwo = findViewById(R.id.card_view_identify_image_2);
        cardViewThree = findViewById(R.id.card_view_identify_image_3);

        // Calling the randomlySelectImage to Display a random Car Image

        int randomNumberOne = quiz.randomlySelectImage(findViewById(R.id.random_car_image_1), previousRandomNumbers, true);
        previousRandomNumbers.add(randomNumberOne);

        int randomNumberTwo = quiz.randomlySelectImage(findViewById(R.id.random_car_image_2), previousRandomNumbers, true);
        previousRandomNumbers.add(randomNumberTwo);

        int randomNumberThree = quiz.randomlySelectImage(findViewById(R.id.random_car_image_3), previousRandomNumbers, true);
        previousRandomNumbers.add(randomNumberOne);

        randomNumber = new Random().nextInt(3) + 1;

        switch (randomNumber)
        {
            case 1:
                carMake = Quiz.CAR_MAKES[randomNumberOne];
                break;

            case 2:
                carMake = Quiz.CAR_MAKES[randomNumberTwo];
                break;

            case 3:
                carMake = Quiz.CAR_MAKES[randomNumberThree];
                break;
        }

        TextView randomCarMake = findViewById(R.id.identify_image_text_view);
        randomCarMake.setText(carMake);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        /* https://suragch.medium.com/adding-a-menu-to-the-toolbar-in-android-60d096f9fb89
         * (Summary - How to make a Custom Toolbar)
         */
        getMenuInflater().inflate(R.menu.custom_toolbar, menu);

        if (timerToggled)
        {
            menu.findItem(R.id.ic_timer).setVisible(true);
            timerTextView = quiz.getTimerTextView(this, getIntent(), guessImageButton);

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
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy()
    {

        if (toast != null)

            toast.cancel();

        if (timerToggled && quiz.getCountDownTimer() != null)

            quiz.getCountDownTimer().cancel();

        super.onDestroy();
    }

    /**
     * This method is used to Toggle 1 of 3 Images at a Time with a Red Overlay
     *
     * @param view
     */
    public void selectImage(View view)
    {
        // If Else Condition that clears any existing Toggles for all Images
        if (imageOneToggle)

            cardViewOne.setForeground(null);

        else if (imageTwoToggle)

            cardViewTwo.setForeground(null);

        else if (imageThreeToggle)

            cardViewThree.setForeground(null);

        imageOneToggle = false;
        imageTwoToggle = false;
        imageThreeToggle = false;

        /* This if else condition checks whether the Id of the View Component is the same
         * as the Id of all 3 that indicate which CardView that holds its respective Car Image.
         */
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

    /**
     * This method checks if the Toggled Image by the User corresponds to the Car Make of 1 of the
     * 3 Randomly Selected Cars
     *
     * @param view
     */
    public void checkImageSelected(View view)
    {
        if ((imageOneToggle && (randomNumber == 1)) || (imageTwoToggle && (randomNumber == 2))
                || (imageThreeToggle && (randomNumber == 3)))
        {
            quiz.restartRound(this, getIntent(), guessImageButton);
            toast = quiz.showToast(true,"Correct !!!","", this);
        }
        else
        {
            toast = quiz.showToast(false, "Incorrect !!!", "", this);
            fails++;
        }
    }
}
