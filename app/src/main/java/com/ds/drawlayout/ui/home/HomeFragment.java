package com.ds.drawlayout.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ds.drawlayout.ChatActivity;
import com.ds.drawlayout.MainActivity;
import com.ds.drawlayout.R;
import com.ds.drawlayout.SignInActivity;
import com.ds.drawlayout.SignInRequest;
import com.ds.drawlayout.SignUpActivity;
import com.ds.drawlayout.databinding.FragmentHomeBinding;
import com.ds.drawlayout.ui.logout.LogoutFragment;
import com.ds.drawlayout.ui.map.MapFragment;
import com.ds.drawlayout.ui.notification.NotificationFragment;
import com.ds.drawlayout.ui.settings.SettingsFragment;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Map;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding mBinding;
//    private FloatingActionMenu mFloatingActionButtonMain;
    private FloatingActionButton mFloatingActionButtonMain, mFloatingActionButtonMap, mFloatingActionButtonInfo;
    private Button mButtonSignIn, mButtonSignUp, mButtonFacebook, mButtonGoogle;
    private SignInButton mButtonGoogleNew;
    private EditText mEditTextId, mEditTextPassword;
    private final int requestCodeHomeFragment = 4;
    private Context mContext;
    private TextView txt_preferences,mForgetPassword;
    private HomeViewModel mHomeViewModel;
    private Animation fab_open, fab_close;
    private boolean isFabOpen;
    private SignInActivity mSignInActivity;
    private MapFragment mMapFragment;
//    private String strtext = getArguments().getString("edttext");

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();
        mEditTextId = root.findViewById(R.id.edittext_id);
        mEditTextPassword = root.findViewById(R.id.edittext_password);
        mButtonSignIn = root.findViewById(R.id.button_signin);
        mButtonSignUp = root.findViewById(R.id.button_signup);
        mButtonFacebook = root.findViewById(R.id.button_login_facebook_fragment_home);
        mButtonGoogle = root.findViewById(R.id.button_login_google_fragment_home);
        mButtonGoogleNew = root.findViewById(R.id.button_login_google_new);
        mFloatingActionButtonMain = root.findViewById(R.id.floating_button_main_fragment_home);
        mFloatingActionButtonMain.setImageResource(android.R.drawable.ic_input_add);
        mFloatingActionButtonMap = root.findViewById(R.id.floating_button_map_fragment_home);
        mFloatingActionButtonInfo = root.findViewById(R.id.floating_button_info_fragment_home);
        mForgetPassword = root.findViewById(R.id.textview_forget_fragment_home);
        mButtonSignIn.setOnClickListener(this::onClick);
        mButtonSignUp.setOnClickListener(this::onClick);
        mButtonFacebook.setOnClickListener(this);
        mButtonSignIn.setOnClickListener(this::onClick);
        mButtonGoogle.setOnClickListener(this::onClick);
        mButtonGoogleNew.setOnClickListener(this);
        mFloatingActionButtonMain.setOnClickListener(this);
        mFloatingActionButtonMap.setOnClickListener(this);
        mFloatingActionButtonInfo.setOnClickListener(this);
        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.ani_fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(), R.anim.ani_fab_close);
        mForgetPassword.setOnClickListener(this);
        mHomeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
//        mHomeViewModel.getSelectedItem().observe(getViewLifecycleOwner(), list -> {
//            // Update the list UI
//        });

//        mCallbackManager = CallbackManager.Factory.create();
//        mSignInRequest = new SignInRequest();
//        mButtonFacebook.setReadPermissions(Arrays.asList("public_profile", "email"));
//        mButtonFacebook.registerCallback(mCallbackManager, mSignInRequest);

//        mContext = root.getContext();
//        String text = mSharedPreference.getString(mContext, "rebuild");
//        if (text.equals("")) {
//            text = "저장된 데이터가 없습니다.";
//            mSharedPreference.setString(mContext, "rebuild", "숲속의 작은 이야기");
//        }
//
//        txt_preferences = root.findViewById(R.id.textView);
//        txt_preferences.setText(text);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onCreateView()");
        mBinding = null;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        mCallbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button_signup:
                Log.d(TAG, "button_signup : ");
                Intent intent = new Intent(getContext(), SignUpActivity.class);
                startActivity(intent);
                break;

            case R.id.button_signin:
                Log.d(TAG, "button_signin : ");
                String userID = mEditTextId.getText().toString();
                String userPass = mEditTextPassword.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {//로그인 성공시
                                Log.d(TAG, "if (success)");
                                String userID = jsonObject.getString("userID");
                                String userPass = jsonObject.getString("userPassword");
                                String userCell = jsonObject.getString("userCell");
                                Toast.makeText(getContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), SignInActivity.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPass", userPass);
                                intent.putExtra("userCell", userCell);
                                startActivity(intent);
                            } else {//로그인 실패시
                                Log.d(TAG, "else()");
                                Toast.makeText(getContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            Log.d(TAG, "catch (JSONException e)");
                            e.printStackTrace();
                        }
                    }
                };
                SignInRequest loginRequest = new SignInRequest(userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(loginRequest);
                break;

            case R.id.textview_forget_fragment_home:
                Log.d(TAG, "textview_forget_fragment_home : ");
                Intent intent1 = new Intent(getContext(), SignUpActivity.class);
                startActivity(intent1);
                break;

            case R.id.button_login_google_fragment_home:
                Log.d(TAG, "button_login_google_fragment_home : ");
//                MapFragment newFragment = new MapFragment();
//                Bundle args = new Bundle();
////                args.putInt(MapFragment.ARG_POSITION, position);
//                newFragment.setArguments(args);
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragment_container_view_tag, newFragment);
//                transaction.addToBackStack(null);
//                transaction.commit();

                Intent intent3 = new Intent(getActivity(), MainActivity.class);
                startActivity(intent3);
                break;

            case R.id.button_login_facebook_fragment_home:
                Log.d(TAG, "button_login_facebook_fragment_home : ");
