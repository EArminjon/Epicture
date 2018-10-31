package epitech.epicture;

import android.content.Context;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ImgurApi {

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

    void getAccountSetting(Context context, Account account, Interface obj) {
        String url = "https://api.imgur.com/3/account/me/settings";
        Map<String, String> map = new HashMap<String, String>();

        map.put("Authorization", "Bearer " + account.getAccessToken());
        get(context, url, map, obj);
    }
}
