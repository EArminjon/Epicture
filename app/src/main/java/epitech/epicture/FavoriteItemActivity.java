package epitech.epicture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class FavoriteItemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_item);

        GalleryItem item = (GalleryItem) getIntent().getSerializableExtra("item");
        Account account = (Account) getIntent().getSerializableExtra("account");
        ImageView image = (ImageView) findViewById(R.id.favoriteItemPictureView);
        VideoView videoView = (VideoView) findViewById(R.id.favoriteItemVideoView);
        String[] array = item.getImages()[0].getType().split("/");
        String url = item.getImages()[0].getLink();
        ItemImageGenerator generator = new ItemImageGenerator();
        if (array[0].equals("image")) {
            videoView.setVisibility(View.INVISIBLE);
            image.setVisibility(View.VISIBLE);
            generator.pictureFromPictureUrl(url, image, 360, 240);
        } else {
            image.setVisibility(View.INVISIBLE);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoPath(url);
            videoView.start();
        }

        String title = item.getTitle();
        TextView textView = (TextView) findViewById(R.id.favoriteItemTitle);
        textView.setText(title);

        ImageButton favoriteButton = (ImageButton) findViewById(R.id.FavoriteButtonFavoriteItem);
        favoriteButton.setOnClickListener(v -> {
            ImgurApi api = new ImgurApi();
            if (account != null)
                new Thread(() -> api.postImageToFavorite(this, account, item.getImages()[0], (String str) -> {
                    System.out.print("FAV:" + str + "\n");
                    return str;
                })).start();
            favoriteButton.setVisibility(View.INVISIBLE);
        });

        ImageButton downloadButton = (ImageButton) findViewById(R.id.DownloadButtonFavoriteItem);
        downloadButton.setOnClickListener(v -> {
            //TODO
        });

        ImageButton deleteButton = (ImageButton) findViewById(R.id.TrashButtonFavoriteItem);
        deleteButton.setOnClickListener(v -> {
            ImgurApi api = new ImgurApi();
            if (account != null)
                new Thread(() -> api.delImageFromAccount(this, account, item.getImages()[0], (String str) -> {
                    System.out.print("FAV:" + str + "\n");
                    finish();
                    return str;
                })).start();
            deleteButton.setVisibility(View.INVISIBLE);
        });

    }
}
