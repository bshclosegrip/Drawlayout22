package com.ds.drawlayout.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ds.drawlayout.MainActivity;
import com.ds.drawlayout.R;
import com.ds.drawlayout.adapter.RecyclerviewAdapter;
import com.ds.drawlayout.data.RecyclerviewData;
import com.ds.drawlayout.databinding.FragmentSettingsBinding;

import java.util.ArrayList;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "SettingsFragment";
    private FragmentSettingsBinding binding;
    private TextView mTextView;
    private RecyclerView mRecyclerView;
    private ConstraintLayout mConstraintLayout;
    private ArrayList<RecyclerviewData> mRecyclerItemList;
    private RecyclerviewAdapter mRecyclerviewAdapter;
    private Object userID;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mTextView = root.findViewById(R.id.textview_settings);
        mConstraintLayout = root.findViewById(R.id.constraint_layout);
        mRecyclerView = root.findViewById(R.id.recyclerview_settings);
        mRecyclerviewAdapter = new RecyclerviewAdapter();
        mRecyclerView.setAdapter(mRecyclerviewAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(null);
        mRecyclerItemList = new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setItemPrefetchEnabled(true);
        mRecyclerView.setLayoutManager(llm);

//        if(root.getParent() != null) {
//            ((ViewGroup)root.getParent()).removeView(root); // <- fix
//        }
//        mConstraintLayout.addView(root);

        mRecyclerItemList.add(new RecyclerviewData(R.drawable.ic_navi_home,1+". General", null));
        mRecyclerItemList.add(new RecyclerviewData(android.R.drawable.ic_lock_idle_low_battery,2+". Connection", null));
        mRecyclerItemList.add(new RecyclerviewData(android.R.drawable.presence_video_online,3+". Display", null));
        mRecyclerItemList.add(new RecyclerviewData(android.R.drawable.ic_dialog_map,4+". Camera", null));
        mRecyclerItemList.add(new RecyclerviewData(android.R.drawable.btn_star_big_on,5+". Downloads", null));
        mRecyclerviewAdapter.setRecyclerList(mRecyclerItemList);
        mRecyclerView.setOnClickListener(this::onClick);

//        mRecyclerviewAdapter.setOnItemClickListener(new mRecyclerviewAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//                // TODO : 아이템 클릭 이벤트를 MainActivity에서 처리.
//            }
//        }); ;

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() != null && getActivity() instanceof MainActivity) {
            userID = ((MainActivity) getActivity()).getData();
        }
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView()");
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {

//        switch(view.getId()) {
//            case R.id.recyclerview_settings:
//                Log.d(TAG, "R.id.recyclerview_settings");
//                String userID  = getActivity().getIntent().getExtras().getString("userID");
//                getActivity().getIntent().putExtra("userID", userID);

//                Intent intent = new Intent(getContext(), MapFragment.class);
//                startActivity(intent);

//                Intent intent = new Intent(this, MapFragment.class);
//                ActivityResultContracts.StartActivityForResult();
//        }
    }
}