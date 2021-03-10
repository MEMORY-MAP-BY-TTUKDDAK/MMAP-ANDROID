package org.techtown.memory_map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public interface ServiceApi {
    @Headers({"Content-Type: application/json"})
    @POST("/user/signup")
    Call<JoinResponse> userJoin(@Body JoinData data);

    @POST("/user/signin")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @GET("/map/{userIdx}")
    Call<MapResponse> userMap(@Path("userIdx") int userIdx);

	@GET("/list/{userIdx}")
    Call<RecordResponse> getData(@Path("userIdx") int userIdx);
    //Call<RecordResponse> getData(@Body RecordResponse.Data data);

    @POST("/record")
    Call<EditResponse> userEdit(@Header("token") String token, @Body EditData data) ;
}
