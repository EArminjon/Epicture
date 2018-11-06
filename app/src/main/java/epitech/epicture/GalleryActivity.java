package epitech.epicture;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

public class GalleryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Account account;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Intent i = getIntent();
        account = (Account) i.getSerializableExtra("account");

        createPages();
        createNavigationBar();
        /* REPLACE VARIABLE */
        updateNavigationAccount();
    }

    private void createPages() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.gallery_activty_camera))); //0
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.gallery_activty_gallery))); //1
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.gallery_activty_upload))); // 2
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.gallery_activty_favorite))); // 3


        viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), account);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setScrollPosition(1, 0f, true);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void createNavigationBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void updateNavigationAccount() {
        ImgurApi api = new ImgurApi();

        new Thread(() -> api.getAccountSetting(getApplicationContext(), account, (String str) -> {
            try {
                JSONObject json = null;
                json = new JSONObject(str);

                TextView email = (TextView) findViewById(R.id.navigationAccountEmail);
                TextView username = (TextView) findViewById(R.id.navigationAccountName);

                email.setText(json.getJSONObject("data").get("email").toString());
                username.setText(json.getJSONObject("data").get("account_url").toString());
                if (json.getJSONObject("data").get("avatar") != null) {
                    new ImageFromUrl((ImageView) findViewById(R.id.navigationAccountImage)).execute("https://imgur.com/user/" + account.getUsername() + "/avatar");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return str;
        })).start();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (viewPager.getCurrentItem() == 1) {
            moveTaskToBack(true);
        } else if (viewPager.getCurrentItem() != 1) {
            viewPager.setCurrentItem(1);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            Intent i = new Intent(this, AuthenticationActivity.class);
            i.putExtra("method", "logout");
            startActivity(i);
            finish();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
