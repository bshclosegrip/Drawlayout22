package com.ds.drawlayout.ui.info;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mTextView = root.findViewById(R.id.textView_Info);
        return inflater.inflate(R.layout.fragment_info, container, false);
    }
}