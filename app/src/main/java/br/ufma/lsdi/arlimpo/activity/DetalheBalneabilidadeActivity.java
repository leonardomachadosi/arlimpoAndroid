package br.ufma.lsdi.arlimpo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.ufma.lsdi.arlimpo.R;
import br.ufma.lsdi.arlimpo.adapter.DetalheDadosGeraisAdapter;
import br.ufma.lsdi.arlimpo.domain.auxiliar.CapabilityDataAuxiliar;

public class DetalheBalneabilidadeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_balneabilidade);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_detalhe_dados_gerais);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            CapabilityDataAuxiliar capabilityDataAuxiliar = (CapabilityDataAuxiliar) bundle.getSerializable("CapabilityDataAuxiliar");
            setDadosGerais(capabilityDataAuxiliar);
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

}
