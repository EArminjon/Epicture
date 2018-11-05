package epitech.epicture;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private int tabCount;
    private Account account;

    TabPagerAdapter(FragmentManager fm, int numberOfTabs, Account account) {
        super(fm);
        this.tabCount = numberOfTabs;
        this.account = account;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: {
                Bundle bundle = new Bundle();
                Fragment frag = new CameraFragment();
                bundle.putSerializable("account", account);
                frag.setArguments(bundle);
                return frag;
            }
            case 1: {
                Bundle bundle = new Bundle();
                Fragment frag = new GalleryListFragment();
                bundle.putSerializable("account", account);
                frag.setArguments(bundle);
                return frag;
            }
            case 2: {
                Bundle bundle = new Bundle();
                Fragment frag = new MyUploadFragment();
                bundle.putSerializable("account", account);
                frag.setArguments(bundle);
                return frag;
            }
            case 3: {
                Bundle bundle = new Bundle();
                Fragment frag = new FavoriteFragment();
                bundle.putSerializable("account", account);
                frag.setArguments(bundle);
                return frag;
            }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
