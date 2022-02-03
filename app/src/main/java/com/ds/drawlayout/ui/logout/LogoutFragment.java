package com.ds.drawlayout.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ds.drawlayout.R;
import com.ds.drawlayout.databinding.FragmentLogoutBinding;
import com.ds.drawlayout.ui.map.MapFragment;

public class LogoutFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {
    private final static String TAG = "LogoutFragment";
    private TextView mTextView, mTexViewOfProgressbar;
    private ProgressBar mProgressBar;
    private Button mButton;
    private WebView mWebView;
    private FragmentLogoutBinding mBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        mBinding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();
//        View v = inflater.inflate(R.layout.fragment_logout, container, false);
        mTextView = root.findViewById(R.id.text_logout);
        mTexViewOfProgressbar = root.findViewById(R.id.textview_of_progressbar_fragment_logout);
        mProgressBar = root.findViewById(R.id.progressbar_logout);
        mWebView = root.findViewById(R.id.webview_fragment_logout);
//        WebView mWebView = new WebView(getActivity());
//        setContentView(mWebView);
        mWebView.loadUrl("http://www.google.com");

//        String unencodedHtml =
//                "&lt;html&gt;&lt;body&gt;'%23' is the percent code for ‘#‘ &lt;/body&gt;&lt;/html&gt;";
//        String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(),
//                Base64.NO_PADDING);
//        mWebView.loadData(encodedHtml, "text/html", "base64");

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

//        mView.OnTouchListener(getView(), null);
        mProgressBar = new ProgressBar(getContext());
        mProgressBar.setProgress(50);
        mButton = root.findViewById(R.id.button_cancel_logout);
        mButton.setOnClickListener(this);
//        WorkManager.getInstance(getApplicationContext())
//                // requestId is the WorkRequest id
//                .getWorkInfoByIdLiveData(requestId)
//                .observe(lifecycleOwner, new Observer<WorkInfo>() {
//                    @Override
//                    public void onChanged(@Nullable WorkInfo workInfo) {
//                        if (workInfo != null) {
//                            Data progress = workInfo.getProgress();
//                            int value = progress.getInt(PROGRESS, 0)
//                            // Do something with progress
//                        }
//                    }
//                });
        return root;
    }

    public void ClickHandler(View view)
    {
        MyAsyncTask asyncTask = new MyAsyncTask(mTexViewOfProgressbar, mProgressBar);
        asyncTask.execute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView()");
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button_cancel_logout:
                Log.d(TAG, "button_cancel_logout");
//                mButton.performClick();
                mButton.setVisibility(View.GONE);
                Intent intent = new Intent(getContext(), MapFragment.class);
                intent.getData();
                intent.getFlags();
//                startActivity(intent);
            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.d(TAG, "onTouch()");
        return false;
    }
}