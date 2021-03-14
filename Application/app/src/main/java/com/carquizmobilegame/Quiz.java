package com.carquizmobilegame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import java.util.List;
import java.util.Random;

public class Quiz extends AppCompatActivity {

    final private int[] CAR_IMAGES = {R.drawable.car_1, R.drawable.car_2, R.drawable.car_3, R.drawable.car_4,
            R.drawable.car_5, R.drawable.car_6, R.drawable.car_7, R.drawable.car_8, R.drawable.car_9,
            R.drawable.car_10, R.drawable.car_11, R.drawable.car_12, R.drawable.car_13, R.drawable.car_14,
            R.drawable.car_15, R.drawable.car_16, R.drawable.car_17, R.drawable.car_18, R.drawable.car_19,
            R.drawable.car_20};

    private int randomNumber;
    private int attempts;

    private boolean multipleAttempts;

    private TextView timerTextView;

    private CountDownTimer countDownTimer;

    /**
     * This Method randomly sets an Image to the ImageView randomImageCar
     */
    public int randomlySelectImage(ImageView randomCarImage, List<Integer> previousRandomNumbers)
    {

        randomNumber = new Random().nextInt(20);

        while (previousRandomNumbers.contains(randomNumber))

            randomNumber = new Random().nextInt(20);

        // This ImageView will be used to store the
        randomCarImage.setImageResource(CAR_IMAGES[randomNumber]);

        return randomNumber;
    }

    public Toast showToast(boolean result, String message, String correctCarMake, Context context)
    {
        //Creating the LayoutInflater instance
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE);

        //Getting the View object as defined in the custom_correct_toast.xml file
        View layout = inflater.inflate(R.layout.custom_correct_toast, null);

        //Creating the Toast object
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

        if (! result)
        {
            layout = inflater.inflate(R.layout.custom_incorrect_toast, null);
            TextView incorrectTextView = layout.findViewById(R.id.custom_incorrect_toast_message);
            incorrectTextView.setText(message);

            if (! correctCarMake.equals(""))
            {
                TextView correctCarMakeTextView = layout.findViewById(R.id.correct_car_make_text_view);
                correctCarMakeTextView.setText(correctCarMakeTextView.getText().toString() + correctCarMake);
                correctCarMakeTextView.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            TextView correctTextView = layout.findViewById(R.id.custom_correct_toast_message);
            correctTextView.setText(message);
        }

        toast.setView(layout); //setting the view of custom toast layout
        toast.show();

        return toast;
    }

    public TextView getTimerTextView(Context context, Intent intent, Button button)
    {
        // TextView that resembles a timer that counts up to 20
        timerTextView = new TextView(context);
        TextViewCompat.setTextAppearance(timerTextView, R.style.WhiteSmallText);
        timerTextView.setTypeface(timerTextView.getTypeface(), Typeface.BOLD);
        timerTextView.setTextSize(20);

        startTimer(context, intent, button);
        return timerTextView;
    }

    public CountDownTimer getCountDownTimer()
    {
        return countDownTimer;
    }

    public void startTimer(Context context, Intent intent, Button button)
    {
        /* Setting total time to 21000ms = 21s and making sure a count occurs every 1000ms = 1s
         * There is about a 1 second delay that occurs due to the nature of activities switching
         * thereby an extra 1000ms is added
         */
        countDownTimer = new CountDownTimer(21000, 1000)
        {
            int remainingTime;
            /* This method is called as each second passes, and in it is a parameter contains the
             * remaining milliseconds of the countdown timer divided by 1000 to seconds
             */
            @Override
            public void onTick(long millisUntilFinished)
            {
                remainingTime = (int) (millisUntilFinished / 1000);

                if (remainingTime < 10)

                    timerTextView.setText( "0" + remainingTime + "s");

                else

                    timerTextView.setText( remainingTime + "s");
            }

            @Override
            // Displaying a Toast as user has lost as time is up,
            public void onFinish()
            {
                showToast(false, "Times Up !!!", "", context);

                if (multipleAttempts)
                {
                    if (attempts == 3)

                        submitToChangeButton(context, intent, button);

                    else
                        button.performClick();
                }
                else
                    submitToChangeButton(context, intent, button);
            }
        }.start();

        System.out.println("dasda");
    }

    public void setAttempts(int attempts)
    {
        this.attempts = attempts;
    }


    public void setMultipleAttempts(boolean multipleAttempts)
    {
        this.multipleAttempts = multipleAttempts;
    }

    public void submitToChangeButton(Context context, Intent intent, Button submit)
    {
        submit.setText("Next");
        submit.setOnClickListener(next ->
        {
            finish();
            context.startActivity(intent);
        });
    }
}
