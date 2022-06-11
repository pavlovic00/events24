package com.pavlovic.cubes.events24.ui.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pavlovic.cubes.events24.R;
import com.pavlovic.cubes.events24.data.model.Event;
import com.pavlovic.cubes.events24.databinding.FragmentMapsBinding;
import com.pavlovic.cubes.events24.databinding.FragmentProfileBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MapsFragment extends Fragment {

    private FragmentMapsBinding binding;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            getEventsList(googleMap);
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    binding.infoView.setVisibility(View.VISIBLE);
                    Event event = (Event) marker.getTag();
                    binding.textViewTitle.setText(event.title);
                    return false;
                }
            });

        }
    };

    public static Fragment newInstance() {

        return new MapsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentMapsBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private void getEventsList(GoogleMap googleMap) {

        if (googleMap == null) {
            return;
        }

        FirebaseFirestore.getInstance().collection("events")
                .whereNotEqualTo("coordinates", null).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                //prodji koz listu dokumenata
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                    Event event = new Event(document.getId(), document.getData());
                    LatLng eventLocation = new LatLng(event.coordinates.getLatitude(), event.coordinates.getLongitude());

                    MarkerOptions options = new MarkerOptions();
                    options.title(event.title);
                    options.position(eventLocation);

                    if (event.type.equalsIgnoreCase("Koncert")) {
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.concert));
                    } else if (event.type.equalsIgnoreCase("Sport")) {
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.sport));
                    } else if (event.type.equalsIgnoreCase("Pozoriste")) {
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.theater));
                    }

                    Marker marker =  googleMap.addMarker(options);
                    marker.setTag(event);
                }

                LatLng camerePosition = new LatLng(43.31992230958837, 21.914473190751913);
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(camerePosition, 12);
                googleMap.animateCamera(update);


                //googleMap.setMyLocationEnabled(true);
            }
        });
    }
}