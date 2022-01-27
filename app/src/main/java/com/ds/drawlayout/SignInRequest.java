package com.ds.drawlayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SignInRequest extends StringRequest {
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://닷홈 호스트/Login.php";
    private Map<String, String> map;
    public SignInRequest(String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userPassword", userPassword);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}