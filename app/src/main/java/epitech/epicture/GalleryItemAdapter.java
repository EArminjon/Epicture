package epitech.epicture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


public class GalleryItemAdapter extends BaseAdapter {
    private Context _mContext;
    private GalleryItem[] _items;

    // 1
    GalleryItemAdapter(Context context, GalleryItem[] items) {
        _mContext = context;
        _items = items;
        System.out.print("LENGTH:");
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
        // 2
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(_mContext);
            convertView = layoutInflater.inflate(R.layout.item_for_gridview, parent, false);
        }

        // 3
        final ImageView imageView = convertView.findViewById(R.id.imageview_cover_art);
        final TextView datetextView = convertView.findViewById(R.id.textview_date);
        final TextView titleTextView = convertView.findViewById(R.id.textview_title);
        final TextView ownerTextView = convertView.findViewById(R.id.textview_owner);

        // 4
        Picasso.get().load("http://i.imgur.com/DvpvklR.png").resize(240, 240).into(imageView);
        datetextView.setText(item.getName());
        titleTextView.setText(item.getTitle());
        ownerTextView.setText(item.getName());
        return convertView;
    }

}
