package com.ds.drawlayout.ui.info;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ds.drawlayout.R;
import com.ds.drawlayout.databinding.FragmentInfoBinding;
import com.ds.drawlayout.databinding.FragmentSettingsBinding;

public class InfoFragment extends Fragment {
    private final static String TAG = "InfoFragment";
    private TextView mTextView;
    private FragmentInfoBinding binding;
    private ViewPager2 mViewpager2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        mTextView = root.findViewById(R.id.textView_Info);
        mViewpager2 = root.findViewById(R.id.view_pager_2_frag_info);
        mViewpager2.setOnClickListener(View::cancelDragAndDrop);
//        mViewpager2.setOnTouchListener(getContext());
        return inflater.inflate(R.layout.fragment_info, container, false);
    }
}