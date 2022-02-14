package com.ds.drawlayout;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ds.drawlayout.ui.main.MainFragment;
import com.ds.drawlayout.ui.map.MapFragment;
import com.ds.drawlayout.ui.home.HomeFragment;
import com.ds.drawlayout.ui.logout.LogoutFragment;
import com.ds.drawlayout.ui.media.MediaFragment;
import com.ds.drawlayout.ui.notification.NotificationFragment;
import com.ds.drawlayout.ui.settings.SettingsFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.ds.drawlayout.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private static final String KEY_TEXT_REPLY = "key_text_reply";
    private static String CHANNEL_ID ="channel1";
    private static String CHANNEL_NAME="Channel1";
    private static String CHANNEL_ID2 ="channel2";
    private static String CHANNEL_NAME2="Channel2";

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding mBinding ;
    private HomeFragment mHomeFragment;
    private SettingsFragment mSettingsFragment;
    private MapFragment mMapFragment;
    private NotificationFragment mNotificationFragment;
    private MainFragment mMainFragment;
    private LogoutFragment mLogoutFragment;
    private MediaFragment mMediaFragment;
    private BottomNavigationView mBottomNavigationView;
    public static String userID;
    private FirebaseAnalytics mFirebaseAnalytics;
    public Object getData() {
        return userID;
    }
    private NotificationManager mNotificationManager;
    private TabLayout mTabLayout;
    private static View v;


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

//        mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view_tag);
        mHomeFragment = new HomeFragment();
        mHomeFragment.setArguments(bundle);
        mLogoutFragment = new LogoutFragment();
        mMainFragment = new MainFragment();
        mMapFragment = new MapFragment();
        mNotificationFragment = new NotificationFragment();
        mSettingsFragment = new SettingsFragment();

//        mTabLayout = findViewById(R.id.tab_layout_appbar);
//        mTabLayout.addTab(mTabLayout.newTab().setText("친구"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("채팅"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("더보기"));
//        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                int position = tab.getPosition();
//                Fragment selected = null;
//                if(position == 0)
//                    selected = mSettingsFragment;
//                else if(position == 1)
//                    selected = mHomeFragment;
//                else if(position == 2)
//                    selected = mLogoutFragment;
//                // NullPointerException: Attempt to invoke virtual method 'java.lang.Class java.lang.Object.getClass()' on a null object reference
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

//        FragmentManager fm = getChildFragmentManager();
//        mSettingsFragment = (SettingsFragment) fm.findFragmentById(R.id.recyclerview_settings);

        DrawerLayout drawer = mBinding.drawerLayout;
        NavigationView navigationView = mBinding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_map, R.id.nav_notification, R.id.nav_settings, R.id.nav_logout, R.id.nav_media, R.id.nav_info /*R.id.navi_home, R.id.navi_chatting, R.id.navi_map, R.id.navi_myinfo*/)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.getGraph();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view_bottom);
//        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            Fragment selectedFragment = null;
//            switch (item.getItemId()) {
//                case R.id.navi_home:
//                    selectedFragment = mSettingsFragment;
//                    break;
//                case R.id.navi_map:
//                    selectedFragment = mMapFragment;
//                    break;
//                case R.id.navi_chatting:
//                    selectedFragment = mNotificationFragment;
//                    break;
//                case R.id.navi_myinfo:
//                    selectedFragment = mMediaFragment;
//                    break;
//            }
////            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            if (selectedFragment != null) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, selectedFragment).commit();
//            }
//            return true;
//        });

//        mBottomNavigationView.setOnClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public void onClick(View view) {
//                switch (view.getId()) {
//                    case R.id.navi_home:
//                        MainActivity activity = (MainActivity) getActivity();
//                        activity.onFragmentChanged(0);
//                    case R.id.navi_map:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, mMapFragment).commit();
//                    case R.id.navi_chatting:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, mNotificationFragment).commit();
//                    case R.id.navi_myinfo:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, mSettingsFragment).commit();
//                }
//            }
//        });

//        // NullPointerException: Attempt to invoke virtual method 'void com.google.android.material.bottomnavigation.BottomNavigationView.setOnNavigationItemSelectedListener(com.google.android.material.bottomnavigation.BottomNavigationView$OnNavigationItemSelectedListener)' on a null object reference
//        mBottomNavigationView = findViewById(R.id.nav_view_bottom);
//        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.navi_home:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, mHomeFragment).commit();
//                        mHomeFragment.setArguments(bundle);
//                        return true;
//                    case R.id.navi_map:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mMapFragment).commit();
//                        return true;
//                    case R.id.navi_chatting:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mNotificationFragment).commit();
//                        return true;
//                    case R.id.navi_myinfo:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mSettingsFragment).commit();
//                        return true;
//                }
//            return false;
//            }
//        });

//        appBarConfig = AppBarConfiguration.Builder(R.id.starFragment, R.id.statsFragment, R.id.userFragment)
//                .setDrawerLayout(drawerLayout)
//                .build();

//        Notification notification = new Notification.Builder(this, CHANNEL_ID)
//                .setStyle(new NotificationCompat.MessagingStyle("Me")
//                        .setConversationTitle("Team lunch")
//                        .addMessage("Hi", timestamp1, null) // Pass in null for user.
//                        .addMessage("What's up?", timestamp2, "Coworker")
//                        .addMessage("Not much", timestamp3, null)
//                        .addMessage("How about lunch?", timestamp4, "Coworker"))
//                .build();

        Intent intent = new Intent(this, SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        String replyLabel = getResources().getString(R.string.reply_label);
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(replyLabel)
                .build();
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_arrow)
//                .setContentTitle("Title")
//                .setContentText("Context for Text")
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText("클릭하세요..."))
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.notify(getTaskId(), builder.build());
        
