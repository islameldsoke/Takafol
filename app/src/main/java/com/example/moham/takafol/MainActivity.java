package com.example.moham.takafol;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.moham.takafol.Adapters.ViewPagerAdapter;
import com.example.moham.takafol.Fragments.HomeFragment;
import com.example.moham.takafol.Fragments.NotificationsFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    MaterialSearchView searchView;


    Drawable userPicDrwable;
    Bitmap userPicBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        viewPager= (ViewPager) findViewById(R.id.viewPager);
        SetupViewPager(viewPager);

        tabLayout= (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.home_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.notifications_icon);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);

        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setCursorDrawable(R.xml.my_cursor);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this,"You Searched for "+query,Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

                searchView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onSearchViewClosed() {
                searchView.setVisibility(View.INVISIBLE);

            }
        });

        userPicDrwable=getResources().getDrawable(R.drawable.frankenstein);
        userPicBitmap      = ((BitmapDrawable) userPicDrwable).getBitmap();


        new DrawerBuilder().withActivity(this).build();

        SetupProfileNavigationDrawer();



    }

    public void SetupViewPager(ViewPager viewPager)
    {

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new HomeFragment());
        viewPagerAdapter.AddFragment(new NotificationsFragment());

        viewPager.setAdapter(viewPagerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        MenuItem menuItem=menu.findItem(R.id.icon_search);
        searchView.setMenuItem(menuItem);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {

            super.onBackPressed();
        }
    }



    public void SetupProfileNavigationDrawer()
    {

        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.cover)
                .addProfiles(
                        new ProfileDrawerItem().withName("Mohamed Sa3d")
                                .withEmail("sa3d@gmail.com")
                                .withIcon(userPicBitmap)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();


        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem()
                .withIdentifier(1).withName("Home")
                .withIcon(R.drawable.home_icon_blach);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem()
                .withIdentifier(2).withName("Profile").withIcon(R.drawable.profile_icon);
        SecondaryDrawerItem item3 = new SecondaryDrawerItem()
                .withIdentifier(2).withName("Top Rated").withIcon(R.drawable.torated_icon);
        SecondaryDrawerItem item4 = new SecondaryDrawerItem()
                .withIdentifier(2).withName("Settings").withIcon(R.drawable.settings_icon);
        SecondaryDrawerItem item5 = new SecondaryDrawerItem()
                .withIdentifier(2).withName("About Us").withIcon(R.drawable.about_icon);
        SecondaryDrawerItem item6 = new SecondaryDrawerItem()
                .withIdentifier(2).withName("Logout").withIcon(R.drawable.logout_icon);



        //Now create your drawer and pass the AccountHeader.Result

        new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        item3,
                        item4,
                        item5,
                        item6

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        Toast.makeText(getBaseContext(),"position selected is"+position,Toast.LENGTH_LONG).show();
                       return true;
                    }
                })
                .build();
    }


}
