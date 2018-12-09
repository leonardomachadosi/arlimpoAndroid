package br.ufma.lsdi.arlimpo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import br.ufma.lsdi.arlimpo.R;
import br.ufma.lsdi.arlimpo.activity.DetalheBalneabilidadeActivity;
import br.ufma.lsdi.arlimpo.activity.MainActivity;
import br.ufma.lsdi.arlimpo.domain.auxiliar.CapabilityDataAuxiliar;
import br.ufma.lsdi.arlimpo.domain.helper.ResourceHelper;
import br.ufma.lsdi.arlimpo.domain.auxiliar.ResourceAuxiliar;
import br.ufma.lsdi.arlimpo.domain.helper.GetDataContextResource;
import br.ufma.lsdi.arlimpo.domain.model.Catalog;
import br.ufma.lsdi.arlimpo.domain.model.Resource;
import br.ufma.lsdi.arlimpo.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private ResourceAuxiliar resourceAuxiliar;
    private Catalog catalog;
    private String capabilityName = "Balneabilidade";
    private ResourceHelper resourceHelper;

    private static final List<String> capabilities = Arrays.asList("Balneabilidade", "PM10");
    private List<String> uuids;
    private List<CapabilityDataAuxiliar> capabilityDataAuxiliars;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        uuids = new ArrayList<>();
        resourceAuxiliar = new ResourceAuxiliar();
        catalog = new Catalog();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.toolbar.setTitle(capabilityName);
        try {
            if (resourceAuxiliar.getResources() != null) {
                getRecursos();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Integer countClick = (Integer) marker.getTag();
                if (countClick != null) {
                    countClick = countClick + 1;
                    marker.setTag(countClick);
                    if (countClick > 1) {

                        for (GetDataContextResource getDataContextResource : resourceHelper.getResources()) {
                            Map<String, List<Map<String, Object>>> capability = getDataContextResource.getCapabilities();
                            List<Map<String, Object>> data = capability.get("Balneabilidade");
                            if (data != null) {
                                for (Map<String, Object> cap : data) {
                                    CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar(cap);
                                    if (dataAuxiliar.getResource().getLat() != null) {
                                        if (dataAuxiliar.getResource().getLon().equals(marker.getPosition().latitude)
                                                && dataAuxiliar.getResource().getLat().equals(marker.getPosition().longitude)) {
                                            Intent intent = new Intent(getActivity(), DetalheBalneabilidadeActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("CapabilityDataAuxiliar", dataAuxiliar);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        }
                                    }
                                }
                            }

                            List<Map<String, Object>> dataSensor = capability.get("PM10");
                            if (dataSensor != null) {
                                for (Map<String, Object> cap : dataSensor) {
                                    CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar(cap);
                                    if (dataAuxiliar.getResource().getLat() != null) {
                                        if (dataAuxiliar.getResource().getLat().equals(marker.getPosition().latitude)
                                                && dataAuxiliar.getResource().getLon().equals(marker.getPosition().longitude)) {
                                            Intent intent = new Intent(getActivity(), DetalheBalneabilidadeActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("CapabilityDataAuxiliar", dataAuxiliar);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            }
        });
        mMap.clear();
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
                                    if (cap.equals("Balneabilidade")) {
                                        uuids.add(re.getUuid());
                                    }
                                }
                            }
                        }
                    }

                    if (!uuids.isEmpty()) {
                        catalog = new Catalog();
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

        Gson gson = new Gson();
        System.out.printf(gson.toJson(catalog));

        Call<ResourceHelper> call = new RetrofitInicializador().getResources().getLastData(catalog);
        try {

            call.enqueue(new Callback<ResourceHelper>() {
                @Override
                public void onResponse(Call<ResourceHelper> call, Response<ResourceHelper> response) {
                    resourceHelper = new ResourceHelper();
                    resourceHelper = response.body();
                    capabilityDataAuxiliars = new ArrayList<>();
                    if (resourceHelper != null && resourceHelper.getResources() != null) {

                        for (GetDataContextResource getDataContextResource : resourceHelper.getResources()) {
                            Map<String, List<Map<String, Object>>> capability = getDataContextResource.getCapabilities();
                            List<Map<String, Object>> data = capability.get("Balneabilidade");

                            if (data != null) {
                                for (Map<String, Object> cap : data) {

                                    CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar(cap);
                                    if (dataAuxiliar.getLat() != null) {
                                        plot(dataAuxiliar);
                                    }
                                }
                            }

                            List<Map<String, Object>> dataSensor = capability.get("PM10");

                            if (dataSensor != null) {
                                for (Map<String, Object> cap2 : dataSensor) {
                                    CapabilityDataAuxiliar dataAuxiliar2 = new CapabilityDataAuxiliar(cap2);
                                    if (dataAuxiliar2.getResource() != null && dataAuxiliar2.getResource().getLat() != null) {
                                        plot(dataAuxiliar2);
                                    }
                                }
                            }

                        }

                    }
                }

                @Override
                public void onFailure(Call<ResourceHelper> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void plot(CapabilityDataAuxiliar capabilityDataAuxiliar) {

        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.teste);

        LatLng now = new LatLng(capabilityDataAuxiliar.getResource().getLon(), capabilityDataAuxiliar.getResource().getLat());
        if (capabilityDataAuxiliar.getValue().equals("PROPRIO")) {
            marker = mMap.addMarker(new MarkerOptions()
                    .position(now)
                    .icon(bitmapDescriptor)
                    .title(capabilityDataAuxiliar.getResource().getDescription() +
                            " (" + capabilityDataAuxiliar.getValue() +
                            ")"));
        } else if (capabilityDataAuxiliar.getValue().equals("IMPROPRIO")) {

            marker = mMap.addMarker(new MarkerOptions()
                    .position(now)
                    .title(capabilityDataAuxiliar.getResource().getDescription()));
        } else {
            now = new LatLng(capabilityDataAuxiliar.getResource().getLat(), capabilityDataAuxiliar.getResource().getLon());
            marker = mMap.addMarker(new MarkerOptions()
                    .position(now)
                    .title(capabilityDataAuxiliar.getResource().getDescription()));
        }
        marker.setTag(0);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(now));
        setCameraPosition(now);
    }

    private void setCameraPosition(LatLng position) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(position)
                .zoom(12)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000, null);
    }
}
