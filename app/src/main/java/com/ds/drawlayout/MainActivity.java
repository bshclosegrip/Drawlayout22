package com.ds.drawlayout;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;

import com.ds.drawlayout.ui.main.MainFragment;
import com.ds.drawlayout.ui.map.MapFragment;
import com.ds.drawlayout.ui.home.HomeFragment;
import com.ds.drawlayout.ui.logout.LogoutFragment;
import com.ds.drawlayout.ui.notification.NotificationFragment;
import com.ds.drawlayout.ui.settings.SettingsFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ShareCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.ds.drawlayout.databinding.ActivityMainBinding;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding mBinding ;
    private HomeFragment mHomeFragment;
    private SettingsFragment mSettingsFragment;
    private MapFragment mMapFragment;
    private NotificationFragment mNotificationFragment;
    private MainFragment mMainFragment;
    private LogoutFragment mLogoutFragment;
    private BottomNavigationView mBottomNavigationView;
    public static String userID;
    private FirebaseAnalytics mFirebaseAnalytics;
    public Object getData() {
        return userID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setSupportActionBar(mBinding.appBarMain.toolbar);
        Bundle bundle = new Bundle();
        bundle.putString("edttext", "From Activity");
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        HomeFragment fragobj = new HomeFragment();
        fragobj.setArguments(bundle);

        mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view_tag);
        mLogoutFragment = new LogoutFragment();
        mMainFragment = new MainFragment();
        mMapFragment = new MapFragment();
        mNotificationFragment = new NotificationFragment();
        mSettingsFragment = new SettingsFragment();

//        FragmentManager fm = getChildFragmentManager();
//        mSettingsFragment = (SettingsFragment) fm.findFragmentById(R.id.recyclerview_settings);
        DrawerLayout drawer = mBinding.drawerLayout;
        NavigationView navigationView = mBinding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_map, R.id.nav_notification, R.id.nav_settings, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.getGraph();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view_bottom);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.navi_home:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, mHomeFragment).commit();
//                        mHomeFragment.setArguments(bundle);
//                        return true;
//                    case R.id.navi_map:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, mMapFragment).commit();
//                        return true;
//                    case R.id.navi_chatting:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, mNotificationFragment).commit();
//                        return true;
//                    case R.id.navi_myinfo:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, mSettingsFragment).commit();
//                        return true;
//                }
//            return false;
//            }
//        });

//        appBarConfig = AppBarConfiguration.Builder(R.id.starFragment, R.id.statsFragment, R.id.userFragment)
//                .setDrawerLayout(drawerLayout)
//                .build();
    }


    public void onFragmentChanged(int index){
        if(index == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, mSettingsFragment).commit();
        } else if(index == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, mLogoutFragment).commit();
        }
    }

    public void selectFrag(View view){
        Fragment fr;
        if(view == findViewById(R.id.button_login_facebook_fragment_home)){
            fr = new MapFragment();
        }else {
            fr = new LogoutFragment();
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.replace(R.id.mapview_fragment_map, getSupportFragmentManager());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu() : menu = " + menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.items_3dot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                // import com.google.firebase.auth.FirebaseAuth;
//                String uri = "intent:#Intent;component=com.ds.drawlayout.ui.settings.SettingsFragment;i.data=인트형데이터;end";
//                String uri = "intent:#Intent;component=com.ds.drawlayout.ui.settings.SettingsFragment;i.data=인트형데이터;end";
//                Intent intent = new Intent();
//                try {
//                    intent = Intent.parseUri(uri, 0);
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
//                startActivity(intent);

////                java.lang.IllegalArgumentException: No view found for id 0x7f0800e5 (com.ds.drawlayout2:id/fragment_container_view_tag) for fragment SettingsFragment{938c735} (fe88f469-fb7a-4eb6-b6f1-a19a631e9b5d id=0x7f0800e5)
//                onFragmentChanged(0);

//                //IllegalArgumentException: Wrong state class, expecting View State but received class com.google.android.material.navigation.NavigationBarView$SavedState instead. This usually happens when two views of different type have the same id in the same hierarchy. This view's id is id/nav_view_bottom. Make sure other views do not use the same id.
//                Intent intent = new Intent(getApplication(), SettingsFragment.class);
//                startActivity(intent);
//                return true;

//                SettingsFragment settingsFragment = new SettingsFragment();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.commit();

                // IllegalArgumentException: ID does not reference a View inside this Activity
//                moveToDetailSettings();

//                Intent intent = new Intent(getApplicationContext(), SettingsFragment.class);
//                startActivityFromFragment(mSettingsFragment, intent, 0);
//                return true;

//                Intent intent = new Intent(getApplicationContext(), SettingsFragment.class);
//                startActivity(intent);

            case R.id.action_logout:
                moveToDetailNotification();
                return true;

//                Intent intent2 = new Intent(getApplicationContext(), LogoutFragment.class);
//                startActivity(intent2);
//                onNavigationItemSelected(item);
//                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.d(TAG, "onSupportNavigateUp()");
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onSupportNavigateUp()");
        super.onDestroy();
    }

    public void moveToDetailNotification(){
//        switch(view.getId()) {
//            case R.id.action_nav_map_to_notification:
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.action_nav_home_to_noti);
//                break;

//            case R.id.action_nav_notifi_to_setting:
//                NavController navController1 = Navigation.findNavController(this, R.id.nav_host_fragment_container);
//                navController1.navigate(R.id.action_nav_notifi_to_setting);
//                break;

//            case 2:
//                NavController navController2 = Navigation.findNavController(this, R.id.nav_host_fragment_container);
//                navController2.navigate(R.id.action_nav_home_to_map);
//        }
    }

    public void moveToDetailSettings() {
        NavController navController1 = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        navController1.navigate(R.id.action_nav_home_to_setting);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navi_chatting) {

        } else if (id == R.id.navi_home) {

        } else if (id == R.id.navi_map) {

        } else if (id == R.id.navi_myinfo) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.items_3dot, menu);
//        menu.findItem(R.id.action_logout).setVisible(true);
//        menu.findItem(R.id.action_settings).setVisible(false);
//
//        // SearchView 셋팅
//        MenuItem item = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) item.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                //adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
}