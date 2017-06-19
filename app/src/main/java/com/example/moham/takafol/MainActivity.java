package com.example.moham.takafol;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    MaterialSearchView searchView;
    String picUrlStr;
    Bitmap profile_pic_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        SetupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.home_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.notifications_icon);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);

        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setCursorDrawable(R.xml.my_cursor);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "You Searched for " + query, Toast.LENGTH_LONG).show();
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

        String user_id = getSharedPreferences("user_id", 0).getString("user_id", "");
        String url_num = "http://takafull.000webhostapp.com/Api.php?INFOUID=" + user_id;
        StringRequest num_request = new StringRequest(Request.Method.GET, url_num, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String pic_url = jsonObject.getString("profile_image");
                        String username = jsonObject.getString("username");
                        String email = jsonObject.getString("email");
                        // String cover_url=jsonObject.get("cover_image");


                        SharedPreferences user_info = getSharedPreferences("user_info", 0);
                        SharedPreferences.Editor editor = user_info.edit();
                        editor.putString("username", username);
                        editor.putString("email", email);
                        editor.putString("pic_url", pic_url);
                        editor.commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(num_request);

        picUrlStr = "http://khaledapi.000webhostapp.com/" +
                getSharedPreferences("user_info", 0).getString("pic_url", "");
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    profile_pic_bitmap = Glide.with(MainActivity.this).load(picUrlStr).asBitmap().into(-1, -1).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

        new DrawerBuilder().withActivity(this).build();
        SetupProfileNavigationDrawer();
    }

    public void SetupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new HomeFragment());
        viewPagerAdapter.AddFragment(new NotificationsFragment());
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.icon_search);
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


    public void SetupProfileNavigationDrawer() {
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.cover)
                .addProfiles(
                        new ProfileDrawerItem().withName(getSharedPreferences("user_info", 0).getString("username", ""))
                                .withEmail(getSharedPreferences("user_info", 0).getString("email", ""))
                                .withIcon(profile_pic_bitmap)
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
                .withIdentifier(1).withName("الصفحه الرئيسيه")
                .withIcon(R.drawable.home_icon_blach);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem()
                .withIdentifier(2).withName("الملف الشخصي").withIcon(R.drawable.profile_icon);
        SecondaryDrawerItem item3 = new SecondaryDrawerItem()
                .withIdentifier(2).withName("أهم الاحداث").withIcon(R.drawable.torated_icon);
        SecondaryDrawerItem item4 = new SecondaryDrawerItem()
                .withIdentifier(2).withName("اقتراحات للمتابعه").withIcon(R.drawable.settings_icon);
        SecondaryDrawerItem item5 = new SecondaryDrawerItem()
                .withIdentifier(2).withName("عن المطورين").withIcon(R.drawable.about_icon);
        SecondaryDrawerItem item6 = new SecondaryDrawerItem()
                .withIdentifier(2).withName("تسجيل الخروج").withIcon(R.drawable.logout_icon);


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

                        Toast.makeText(getBaseContext(), "position selected is" + position, Toast.LENGTH_LONG).show();

                        switch (position) {
                            case 3:
                                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        }
                        return true;
                    }
                })
                .build();
    }
}
