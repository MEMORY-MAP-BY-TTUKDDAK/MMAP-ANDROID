package org.techtown.memory_map;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;


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

    @Multipart
    @POST("/record")
    Call<EditResponse> userEdit(@Header("token")String token, @Part MultipartBody.Part postImg, @PartMap HashMap<String, RequestBody> data);

    @DELETE("/list/delete")
    Call<DeleteResponse> deleteRecord(@Header("token")String token, @Path("makerIdx") int markerIdx);

    @Multipart
    @POST("/list/update/{markerIdx}")
    Call<ModifyResponse> modifyRecord(@Header("token")String token, @Part MultipartBody.Part postImg, @PartMap HashMap<String, RequestBody> data);
}
