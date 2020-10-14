package com.example.salonspace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mgoogleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment supportMapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

    }
    @Override
    public void onMapReady(final GoogleMap googlemap){
        mgoogleMap = googlemap;
        final LatLng Address=new LatLng(37.283102,127.044884);
        MarkerOptions markerOptions=new MarkerOptions(); //마커 추가
        markerOptions.position(Address);
        markerOptions.title("아주대");  //마커옵션 추가
        googlemap.addMarker(markerOptions); //마커등록

        googlemap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mgoogleMap.moveCamera(CameraUpdateFactory.newLatLng(Address));
                mgoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        }); // 구글 맵 로딩 완료되면 카메라 조정 하기위함
    }

}