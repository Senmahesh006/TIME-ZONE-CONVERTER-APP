package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    EditText editTextDate, editTextTime;
    Spinner spinnerTimeZone, spinnerTimeZone1;
    TextView textViewResult,textViewResult2,textViewResult3,textViewResult4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);
        spinnerTimeZone = findViewById(R.id.spinnerTimeZone);
        spinnerTimeZone1 = findViewById(R.id.spinnerTimeZone1);
        textViewResult = findViewById(R.id.textViewResult);
        textViewResult2 = findViewById(R.id.textViewResult2);
        textViewResult3 = findViewById(R.id.textViewResult3);
        textViewResult4 = findViewById(R.id.textViewResult4);

        // Populate spinners with time zones
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, TimeZone.getAvailableIDs());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeZone.setAdapter(adapter);
        spinnerTimeZone1.setAdapter(adapter);

        Button buttonConvert = findViewById(R.id.buttonConvert);
        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertTimezones();
            }
        });
    }

    private void convertTimezones() {
        String inputDateStr = editTextDate.getText().toString().trim();
        String inputTimeStr = editTextTime.getText().toString().trim();
        String selectedTimeZone1 = spinnerTimeZone.getSelectedItem().toString();
        String selectedTimeZone2 = (String) spinnerTimeZone1.getSelectedItem().toString();
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date dateTime1 = inputFormat.parse(inputDateStr + " " + inputTimeStr);

            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            outputFormat.setTimeZone(TimeZone.getTimeZone(selectedTimeZone1));
            String convertedTime1 = outputFormat.format(dateTime1);

            outputFormat.setTimeZone(TimeZone.getTimeZone(selectedTimeZone2));
            String convertedTime2 = outputFormat.format(dateTime1);


            textViewResult4.setText("Input Date : "+ inputDateStr);
            textViewResult3.setText("Input Time : "+ inputTimeStr);
            textViewResult.setText("Converted Time : " + convertedTime2+" ");

            Date date1 = outputFormat.parse(convertedTime1);
            Date date2 = outputFormat.parse(convertedTime2);

            long diff = Math.abs(date1.getTime()-date2.getTime());
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffMinutes = diff / (60 * 1000) % 60;

            if (date1.after(date2)) {
                textViewResult2.setText(selectedTimeZone1 + " Time Is " + + diffHours + " hours " + diffMinutes + " minutes "+" ahead of "+selectedTimeZone2);
            } else if (date1.before(date2)) {
                textViewResult2.setText(selectedTimeZone2 + " Time Is " + + diffHours + " hours " + diffMinutes + " minutes "+" ahead of "+selectedTimeZone1);
            } else {
                textViewResult2.setText(selectedTimeZone1 + " is equal to " + selectedTimeZone2);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            textViewResult.setText("Invalid date or time format");
        }
    }
}
