package com.example.gohangout;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    // 중복 체크를 위한 GET 요청 정의 (서버 API에 맞게 수정 필요)
    @GET("/user/checkDuplicate")
    Call<Boolean> checkDuplicate(@Query("152.67.209.177:3000") String name);

        @POST("/logout")
        Call<Void> logout();

        @DELETE("/deleteAccount")
        Call<Void> deleteAccount();


}
