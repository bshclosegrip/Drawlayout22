package com.ds.drawlayout.ui.notification;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ds.drawlayout.MainActivity;
import com.ds.drawlayout.R;
import com.ds.drawlayout.adapter.RecyclerviewAdapter;
import com.ds.drawlayout.data.RecyclerviewData;
import com.ds.drawlayout.databinding.FragmentNotificationBinding;

import java.util.ArrayList;

public class NotificationFragment extends Fragment implements View.OnClickListener {
    private final static String TAG = "NotificationFragment";
    private NotificationViewModel notificationViewModel;
    private FragmentNotificationBinding binding;
    private TextView mTextView;
    private RecyclerView mRecyclerView2;
    protected ArrayList<RecyclerviewData> mRecyclerItemList;
    private RecyclerviewAdapter mRecyclerviewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "onCreateView()");
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mRecyclerviewAdapter = new RecyclerviewAdapter();
        mTextView = root.findViewById(R.id.textview_notification);
        mRecyclerView2 = root.findViewById(R.id.recyclerview_notification);
        mRecyclerView2.setAdapter(mRecyclerviewAdapter);
        mRecyclerView2.setHasFixedSize(true);
        mRecyclerView2.setItemAnimator(null);
        mRecyclerItemList = new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setItemPrefetchEnabled(true);
        mRecyclerView2.setLayoutManager(llm);

        for(int i=1;i<=10;i++){
            if(i%2==0) {
                Log.d(TAG, "if(i%2==0)");
                mRecyclerItemList.add(new RecyclerviewData(android.R.drawable.bottom_bar, i + "번째 알림", "농구 가능"));
            } else {
                Log.d(TAG, "else()");
                mRecyclerItemList.add(new RecyclerviewData(android.R.drawable.arrow_up_float, i + "번째 알림", "농구 불가"));
            }
        }
        mRecyclerviewAdapter.setRecyclerList(mRecyclerItemList);
        mRecyclerView2.setOnClickListener(this);
        return root;
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView()");
        super.onDestroyView();
        binding = null;
    }

    public void recyListAdd() {
        Log.d(TAG, "recyListAdd()");
    }

    @Override
    public void onClick(View view) {
        MainActivity activity = (MainActivity) getActivity();
        activity.moveToDetailSettings();
    }
}