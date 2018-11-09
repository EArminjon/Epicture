package epitech.epicture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class GalleryItemAdapter extends BaseAdapter {
    private Context _mContext;
    private GalleryItem[] _items;

    // 1
    GalleryItemAdapter(Context context, GalleryItem[] items) {
        _mContext = context;
        _items = items;
        System.out.print(_items.length);
    }

    // 2
    @Override
    public int getCount() {
        return _items.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 4
    @Override
    public Object getItem(int position) {
        System.out.print("ITEM: " + position);
        return _items[position];
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final GalleryItem item = _items[position];
        if (item == null) {
            System.out.print("y'a une erreur chelou");
        }
        // 2
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(_mContext);
            convertView = layoutInflater.inflate(R.layout.item_for_gridview, parent, false);
        }

        // 3
        final ImageView imageView = convertView.findViewById(R.id.imageview_cover_art);
        final TextView titleTextView = convertView.findViewById(R.id.textview_title);
        final TextView nameTextView = convertView.findViewById(R.id.textview_name);

        // 4
        GalleryImageItem[] images = item.getImages();
        if (images != null) {
            String[] array = images[0].getType().split("/");
            String url = item.getImages()[0].getLink();
            ItemImageGenerator generator = new ItemImageGenerator();
            if (array[0].equals("image")) {
                generator.pictureFromPictureUrl(url, imageView, 360, 240);
            } else {
                generator.pictureFromPictureUrl("https://www.rig.net/wp-content/uploads/vimeo-play-button-icon.png", imageView, 360, 240);
            }
        }

        titleTextView.setText(item.getTitle());
        nameTextView.setText(item.getName());
        return convertView;
    }
}
