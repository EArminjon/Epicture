package epitech.epicture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onLogin(View view) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    protected void onRegister(View view) {
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }

    protected void onSkipAuthentication(View view) {
        startActivity(new Intent(MainActivity.this, GalleryActivity.class));
    }
}