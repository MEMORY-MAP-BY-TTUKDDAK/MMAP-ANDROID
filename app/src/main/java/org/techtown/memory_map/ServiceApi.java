package org.techtown.memory_map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ServiceApi {
    @Headers({"Content-Type: application/json"})
    @POST("/user/signup")
    Call<JoinResponse> userJoin(@Body JoinData data);

    @POST("/user/signin")
    Call<LoginResponse> userLogin(@Body LoginData data);
}
