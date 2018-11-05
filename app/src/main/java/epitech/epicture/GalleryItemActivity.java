package epitech.epicture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class GalleryItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_item);

        GalleryItem item = (GalleryItem) getIntent().getSerializableExtra("item");
        ImageView image = (ImageView) findViewById(R.id.galleryItemView);

        String[] array = item.getImages()[0].getType().split("/");
        if (array[0].equals("image")) {
            String url = item.getImages()[0].getLink();
            Picasso.get()
                    .load(url)
                    .resize(360, 240)
                    .centerInside()
                    .into(image);
        } else
            Picasso.get()
                    .load("http://i.imgur.com/DvpvklR.png")
                    .resize(360, 240)
                    .centerInside()
                    .into(image);

        String title = item.getTitle();
        TextView textView = (TextView) findViewById(R.id.galleryItemTitle);
        textView.setText(title);

    }
}
