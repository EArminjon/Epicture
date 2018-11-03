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


/**
 * A simple {@link Fragment} subclass.
 */
public class MyUploadFragment extends Fragment {

    public MyUploadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        if (bundle == null)
            return inflater.inflate(R.layout.my_upload_fragment, container, false);
        Account account = (Account) bundle.getSerializable("account");

        View myFragmentView = inflater.inflate(R.layout.my_upload_fragment, container, false);
        loadContent(myFragmentView, account);
        return myFragmentView;
    }

    private void loadContent(View myFragmentView, Account account) {
        ImgurApi api = new ImgurApi();

        if (account != null)
            api.getAccountImages(this.getContext(), account, (String str) -> {
                System.out.print("OUAI: " + str);
                try {
                    JSONArray jsonarray = new JSONObject(str).getJSONArray("data");
                    GalleryItem[] items = new GalleryItem[jsonarray.length() > 14 ? 14 : jsonarray.length()];
                    //TODO: J'mets une limite car j'ai pas envie d'enculer mon forfait en affichant les images
                    for (int i = 0; i < jsonarray.length() && i < 14; i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String id = jsonobject.get("id").toString();
                        String title = jsonobject.get("name").toString();
                        String name = jsonobject.get("account_url").toString();
                        String type = jsonobject.get("type").toString();
                        String link = jsonobject.get("link").toString();
                        GalleryImageItem[] images = {new GalleryImageItem(id, type, link)};
                        GalleryItem item = new GalleryItem(id, title, name, images);
                        items[i] = item;
                    }

                    GridView gridView = (GridView) myFragmentView.findViewById(R.id.messageArea2);
                    final GalleryItemAdapter adapter = new GalleryItemAdapter(getActivity(), items);
                    gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener((parent, view, position, id) -> {
                        GalleryItem item = items[position];
                        /*displayInfo(item);*/
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("HERE: " + str);
                return str;
            });
    }

}
