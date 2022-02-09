package com.ds.drawlayout.ui.logout;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.ds.drawlayout.MainActivity;
import com.ds.drawlayout.R;
import com.ds.drawlayout.SignInActivity;
import com.ds.drawlayout.databinding.FragmentLogoutBinding;
import com.ds.drawlayout.ui.map.MapFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LogoutFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {
    private final static String TAG = "LogoutFragment";
    private TextView mTextView, mTexViewOfProgressbar;
    private ProgressBar mProgressBar;
    private Button mButton, mButtonGo;
    private WebView mWebView;
    private FragmentLogoutBinding mBinding;
    private EditText mEditText;
    private Handler mHandler;
    private MainActivity mMainActivity;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        mBinding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();
//        View v = inflater.inflate(R.layout.fragment_logout, container, false);
        mTextView = root.findViewById(R.id.text_logout);
        mTexViewOfProgressbar = root.findViewById(R.id.textview_of_progressbar_fragment_logout);
        mProgressBar = root.findViewById(R.id.progressbar_logout);
        mButtonGo = root.findViewById(R.id.button_url_go);
        mEditText = root.findViewById(R.id.edittext_url);
        mEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEditText.setImeOptions(EditorInfo.IME_ACTION_GO);
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                switch(getId()) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        Toast.makeText(getContext(), "이동 중", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(getContext(), "기본", Toast.LENGTH_LONG).show();
                        return false;
                }
                return true;
            }
        });
        mWebView = root.findViewById(R.id.webview_fragment_logout);

//        WebView mWebView = new WebView(getActivity());
//        setContentView(mWebView);

        // https "false" || http "true"
        mWebView.loadUrl("https://www.google.com");

//        String unencodedHtml =
//                "&lt;html&gt;&lt;body&gt;'%23' is the percent code for ‘#‘ &lt;/body&gt;&lt;/html&gt;";
//        String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(),
//                Base64.NO_PADDING);
//        mWebView.loadData(encodedHtml, "text/html", "base64");

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mHandler = new Handler();
        mButtonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = mEditText.getText().toString();
                        try {
                            URL httpURL = new URL(url);
                            HttpURLConnection conn = (HttpURLConnection) httpURL.openConnection();
                            conn.setDoInput(true);
                            conn.setDoOutput(true);
                            conn.setConnectTimeout(5000);

                            /**
                             * GET 요청을 한다.
                             * POST 요청을 원할 경우 "POST"라고 작성한다.
                             */
                            conn.setRequestMethod("GET");
                            int responseCode = conn.getResponseCode();
                            if( responseCode == HttpURLConnection.HTTP_OK ){
                                final StringBuffer responseBody = new StringBuffer();
                                String line  = null;
                                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                while ( (line = reader.readLine()) != null ){
                                    responseBody.append(line + "\n");
                                }
                                reader.close();
                                conn.disconnect();

                                /**
                                 * 독립된 Thread 에서 Android Application 에 Main Thread 로 접근할 수 있는 Handler
                                 * UI View 를 컨트롤 한다.
                                 */
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // 결과를 사용자에게 보여준다.
                                        mTexViewOfProgressbar.setText(responseBody.toString());
                                    }
                                });
                            }
                        } catch (MalformedURLException e) {
                            return;
                        } catch (IOException e) {
                            return;
                        }
                    }
                }).start();
            }
        });

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

//                Intent intent = new Intent(getContext(), SignInActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);

                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();

//                Intent intent = new Intent(getContext(), MapFragment.class);
//                intent.getData();
//                intent.getFlags();
//                startActivity(intent);

                // NullPointer
//                mMainActivity.showNoti1();
//                ((MainActivity) getActivity()).showNoti1();
            default:
//                mMainActivity.showNoti2();
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.d(TAG, "onTouch()");
        return false;
    }
}