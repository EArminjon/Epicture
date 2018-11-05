package epitech.epicture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GalleryItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_item);

        GalleryItem item = (GalleryItem) getIntent().getSerializableExtra("item");
        System.out.print(item);
    }
}
