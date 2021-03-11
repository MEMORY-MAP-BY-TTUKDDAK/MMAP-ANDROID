package org.techtown.memory_map;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MapViewFragment extends Fragment implements OnMapReadyCallback{

    MapView mapView;
    View rootView;
    Context context;
    private ServiceApi serviceApi;
    List<MarkerData> MarkerList = new ArrayList<>();
    MarkerData markerData;
    LatLng latLng;
    int userIdx;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mapview, container, false);
        context = container.getContext();
        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        SharedPreferences sharedPreferences = context.getSharedPreferences("login", MODE_PRIVATE);
        userIdx = sharedPreferences.getInt("userIdx", -1);
        return rootView;
    }

    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(this.getActivity());
        startMap();
        for(int i=0; i<MarkerList.size(); i++){
            MarkerOptions marker = new MarkerOptions();
            markerData = (MarkerData)MarkerList.get(i);
            latLng = new LatLng(markerData.getLatitude(), markerData.getLongitude());
            System.out.println("위도 : "+markerData.getLatitude() + "경도 : " + markerData.getLongitude());
            marker.position(latLng);
            googleMap.addMarker(marker);
        }
    }

    private void startMap(){
        serviceApi.userMap(userIdx).enqueue(new Callback<MapResponse>(){
            @Override
            public void onResponse(Call<MapResponse> call, Response<MapResponse> response) {
                MapResponse mapResponse = response.body();
                try{
                    MarkerList = mapResponse.getData();
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MapResponse> call, Throwable t) {
                Toast.makeText(context, "map load fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
