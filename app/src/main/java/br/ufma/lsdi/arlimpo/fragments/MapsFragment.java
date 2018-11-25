package br.ufma.lsdi.arlimpo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.ufma.lsdi.arlimpo.R;
import br.ufma.lsdi.arlimpo.domain.auxiliar.ResourceAuxiliar;
import br.ufma.lsdi.arlimpo.domain.model.Resource;
import br.ufma.lsdi.arlimpo.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private ResourceAuxiliar resourceAuxiliar;

    public MapsFragment() {

        resourceAuxiliar = new ResourceAuxiliar();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            getRecursos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    private void getRecursos() throws Exception {

        Call<ResourceAuxiliar> call = new RetrofitInicializador().getResources().getResources();
        try {

            call.enqueue(new Callback<ResourceAuxiliar>() {
                @Override
                public void onResponse(Call<ResourceAuxiliar> call, Response<ResourceAuxiliar> response) {
                    resourceAuxiliar = new ResourceAuxiliar();
                    resourceAuxiliar = response.body();

                    if (resourceAuxiliar != null && !resourceAuxiliar.getResources().isEmpty()) {
                        resourceAuxiliar.getResources().forEach(resource -> plot(resource));
                    }

                }

                @Override
                public void onFailure(Call<ResourceAuxiliar> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void plot(Resource resource) {
        LatLng sydney = new LatLng(resource.getLat(), resource.getLon());
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
