package com.ds.drawlayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ds.drawlayout.adapter.ChatAdapter;
import com.ds.drawlayout.data.ChatItemData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.core.view.View;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
//import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private final static String TAG = "ChatActivity";
    private EditText et;
    private ListView listView;
    private ArrayList<ChatItemData> messageItems = new ArrayList<>();
    private ChatAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference chatRef;
    private Button mButtonSend;
    private EditText etName;
    private CircleImageView ivProfile;
    private Uri imgUri;
    boolean isFirst = true;
    boolean isChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // NullPointerException: Attempt to invoke virtual method 'void androidx.appcompat.app.ActionBar.setTitle(java.lang.CharSequence)' on a null object reference
//        getSupportActionBar().setTitle(G.nickName);
        et = findViewById(R.id.et);
        listView = findViewById(R.id.listview);
        adapter = new ChatAdapter(messageItems, getLayoutInflater());
        listView.setAdapter(adapter);
        firebaseDatabase = FirebaseDatabase.getInstance();
        chatRef = firebaseDatabase.getReference("chat");
        mButtonSend = findViewById(R.id.button_send_activity_chat);
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "mButtonSend.setOnClickListener : onClick()");
                clickSend(view);
            }
        });

        chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatItemData messageItem= dataSnapshot.getValue(ChatItemData.class);
                Log.d(TAG, "onChildAdded() : messageItem = " + messageItem);
                messageItems.add(messageItem);
                adapter.notifyDataSetChanged();
                listView.setSelection(messageItems.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildChanged()");
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved()");
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildMoved()");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled()");
            }
        });
        loadData();
    }

    public void clickSend(View view) {
        String nickName= G.nickName;
        String message= et.getText().toString();
        String profileUrl= G.porfileUrl;
        Calendar calendar= Calendar.getInstance();
        Log.d(TAG, "clickSend(View view) : nickName = " + nickName + ", message = " + message + ", profileUrl = " + profileUrl + ", calendar = " + calendar);
        String time=calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
        ChatItemData messageItem = new ChatItemData(nickName,message,time,profileUrl);
        chatRef.push().setValue(messageItem);
        et.setText("");
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }

    public void clickBtn(View view) {
        if(!isChanged && !isFirst) {
            Log.d(TAG, "if(!isChanged && !isFirst)()");
            Intent intent= new Intent(this, ChatActivity.class);
            startActivity(intent);
            finish();
        } else {
            Log.d(TAG, "else()");
//            saveData();
        }
    }

    void saveData(){
        Log.d(TAG, "saveData()");
        G.nickName= etName.getText().toString();
        if(imgUri == null) return;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyMMddhhmmss"); //20191024111224
        String fileName = sdf.format(new Date())+".png";
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        final StorageReference imgRef = firebaseStorage.getReference("profileImages/"+fileName);
        UploadTask uploadTask=imgRef.putFile(imgUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, "onSuccess(Uri uri)");
                        G.porfileUrl= uri.toString();
                        Toast.makeText(getApplicationContext(), "프로필 저장 완료", Toast.LENGTH_SHORT).show();
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference profileRef = firebaseDatabase.getReference("profiles");
                        profileRef.child(G.nickName).setValue(G.porfileUrl);
                        SharedPreferences preferences= getSharedPreferences("account",MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString("nickName",G.nickName);
                        editor.putString("profileUrl", G.porfileUrl);
                        editor.apply();
                        Intent intent = new Intent(getBaseContext(), ChatActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    void loadData(){
        Log.d(TAG, "loadData()");
        SharedPreferences preferences=getSharedPreferences("account",MODE_PRIVATE);
        G.nickName = preferences.getString("nickName", null);
        G.porfileUrl = preferences.getString("profileUrl", null);
    }
}