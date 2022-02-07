package com.ds.drawlayout.ui.map;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ds.drawlayout.R;
import com.ds.drawlayout.databinding.FragmentMapKakaoBinding;
import com.kakao.util.maps.helper.Utility;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;
//import net.daum.android.map.MapView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapKakaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapKakaoFragment extends Fragment {
    private final String TAG = "MapKakaoFragment";
    public String KakaoMapAPI = "API";
    private FragmentMapKakaoBinding mBinding;
    private MapPoint mMapPoint;
    private MapView mMapView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MapKakaoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapNaverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapKakaoFragment newInstance(String param1, String param2) {
        MapKakaoFragment fragment = new MapKakaoFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        mBinding = FragmentMapKakaoBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();

//        mMapView = root.findViewById(R.id.mapview_fragment_map_kakao);

//        mMapView = new MapView(root.getContext());

        // NullPointerException
//        mMapView = new MapView(root.findFocus().getContext());

//        ViewGroup mapViewContainer = (ViewGroup) mMapView.findViewById(R.id.mapview_fragment_map_kakao);
//        mapViewContainer.addView(mMapView);

//        getKeyHash(getContext());
//        mMapView.setDaumMapApiKey(KakaoMapAPI);
//        mMapView.removeAllPOIItems();
//        Marker("도코", 139.6917064, 35.6894875);
//        MapMarker("붓싼", "출발", 128.7384361, 34.8799083 );
//
//        Handler mHandler = new Handler();
//        mHandler.postDelayed( new Runnable() {
//            public void run() {
//                // 3초 후에 현재위치를 받아오도록 설정 , 바로 시작 시 에러납니다.
//                mMapView.setCurrentLocationTrackingMode( MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading );
//                }
//            }, 4000); // 1000 = 1초
//        LocationManager lm = (LocationManager) getContext().getSystemService( Context.LOCATION_SERVICE );

        return root;
    }

    public void Marker(String MakerName, double startX, double startY) {
        mMapPoint = MapPoint.mapPointWithGeoCoord( startY, startX );
        mMapView.setMapCenterPoint(mMapPoint, true); //true면 앱 실행 시 애니메이션 효과가 나오고 false면 애니메이션이 나오지않음.
        MapPOIItem marker = new MapPOIItem(); marker.setItemName(MakerName); // 마커 클릭 시 컨테이너에 담길 내용
        marker.setMapPoint(mMapPoint); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setMarkerType( MapPOIItem.MarkerType.RedPin ); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        marker.setSelectedMarkerType( MapPOIItem.MarkerType.BluePin );
        mMapView.addPOIItem( marker );
    }

    public void MapMarker(String MakerName, String detail, double startX, double startY) {
        mMapPoint = MapPoint.mapPointWithGeoCoord(startY, startX);
        mMapView.setMapCenterPoint(mMapPoint, true); //true면 앱 실행 시 애니메이션 효과가 나오고 false면 애니메이션이 나오지않음.
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(MakerName + "(" + detail + ")"); // 마커 클릭 시 컨테이너에 담길 내용
        marker.setMapPoint(mMapPoint); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin);
        mMapView.addPOIItem(marker);
    }

    public String getKeyHash(final Context context) {
        PackageInfo packageInfo = Utility.getPackageInfo( context, PackageManager.GET_SIGNATURES );
        if (packageInfo == null) return null;
        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance( "SHA" );
                md.update( signature.toByteArray() );
                return Base64.encodeToString( md.digest(), Base64.NO_WRAP );
            } catch (NoSuchAlgorithmException e) {
                System.out.println( "디버그 keyHash" + signature );
            }
        }
        return null;
    }
}