package com.example.edocontrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


/**
 * Class to define and create an activity where the user can save their input data to a database
 *
 * Extends to AppCompatActivity
 *
 * @author      Jenni Tynkkynen
 */
public class InfoActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher{
    private RadioGroup groupPeriodIntensity;
    private RadioGroup groupPeriod;
    private RadioButton buttonPeriodIntensity1;
    private RadioButton buttonPeriodIntensity2;
    private RadioButton buttonPeriodIntensity3;
    private RadioButton buttonPeriodIntensity4;
    private RadioButton buttonNoIntensity;
    private RadioButton buttonPeriodYes;
    private RadioButton buttonPeriodNo;
    private CheckBox painBox1;
    private CheckBox painBox2;
    private CheckBox painBox3;
    private CheckBox painBox4;
    private CheckBox painBox5;
    private CheckBox painBox6;
    private CheckBox painBox7;
    private CheckBox painBox8;
    private CheckBox medsBox1;
    private CheckBox medsBox2;
    private CheckBox medsBox3;
    private Button saveButton;
    private Button backButton;
    private Button clearButton;
    private Switch appontmentButton;
    public DatabaseHelper endoDB;
    private TextView todayText;
    private TextView editNotes;
    private Period period;
    private Pain pain;
    private Meds meds;
    private Appointment appointment;
    private String clickedDate;
    private String notes;
    private String allUsedMeds;
    private String allPains;
    private Singleton user;

    /**
     * Called when the activity is first created
     * @param savedInstanceState Bundle, saves the instanced state to a bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        final Context context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        user = Singleton.getInstance();
        Bundle b = getIntent().getExtras();
        clickedDate = b.getString(MainActivity.EXTRA_DATE,"0");


        endoDB = new DatabaseHelper(InfoActivity.this);


        // Period activation
        groupPeriod = findViewById(R.id.periodGroup);
        buttonPeriodYes = findViewById(R.id.periodYes);
        buttonPeriodNo = findViewById(R.id.periodNo);
        buttonPeriodYes.setOnClickListener(this);
        buttonPeriodNo.setOnClickListener(this);


        // Period intensity
        groupPeriodIntensity = findViewById(R.id.intensityGroup);
        buttonPeriodIntensity1 = findViewById(R.id.periodIntensity1);
        buttonPeriodIntensity2 = findViewById(R.id.periodIntensity2);
        buttonPeriodIntensity3 = findViewById(R.id.periodIntensity3);
        buttonPeriodIntensity4 = findViewById(R.id.periodIntensity4);
        buttonNoIntensity = findViewById(R.id.noIntensity);
        buttonPeriodIntensity1.setOnClickListener(this);
        buttonPeriodIntensity2.setOnClickListener(this);
        buttonPeriodIntensity3.setOnClickListener(this);
        buttonPeriodIntensity4.setOnClickListener(this);
        buttonPeriodIntensity1.setEnabled(false);
        buttonPeriodIntensity2.setEnabled(false);
        buttonPeriodIntensity3.setEnabled(false);
        buttonPeriodIntensity4.setEnabled(false);

        // Pain
        painBox1 = findViewById(R.id.pain1);
        painBox2 = findViewById(R.id.pain2);
        painBox3 = findViewById(R.id.pain3);
        painBox4 = findViewById(R.id.pain4);
        painBox5 = findViewById(R.id.pain5);
        painBox6 = findViewById(R.id.pain6);
        painBox7 = findViewById(R.id.pain7);
        painBox8 = findViewById(R.id.pain8);
        painBox1.setOnClickListener(this);
        painBox2.setOnClickListener(this);
        painBox3.setOnClickListener(this);
        painBox4.setOnClickListener(this);
        painBox5.setOnClickListener(this);
        painBox6.setOnClickListener(this);
        painBox7.setOnClickListener(this);
        painBox8.setOnClickListener(this);

        // Meds
        medsBox1 = findViewById(R.id.meds1);
        medsBox2 = findViewById(R.id.meds2);
        medsBox3 = findViewById(R.id.meds3);
        medsBox1.setOnClickListener(this);
        medsBox2.setOnClickListener(this);
        medsBox3.setOnClickListener(this);

        // Doctor's appontment
        appontmentButton = findViewById(R.id.appointmentButton);
        appontmentButton.setOnClickListener(this);

        // Notes
        editNotes = findViewById(R.id.editNotes);
        editNotes.addTextChangedListener(this);

        // Save button
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        // Back button
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        // Clear button
        clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(this);

        //Date
        todayText = findViewById(R.id.todayText);
        todayText.setText(clickedDate);
    }


    /**
     * Listener for the all buttons in InfoActivity, called when a button is clicked or un-clicked
     */
    public void onClick(View v) {

        // Activating period intensity
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

        // Saving selected data
        if (saveButton.isPressed()){

            // Period and intensity
            if (buttonPeriodYes.isChecked()){
                if (buttonPeriodIntensity1.isChecked()){
                    period = new Period(true, 1);
                }
                else if (buttonPeriodIntensity2.isChecked()){
                    period = new Period( true, 2);
                }
                else if (buttonPeriodIntensity3.isChecked()){
                    period = new Period(true, 3);
                }
                else if (buttonPeriodIntensity4.isChecked()){
                    period = new Period(true, 4);
                }
            }else if(buttonPeriodNo.isChecked()){
                period = new Period();
            }else{
                period = new Period();
            }

            // Appointment
            if (appontmentButton.isChecked()){
                appointment = new Appointment(true);
            }else{
                appointment = new Appointment(false);
            }

            // Notes
            if (!editNotes.toString().isEmpty()){
                notes = editNotes.getText().toString();
            }

            // Pain
            if (painBox1.isChecked()){
                pain = new Pain(1); // 1 = Lower abdomen pain
                allPains += "," + pain.getPainType();
            }
            if (painBox2.isChecked()){
                pain = new Pain(2); // 2 = Back pain
                allPains += "," + pain.getPainType();
            }
            if (painBox3.isChecked()){
                pain = new Pain(3); // 3 = Shoulder pain
                allPains += "," + pain.getPainType();
            }
            if (painBox4.isChecked()){
                pain = new Pain(4); // 4 = Chest pain
                allPains += "," + pain.getPainType();
            }
            if (painBox5.isChecked()){
                pain = new Pain(5); // 5 = Headache
                allPains += "," + pain.getPainType();
            }
            if (painBox6.isChecked()){
                pain = new Pain(6); // 6 = Pain when urinating
                allPains += "," + pain.getPainType();
            }
            if (painBox7.isChecked()){
                pain = new Pain(7); // 7 = Pain during bowel movement
                allPains += "," + pain.getPainType();
            }
            if (painBox8.isChecked()) {
                pain = new Pain(8); // 8 = Pain during intercourse
                allPains += "," + pain.getPainType();
            }

            // Meds
            if (medsBox1.isChecked()){
                meds = new Meds(1); // 1 = Hormonal contraception
                allUsedMeds += "," +meds.getMedType();
            }
            if (medsBox2.isChecked()){
                meds = new Meds(2); // 2 = Pain medication
                allUsedMeds += "," +meds.getMedType();
            }
            if (medsBox3.isChecked()){
                meds = new Meds(3); // 3 = Herbal remedies
                allUsedMeds += "," +meds.getMedType();
            }

            String userID = LoginActivity.EMAIL;
            endoDB.addEverything(period,allPains,appointment.isAppointment(),allUsedMeds,clickedDate,notes,userID);
            endoDB.updateData(period,allPains,appointment.isAppointment(),allUsedMeds,clickedDate,notes,userID);
            // Shows on data save
            @SuppressLint("WrongConstant")
            Toast toast = Toast.makeText(this, "Details saved!", 2);
            toast.show();

        }

        // Back button
        if (backButton.isPressed()){
            startActivity(new Intent(InfoActivity.this, MainActivity.class));
        }

        // Clear button
        if (clearButton.isPressed()){
            clearOptions();
        }

    }

