package com.carquizmobilegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class IdentifyCarMake extends AppCompatActivity {

    // This Array contains the IDs of all Car Images (Currently only 19)
    final private int[] randomCarImages = {R.drawable.car_1, R.drawable.car_2, R.drawable.car_3, R.drawable.car_4,
            R.drawable.car_5, R.drawable.car_6, R.drawable.car_7, R.drawable.car_8, R.drawable.car_9,
            R.drawable.car_10, R.drawable.car_11, R.drawable.car_12, R.drawable.car_13, R.drawable.car_14,
            R.drawable.car_15, R.drawable.car_16, R.drawable.car_17, R.drawable.car_18, R.drawable.car_19};

    // A Spinner is a Widget that creates a Dropdown Menu that is used
    private Spinner carOptionsSpinner;
    private int randomNumber;
    private List<String> randomCarMakes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_car_make);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        List<String> randomCarNames = Arrays.asList(getResources().getStringArray(R.array.car_names_array));
        randomCarMakes = new ArrayList<>();

        for (int i = 0; i < randomCarNames.size(); i++) {
            String carName = randomCarNames.get(i);
            String carMake = carName.substring(0, carName.indexOf(' '));
            randomCarMakes.add(carMake);
        }

        carOptionsSpinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.car_makes_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carOptionsSpinner.setAdapter(adapter);

        randomlySelectImage();
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

    /**
     * This Method randomly sets an Image to the ImageView randomImageCar
     */
    public void randomlySelectImage()
    {
        randomNumber = new Random().nextInt(18);
        // This ImageView will be used to store the
        ImageView randomCarImage = findViewById(R.id.imageView);
        randomCarImage.setImageResource(randomCarImages[randomNumber]);
    }

    /**
     * @param view
     *
     * This Method checks if the option that the user selects in the Spinner carOptionsSpinner,
     * if the option is Correct a Toast is displayed showing the message "CORRECT" else "WRONG" is
     * shown along with the correct answer.
     */
    public void checkIfImageMatches(View view)
    {
        System.out.println(carOptionsSpinner.getSelectedItem());
        System.out.println(randomCarMakes.get(randomNumber));
        if (!(carOptionsSpinner.getSelectedItem().equals(randomCarMakes.get(randomNumber))))

            Toast.makeText(this, R.string.incorrect_toast_message, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, R.string.correct_toast_message, Toast.LENGTH_LONG).show();
    }
}
