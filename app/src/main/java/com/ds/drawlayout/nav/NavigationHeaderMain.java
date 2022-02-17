package com.ds.drawlayout.nav;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ds.drawlayout.R;
import com.ds.drawlayout.ui.info.InfoFragment;
import com.ds.drawlayout.ui.main.MainFragment;

public class NavigationHeaderMain extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "NavigationHeaderMain";
    private ImageView mImageView;
    private TextView mTextViewEmail, mTextViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        mImageView = findViewById(R.id.imageView_profile);
        mTextViewEmail = findViewById(R.id.textView_email);
        mTextViewName = findViewById(R.id.textView_name);
        mImageView.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.imageView_profile:
                Log.d(TAG, "case R.id.imageView_profile()");
                startActivity(new Intent(this, InfoFragment.class));
                break;
//
//            case R.id.textView_email:
//                Log.d(TAG, "case R.id.textView_email()");
//                startActivity(new Intent(this, InfoFragment.class));
//                break;
//
//            case R.id.textView_name:
//                Log.d(TAG, "case R.id.textView_name()");
//                startActivity(new Intent(this, InfoFragment.class));
//                break;

            default:
                Log.d(TAG, "default()");
                startActivity(new Intent(this, MainFragment.class));
                break;
        }
    }
}
