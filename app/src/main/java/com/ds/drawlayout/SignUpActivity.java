package com.ds.drawlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
    private AlertDialog mAlertDialog;
    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.button_save_activity_sign_up).setOnClickListener(onClickListener);

        mButtonSave.setText(result); // 오늘 날짜로 초기화
//        mButtonSave.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG,"onClick()");
//                String userID = et_id.getText().toString();
//                String userPass = et_id.getText().toString();
//                String userCell = et_cellphone.getText().toString();
////                String userDate = mDatePicker.getDayOfMonth();
//
//                if (userID.equals("")) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
//                    mAlertDialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).setNegativeButton("취소", null).create();
//                    mAlertDialog.show();
//                    return;
//                }
//
//                Response.Listener<String> responseListener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            Log.d(TAG,"try()");
//                            JSONObject jsonObject = new JSONObject();
//                            boolean success = jsonObject.getBoolean( "success" );
//                            //회원가입 성공시
//                            if(success) {
//                                Log.d(TAG,"if(success)");
//                                Toast.makeText( getApplicationContext(), "회원가입</br>성공", Toast.LENGTH_SHORT ).show();
//                                Intent intent = new Intent( SignUpActivity.this, SignInActivity.class );
//                                startActivity( intent );
//                                //회원가입 실패시
//                            } else {
//                                Log.d(TAG,"else()");
//                                Toast.makeText( getApplicationContext(), "회원가입</br>실패", Toast.LENGTH_SHORT ).show();
//                                return;
//                            }
//                        } catch (JSONException e) {
//                            Log.d(TAG,"catch (JSONException e)");
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                SignUpRequest registerRequest = new SignUpRequest(userID, userPass, userCell, responseListener);
//                RequestQueue queue = Volley.newRequestQueue( SignUpActivity.this );
//                queue.add( registerRequest );
//            }
//        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"mButtonCancel.onClick()");
                finish();
            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_save_activity_sign_up:
                    signUp();
                    break;
            }
        }
    };

    private void signUp(){
        String id=((EditText)findViewById(R.id.edittext_id)).getText().toString();
        String password=((EditText)findViewById(R.id.edittext_password)).getText().toString();

        if(id.length()>0 && password.length()>0) {
            if(password.equals(password)){
                mAuth.createUserWithEmailAndPassword(id, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "회원가입에 성공했습니다." ,Toast.LENGTH_SHORT).show();
                                } else {
                                    if(task.getException().toString() !=null){
                                        Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다." ,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
            else{
                Toast.makeText(this, "비밀번호가 일치하지 않습니다." ,Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "아아디와 비밀번호를 확인해주세요." ,Toast.LENGTH_SHORT).show();
        }
    }
}