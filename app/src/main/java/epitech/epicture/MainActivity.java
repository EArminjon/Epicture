package epitech.epicture;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final int AUTHENTICATION = 0;
    private Account account = new Account();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLogin(View view) {
        Intent i = new Intent(this, AuthenticationActivity.class);
        i.putExtra("method", "login");
        startActivityForResult(i, AUTHENTICATION);
    }

    /*public void onLogout(View view) {
        Intent i = new Intent(this, AuthenticationActivity.class);
        i.putExtra("method", "logout");
        startActivity(i);
    }*/

    public void onSkipAuthentication(View view) {
        Intent i = new Intent(MainActivity.this, GalleryActivity.class);
        i.putExtra("account", account);
        startActivity(i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AUTHENTICATION: {
                if (resultCode == Activity.RESULT_OK) {
                    account.setUsername(data.getStringExtra("username"));
                    account.setAccessToken(data.getStringExtra("accessToken"));
                    account.setRefreshToken(data.getStringExtra("refreshToken"));
                    account.setAccountState(Account.AccountState.CONNECTED);
                    System.out.println(account.getUsername());
                    System.out.println(account.getAccessToken());
                    System.out.println(account.getRefreshToken());
                    onSkipAuthentication(null);
                }
                break;
            }
        }
    }
}
