package com.ds.drawlayout.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ds.drawlayout.R;
import com.ds.drawlayout.adapter.Viewpager2Adapter;
import com.ds.drawlayout.data.RecyclerviewData;
import com.ds.drawlayout.databinding.FragmentMainBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentMainBinding mBinding;
    private TextView mTextViewPushLeft, mTextViewPushRight;
    private ImageView mImageViewLogo, mImageViewArrowLeft, mImageViewArrowRight;
    private ViewPager2 mViewPager2;
    private ArrayList<RecyclerviewData> mRecyclerItemList;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        mBinding = FragmentMainBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();
        mTextViewPushLeft = root.findViewById(R.id.textview_push_left);
        mTextViewPushRight = root.findViewById(R.id.textview_push_right);
        mImageViewLogo = root.findViewById(R.id.imageview_fragment_main);
        mImageViewArrowLeft = root.findViewById(R.id.imageview_arrow_left_fragment_main);
        mImageViewArrowRight = root.findViewById(R.id.imageview_arrow_right_fragment_main);

        ArrayList<RecyclerviewData> list = new ArrayList<>();
        mRecyclerItemList = new ArrayList<>();
        list.add(new RecyclerviewData(R.layout.fragment_home, "Home Fragment", "1"));
        list.add(new RecyclerviewData(R.layout.fragment_map, "Map Fragment", "2"));
        list.add(new RecyclerviewData(R.layout.fragment_notification, "Notification Fragment", "3"));
        list.add(new RecyclerviewData(R.layout.fragment_settings, "Settings Fragment", "4"));
//        mViewPager2.setAdapter(new Viewpager2Adapter());

        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}