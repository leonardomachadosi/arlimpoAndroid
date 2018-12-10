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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import br.ufma.lsdi.arlimpo.R;
import br.ufma.lsdi.arlimpo.activity.MainActivity;
import br.ufma.lsdi.arlimpo.domain.auxiliar.CapabilityDataAuxiliar;
import br.ufma.lsdi.arlimpo.domain.auxiliar.ResourceAuxiliar;
import br.ufma.lsdi.arlimpo.domain.auxiliar.ResourceData;
import br.ufma.lsdi.arlimpo.domain.helper.GetDataContextResource;
import br.ufma.lsdi.arlimpo.domain.helper.ResourceHelper;
import br.ufma.lsdi.arlimpo.domain.model.Catalog;
import br.ufma.lsdi.arlimpo.domain.model.Resource;
import br.ufma.lsdi.arlimpo.retrofit.RetrofitInicializador;
import br.ufma.lsdi.arlimpo.util.IndexUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArMapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private ResourceAuxiliar resourceAuxiliar;
    private Catalog catalog;
    private ResourceHelper resourceHelper;

    private List<String> uuids;
    private List<CapabilityDataAuxiliar> capabilityDataAuxiliars;
    private List<ResourceData> resourceDatas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ar_maps, container, false);
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
        MainActivity.toolbar.setTitle("Qualidade do Ar");
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
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Integer countClick = (Integer) marker.getTag();
                if (countClick != null) {
                    countClick = countClick + 1;
                    marker.setTag(countClick);
                    if (countClick > 1) {

                    }
                }
                return false;
            }
        });
        mMap.clear();
    }

    public void getRecursos() throws Exception {

        Call<ResourceAuxiliar> call = new RetrofitInicializador().getResources().getResourcesSensor();

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
                                    if (cap.equals("PM10")) {
                                        if (!re.getUuid().equals("70b8f4fe-3f17-4dcb-beff-cfb586fb344f")) {
                                            uuids.add(re.getUuid());
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (!uuids.isEmpty()) {
                        catalog = new Catalog();
                        catalog.setCapabilities(Arrays.asList("PM10", "PM25", "OZONE", "SULFURE_DIOXIDE", "NITROGEN_DIOXIDE"));
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

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLastData(Catalog catalog) throws Exception {

        Gson gson = new Gson();
        Log.d("LEO", gson.toJson(catalog));
        Call<ResourceHelper> call = new RetrofitInicializador().getResources().getLastData(catalog);
        try {

            call.enqueue(new Callback<ResourceHelper>() {
                @Override
                public void onResponse(Call<ResourceHelper> call, Response<ResourceHelper> response) {
                    resourceHelper = new ResourceHelper();
                    resourceHelper = response.body();
                    capabilityDataAuxiliars = new ArrayList<>();
                    capabilityDataAuxiliars = new ArrayList<>();
                    resourceDatas = new ArrayList<>();
                    if (resourceHelper != null && resourceHelper.getResources() != null) {

                        for (GetDataContextResource getDataContextResource : resourceHelper.getResources()) {
                            Map<String, List<Map<String, Object>>> capability = getDataContextResource.getCapabilities();

                            List<Map<String, Object>> dataSensor = capability.get("PM10");
                            List<Map<String, Object>> dataSensorPM25 = capability.get("PM25");
                            List<Map<String, Object>> dataSensorOzone = capability.get("OZONE");
                            List<Map<String, Object>> dataSensorSulfureDioxide = capability.get("SULFURE_DIOXIDE");
                            List<Map<String, Object>> dataSensorNitrogenDioxide = capability.get("NITROGEN_DIOXIDE");

                            ResourceData resourceData = new ResourceData();
                            List<CapabilityDataAuxiliar> dataAuxiliars = new ArrayList<>();

                            resourceData.setUuid(getDataContextResource.getUuid());
                            if (dataSensor != null) {
                                for (Map<String, Object> cap2 : dataSensor) {
                                    CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar(cap2);
                                    if (dataAuxiliar.getResource() != null && dataAuxiliar.getResource().getLat() != null) {
                                        if (dataAuxiliar.getValue() != null) {
                                            dataAuxiliar.setName("PM10");
                                            dataAuxiliars.add(dataAuxiliar);
                                        }
                                    }
                                }
                            }

                            if (dataSensorPM25 != null) {
                                for (Map<String, Object> cap2 : dataSensorPM25) {
                                    CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar(cap2);
                                    if (dataAuxiliar.getResource() != null && dataAuxiliar.getResource().getLat() != null) {
                                        if (dataAuxiliar.getValue() != null) {
                                            dataAuxiliar.setName("PM25");
                                            dataAuxiliars.add(dataAuxiliar);
                                        }
                                    }
                                }
                            }

                            if (dataSensorOzone != null) {
                                for (Map<String, Object> cap2 : dataSensorOzone) {
                                    CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar(cap2);
                                    if (dataAuxiliar.getResource() != null && dataAuxiliar.getResource().getLat() != null) {
                                        if (dataAuxiliar.getValue() != null) {
                                            dataAuxiliar.setName("OZONE");
                                            dataAuxiliars.add(dataAuxiliar);
                                        }
                                    }
                                }
                            }

                            if (dataSensorSulfureDioxide != null) {
                                for (Map<String, Object> cap2 : dataSensorSulfureDioxide) {
                                    CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar(cap2);
                                    if (dataAuxiliar.getResource() != null && dataAuxiliar.getResource().getLat() != null) {
                                        if (dataAuxiliar.getValue() != null) {
                                            dataAuxiliar.setName("SULFURE_DIOXIDE");
                                            dataAuxiliars.add(dataAuxiliar);
                                        }
                                    }
                                }
                            }

                            if (dataSensorNitrogenDioxide != null) {
                                for (Map<String, Object> cap2 : dataSensorNitrogenDioxide) {
                                    CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar(cap2);
                                    if (dataAuxiliar.getResource() != null && dataAuxiliar.getResource().getLat() != null) {
                                        if (dataAuxiliar.getValue() != null) {
                                            dataAuxiliar.setName("NITROGEN_DIOXIDE");
                                            dataAuxiliars.add(dataAuxiliar);
                                        }
                                    }
                                }
                            }

                            resourceData.getCapabilityDataAuxiliars().addAll(dataAuxiliars);
                            resourceDatas.add(resourceData);


                        }
                        if (!resourceDatas.isEmpty()) {
                            for (ResourceData resourceData : resourceDatas) {
                                if (!resourceData.getCapabilityDataAuxiliars().isEmpty()) {
                                    IndexUtil.getIndexScore(resourceData.getCapabilityDataAuxiliars());
                                    plot(resourceData);
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

    private void plot(ResourceData resourceData) {

        List<CapabilityDataAuxiliar> lista = resourceData.getCapabilityDataAuxiliars();
        Collections.sort(lista, new Comparator<CapabilityDataAuxiliar>() {
            @Override
            public int compare(CapabilityDataAuxiliar o1, CapabilityDataAuxiliar o2) {
                Long value1;
                Long value2;

                value1 = o1.getIndex();
                value2 = o2.getIndex();
                if (value2 > value1) {
                    return -1;
                }
                if (value2 < value1) {
                    return 1;
                }
                return 0;
            }
        });

        CapabilityDataAuxiliar dataAuxiliar = lista.get(0);
        LatLng now = new LatLng(dataAuxiliar.getResource().getLat(), dataAuxiliar.getResource().getLon());
        BitmapDescriptor bitmapDescriptor = null;
        if (dataAuxiliar.getLabel().equals(IndexUtil.BAIXO)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.baixo1);
        } else if (dataAuxiliar.getLabel().equals(IndexUtil.BAIXO2)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.baixo2);
        } else if (dataAuxiliar.getLabel().equals(IndexUtil.BAIXO3)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.baixo3);
        } else if (dataAuxiliar.getLabel().equals(IndexUtil.MODERADO)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.moderado1);
        } else if (dataAuxiliar.getLabel().equals(IndexUtil.MODERADO2)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.moderado2);
        } else if (dataAuxiliar.getLabel().equals(IndexUtil.MODERADO3)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.moderado3);
        } else if (dataAuxiliar.getLabel().equals(IndexUtil.ALTO)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.alto1);
        } else if (dataAuxiliar.getLabel().equals(IndexUtil.ALTO2)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.alto2);
        } else if (dataAuxiliar.getLabel().equals(IndexUtil.ALTO3)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.alto3);
        } else if (dataAuxiliar.getLabel().equals(IndexUtil.MUITOALTO)) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.muitoalto);
        }
        marker = mMap.addMarker(new MarkerOptions()
                .position(now)
                .icon(bitmapDescriptor)
                .title(dataAuxiliar.getResource().getDescription() +
                        " (" + dataAuxiliar.getLabel() +
                        ")"));
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
