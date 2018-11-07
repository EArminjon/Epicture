package epitech.epicture;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GalleryItemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_item);

        GalleryItem item = (GalleryItem) getIntent().getSerializableExtra("item");
        Account account = (Account) getIntent().getSerializableExtra("account");
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

        ImageButton favoriteButton = (ImageButton) findViewById(R.id.FavoriteButtonGalleryItem);
        favoriteButton.setOnClickListener(v -> {
            ImgurApi api = new ImgurApi();

            if (account != null)
                new Thread(() -> api.postImageToFavorite(this, account, item.getImages()[0], (String str) -> {
                    System.out.print("FAV:" + str + "\n");
                    return str;
                })).start();
            favoriteButton.setVisibility(View.INVISIBLE);
        });

        ImageButton downloadButton = (ImageButton) findViewById(R.id.DownloadButtonGalleryItem);
        downloadButton.setOnClickListener(v -> {
            //TODO
        });

    }
}
