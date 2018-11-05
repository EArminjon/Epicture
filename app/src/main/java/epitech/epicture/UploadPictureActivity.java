package epitech.epicture;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class UploadPictureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_picture);

        Account account = (Account) getIntent().getSerializableExtra("account");
        Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra("image");

        ImageView image = (ImageView) findViewById(R.id.imageForUpload);

        float scaleHt = 500.0f / bitmap.getWidth();
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 500, (int) (bitmap.getWidth() * scaleHt), true);
        image.setImageBitmap(scaled);
    }

    private void upload(Account account, Bitmap imageBitmap) {
        ImgurApi api = new ImgurApi();

        if (account != null)
            new Thread(() -> api.postImageToAccount(getApplicationContext(), account, imageBitmap)).start();
    }
}
