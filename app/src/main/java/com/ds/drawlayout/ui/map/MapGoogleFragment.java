package com.ds.drawlayout.ui.map;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ds.drawlayout.R;
import com.ds.drawlayout.databinding.FragmentMapBinding;
import com.ds.drawlayout.databinding.FragmentMapGoogleBinding;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapGoogleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapGoogleFragment extends Fragment implements OnMapReadyCallback {
    private final String TAG = "MapGoogleFragment";
    private FragmentMapGoogleBinding binding;
    private MapView mMapView;
    private GoogleMap mGoogleMap;
//    private GoogleMap mGoogleMap = null;
    private String mData, ARG_POSITION;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public MapGoogleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapGoogleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapGoogleFragment newInstance(String param1, String param2) {
        MapGoogleFragment fragment = new MapGoogleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        binding = FragmentMapGoogleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mMapView = root.findViewById(R.id.mapview_fragment_map);
        mMapView.getMapAsync(this);
//        mGoogleMap = (GoogleMap) root.findViewById(mMapView);
//        mGoogleMap = root.findViewById(R.id.map);
        return root;
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart()");
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop()");
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause()");
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onLowMemory() {
        Log.d(TAG, "onLowMemory()");
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
        mMapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
        if(mMapView != null) {
            Log.d(TAG, "if(mMapView != null)");
            mMapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady(googleMap)");
        LatLng SEOUL = new LatLng(37.551968, 126.988500);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("남산타워");
        markerOptions.snippet("수도");
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));

//        LatLng sydney = new LatLng(-33.852, 151.211);
//        mGoogleMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney"));

    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //경도
            double lng = location.getLongitude();
            //위도
            double lat = location.getLatitude();
            //고도
            double alt = location.getAltitude();
            //정확도
            float acc = location.getAccuracy();
            //위치제공자(ISP)
            String provider = location.getProvider();

            //바뀐 현재 좌표
            LatLng current = new LatLng(lat,lng);
            //현재좌표로 카메라를 이동시킬때 // TODO 바로 여기에 전역변수 구글맵이 들어감
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current,17));
        }
    };
}