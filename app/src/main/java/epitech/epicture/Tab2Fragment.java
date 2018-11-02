package epitech.epicture;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2Fragment extends Fragment {

    public Tab2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ImgurApi api = new ImgurApi();
        Bundle bundle = getArguments();
        if (bundle == null) {
            System.out.println("Tab2Frag ERRORRRRRRRRRRRRRRRRRRRRR");
            return inflater.inflate(R.layout.fragment_tab2, container, false);
        }
        Account account = (Account) bundle.getSerializable("account");

        View myFragmentView = inflater.inflate(R.layout.fragment_tab2, container, false);

        if (account != null)
            api.getGallery(this.getContext(), account, (String str) -> {
                try {
                    JSONArray jsonarray = new JSONObject(str).getJSONArray("data");

                    GalleryItem[] items = new GalleryItem[jsonarray.length()];

                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String id = jsonobject.get("id").toString();
                        String title = jsonobject.get("title").toString();
                        String name = jsonobject.get("account_url").toString();
                        GalleryItem item = new GalleryItem(id, title, name);
                        items[i] = item;
                    }

                    GridView gridView = (GridView) myFragmentView.findViewById(R.id.messageArea);
                    final GalleryItemAdapter adapter = new GalleryItemAdapter(getActivity(), items);
                    gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener((parent, view, position, id) -> {
                        GalleryItem item = items[position];
                        /*displayInfo(item);*/
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*System.out.println("HERE: " + str);*/
                return str;
            });


        return myFragmentView;
    }

}
