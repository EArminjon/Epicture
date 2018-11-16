package epitech.epicture;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;

public class SettingsActivity extends AppCompatActivity {

    private GridSetting settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent i = getIntent();
        settings = (GridSetting) i.getSerializableExtra("settings");


        ImageButton cancelButton = findViewById(R.id.CancelButton);
        cancelButton.setOnClickListener(v -> {
            setResult(Activity.RESULT_CANCELED, new Intent());
            finish();
        });

        ImageButton submitButton = findViewById(R.id.SubmitButton);
        submitButton.setOnClickListener(v -> {
            setResult(Activity.RESULT_OK, new Intent().putExtra("settings", settings));
            finish();
        });

        switchMature();
        numberPickerPage();
        radioSort();
        radioSection();
    }

    private void switchMature() {
        Switch sw = findViewById(R.id.switch1);
        sw.setChecked(settings.isMatureBool());
        sw.setOnCheckedChangeListener((buttonView, isChecked) -> settings.setMature(isChecked));
    }

    private void numberPickerPage() {
        NumberPicker np = findViewById(R.id.numberPicker);
        np.setMinValue(1);
        np.setMaxValue(300);
        np.setValue(settings.getItemsNb());
        NumberPicker.OnValueChangeListener onValueChangeListener = (numberPicker, previous, value) -> settings.setItemsNb(value);
        np.setOnValueChangedListener(onValueChangeListener);
    }

    private void radioSort() {
        RadioGroup radioGroup = findViewById(R.id.radioGroupSort);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioTIME:
                    settings.setSort("time");
                    break;
                case R.id.radioTOP:
                    settings.setSort("top");
                    break;
                case R.id.radioVIRAL:
                    settings.setSort("viral");
                    break;
            }
        });

    }

    private void radioSection() {
        RadioGroup radioGroup = findViewById(R.id.radioGroupSection);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioSectionHot:
                    settings.setSection("hot");
                    break;
                case R.id.radioSectionTop:
                    settings.setSection("top");
                    break;
                case R.id.radioSectionUser:
                    settings.setSection("user");
                    break;
            }
        });

    }
}
