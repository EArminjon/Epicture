package epitech.epicture;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


class TestManager {
    private Boolean end;

    TestManager() {

    }

    void start() {
        end = false;
    }

    void wait(int max) throws InterruptedException {
        int i = 0;
        while (!end && i < max) {
            Thread.sleep(1000);
            i += 1;
        }
    }

    void success() {
        end = true;
    }

    void terminate() {
        if (!end) {
            Log.d("result", "fail");
            Assert.assertEquals(true, false);
        }
    }
}

@RunWith(AndroidJUnit4.class)
public class ImgurAPIInstrumentedTest {

    @Test
    public void getGallery() throws InterruptedException {
        TestManager manager = new TestManager();

        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        GridSetting setting = new GridSetting();
        Account account = new Account();
        account.setUsername("Relaxas");
        account.setAccessToken("1458091ed58b7ca9c1ef234fe21ef40d1d082276");

        manager.start();
        ImgurApi api = new ImgurApi();
        api.getGallery(appContext, account, setting, (String str) -> {
            Log.d("json", str);
            try {
                int code = new JSONObject(str).getInt("status");
                if (code == 200)
                    manager.success();
                else
                    Log.d("ERROR CODE, except 200, received: " + code, "getGallery: json fail.");
            } catch (JSONException e) {
                Log.d("JSON FAIL", "getGallery: json fail.");
            }
            return str;
        });
        manager.wait(10);
        manager.terminate();
    }
}
