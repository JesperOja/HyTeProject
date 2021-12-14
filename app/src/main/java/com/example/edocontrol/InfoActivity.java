package com.example.edocontrol;

import android.annotation.SuppressLint;
import android.app.backup.BackupManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.database.sqlite.*;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import java.time.LocalDate;
import java.util.Locale;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher{
    private RadioButton buttonPeriodIntensity1;
    private RadioButton buttonPeriodIntensity2;
    private RadioButton buttonPeriodIntensity3;
    private RadioButton buttonPeriodIntensity4;
    private RadioButton buttonPeriodYes;
    private RadioButton buttonPeriodNo;
    private RadioButton buttonPain1;
    private RadioButton buttonPain2;
    private RadioButton buttonPain3;
    private RadioButton buttonPain4;
    private RadioButton buttonPain5;
    private CheckBox medsBox1;
    private CheckBox medsBox2;
    private CheckBox medsBox3;
    private CheckBox medsBox4;
    private Button saveButton;
    private Switch appontmentButton;
    private LocalDate localDate;
    public DatabaseHelper endoDB;
    public DatabaseHelper dateDB;
    private TextView editNotes;
    private Period period;
    private Pain pain;


    /**
     * Aktiviteetti luodaan
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        final Context context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
/*
        // Backbutton
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
*/
        // Hae tiedot
        Intent intent = getIntent();

        localDate = LocalDate.now();

        dateDB = new DatabaseHelper(InfoActivity.this);
        endoDB = new DatabaseHelper(InfoActivity.this);
        dateDB.addDate(localDate.toString());

        // Aseta vuoto
        buttonPeriodYes = findViewById(R.id.periodYes);
        buttonPeriodNo = findViewById(R.id.periodNo);
        buttonPeriodYes.setOnClickListener(this);
        buttonPeriodNo.setOnClickListener(this);


        // Set period intensity
        buttonPeriodIntensity1 = findViewById(R.id.periodIntensity1);
        buttonPeriodIntensity2 = findViewById(R.id.periodIntensity2);
        buttonPeriodIntensity3 = findViewById(R.id.periodIntensity3);
        buttonPeriodIntensity4 = findViewById(R.id.periodIntensity4);
        buttonPeriodIntensity1.setOnClickListener(this);
        buttonPeriodIntensity2.setOnClickListener(this);
        buttonPeriodIntensity3.setOnClickListener(this);
        buttonPeriodIntensity4.setOnClickListener(this);
        buttonPeriodIntensity1.setEnabled(false);
        buttonPeriodIntensity2.setEnabled(false);
        buttonPeriodIntensity3.setEnabled(false);
        buttonPeriodIntensity4.setEnabled(false);

        // Pain
        buttonPain1 = findViewById(R.id.pain1);
        buttonPain2 = findViewById(R.id.pain2);
        buttonPain3 = findViewById(R.id.pain3);
        buttonPain4 = findViewById(R.id.pain4);
        buttonPain5 = findViewById(R.id.pain5);
        buttonPain1.setOnClickListener(this);
        buttonPain2.setOnClickListener(this);
        buttonPain3.setOnClickListener(this);
        buttonPain4.setOnClickListener(this);
        buttonPain5.setOnClickListener(this);

        // Lääkärikäynti
        appontmentButton = findViewById(R.id.appointmentButton);
        appontmentButton.setOnClickListener(this);


        // Muistiinpanot
        editNotes = findViewById(R.id.editNotes);
        editNotes.addTextChangedListener(this);

        // Tallennus
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);
    }


    /**
     * Listener radiobuttoneille ja checkboxeille
     */
    public void onClick(View v) {

        if (buttonPeriodYes.isChecked()){
            buttonPeriodIntensity1.setEnabled(true);
            buttonPeriodIntensity2.setEnabled(true);
            buttonPeriodIntensity3.setEnabled(true);
            buttonPeriodIntensity4.setEnabled(true);
        }
        else {
            buttonPeriodIntensity1.setSelected(false);
            buttonPeriodIntensity2.setSelected(false);
            buttonPeriodIntensity3.setSelected(false);
            buttonPeriodIntensity4.setSelected(false);
            buttonPeriodIntensity1.setEnabled(false);
            buttonPeriodIntensity2.setEnabled(false);
            buttonPeriodIntensity3.setEnabled(false);
            buttonPeriodIntensity4.setEnabled(false);
        }

        if (buttonPeriodNo.isChecked()){
            buttonPeriodIntensity1.setSelected(false);
            buttonPeriodIntensity2.setSelected(false);
            buttonPeriodIntensity3.setSelected(false);
            buttonPeriodIntensity4.setSelected(false);
        }

        if (saveButton.isPressed()){
            if (buttonPeriodYes.isChecked()){
                if (buttonPeriodIntensity1.isChecked()){
                    endoDB.addPeriod(new Period(true, 1));
                }
                else if (buttonPeriodIntensity2.isChecked()){
                    endoDB.addPeriod(new Period( true, 2));
                }
                else if (buttonPeriodIntensity3.isChecked()){
                    endoDB.addPeriod(new Period(true, 3));
                }
                else if (buttonPeriodIntensity4.isChecked()){
                    endoDB.addPeriod(new Period(true, 4));
                }
            }

            if (appontmentButton.isActivated()){
                endoDB.addAppointment(1);
            }

            if (editNotes.toString().isEmpty()){

            }
            else {
                endoDB.addEntryDetails(editNotes.toString());
            }

            if (buttonPain1.isChecked()){
                endoDB.addPain(new Pain(1));
            }
            else if (buttonPain2.isChecked()){
                endoDB.addPain(new Pain(2));
            }
            else if (buttonPain3.isChecked()){
                endoDB.addPain(new Pain(3));
            }
            else if (buttonPain4.isChecked()){
                endoDB.addPain(new Pain(4));
            }
            else if (buttonPain5.isChecked()){
                endoDB.addPain(new Pain(5));
            }

            @SuppressLint("WrongConstant")
            Toast toast = Toast.makeText(this, "Details saved!", 2);
            toast.show();


        }



        /**/

    }

    /**
     * Tekstikentän muutosten handler
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        editNotes = findViewById(R.id.editNotes);
        String notes = editNotes.getText().toString();
        endoDB.addEntryDetails(notes);
    }

    /**
     * Kutsutaab, kun sovellus suljetaan
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (endoDB != null)
            endoDB.close();
    }

    /**
     * Databasen muutosten handler
     */
    private void databaseChanged() {

        BackupManager bm = new BackupManager(this);
        bm.dataChanged();
    }
}

