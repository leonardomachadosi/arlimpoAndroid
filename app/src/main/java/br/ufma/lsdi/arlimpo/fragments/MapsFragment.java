package br.ufma.lsdi.arlimpo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.ufma.lsdi.arlimpo.R;
import br.ufma.lsdi.arlimpo.activity.MainActivity;
import br.ufma.lsdi.arlimpo.domain.auxiliar.CapabilityDataAuxiliar;
import br.ufma.lsdi.arlimpo.domain.auxiliar.ResorceHelper;
import br.ufma.lsdi.arlimpo.domain.auxiliar.ResourceAuxiliar;
import br.ufma.lsdi.arlimpo.domain.helper.GetDataContextResource;
import br.ufma.lsdi.arlimpo.domain.helper.Novo;
import br.ufma.lsdi.arlimpo.domain.model.Catalog;
import br.ufma.lsdi.arlimpo.domain.model.Resource;
import br.ufma.lsdi.arlimpo.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private ResourceAuxiliar resourceAuxiliar;
    private Catalog catalog;
    private String capabilityName = "Balneabilidade";

    private List<String> capabilities;
    private List<String> uuids;
    private List<CapabilityDataAuxiliar> capabilityDataAuxiliars;


    public MapsFragment() {

        resourceAuxiliar = new ResourceAuxiliar();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        capabilities = new ArrayList<>();
        uuids = new ArrayList<>();
        catalog = new Catalog();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.toolbar.setTitle(capabilityName);
        try {
            getRecursos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.clear();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    /**
     * Busca todos os recursos na plataforma InterSCity
     *
     * @throws Exception
     */
    private void getRecursos() throws Exception {

        Call<ResourceAuxiliar> call = new RetrofitInicializador().getResources().getResources();
        try {

            call.enqueue(new Callback<ResourceAuxiliar>() {
                @Override
                public void onResponse(Call<ResourceAuxiliar> call, Response<ResourceAuxiliar> response) {
                    resourceAuxiliar = new ResourceAuxiliar();
                    resourceAuxiliar = response.body();

                    if (resourceAuxiliar != null && !resourceAuxiliar.getResources().isEmpty()) {
                        for (Resource re : resourceAuxiliar.getResources()) {
                            if (re.getLat() != null) {
                                for (String cap : re.getCapabilities()) {
                                    if (cap.equals(capabilityName)) {
                                        // plot(resource);
                                        uuids.add(re.getUuid());
                                    }
                                }
                            }
                        }
                    }

                    if (!uuids.isEmpty()) {
                        catalog = new Catalog();
                        capabilities.add(capabilityName);
                        catalog.setCapabilities(capabilities);
                        catalog.setUuids(uuids);
                        try {
                            getLastData(catalog);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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


    /**
     * Busca o ultimo dado de contexto de um conjunto de recursos
     *
     * @param catalog
     * @throws Exception
     */
    private void getLastData(Catalog catalog) throws Exception {
        Call<ResorceHelper> call = new RetrofitInicializador().getResources().getLastData(catalog);
        try {

            call.enqueue(new Callback<ResorceHelper>() {
                @Override
                public void onResponse(Call<ResorceHelper> call, Response<ResorceHelper> response) {
                    ResorceHelper resorceHelper = new ResorceHelper();
                    resorceHelper = response.body();
                    capabilityDataAuxiliars = new ArrayList<>();
                    if (resorceHelper != null && resorceHelper.getResources() != null) {

                        for (GetDataContextResource getDataContextResource : resorceHelper.getResources()) {
                            Map<String, List<Map<String, Object>>> capability = getDataContextResource.getCapabilities();
                            List<Map<String, Object>> data = capability.get("Balneabilidade");

                            for (Map<String, Object> cap : data) {

                                CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar(cap);
                                if (dataAuxiliar.getLat() != null) {
                                    plot(dataAuxiliar);
                                }
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<ResorceHelper> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void plot(CapabilityDataAuxiliar capabilityDataAuxiliar) {

        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.green);

        LatLng now = new LatLng(capabilityDataAuxiliar.getResource().getLon(), capabilityDataAuxiliar.getResource().getLat());
        if (capabilityDataAuxiliar.getValue().equals("PROPRIO")) {
            mMap.addMarker(new MarkerOptions()
                    .position(now)
                    .icon(bitmapDescriptor)
                    .title(capabilityDataAuxiliar.getResource().getDescription() +
                            " (" + capabilityDataAuxiliar.getValue() +
                            ")"));
        } else {

            mMap.addMarker(new MarkerOptions()
                    .position(now)
                    .title(capabilityDataAuxiliar.getResource().getDescription() +
                            " (" + capabilityDataAuxiliar.getValue() +
                            ")"));

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(now));
    }
}