//        String replyLabel = getResources().getString(R.string.reply_label);
//        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
//                .setLabel(replyLabel)
//                .build();
//
//        PendingIntent replyPendingIntent =
//                PendingIntent.getBroadcast(getApplicationContext(),
//                        conversation.getConversationId(),
//                        getMessageReplyIntent(conversation.getConversationId()),
//                        PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Action action =
//                new NotificationCompat.Action.Builder(R.drawable.ic_menu_camera,
//                        getString(R.string.hide_bottom_view_on_scroll_behavior), replyPendingIntent)
//                        .addRemoteInput(remoteInput)
//                        .build();
//
//        Notification newMessageNotification = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_menu_camera)
//                .setContentTitle("답장하기")
//                .setContentText(getString(R.string.content))
//                .addAction(action)
//                .build();
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.notify(getTaskId(), newMessageNotification);

        getHashKey();
    }

    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }

    public void selectFrag(MenuItem view){
        Fragment fr;
        if(view == findViewById(R.id.action_settings)){
            fr = new SettingsFragment();
        }else {
            fr = new LogoutFragment();
        }
        androidx.fragment.app.FragmentManager fm = getSupportFragmentManager();
        androidx.fragment.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_view_tag, fr).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu() : menu = " + menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.items_3dot, menu);
        return true;
    }

    public void onFragmentChanged(int index) {
            if (index == 0) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mSettingsFragment).commit();
                getSupportFragmentManager().beginTransaction().addToBackStack(null);
                // bsh
                onDestroyView();
            } else if (index == 1) {
                // null error // fixed : new LogoutFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mLogoutFragment).addToBackStack(null).commit();
                getSupportFragmentManager().beginTransaction().addToBackStack(null);
                onDestroyView();
            } else if (index == 2) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mHomeFragment).addToBackStack(null).commit();
                getSupportFragmentManager().beginTransaction().addToBackStack(null);
                onDestroyView();
            } else if (index == 3) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mMainFragment).addToBackStack(null).commit();
                getSupportFragmentManager().beginTransaction().addToBackStack(null);
                onDestroyView();

            } else if (index == 4) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, mNotificationFragment).addToBackStack(null).commit();
                getSupportFragmentManager().beginTransaction().addToBackStack(null);
                onDestroyView();
            }
    }

    public void onDestroyView() {
        if(v!=null){
            ViewGroup parent = (ViewGroup)v.getParent();
            if(parent!=null){
                parent.removeView(v);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // IllegalArgumentException: No view found for id 0x7f0800e5 (com.ds.drawlayout2:id/fragment_container_view_tag) for fragment SettingsFragment{e9ed7bc} (4b147e26-f138-42d7-996a-34ea17f2ed71 id=0x7f0800e5)
//                selectFrag(findViewById(R.id.action_settings));
                onFragmentChanged(0);
                return true;

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
                onFragmentChanged(1);
                return true;

//                moveToDetailNotification();

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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        Log.d(TAG, "onSupportNavigateUp() : navController = " + navController + ", mAppBarConfiguration = " + mAppBarConfiguration);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy()");
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
            Intent intent = new Intent(this, NotificationFragment.class);
            startActivity(intent);
        } else if (id == R.id.navi_home) {
            Intent intent = new Intent(this, HomeFragment.class);
            startActivity(intent);
        } else if (id == R.id.navi_map) {
            Intent intent = new Intent(this, MapFragment.class);
            startActivity(intent);
        } else if (id == R.id.navi_myinfo) {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
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

    public void showNoti1() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (mNotificationManager.getNotificationChannel(CHANNEL_ID) == null) {
                mNotificationManager.createNotificationChannel(new NotificationChannel(
                        CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
                ));

                builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            }
        } else {
            builder = new NotificationCompat.Builder(this);
        }
        builder.setContentTitle("간단 알림");
        builder.setContentText("알림 메시지입니다.");
        builder.setSmallIcon(android.R.drawable.ic_menu_view);
        Notification noti = builder.build();
        mNotificationManager.notify(1, noti);
    }

    public void showNoti2() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (mNotificationManager.getNotificationChannel(CHANNEL_ID2) == null) {
                mNotificationManager.createNotificationChannel(new NotificationChannel(
                        CHANNEL_ID2, CHANNEL_NAME2, NotificationManager.IMPORTANCE_DEFAULT
                ));

                builder = new NotificationCompat.Builder(this, CHANNEL_ID2);
            }
        } else {
            builder = new NotificationCompat.Builder(this);
        }

        Intent intent = new Intent(this, MainActivity.class);
        //PendingIntent 객체 만들기
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentTitle("간단 알림");
        builder.setContentText("알림 메시지입니다.");
        builder.setSmallIcon(android.R.drawable.ic_menu_view);
        builder.setAutoCancel(true);
        //빌더에 PendingIntent 객체 설정하기
        builder.setContentIntent(pendingIntent);

        Notification noti = builder.build();

        mNotificationManager.notify(2, noti);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

//    private void ShortcutIcon(){
//        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
//        boolean shortCutWasAlreadyAdded = sharedPreferences.getBoolean(PREF_KEY_SHORTCUT_ADDED, false);
//        if (shortCutWasAlreadyAdded) return;
//        Intent shortcutIntent = new Intent(getApplicationContext(), MainActivity.class);
//        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        Intent addIntent = new Intent();
//        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
//        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,getResources().getString(R.string.app_name));
//        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher));
//        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
//        getApplicationContext().sendBroadcast(addIntent);
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.commit();
//    }
}