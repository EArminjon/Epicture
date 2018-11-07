package epitech.epicture;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

class ImgurApi {

    public interface Interface {
        String function(String str);
    }

    private void send(Context context, int method, String url, Map<String, String> header, String body, Interface obj) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(method, url,
                response -> {
                    if (obj != null)
                        obj.function(response);

                },
                error -> {
                    if (obj != null)
                        obj.function(error.toString());
                }) {


            @Override
            public byte[] getBody() {
                return body != null ? body.getBytes(StandardCharsets.UTF_8) : null;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() {
                return header;
            }
        };
        if (method == Request.Method.POST) // because volley is shit and upload image twice time, or imgur is shit maybe
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    void getGallery(Context context, Account account, Interface obj) {
        String section = "hot", sort = "time", window = "month", page = "0", viral = "false", mature = "false";

        String url = MessageFormat.format("https://api.imgur.com/3/gallery/{0}/{1}/{2}/{3}?showViral={4}&mature={5}",
                section, sort, window, page, viral, mature);

        Map<String, String> map = new HashMap<>();

        map.put("Authorization", "Client-ID " + context.getString(R.string.api_client_id));
        send(context, Request.Method.GET, url, map, null, obj);
    }

    void getAccountSetting(Context context, Account account, Interface obj) {
        String url = "https://api.imgur.com/3/account/me/settings";
        Map<String, String> map = new HashMap<>();

        map.put("Authorization", "Bearer " + account.getAccessToken());
        send(context, Request.Method.GET, url, map, null, obj);
    }

    void getAccountImages(Context context, Account account, Interface obj) {
        String url = "https://api.imgur.com/3/account/me/images";
        Map<String, String> map = new HashMap<>();

        map.put("Authorization", "Bearer " + account.getAccessToken());
        send(context, Request.Method.GET, url, map, null, obj);
    }

    void getAccountFavorite(Context context, Account account, Interface obj) {
        String page = "0";
        String url = MessageFormat.format("https://api.imgur.com/3/account/{0}/favorites/{1}",
                account.getUsername(), page);
        Map<String, String> map = new HashMap<>();

        map.put("Authorization", "Bearer " + account.getAccessToken());
        send(context, Request.Method.GET, url, map, null, obj);
    }

    private String get64BaseImage(Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    void postImageToAccount(Context context, Account account, Bitmap image) {
        String url = "https://api.imgur.com/3/image";
        Map<String, String> map = new HashMap<>();
        String encoded = get64BaseImage(image);

        JSONObject json = new JSONObject();
        try {
            json.put("image", encoded);
            json.put("type", "base64");
            json.put("title", "test");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put("Authorization", "Bearer " + account.getAccessToken());
        send(context, Request.Method.POST, url, map, json.toString(), null);
    }

    void postImageToFavorite(Context context, Account account, GalleryImageItem image, Interface obj) {
        String url = MessageFormat.format("https://api.imgur.com/3/image/{0}/favorite",
                image.getId());
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + account.getAccessToken());
        send(context, Request.Method.POST, url, map, null, obj);
    }

    void delImageFromAccount(Context context, Account account, GalleryImageItem image, Interface obj) {
        String url = MessageFormat.format("https://api.imgur.com/3/image/{0}",
                image.getId());
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + account.getAccessToken());
        send(context, Request.Method.DELETE, url, map, null, obj);
    }

    void get(Context context, String url, Interface obj) {
        send(context, Request.Method.GET, url, new HashMap<>(), null, obj);
    }
}
