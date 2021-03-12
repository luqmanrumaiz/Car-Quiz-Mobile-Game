package com.carquizmobilegame;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import java.util.Random;

public class Quiz {

    // This Array contains the IDs of all Car Images (Currently only 19)
    final private int[] randomCarImages = {R.drawable.car_1, R.drawable.car_2, R.drawable.car_3, R.drawable.car_4,
            R.drawable.car_5, R.drawable.car_6, R.drawable.car_7, R.drawable.car_8, R.drawable.car_9,
            R.drawable.car_10, R.drawable.car_11, R.drawable.car_12, R.drawable.car_13, R.drawable.car_14,
            R.drawable.car_15, R.drawable.car_16, R.drawable.car_17, R.drawable.car_18, R.drawable.car_19,
            R.drawable.car_20};

    private int randomNumber;

    /**
     * This Method randomly sets an Image to the ImageView randomImageCar
     */
    public int randomlySelectImage(ImageView randomCarImage)
    {
        randomNumber = new Random().nextInt(18);
        // This ImageView will be used to store the
        randomCarImage.setImageResource(randomCarImages[randomNumber]);

        return randomNumber;
    }

    public Toast showToast(boolean result, Context context)
    {
        //Creating the LayoutInflater instance
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE);

        //Getting the View object as defined in the customtoast.xml file
        View layout = inflater.inflate(R.layout.custom_correct_toast, null);

        //Creating the Toast object
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

        if (! result)

            layout = inflater.inflate(R.layout.custom_incorrect_toast, null);

        toast.setView(layout);//setting the view of custom toast layout
        toast.show();

        return toast;
    }
}
