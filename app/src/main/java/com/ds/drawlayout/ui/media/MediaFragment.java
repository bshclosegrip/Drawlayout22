package com.ds.drawlayout.ui.media;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.ds.drawlayout.R;
import com.ds.drawlayout.databinding.FragmentMediaBinding;

import java.io.IOException;
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
    private TextureView mTextureView;

    private MediaRecorder mMediaRecorder = null;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;
    private RecordButton recordButton = null;
    private MediaRecorder recorder = null;
    private PlayButton playButton = null;
    private MediaPlayer player = null;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

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

        mTextureView = (TextureView) root.findViewById(R.id.screenTextureView);
//        mTextureView.getControls();
//        mTextureView.prepareTextureViewVideo();
//        mTextureView.prepareVideoViewVideo();
//        mTextureView.playTextureView();

        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.sample_video);
        mediaPlayer.start();
        MediaController controller = new MediaController(getContext());
        // NullPointerException: Attempt to invoke virtual method 'void android.widget.VideoView.setMediaController(android.widget.MediaController)' on a null object reference
        mVideoView.setMediaController(controller);
        mVideoView.setVideoURI(Uri.parse(url));
        mVideoView.requestFocus();
//        mVideoView.setVideoPath("http://clips.vorwaerts-gmbh.de/VfE_html5.mp4");
        mVideoView.start();
        Log.d(TAG, "setVideoURI() : url = " + url + ", mVideoView.setVideoURI(Uri.parse(url)) = " + Uri.parse(url));

//        mVideoView.seekTo(12);
//        mVideoView.start();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Log.d(TAG, "onPrepared()");

//                int duration = mediaPlayer.getDuration();
//                mSeekbar.setMax(duration);

//                mediaPlayer.start();

                Toast.makeText(getContext(), "동영상 준비됨.", Toast.LENGTH_SHORT).show();
            }
        });

//        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                int topContainerId = getResources().getIdentifier("mediacontroller_progress",
//                        "id", "android");
//
////                mSeekbar = (SeekBar) controller.findViewById(topContainerId);
//
//                mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
//                    @Override
//                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
//                        if (percent<mSeekbar.getMax()) {
//                            mSeekbar.setSecondaryProgress(percent);
//                        }
//                    }
//                });
//            }
//        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "mButton : onClick(View view)");
                mVideoView.seekTo(0); // 처음 위치로
                mVideoView.start();

            }
        });

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
        setSeekBarMax(mSeekbar, max);
        setSeekBarAnimation(mSeekbar);



//        ContextWrapper m = new ContextWrapper(getActivity());
//        fileName = m.getExternalCacheDir().getAbsolutePath();
//        //    java.lang.RuntimeException: Stub!
//        fileName += "/audiorecordtest.3gp";
//        recordButton = root.findViewById(R.id.button_record_start);
//        recorder = null;
//        playButton = root.findViewById(R.id.button_record_liston);
//        player = null;

//        ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION);
//        ConstraintLayout ll = new ConstraintLayout(getContext());
//        recordButton = new RecordButton(getContext());
//        ll.addView(recordButton,
//                new LinearLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        0));
//        playButton = new PlayButton(getContext());
//        ll.addView(playButton,
//                new LinearLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        0));
//        setContentView(ll);

//        return inflater.inflate(R.layout.fragment_media, container, false);
        return root;
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
        Log.d(TAG, "setSeekBarMax()");
        sb.setMax((int)((max_value-min) / step));
    }

    private void setSeekBarChange(int progress, TextView tv) {
        float value = min + (progress * step);
        DecimalFormat format = new DecimalFormat(".#");
        String str = format.format(value);
        Log.d(TAG, "setSeekBarChange() : str = " + str + ", tv = " + tv + ", format = " + format);
        tv.setText(str_title +" ( "+Float.valueOf(str)+" )");
    }

    private void setSeekBarAnimation(AppCompatSeekBar sb) {
        int progress_half = (int)(((max / min) / 2)-1);
        ObjectAnimator animation = ObjectAnimator.ofInt(sb, "progress", progress_half);
        Log.d(TAG, "setSeekBarAnimation() : progress_half = " + progress_half + ", animation = " + animation);
        animation.setDuration(100); // 0.5 second
        animation.setInterpolator(new /*DecelerateInterpolator()*/LinearInterpolator());
        animation.start();
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        player.release();
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_2_TS);
        player = null;
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    //  ClassCastException: androidx.appcompat.widget.AppCompatButton cannot be cast to com.ds.drawlayout.ui.media.MediaFragment$RecordButton
//    class RecordButton extends androidx.appcompat.widget.AppCompatButton {
    class RecordButton extends AppCompatButton {
        boolean mStartRecording = true;
        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                }
                mStartRecording = !mStartRecording;
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
    }

    // ClassCastException: androidx.appcompat.widget.AppCompatButton cannot be cast to com.ds.drawlayout.ui.media.MediaFragment$RecordButton
//    class PlayButton extends androidx.appcompat.widget.AppCompatButton {
    class PlayButton extends AppCompatButton {
        boolean mStartPlaying = true;
        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    setText("Stop playing");
                } else {
                    setText("Start playing");
                }
                mStartPlaying = !mStartPlaying;
            }
        };

        public PlayButton(Context ctx) {
            super(ctx);
            setText("Start playing");
            setOnClickListener(clicker);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }
    }

//    private File ContextWrapper.getExternalCacheDir() {
//        //    java.lang.RuntimeException: Stub!
//        throw new RuntimeException("Stub!");
//    }
//
//    public File[] getExternalCacheDirs() {
//        throw new RuntimeException("Stub!");
//    }
//
//    public File[] getExternalMediaDirs() {
//        throw new RuntimeException("Stub!");
//    }
}