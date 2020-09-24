package com.example.salonspace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentMypage extends Fragment implements OnMapReadyCallback {
    private GoogleMap mgoogleMap;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v= (View)inflater.inflate(R.layout.fragment_mypage, container, false);
        MapView mapView = (MapView)v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googlemap) {
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
