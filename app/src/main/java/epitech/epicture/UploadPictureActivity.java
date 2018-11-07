package epitech.epicture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.FileNotFoundException;

public class UploadPictureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_picture);

        Account account = (Account) getIntent().getSerializableExtra("account");
        String imageLocation = (String) getIntent().getSerializableExtra("imageLocation");
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(openFileInput(imageLocation));

            ImageView image = (ImageView) findViewById(R.id.imageForUpload);
            float scaleHt = 500.0f / bitmap.getWidth();
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 500, (int) (bitmap.getWidth() * scaleHt), true);
            image.setImageBitmap(scaled);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        ImageButton cancelButton = (ImageButton) findViewById(R.id.CancelButton);
        cancelButton.setOnClickListener(v -> {
            finish();
        });

        if (bitmap != null) {
            ImageButton uploadButton = (ImageButton) findViewById(R.id.UploadButton);
            Bitmap finalBitmap = bitmap;
            uploadButton.setOnClickListener(v -> upload(account, finalBitmap));
        } else {
            ImageButton uploadButton = (ImageButton) findViewById(R.id.UploadButton);
            uploadButton.setVisibility(View.INVISIBLE);
        }

    }

    private void upload(Account account, Bitmap imageBitmap) {
        ImgurApi api = new ImgurApi();

        if (account != null)
            new Thread(() -> {
                api.postImageToAccount(this, account, imageBitmap);
                finish();
            }).start();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
