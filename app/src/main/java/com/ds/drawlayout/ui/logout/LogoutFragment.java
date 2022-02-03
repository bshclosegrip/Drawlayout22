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
import com.ds.drawlayout.databinding.FragmentLogoutBinding;
import com.ds.drawlayout.ui.map.MapFragment;

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
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                switch(getId()) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        Toast.makeText(getContext(), "검색", Toast.LENGTH_LONG).show();
                        break;
                }
                return true;
            }
        });
        mWebView = root.findViewById(R.id.webview_fragment_logout);

//        WebView mWebView = new WebView(getActivity());
//        setContentView(mWebView);

        // when https : usesCleartextTraffic="false" || when http : usesCleartextTraffic="true"
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

                // HTTP 로 요청을 보낸다. Thread 작업이 필요함
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 작업시작
                        String url = mEditText.getText().toString();
                        try {
                            //요청을 보낼 URL 인스턴스 정보
                            URL httpURL = new URL(url);
                            // 요청을 보내기 위한 준비를 한다.( 요청을 보내기 전)
                            HttpURLConnection conn = (HttpURLConnection) httpURL.openConnection();

                            conn.setDoInput(true);
                            conn.setDoOutput(true);

                            // 최대 요청 지연 시간, 요청이 5초 이상 걸릴 경우 요청을 끊는다.
                            conn.setConnectTimeout(5000);

                            /**
                             * GET 요청을 한다.
                             * POST 요청을 원할 경우 "POST"라고 작성한다.
                             */
                            conn.setRequestMethod("GET");

                            // 요청을 보내고, 동시에 응답을 받는다.
                            int responseCode = conn.getResponseCode();

                            // 요청과 응답이 제대로 이루어졌는지 검사한다.
                            // HttpURLConnection.HTTP_OK : 응답이 200 OK 라는 의미이다.
                            if( responseCode == HttpURLConnection.HTTP_OK ){
                                // 응답 본문 전체를 닫는다.
                                final StringBuffer responseBody = new StringBuffer();

                                // 응답 본문의 한줄 한줄씩 얻어온다.
                                String line  = null;

                                /**
                                 * 응답 본문을 담고 있는 InputStream 을 얻어온다.
                                 * BufferedReader 는 InputStream 한줄 씩 얻어올 수 있는 객체다.
                                 */
                                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                                while ( (line = reader.readLine()) != null ){
                                    // 응답 본문이 종료될 때 까지 반복한다.

                                    // 응답 본문 한줄씩 결과 객체에 담는다.
                                    // 줄바꿈을 위해서 매 라인 끝마다 "\n"을 더해준다.
                                    responseBody.append(line + "\n");
                                }
                                // 연결을 순차적으로 끊는다.
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