    /**
     * Method to clear all selected values from the activity
     */
    public void clearOptions () {

        if (medsBox1.isChecked()) {
            medsBox1.toggle();
        }
        if (medsBox2.isChecked()) {
            medsBox2.toggle();
        }
        if (medsBox3.isChecked()) {
            medsBox3.toggle();
        }
        if (painBox1.isChecked()) {
            painBox1.toggle();
        }
        if (painBox2.isChecked()) {
            painBox2.toggle();
        }
        if (painBox3.isChecked()) {
            painBox3.toggle();
        }
        if (painBox4.isChecked()) {
            painBox4.toggle();
        }
        if (painBox5.isChecked()) {
            painBox5.toggle();
        }
        if (painBox6.isChecked()) {
            painBox6.toggle();
        }
        if (painBox7.isChecked()) {
            painBox7.toggle();
        }
        if (painBox8.isChecked()) {
            painBox8.toggle();
        }
        if (appontmentButton.isChecked()) {
            appontmentButton.toggle();
        }
        groupPeriod.clearCheck();
        groupPeriodIntensity.clearCheck();
        editNotes.setText("");

    }

    /**
     * Method that is called when the application is destroyed
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (endoDB != null)
            endoDB.close();
    }

    /**
     * Handler for text changes
     * @param s CharSequence
     * @param start int
     * @param before int
     * @param count count
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int before, int count) {
    }

    /**
     * Handler for text changes
     * @param s CharSequence
     * @param start int
     * @param before int
     * @param count count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    /**
     * Handler for text changes
     * @param editable Editable
     */
    @Override
    public void afterTextChanged(Editable editable) {

    }

}

