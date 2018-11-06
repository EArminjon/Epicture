package epitech.epicture;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryListFragment extends Fragment {

    Account account;
    View myFragmentView;

    public GalleryListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        if (bundle == null)
            return inflater.inflate(R.layout.gallery_fragment, container, false);
        account = (Account) bundle.getSerializable("account");

        myFragmentView = inflater.inflate(R.layout.gallery_fragment, container, false);
        loadGalleryContent(myFragmentView, account);
        return myFragmentView;
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible) {
            loadGalleryContent(myFragmentView, account);
            onResume();
        }
    }


    private void loadGalleryContent(View myFragmentView, Account account) {
        ImgurApi api = new ImgurApi();

        if (account != null)
            new Thread(() -> api.getGallery(getContext(), account, (String str) -> {
                try {
                    JSONArray jsonarray = new JSONObject(str).getJSONArray("data");
                    GalleryItem[] items = new GalleryItem[jsonarray.length() > 14 ? 14 : jsonarray.length()];
                    //TODO: J'mets une limite car j'ai pas envie d'enculer mon forfait en affichant les images
                    for (int i = 0; i < jsonarray.length() && i < 14; i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String id = jsonobject.get("id").toString();
                        String title = jsonobject.get("title").toString();
                        String name = jsonobject.get("account_url").toString();
                        GalleryImageItem[] images = null;
                        if (jsonobject.has("images")) {
                            JSONArray jsonImagesArray = jsonobject.getJSONArray("images");
                            images = new GalleryImageItem[jsonImagesArray.length()];
                            for (int j = 0; j < jsonImagesArray.length(); j++) {
                                JSONObject jsonImageobject = jsonImagesArray.getJSONObject(j);
                                String imageId = jsonImageobject.get("id").toString();
                                String imageType = jsonImageobject.get("type").toString();
                                String imageLink = jsonImageobject.get("link").toString();
                                GalleryImageItem image = new GalleryImageItem(imageId, imageType, imageLink);
                                images[j] = image;
                            }
                        }
                        GalleryItem item = new GalleryItem(id, title, name, images);
                        items[i] = item;
                    }

                    GridView gridView = (GridView) myFragmentView.findViewById(R.id.messageArea);
                    final GalleryItemAdapter adapter = new GalleryItemAdapter(getActivity(), items);
                    gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener((parent, view, position, id) -> {
                        GalleryItem item = items[position];
                        Intent intent = new Intent((Activity) getContext(), GalleryItemActivity.class);
                        intent.putExtra("item", item);
                        intent.putExtra("account", account);
                        startActivity(intent);
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return str;
            })).start();
    }

}
