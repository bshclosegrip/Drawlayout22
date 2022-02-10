package com.ds.drawlayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
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
    EditText et;
    ListView listView;
    ArrayList<ChatItemData> messageItems=new ArrayList<>();
    ChatAdapter adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference chatRef;
    private Button mButtonSend;

    private EditText etName;
    private CircleImageView ivProfile;
    private Uri imgUri;//선택한 프로필 이미지 경로 Uri
    boolean isFirst= true; //앱을 처음 실행한 것인가?
    boolean isChanged= false; //프로필을 변경한 적이 있는가?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //제목줄 제목글시를 닉네임으로(또는 채팅방)
        getSupportActionBar().setTitle(G.nickName);

        et=findViewById(R.id.et);
        listView=findViewById(R.id.listview);
        adapter=new ChatAdapter(messageItems,getLayoutInflater());
        listView.setAdapter(adapter);
        //Firebase DB관리 객체와 'caht'노드 참조객체 얻어오기
        firebaseDatabase= FirebaseDatabase.getInstance();
        chatRef= firebaseDatabase.getReference("chat");
        mButtonSend = findViewById(R.id.button_send_activity_chat);
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSend(view);
            }
        });

        //firebaseDB에서 채팅 메세지들 실시간 읽어오기..
        //'chat'노드에 저장되어 있는 데이터들을 읽어오기
        //chatRef에 데이터가 변경되는 것으 듣는 리스너 추가
        chatRef.addChildEventListener(new ChildEventListener() {
            //새로 추가된 것만 줌 ValueListener는 하나의 값만 바뀌어도 처음부터 다시 값을 줌
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //새로 추가된 데이터(값 : MessageItem객체) 가져오기
                ChatItemData messageItem= dataSnapshot.getValue(ChatItemData.class);
                //새로운 메세지를 리스뷰에 추가하기 위해 ArrayList에 추가
                messageItems.add(messageItem);
                //리스트뷰를 갱신
                adapter.notifyDataSetChanged();
                listView.setSelection(messageItems.size()-1); //리스트뷰의 마지막 위치로 스크롤 위치 이동
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        loadData();
    }

    public void clickSend(View view) {
        //firebase DB에 저장할 값들( 닉네임, 메세지, 프로필 이미지URL, 시간)
        String nickName= G.nickName;
        String message= et.getText().toString();
        String pofileUrl= G.porfileUrl;
        //메세지 작성 시간 문자열로..
        Calendar calendar= Calendar.getInstance(); //현재 시간을 가지고 있는 객체
        String time=calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE); //14:16
        //firebase DB에 저장할 값(MessageItem객체) 설정
        ChatItemData messageItem= new ChatItemData(nickName,message,time,pofileUrl);
        //'char'노드에 MessageItem객체를 통해
        chatRef.push().setValue(messageItem);
        //EditText에 있는 글씨 지우기
        et.setText("");
        //소프트키패드를 안보이도록..
        InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        //처음 시작할때 EditText가 다른 뷰들보다 우선시 되어 포커스를 받아 버림.
        //즉, 시작부터 소프트 키패드가 올라와 있음.

        //그게 싫으면...다른 뷰가 포커스를 가지도록
        //즉, EditText를 감싼 Layout에게 포커스를 가지도록 속성을 추가!![[XML에]
    }

    public void clickBtn(View view) {
        //바꾼것도 없고, 처음 접속도 아니고..
        if(!isChanged && !isFirst){
            //ChatActivity로 전환
            Intent intent= new Intent(this, ChatActivity.class);
            startActivity(intent);
            finish();
        }else{
            //1. save작업
            saveData();
        }
    }

    void saveData(){
        //EditText의 닉네임 가져오기 [전역변수에]
        G.nickName= etName.getText().toString();

        //이미지를 선택하지 않았을 수도 있으므로
        if(imgUri==null) return;
        //Firebase storage에 이미지 저장하기 위해 파일명 만들기(날짜를 기반으로)
        SimpleDateFormat sdf= new SimpleDateFormat("yyyMMddhhmmss"); //20191024111224
        String fileName= sdf.format(new Date())+".png";

        //Firebase storage에 저장하기
        FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();
        final StorageReference imgRef= firebaseStorage.getReference("profileImages/"+fileName);

        //파일 업로드
        UploadTask uploadTask=imgRef.putFile(imgUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //이미지 업로드가 성공되었으므로...
                //곧바로 firebase storage의 이미지 파일 다운로드 URL을 얻어오기
                imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //파라미터로 firebase의 저장소에 저장되어 있는
                        //이미지에 대한 다운로드 주소(URL)을 문자열로 얻어오기
                        G.porfileUrl= uri.toString();
                        Toast.makeText(getApplicationContext(), "프로필 저장 완료", Toast.LENGTH_SHORT).show();
                        //1. Firebase Database에 nickName, profileUrl을 저장
                        //firebase DB관리자 객체 소환
                        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                        //'profiles'라는 이름의 자식 노드 참조 객체 얻어오기
                        DatabaseReference profileRef= firebaseDatabase.getReference("profiles");
                        //닉네임을 key 식별자로 하고 프로필 이미지의 주소를 값으로 저장
                        profileRef.child(G.nickName).setValue(G.porfileUrl);
                        //2. 내 phone에 nickName, profileUrl을 저장
                        SharedPreferences preferences= getSharedPreferences("account",MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString("nickName",G.nickName);
                        editor.putString("profileUrl", G.porfileUrl);
                        editor.commit();
                        //저장이 완료되었으니 ChatActivity로 전환
                        Intent intent=new Intent(getBaseContext(), ChatActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

//    //내 phone에 저장되어 있는 프로필정보 읽어오기
//    void loadData(){
//        SharedPreferences preferences=getSharedPreferences("account",MODE_PRIVATE);
//        G.nickName=preferences.getString("nickName", null);
//        G.porfileUrl=preferences.getString("profileUrl", null);
//    }
}