package epitech.epicture;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent i = getIntent();
        GridSetting settings = (GridSetting) i.getSerializableExtra("settings");


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

        Switch sw = findViewById(R.id.switch1);
        sw.setChecked(settings.isMatureBool());
        sw.setOnCheckedChangeListener((buttonView, isChecked) -> settings.setMature(isChecked));

        NumberPicker np = findViewById(R.id.numberPicker);
        np.setMinValue(1);
        np.setValue(settings.getItemsNb());
        np.setMaxValue(300);
        NumberPicker.OnValueChangeListener onValueChangeListener =
                (numberPicker, previous, value) -> settings.setItemsNb(value);

        np.setOnValueChangedListener(onValueChangeListener);
    }
}
