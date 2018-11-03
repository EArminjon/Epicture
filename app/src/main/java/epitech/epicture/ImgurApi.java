package epitech.epicture;

import android.content.Context;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

class ImgurApi {

    public interface Interface {
        String function(String str);
    }

    private void get(Context context, String url, Map<String, String> map, Interface obj) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        obj.function(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        obj.function(error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                System.out.print(map);
                return map;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    void getGallery(Context context, Account account, Interface obj) {
        String section = "hot", sort = "time", window = "month", page = "0", viral = "false", mature = "false";

        String url = MessageFormat.format("https://api.imgur.com/3/gallery/{0}/{1}/{2}/{3}?showViral={4}&mature={5}",
                section, sort, window, page, viral, mature);

        Map<String, String> map = new HashMap<>();

        map.put("Authorization", "Client-ID " + context.getString(R.string.api_client_id));
        get(context, url, map, obj);
    }

    void getAccountSetting(Context context, Account account, Interface obj) {
        String url = "https://api.imgur.com/3/account/me/settings";
        Map<String, String> map = new HashMap<>();

        map.put("Authorization", "Bearer " + account.getAccessToken());
        get(context, url, map, obj);
    }

    void getAccountImages(Context context, Account account, Interface obj) {
        String url = "https://api.imgur.com/3/account/me/images";
        Map<String, String> map = new HashMap<>();

        map.put("Authorization", "Bearer " + account.getAccessToken());
        get(context, url, map, obj);
    }

    void getAccountFavorite(Context context, Account account, Interface obj) {
        String page = "0";
        String url = MessageFormat.format("https://api.imgur.com/3/account/{0}/favorites/{1}",
                account.getUsername(), page);
        Map<String, String> map = new HashMap<>();

        map.put("Authorization", "Bearer " + account.getAccessToken());
        get(context, url, map, obj);
    }
}
