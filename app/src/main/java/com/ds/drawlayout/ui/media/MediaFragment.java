package com.ds.drawlayout.ui.media;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.ds.drawlayout.MainActivity;
import com.ds.drawlayout.R;
import com.ds.drawlayout.databinding.FragmentMapBinding;
import com.ds.drawlayout.databinding.FragmentMediaBinding;
import com.ds.drawlayout.ui.map.MapFragment;
import com.ds.drawlayout.ui.map.MapViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MediaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MediaFragment extends Fragment {
    private final static String TAG = "MediaFragment";
    private FragmentMediaBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private VideoView mVideoView;
    public static String url = "http://sites.google.com/site/ubiaccessmobile/sample_video.mp4";
    private Button mButton;
    private AppCompatSeekBar mSeekbar;
    private MediaViewModel mMediaViewModel;

    public MediaFragment() {
        Log.d(TAG, "MediaFragment()");
    }

    public static MediaFragment newInstance(String param1, String param2) {
        Log.d(TAG, "MediaFragment()");
        MediaFragment fragment = new MediaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.d(TAG, "if (getArguments() != null)");
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        binding = FragmentMediaBinding.inflate(inflater, container, false);
        mMediaViewModel = new ViewModelProvider(this).get(MediaViewModel.class);
        View root = binding.getRoot();
        mVideoView = root.findViewById(R.id.video_view);
        mButton = root.findViewById(R.id.button_start_video);
        mSeekbar = root.findViewById(R.id.seekbar_video);
//        mSeekbar.setOnClickListener(this::onClick);
        MediaController controller = new MediaController(getContext());
        mVideoView.setMediaController(controller);
        mVideoView.setVideoURI(Uri.parse(url));
        mVideoView.requestFocus();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Toast.makeText(getContext(), "동영상 준비됨.", Toast.LENGTH_SHORT).show();
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick(View view)");
                mVideoView.seekTo(0); // 처음 위치로
                mVideoView.start();
            }
        });
        return inflater.inflate(R.layout.fragment_media, container, false);
    }

    public void setMediaController(){
        Log.d(TAG, "setMediaController()");
    }

    public void setVideoURI(){
        Log.d(TAG, "setVideoURI()");
    }

    public void setStreamVolume(){
        Log.d(TAG, "setStreamVolume()");
    }
}