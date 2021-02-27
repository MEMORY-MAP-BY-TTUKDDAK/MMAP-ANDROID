package org.techtown.memory_map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {
    @POST("/user/signup")
    Call<JoinResponse> userJoin(@Body JoinData data);
}