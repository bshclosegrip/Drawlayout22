package com.ds.drawlayout.ui.map;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
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

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ds.drawlayout.R;
import com.ds.drawlayout.databinding.FragmentMapBinding;
import com.ds.drawlayout.databinding.FragmentMapGoogleBinding;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapGoogleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapGoogleFragment extends Fragment implements OnMapReadyCallback {
//    GoogleMap.OnMarkerClickListener
//    GoogleMap.OnMapClickListener
    private final String TAG = "MapGoogleFragment";
    private FragmentMapGoogleBinding binding;
    private MapView mMapView;
    private GoogleMap mGoogleMap;
//    private GoogleMap mGoogleMap = null;
    private String mData, ARG_POSITION;
    private final LatLng PERTH = new LatLng(-31.952854, 115.857342);
    private final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);
    private Marker markerPerth;
    private Marker markerSydney;
    private Marker markerBrisbane;
    Marker selectedMarker;

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
//        getSampleMarkerItems();
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
//        markerOptions.position(SEOUL);
//        markerOptions.title("남산타워");
//        markerOptions.snippet("수도");

        for (int idx = 0; idx < 10; idx++) {
            MarkerOptions makerOptions = new MarkerOptions();
            makerOptions
                    .position(new LatLng(37.52487 + idx, 126.92723))
                    .title("마커" + idx);
            makerOptions.snippet("여의도 한강 치맥 합시다.");
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));

        markerPerth = googleMap.addMarker(new MarkerOptions()
                .position(PERTH)
                .title("Perth"));
        markerPerth.setTag(0);
        markerSydney = googleMap.addMarker(new MarkerOptions()
                .position(SYDNEY)
                .title("Sydney"));
        markerSydney.setTag(0);
        markerBrisbane = googleMap.addMarker(new MarkerOptions()
                .position(BRISBANE)
                .title("Brisbane"));
        markerBrisbane.setTag(0);

//        LatLng sydney = new LatLng(-33.852, 151.211);
//        mGoogleMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney"));

    }

//    LocationListener locationListener = new LocationListener() {
//        @Override
//        public void onLocationChanged(Location location) {
//            //경도
//            double lng = location.getLongitude();
//            //위도
//            double lat = location.getLatitude();
//            //고도
//            double alt = location.getAltitude();
//            //정확도
//            float acc = location.getAccuracy();
//            //위치제공자(ISP)
//            String provider = location.getProvider();
//
//            //바뀐 현재 좌표
//            LatLng current = new LatLng(lat,lng);
//            //현재좌표로 카메라를 이동시킬때 // TODO 바로 여기에 전역변수 구글맵이 들어감
//            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current,17));
//        }
//    };
//
//    @Override public void onMapClick(LatLng latLng) {
//        changeSelectedMarker(null);
//    }
//
//    public static class MarkerItem {
//        double lat;
//        double lon;
//        int price;
//
//        public MarkerItem(double lat, double lon, int price) {
//            this.lat = lat;
//            this.lon = lon;
//            this.price = price;
//        }
//
//        public double getLat() {
//            return lat;
//        }
//
//        public void setLat(double lat) {
//            this.lat = lat;
//        }
//
//        public double getLon() {
//            return lon;
//        }
//
//        public void setLon(double lon) {
//            this.lon = lon;
//        }
//
//        public int getPrice() {
//            return price;
//        }
//
//        public void setPrice(int price) {
//            this.price = price;
//        }
//    }
//
//    private void getSampleMarkerItems() {
//        ArrayList<MarkerItem> sampleList = new ArrayList();
//        sampleList.add(new MarkerItem(37.538523, 126.96568, 2500000));
//        sampleList.add(new MarkerItem(37.527523, 126.96568, 100000));
//        sampleList.add(new MarkerItem(37.549523, 126.96568, 15000));
//        sampleList.add(new MarkerItem(37.538523, 126.95768, 5000));
//        for (MarkerItem markerItem : sampleList) {
//            addMarker(markerItem, false);
//        }
//    }
//
//    private Marker addMarker(MarkerItem markerItem, boolean isSelectedMarker) {
//        LatLng position = new LatLng(markerItem.getLat(), markerItem.getLon());
//        int price = markerItem.getPrice();
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.title(Integer.toString(price));
//        markerOptions.position(position);
//        return mGoogleMap.addMarker(markerOptions);
//    }
//
//    private Bitmap createDrawableFromView(Context context, View view) {
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
//        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
//        view.buildDrawingCache();
//        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        view.draw(canvas);
//        return bitmap;
//    }
//
//    public boolean onMarkerClick(Marker marker) {
//        CameraUpdate center = CameraUpdateFactory.newLatLng(marker.getPosition());
//        mGoogleMap.animateCamera(center);
//        changeSelectedMarker(marker);
//        return true;
//    }
//
//    private void changeSelectedMarker(Marker marker) {
//        // 선택했던 마커 되돌리기
//        if (selectedMarker != null) {
//            addMarker(selectedMarker, false);
//            selectedMarker.remove();
//        }
//        // 선택한 마커 표시
//        if (marker != null) {
//            selectedMarker = addMarker(marker, true);
//            marker.remove();
//        }
//    }
//
//    private Marker addMarker(Marker marker, boolean isSelectedMarker) {
//        double lat = marker.getPosition().latitude;
//        double lon = marker.getPosition().longitude;
//        int price = Integer.parseInt(marker.getTitle());
//        MarkerItem temp = new MarkerItem(lat, lon, price);
//        return addMarker(temp, isSelectedMarker);
//    }
}