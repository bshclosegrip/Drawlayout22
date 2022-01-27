package com.ds.drawlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ds.drawlayout.ui.home.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private EditText et_id, et_pass, et_cellphone;
    private Button mButtonSave, mButtonCancel;
    private DatePicker mDatePicker;
    private final int requestCodeSignUpActivity = 3;

    Date curDate = new Date();
    final SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
    String result = dataFormat.format(curDate) + "\n저장";

    public SignUpActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate()");
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );
        et_id = findViewById( R.id.edittext_id );
        et_pass = findViewById( R.id.edittext_password );
        et_cellphone = findViewById( R.id.edittext_cellphone_number);
        mDatePicker = findViewById(R.id.date_picker);
        mButtonSave = findViewById( R.id.button_save_activity_sign_up );
        mButtonCancel = findViewById( R.id.button_cancel_activity_sign_up );
        mButtonSave.setText(result); // 오늘 날짜로 초기화
        mButtonSave.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onClick()");
                String userID = et_id.getText().toString();
                String userPass = et_id.getText().toString();
                String userCell = et_cellphone.getText().toString();
//                String userDate = mDatePicker.getDayOfMonth();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG,"try()");
                            JSONObject jsonObject = new JSONObject();
                            boolean success = jsonObject.getBoolean( "success" );
                            //회원가입 성공시
                            if(success) {
                                Log.d(TAG,"if(success)");
                                Toast.makeText( getApplicationContext(), "회원가입</br>성공", Toast.LENGTH_SHORT ).show();
                                Intent intent = new Intent( SignUpActivity.this, SignInActivity.class );
                                startActivity( intent );
                                //회원가입 실패시
                            } else {
                                Log.d(TAG,"else()");
                                Toast.makeText( getApplicationContext(), "회원가입</br>실패", Toast.LENGTH_SHORT ).show();
                                return;
                            }
                        } catch (JSONException e) {
                            Log.d(TAG,"catch (JSONException e)");
                            e.printStackTrace();
                        }
                    }
                };
                SignUpRequest registerRequest = new SignUpRequest(userID, userPass, userCell, responseListener);
                RequestQueue queue = Volley.newRequestQueue( SignUpActivity.this );
                queue.add( registerRequest );
            }
        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"mButtonCancel.onClick()");
                finish();
            }
        });
    }
}