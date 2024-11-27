package com.example.minip;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class navigation extends AppCompatActivity {

    private CheckBox checkbox1;
    private Button btn;
    private ImageView arrowLeft, arrowRight;
    private TextView dateText, titleText1, companyName1, companyAddress1, timeSlot1;
    private boolean isButtonClicked = false;
    private boolean isCheckboxChecked = false;
    private Calendar currentDate;
    private Calendar startDate;
    private Calendar endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        checkbox1 = findViewById(R.id.checkbox1);
        btn = findViewById(R.id.button);
        arrowLeft = findViewById(R.id.arrowLeft);
        arrowRight = findViewById(R.id.arrowRight);
        dateText = findViewById(R.id.dateText);
        titleText1 = findViewById(R.id.titleText1);
        companyName1 = findViewById(R.id.companyName1);
        companyAddress1 = findViewById(R.id.companyAddress1);
        timeSlot1 = findViewById(R.id.timeSlot1);

        // Initialize date range
        startDate = Calendar.getInstance();
        startDate.set(2018, Calendar.JUNE, 1);  // Set start date to June 1, 2018
        endDate = Calendar.getInstance();
        endDate.set(2018, Calendar.JUNE, 25);  // Set end date to June 25, 2018

        // Set current date to start date (June 1, 2018)
        currentDate = (Calendar) startDate.clone();
        updateDisplayedDate();

        // Set listeners
        setupCheckboxListener(checkbox1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isButtonClicked = true;
                checkAndNavigate();
            }
        });

        arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDate(-1);
            }
        });

        arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDate(1);
            }
        });
    }

    private void updateDisplayedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        dateText.setText(sdf.format(currentDate.getTime()));

        // Check if the current date is June 21, 2018
        if (isSameDate(currentDate, 2018, Calendar.JUNE, 21)) {
            showInterventionDetails();
        } else {
            hideInterventionDetails();
        }
    }

    private boolean isSameDate(Calendar calendar, int year, int month, int day) {
        return calendar.get(Calendar.YEAR) == year &&
                calendar.get(Calendar.MONTH) == month &&
                calendar.get(Calendar.DAY_OF_MONTH) == day;
    }

    private void changeDate(int days) {
        // Modify the current date by the specified number of days
        currentDate.add(Calendar.DAY_OF_MONTH, days);

        // Check if the new date is within the allowed range (June 1 - June 25, 2018)
        if (currentDate.before(startDate)) {
            currentDate = (Calendar) startDate.clone();
        } else if (currentDate.after(endDate)) {
            currentDate = (Calendar) endDate.clone();
        }

        updateDisplayedDate();
    }

    private void showInterventionDetails() {
        titleText1.setVisibility(View.VISIBLE);
        companyName1.setVisibility(View.VISIBLE);
        companyAddress1.setVisibility(View.VISIBLE);
        timeSlot1.setVisibility(View.VISIBLE);
        checkbox1.setVisibility(View.VISIBLE);
    }

    private void hideInterventionDetails() {
        titleText1.setVisibility(View.GONE);
        companyName1.setVisibility(View.GONE);
        companyAddress1.setVisibility(View.GONE);
        timeSlot1.setVisibility(View.GONE);
        checkbox1.setVisibility(View.GONE);
    }

    private void setupCheckboxListener(final CheckBox checkbox) {
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    showConfirmationDialog(checkbox);
                } else {
                    isCheckboxChecked = true;
                    checkAndNavigate();
                }
            }
        });
    }

    private void checkAndNavigate() {
        if (isButtonClicked && isCheckboxChecked) {
            Intent i = new Intent(navigation.this, intervention.class);
            startActivity(i);
        }
    }



    private void showConfirmationDialog(final CheckBox checkbox) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Are you sure you want to uncheck this item?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkbox.setChecked(false);
                        isCheckboxChecked = checkbox1.isChecked();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkbox.setChecked(true);
                    }
                })
                .show();
    }
}
