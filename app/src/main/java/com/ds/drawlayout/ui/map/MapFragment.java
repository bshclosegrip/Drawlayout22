package com.ds.drawlayout.ui.map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.ds.drawlayout.MainActivity;
import com.ds.drawlayout.R;
import com.ds.drawlayout.SignUpActivity;
import com.ds.drawlayout.databinding.FragmentMapBinding;
import com.ds.drawlayout.ui.home.HomeFragment;
import com.ds.drawlayout.ui.notification.NotificationFragment;
import com.ds.drawlayout.ui.settings.SettingsFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MapFragment extends Fragment {
    private final String TAG = "MapFragment";
    private MapViewModel mapViewModel;
    private FragmentMapBinding binding;
//    private MapView mMapView;
//    private GoogleMap mGoogleMap = null;
    private Button mButtonGoogleMap, mButtonNaverMap;
    private ViewPager mViewPager;
    private String mData, ARG_POSITION;
    private int position = 0;
    private FloatingActionButton mFloatingActionButton;
    private MainActivity mMainActivitiy;

    public MapFragment() {
        // required
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        mGoogleMap = (GoogleMap) root.findViewById(mMapView);
//        mGoogleMap = root.findViewById(R.id.map);
        mButtonGoogleMap = root.findViewById(R.id.button_google_map_fragment_gallery);
        mButtonNaverMap = root.findViewById(R.id.button_kakao_map_fragment_gallery);
        mViewPager = root.findViewById(R.id.viewpager_fragment_gallery);
        mViewPager.setAdapter(new pagerAdapter(getChildFragmentManager()));
        mViewPager.setCurrentItem(0);

        View.OnClickListener movePageListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int)view.getTag();
                Log.d(TAG, "onClick() : tag = " + tag);
                mViewPager.setCurrentItem(tag);
//                WebView wv = new WebView(getContext());
//                wv.startViewTransition(view);
            }
        };

        mButtonGoogleMap.setOnClickListener(movePageListener);
        mButtonGoogleMap.setTag(0);
        mButtonNaverMap.setOnClickListener(movePageListener);
        mButtonNaverMap.setTag(1);
//        onMapReady(mGoogleMap);

        Bundle extra = getArguments();
        if(extra != null){
            mData = extra.getString("data2");
        }

        mFloatingActionButton = root.findViewById(R.id.floating_button_fragment_map);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick : FAB");
                startActivity(new Intent(getContext(), SignUpActivity.class));

//                Bundle bundle = new Bundle();
//                bundle.putString("From MapFragment", "MapFragment의 번들데이터");
//                onDestroyView();
//                onDestroy();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                SettingsFragment settingsFragment = new SettingsFragment();
//                settingsFragment.setArguments(bundle);
//                transaction.replace(R.id.viewpager_fragment_gallery, settingsFragment);
//                transaction.commit();

//                mMainActivitiy = new MainActivity();
//                mMainActivitiy.onFragmentChanged(1);

//                // NullPointerException: Attempt to invoke virtual method 'void com.ds.drawlayout.MainActivity.onFragmentChanged(int)' on a null object reference
//                mMainActivitiy.onFragmentChanged(0);

//                // recycler overlap error
//                MainActivity activity = (MainActivity) getActivity();
//                activity.onFragmentChanged(R.id.nav_settings);

//                MainActivity activity = (MainActivity) getActivity();
//                activity.moveToDetailSettings();
//                Fragment fragment = new NotificationFragment();
//                FragmentTransaction transaction = new getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragment_container_view_tag, SettingsFragment.class);
//                transaction.addToBackStack(null);
//                transaction.commit();

//                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mFloatingActionButton).commit();

            }
        });
        return root;
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        binding = null;
//    }

    private class pagerAdapter extends FragmentStatePagerAdapter {
        public pagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    Log.d(TAG, "pagerAdapter : case 0");
                    return new MapGoogleFragment();
                case 1:
                    Log.d(TAG, "case 1");
                    return new MapKakaoFragment();
                default:
                    Log.d(TAG, "default");
                    return null;
            }
        }

        @Override
        public int getCount() {
            Log.d(TAG, "getCount()");
            // total page count
            return 2;
        }
    }
}