package daewhan.example.com.lab_googlemap;

import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    static final LatLng SEOUL = new LatLng( 37.56, 126.97);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
            @Override
            public void gotLocation(Location location) {

                String msg = "lon: "+location.getLongitude()+" -- lat: "+location.getLatitude();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                drawMarker(location);

            }
        };

        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(getApplicationContext(), locationResult);
    }



    private void drawMarker(Location location) {

        mMap.clear();//기존 마커 지우기

        LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 17));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);

        //마커 추가
        mMap.addMarker(new MarkerOptions()
                .position(currentPosition)
                .snippet("Lat:" + location.getLatitude() + "Lng:" + location.getLongitude())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title("현재위치"));
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Seoul and move the camera
        mMap.addMarker(new MarkerOptions().position(SEOUL).title("Marker in Seoul"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }
}
