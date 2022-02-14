package com.ds.drawlayout.ui.media;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.ds.drawlayout.R;
import com.ds.drawlayout.databinding.FragmentMediaBinding;

import java.text.DecimalFormat;

public class MediaFragment extends Fragment {
    private final static String TAG = "MediaFragment";
    private FragmentMediaBinding binding;
    private VideoView mVideoView;
    public static String url = "http://sites.google.com/site/ubiaccessmobile/sample_video.mp4";
//    public static String url = null;
    private Button mButton;
    private AppCompatSeekBar mSeekbar;
    private MediaViewModel mMediaViewModel;
    private TextView mTextView;
    private ConstraintLayout mConstraintLayout;

    final int max = 5;
    final float min = (float) 0.1;
    final float step = (float) 0.1;
    final String str_title = "SeekBar : ";
    private boolean isPrepared = false;
    private boolean isTouch = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
//        mMediaViewModel = new ViewModelProvider(this).get(MediaViewModel.class);
        binding = FragmentMediaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mConstraintLayout = root.findViewById(R.id.constraint_layout_fragment_media);
        mVideoView = root.findViewById(R.id.video_view);
        mTextView = root.findViewById(R.id.textView_seekbar_index);
        mButton = root.findViewById(R.id.button_start_video);
        mSeekbar = root.findViewById(R.id.seekbar_video);
//        mSeekbar = root.findViewById(R.id.seekbar_video);
//        mSeekbar.setOnClickListener(this::onClick);

        setSeekBarMax(mSeekbar, max);
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Log.d(TAG, "onProgressChanged()");
                setSeekBarChange(progress, mTextView);
                mTextView.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStartTrackingTouch()");
                mTextView.setText("트래킹 시작");
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStopTrackingTouch()");
                mTextView.setText("트래킹 종료");
                isTouch =false;
            }
        });

        setSeekBarAnimation(mSeekbar);

        MediaController controller = new MediaController(getContext());
        // NullPointerException: Attempt to invoke virtual method 'void android.widget.VideoView.setMediaController(android.widget.MediaController)' on a null object reference
        mVideoView.setMediaController(controller);
        mVideoView.setVideoURI(Uri.parse(url));
        mVideoView.requestFocus();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Log.d(TAG, "onPrepared()");
                mediaPlayer.start();
                Toast.makeText(getContext(), "동영상 준비됨.", Toast.LENGTH_SHORT).show();
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "mButton : onClick(View view)");
                mVideoView.seekTo(0); // 처음 위치로
                mVideoView.start();
            }
        });

        return inflater.inflate(R.layout.fragment_media, container, false);
    }

//    public void setMediaController(){
//        Log.d(TAG, "setMediaController()");
//    }
//
//    public void setVideoURI(){
//        Log.d(TAG, "setVideoURI()");
//    }
//
//    public void setStreamVolume(){
//        Log.d(TAG, "setStreamVolume()");
//    }

    private void setSeekBarMax(AppCompatSeekBar sb, int max_value) {
        Log.d(TAG, "setSeekBarMax");
        sb.setMax((int)((max_value-min) / step));
    }

    private void setSeekBarChange(int progress, TextView tv) {
        Log.d(TAG, "setSeekBarChange");
        float value = min + (progress * step);
        DecimalFormat format = new DecimalFormat(".#");
        String str = format.format(value);
        mTextView.setText(str_title +" ( "+Float.valueOf(str)+" )");
    }

    private void setSeekBarAnimation(AppCompatSeekBar sb) {
        Log.d(TAG, "setSeekBarAnimation");
        int progress_half = (int)(((max / min) / 2)-1);
        ObjectAnimator animation = ObjectAnimator.ofInt(sb, "progress", progress_half);
        animation.setDuration(100); // 0.5 second
        animation.setInterpolator(new /*DecelerateInterpolator()*/LinearInterpolator());
        animation.start();
    }
}