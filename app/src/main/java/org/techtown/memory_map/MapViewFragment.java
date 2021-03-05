package org.techtown.memory_map;
import android.content.Context;
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

public class MapViewFragment extends Fragment implements OnMapReadyCallback{

    MapView mapView;
    View rootView;
    Context context;
    private ServiceApi serviceApi;
    List MarkerList = new ArrayList<>();
    MarkerData markerData;
    LatLng latLng;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mapview, container, false);
        context = container.getContext();
        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
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
        /*
        LatLng curPoint = new LatLng(35.7, 127);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(curPoint, 5);
        googleMap.animateCamera(cameraUpdate);
        googleMap.addMarker(new MarkerOptions().position(curPoint).title("temporary"));*/
       // startMap(new MapData("aa", "aa"));

        //2021.03.04임시로 짜놓은 코드
        startMap();
        for(int i=0; i<MarkerList.size(); i++){
            MarkerOptions marker = new MarkerOptions();
            markerData = (MarkerData)MarkerList.get(i);
            latLng = new LatLng(markerData.getLatitude(), markerData.getLongitude());
            marker.position(latLng);
            googleMap.addMarker(marker);
        }
        //여기까지..
    }

    private void startMap(){
        serviceApi.userMap().enqueue(new Callback<MapResponse>(){

            @Override
            public void onResponse(Call<MapResponse> call, Response<MapResponse> response) {
                MapResponse mapResponse = response.body();
                MarkerList = mapResponse.getData();
                Toast.makeText(context, mapResponse.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MapResponse> call, Throwable t) {
                Toast.makeText(context, "map load fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