//                getChildFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_container, getParentFragment()).commit();
//                break;

                MainActivity activity = (MainActivity) getActivity();
                activity.moveToDetailNotification();

            case R.id.floating_button_main_fragment_home:
                Log.d(TAG, "floating_button_main_fragment_home : ");
                toggleFab();
                break;

//                if(isFabOpen) isFabOpen = false;
//                else isFabOpen = true;

                // 디민 값
//                WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//                View parent = getView();
//                WindowManager.LayoutParams p = (WindowManager.LayoutParams) parent.getLayoutParams();
//                p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//                p.dimAmount = 0.4f;
//                wm.updateViewLayout(parent, p);

            case R.id.floating_button_map_fragment_home:
                Log.d(TAG, "floating_button_map_fragment_home : ");
                startActivity(new Intent(getContext(), ChatActivity.class));
                toggleFab();
                break;

                //IllegalArgumentException: No view found for id 0x7f0800e5 (com.ds.drawlayout2:id/fragment_container_view_tag) for fragment SettingsFragment{9f5551d} (6728447d-5505-4cbe-9e1b-d0a166f6b864 id=0x7f0800e5)
//                SettingsFragment settingsFragment = new SettingsFragment();
//                getChildFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, settingsFragment).commit();

            // java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.Class java.lang.Object.getClass()' on a null object reference
//                getChildFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, mMapFragment).commit();
//                getFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, mMapFragment).commit();


            //ActivityNotFoundException //Unable to find explicit activity class {com.ds.drawlayout2/com.ds.drawlayout.ui.map.MapFragment}; have you declared this activity in your AndroidManifest.xml?
//                Intent intent2 = new Intent(getContext(), MapFragment.class);
//                startActivity(intent2);

            case R.id.floating_button_info_fragment_home:
                Log.d(TAG, "R.id.floating_button_info_fragment_home : ");
                toggleFab();
                Toast.makeText(getContext(), "Information \n" + "Motherfucking Open-!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.button_login_google_new:
                Log.d(TAG, "R.id.button_login_google_new : ");
                Intent intent4 = new Intent(getContext(), SignInActivity.class);
                startActivity(intent4);
                break;
//                        return true;

//                mSignInActivity.signIn();

//                SupportFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//                // 맵이 사용할 준비가 되면 onMapReady 함수를 자동으로 호출된다
//                mapFragment.signIn(getClass());
//                //프래그먼트가 호출된 상위 액티비티를 가져올수있음 (나를 호출한 액티비티를 가져옴) / getActivity는 나를 가지고있는 액티비티를 뜻한다
//                //상위 액티비티의 자원을 사용하기 위해서 Activity를 가져온다
//                MainActivity activity = (MainActivity) getActivity();
//                manager = activity.getLocationManager();

//                SignInActivity activity1 = (SignInActivity) getActivity();
//                activity1.signIn();

//                FragmentManager parentManager = getParentFragmentManager();
//                parentManager.beginTransaction().replace(R.id.activity_);

//                SettingsFragment settingsFragment = new SettingsFragment();
//                FragmentManager fragmentManager = getSupportFragmentManager(settingsFragment);
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.commit();

            default:
                Log.d(TAG, "default()");
                Toast toast = Toast.makeText(getContext(), "The Service of the \nInformationService Soon will be Generated", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 10);
                toast.show();

//                Toast toast = new Toast(getContext());
//                toast.makeText(getContext(), "The Information \nService Soon be Generated", Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.toString();
//                toast.show();

////                toast.setGravity(Gravity.CENTER,0,0);
//                TextView tv = (TextView) toast.getView();
////                if(tv != null) tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                if( tv != null) {
//                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                    tv.setGravity(Gravity.CENTER);
//                }
//                toast.show();
//                break;

//                Toast.makeText(getContext(), "The Information \nService Soon be Generated", Toast.LENGTH_LONG).show();
//                break;
        }
    }

//    private FragmentManager getSupportFragmentManager() {
//        String ARG_POSITION = "0";
//        int position = 0;
//        return getSupportFragmentManager();
//    }

    private void toggleFab() {
        if (isFabOpen) {
            Log.d(TAG, "if (isFabOpen)");
            mFloatingActionButtonMain.setImageResource(android.R.drawable.ic_input_add);
            mFloatingActionButtonMap.startAnimation(fab_close);
            mFloatingActionButtonInfo.startAnimation(fab_close);
            mFloatingActionButtonMap.setClickable(false);
            mFloatingActionButtonInfo.setClickable(false);
            isFabOpen = false;

        } else {
            Log.d(TAG, "else()");
            mFloatingActionButtonMain.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
            mFloatingActionButtonMap.startAnimation(fab_open);
            mFloatingActionButtonInfo.startAnimation(fab_open);
            mFloatingActionButtonMap.setClickable(true);
            mFloatingActionButtonInfo.setClickable(true);
            isFabOpen = true;
        }
    }
}