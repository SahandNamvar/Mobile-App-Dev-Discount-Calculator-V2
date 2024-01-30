// Assignment Number: 1
// File Name: Discount_Calculator
// Student Names: Sahand Namvar, Aaron Crandall
// Group Number: 19

package edu.uncc.discount_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{ // From View class, implement OnClickListener Interface

    // Declare variables for the UI fields
    EditText editTextEnterPrice;
    RadioGroup radioGroup;
    SeekBar seekBar;
    TextView textViewDisplayDiscount, textViewDisplayFinalPrice, textViewSeekBarTracker;
    Button buttonCalculate, buttonReset;

    // TAG
    final String TAG = "Log.d --> ";

    // Main method - OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize variables - find corresponding IDs
        editTextEnterPrice = (EditText) findViewById(R.id.editTextEnterPrice);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);
        textViewDisplayDiscount = (TextView) findViewById(R.id.textViewDisplayDiscount);
        textViewDisplayFinalPrice = (TextView) findViewById(R.id.textViewDisplayFinalPrice);
        textViewSeekBarTracker = (TextView) findViewById(R.id.textViewSeekBarTracker);
        buttonCalculate = (Button) findViewById(R.id.buttonCalculate);
        buttonCalculate.setOnClickListener(this);
        buttonReset = (Button) findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(this);

        // Set the SeekBar to be initially disabled -- (Extra - not part of assignment!) --
        seekBar.setEnabled(false);
        // Set a listener for the RadioGroup to enable/disable SeekBar
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonCustom) {
                    // Enable SeekBar when "Custom" radio button is selected
                    seekBar.setEnabled(true);
                } else {
                    // Disable SeekBar for other radio buttons
                    seekBar.setEnabled(false);
                }
            }
        });
    }

    // Implement OnClickListener Interface method onClick()
    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonCalculate){
            Log.d(TAG, "Calculate Button Clicked");
            // Which radio button?
            int radioID = radioGroup.getCheckedRadioButtonId();
            if (radioID == R.id.radioButton10) {
                calculateAndDisplayDiscount(10.0);
            } else if (radioID == R.id.radioButton15) {
                calculateAndDisplayDiscount(15.0);
            } else if (radioID == R.id.radioButton18) {
                calculateAndDisplayDiscount(18.0);
            } else if (radioID == R.id.radioButtonCustom) {
                if (seekBar.isEnabled()) {
                    calculateAndDisplayDiscount(seekBar.getProgress()); // SeekBar class is a subclass of ProgressBar, which contains a getProgress() method.
                } else {
                    // Handle the case where "Custom" radio button is selected but SeekBar is disabled
                    Toast.makeText(MainActivity.this, "Please enable Custom discount using the SeekBar", Toast.LENGTH_LONG).show();
                }
            }
        } else if (v.getId() == R.id.buttonReset) {
            Log.d(TAG, "Reset Button Clicked");
            editTextEnterPrice.setText("");
            textViewDisplayDiscount.setText("0.00");
            textViewDisplayFinalPrice.setText("0.00");
            radioGroup.check(R.id.radioButton10);
            seekBar.setProgress(25);
            seekBar.setEnabled(false);

        }
    }

    // Method to retrieve EditText value, calculate discount, show results
    @SuppressLint("SetTextI18n")
    private void calculateAndDisplayDiscount(double discountPercentage){
        Log.d(TAG, discountPercentage + "%");
        String itemPrice = editTextEnterPrice.getText().toString(); // retrieve edit text amount as String
        try {
            double itemPrice_d = (double) Double.parseDouble(itemPrice); // Convert to double
            double discountPrice = itemPrice_d - ((discountPercentage/100) * itemPrice_d);
            double discountedPrice = itemPrice_d - discountPrice;
            textViewDisplayDiscount.setText(String.valueOf(discountedPrice));
            textViewDisplayFinalPrice.setText(String.valueOf(discountPrice));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Enter a Valid Number!", Toast.LENGTH_LONG).show();
        }
    }

    // Implement OnSeekBarChanged Listener method from the SeekBar interface
    @SuppressLint("SetTextI18n")
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.d(TAG, "onProgressChanged" + progress);
        textViewSeekBarTracker.setText(String.valueOf(progress) + "%");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "onStartTrackingTouch");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "onStopTrackingTouch");
    }
}