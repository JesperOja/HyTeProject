package com.example.edocontrol;

import android.app.backup.BackupManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatCheckBox;

import java.util.Locale;

public class InfoActivity extends MainActivity implements View.OnClickListener, TextWatcher {
    private RadioButton buttonPeriodIntensity1;
    private RadioButton buttonPeriodIntensity2;
    private RadioButton buttonPeriodIntensity3;
    private RadioButton buttonPeriodIntensity4;

    /**
     * Aktiviteetti luodaan
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        final Context context = getApplicationContext();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_info);

        // Backbutton
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Hae tiedot
        Intent intent = getIntent();
        int year = intent.getIntExtra("year", 2000);
        int month = intent.getIntExtra("month", 1);
        int day = intent.getIntExtra("day", 1);

        endo.db = new EndoDatabase(context);
        endo.db.loadCalculatedData();
        entry = endo.db.getEntryWithDetails(year, month, day);

        // Aseta vuoto
        RadioButton buttonPeriodYes = findViewById(R.id.periodYes);
        RadioButton buttonPeriodNo = findViewById(R.id.periodNo);
        boolean intensityEnabled = false;

        switch (entry.type) {
            case PERIOD_START:
            case PERIOD_CONFIRMED:
                buttonPeriodYes.setChecked(true);
                intensityEnabled = true;
                break;
            default:
                buttonPeriodNo.setChecked(true);
                // Default intensity for new period days
                entry.intensity = 2;
                break;
        }

        buttonPeriodYes.setOnClickListener(this);
        buttonPeriodNo.setOnClickListener(this);

        // Set period intensity
        buttonPeriodIntensity1 = findViewById(R.id.periodIntensity1);
        buttonPeriodIntensity2 = findViewById(R.id.periodIntensity2);
        buttonPeriodIntensity3 = findViewById(R.id.periodIntensity3);
        buttonPeriodIntensity4 = findViewById(R.id.periodIntensity4);

        switch (entry.intensity) {
            case 1:
                buttonPeriodIntensity1.setChecked(true);
                break;
            case 2:
                buttonPeriodIntensity2.setChecked(true);
                break;
            case 3:
                buttonPeriodIntensity3.setChecked(true);
                break;
            case 4:
                buttonPeriodIntensity4.setChecked(true);
                break;
        }

        buttonPeriodIntensity1.setEnabled(intensityEnabled);
        buttonPeriodIntensity2.setEnabled(intensityEnabled);
        buttonPeriodIntensity3.setEnabled(intensityEnabled);
        buttonPeriodIntensity4.setEnabled(intensityEnabled);
        buttonPeriodIntensity1.setOnClickListener(this);
        buttonPeriodIntensity2.setOnClickListener(this);
        buttonPeriodIntensity3.setOnClickListener(this);
        buttonPeriodIntensity4.setOnClickListener(this);

        // Muistiinpanot
        TextView editNotes = findViewById(R.id.editNotes);
        editNotes.setText(entry.notes);
        editNotes.addTextChangedListener(this);

        // Oireet ja tapahtumat
        LinearLayout groupAppointments = findViewById(R.id.groupAppointments);
        LinearLayout groupMeds = findViewById(R.id.groupMeds);
        LinearLayout groupSymptoms = findViewById(R.id.groupSymptoms);
        String packageName = getPackageName();
        Resources resources = getResources();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int marginLeft = (int) (12 * Resources.getSystem().getDisplayMetrics().density);
        int marginRight = (int) (12 * Resources.getSystem().getDisplayMetrics().density);
        layoutParams.setMargins(marginLeft, 0, marginRight, 0);

        // 0-1 taaphtumia, 2-6 lääkkeitä, 7-22 oireita
        int eventIds[] = {
                1,  // Doctor's appointment
                2,  // Contraceptive pill
                3,  // Pain medication
                4,  // Herbal remedies
                5,  // Heat pad
                6,  // Other contraceptive method
                7,  // Mild cramping
                8,  // Medium cramping
                9,  // Intense cramping
                10, // Temperature fluctuations
                11, // Spotting
                12, // Intense bleeding off period
                13, // Back pain
                14, // Middle pain left
                15, // Middle pain right
                16, // Nausea
                17, // Breast pain
                18, // Headache
                20, // Migraine
                21, // Energized
                22, // Sad
                23, // Edgy
                19, // Tired



        };
        int num = 0;
        for (int eventId : eventIds) {
            String resName = String.format(Locale.ENGLISH, "label_details_ev%d", eventId);
            int resId = resources.getIdentifier(resName, "string", packageName);
            if (resId != 0) {
                AppCompatCheckBox option = new AppCompatCheckBox(this);
                option.setLayoutParams(layoutParams);
                option.setTextSize(18);
                option.setText(resId);
                option.setId(resId);
                if (entry.symptoms.contains(eventId)) option.setChecked(true);
                option.setOnClickListener(this);
                if (num < 2) {
                    groupAppointments.addView(option);
                } else if(num > 1 && num < 7) {
                    groupMeds.addView(option);
                } else {
                    groupSymptoms.addView(option);
                }
            }
            num++;
        }
    }


    /**
     * Listener for clicks on the radio buttons and checkboxes
     */
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.periodYes:
                endo.db.addPeriod(entry.date);
                entry.type = EndoDatabase.DayEntry.PERIOD_START;
                databaseChanged();
                buttonPeriodIntensity1.setEnabled(true);
                buttonPeriodIntensity2.setEnabled(true);
                buttonPeriodIntensity3.setEnabled(true);
                buttonPeriodIntensity4.setEnabled(true);
                break;
            case R.id.periodNo:
                endo.db.removePeriod(entry.date);
                entry.type = EndoDatabase.DayEntry.EMPTY;
                databaseChanged();
                buttonPeriodIntensity1.setEnabled(false);
                buttonPeriodIntensity2.setEnabled(false);
                buttonPeriodIntensity3.setEnabled(false);
                buttonPeriodIntensity4.setEnabled(false);
                break;
            case R.id.periodIntensity1:
                entry.intensity = 1;
                endoDB.addEntryDetails(entry);
                databaseChanged();
                break;
            case R.id.periodIntensity2:
                entry.intensity = 2;
                endoDB.addEntryDetails(entry);
                databaseChanged();
                break;
            case R.id.periodIntensity3:
                entry.intensity = 3;
                endoDB.addEntryDetails(entry);
                databaseChanged();
                break;
            case R.id.periodIntensity4:
                entry.intensity = 4;
                endoDB.addEntryDetails(entry);
                databaseChanged();
                break;
            default:
                String packageName = getPackageName();
                int resId;
                entry.symptoms.clear();
                int num = 1;
                while (num < 24) {
                    String resName = String.format(Locale.ENGLISH,"label_details_ev%d", num);
                    resId = getResources().getIdentifier(resName, "string", packageName);
                    if (resId != 0) {
                        CheckBox option = findViewById(resId);
                        if (option.isChecked()) entry.symptoms.add(num);
                    }
                    num++;
                }
                endoDB.addEntryDetails(entry);
                databaseChanged();
                break;
        }
    }

    /**
     * Handler for text changes in edit fields
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        entry.notes = ((TextView) findViewById(R.id.editNotes)).getText().toString();
        endoDB.addEntryDetails(entry);
        databaseChanged();
    }

    /**
     * Called when the activity is destroyed
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (endoDB != null)
            endoDB.close();
    }

    /**
     * Helper to handle changes in the database
     */
    private void databaseChanged() {
        endoDB.loadCalculatedData();

        BackupManager bm = new BackupManager(this);
        bm.dataChanged();
    }
}

