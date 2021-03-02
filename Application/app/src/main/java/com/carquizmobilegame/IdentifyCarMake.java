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

    private int randomNumber;

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
}
