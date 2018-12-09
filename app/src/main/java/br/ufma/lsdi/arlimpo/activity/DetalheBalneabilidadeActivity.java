package br.ufma.lsdi.arlimpo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.ufma.lsdi.arlimpo.R;
import br.ufma.lsdi.arlimpo.adapter.DetalheDadosGeraisAdapter;
import br.ufma.lsdi.arlimpo.adapter.DetalheDadosGraficoAdapter;
import br.ufma.lsdi.arlimpo.domain.auxiliar.CapabilityDataAuxiliar;
import br.ufma.lsdi.arlimpo.domain.helper.GetDataContextResource;
import br.ufma.lsdi.arlimpo.domain.helper.ResourceHelper;
import br.ufma.lsdi.arlimpo.domain.model.Catalog;
import br.ufma.lsdi.arlimpo.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalheBalneabilidadeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerViewChart;
    private LinearLayoutManager mLayoutManagerChart;
    private RecyclerView.Adapter mAdapterChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_balneabilidade);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_detalhe_dados_gerais);
        mRecyclerViewChart = (RecyclerView) findViewById(R.id.rv_detalhe_chart);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            CapabilityDataAuxiliar capabilityDataAuxiliar = (CapabilityDataAuxiliar) bundle.getSerializable("CapabilityDataAuxiliar");
            setDadosGerais(capabilityDataAuxiliar);
            try {
                getHistoricalData("Balneabilidade", capabilityDataAuxiliar.getResource().getUuid());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setDadosGerais(CapabilityDataAuxiliar dadosGerais) {
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new DetalheDadosGeraisAdapter(this, dadosGerais);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setDadosChart(Set<CapabilityDataAuxiliar> auxiliars) {
        mLayoutManagerChart = new LinearLayoutManager(this);
        mRecyclerViewChart.setHasFixedSize(true);
        mRecyclerViewChart.setLayoutManager(mLayoutManagerChart);
        mRecyclerViewChart.setItemAnimator(new DefaultItemAnimator());
        mAdapterChart = new DetalheDadosGraficoAdapter(this, auxiliars);
        mRecyclerViewChart.setAdapter(mAdapterChart);
    }


    public void getHistoricalData(String capability, String uuid) throws Exception {

        Catalog catalog = new Catalog();
        catalog.setCapabilities(Arrays.asList(capability));
        Call<ResourceHelper> call =
                new RetrofitInicializador().getResources().getHistoricalDataByResouceAndCapability(uuid, catalog);

        try {

            call.enqueue(new Callback<ResourceHelper>() {
                @Override
                public void onResponse(Call<ResourceHelper> call, Response<ResourceHelper> response) {
                    ResourceHelper resourceHelper = new ResourceHelper();
                    resourceHelper = response.body();
                    Set<CapabilityDataAuxiliar> capabilityDataAuxiliars = new HashSet<>();
                    if (resourceHelper != null && resourceHelper.getResources() != null) {
                        for (GetDataContextResource getDataContextResource : resourceHelper.getResources()) {
                            Map<String, List<Map<String, Object>>> capability = getDataContextResource.getCapabilities();
                            List<Map<String, Object>> data = capability.get("Balneabilidade");

                            if (data != null) {
                                for (Map<String, Object> cap : data) {
                                    CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar(cap);
                                    if (dataAuxiliar.getLat() != null) {
                                        capabilityDataAuxiliars.add(dataAuxiliar);
                                    }
                                }

                                if (!capabilityDataAuxiliars.isEmpty()) {
                                    setDadosChart(capabilityDataAuxiliars);
                                }
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<ResourceHelper> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
