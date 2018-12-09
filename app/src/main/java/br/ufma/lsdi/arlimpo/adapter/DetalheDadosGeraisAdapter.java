package br.ufma.lsdi.arlimpo.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.ufma.lsdi.arlimpo.R;
import br.ufma.lsdi.arlimpo.domain.auxiliar.CapabilityDataAuxiliar;


/**
 * Created by Leeo on 25/10/2016.
 */

public class DetalheDadosGeraisAdapter extends RecyclerView.Adapter<DetalheDadosGeraisAdapter.DetalheDadosGeraisViewHolder> {

    private LayoutInflater mLayoutInflater;
    final Dialog dialog;
    private final Context context;
    private static final String TAG = "DetalheDadosGeraisAdapter";
    private CapabilityDataAuxiliar dataAuxiliar;

    public DetalheDadosGeraisAdapter(Context context, CapabilityDataAuxiliar dataAuxiliar) {
        this.dataAuxiliar = dataAuxiliar;
        this.context = context;
        dialog = new Dialog(context);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public DetalheDadosGeraisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_detalhe_dados_gerais, parent, false);
        CardView cardView = (CardView) v.findViewById(R.id.cv_dados_pessoais_preso_adapter);
        DetalheDadosGeraisViewHolder mvh = new DetalheDadosGeraisViewHolder(v);


        return mvh;
    }

    @Override
    public void onBindViewHolder(final DetalheDadosGeraisViewHolder holder, final int position) {


        String titulo = dataAuxiliar.getResource().getDescription() != null ? dataAuxiliar.getResource().getDescription() : "";
        String cidade = dataAuxiliar.getResource().getCity() != null ? dataAuxiliar.getResource().getCity() : "";
        String lat = dataAuxiliar.getResource().getLat() != null ? dataAuxiliar.getResource().getLat() + "" : "";
        String lon = dataAuxiliar.getResource().getLon() != null ? dataAuxiliar.getResource().getLon() + "" : "";
        String status = dataAuxiliar.getValue() != null ? dataAuxiliar.getValue() : "";

        holder.titulo.setText(titulo);
        holder.cidade.setText(Html.fromHtml("<b>Cidade: </b>" + cidade));
        holder.lat.setText(Html.fromHtml("<b>Latitude: </b>" + lat));
        holder.lon.setText(Html.fromHtml("<b>Longitude: </b>" + lon));
        holder.status.setText(status.toUpperCase());
        if (status.equals("PROPRIO")) {
            holder.status.setTextColor(context.getResources().getColor(R.color.dot_dark_screen2));
        } else {
            holder.status.setTextColor(context.getResources().getColor(R.color.dot_dark_screen1));
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class DetalheDadosGeraisViewHolder extends RecyclerView.ViewHolder {
        private TextView titulo;
        private TextView cidade;
        private TextView status;
        private TextView lat;
        private TextView lon;

        public DetalheDadosGeraisViewHolder(final View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.tv_adapter_titulo);
            cidade = (TextView) itemView.findViewById(R.id.tv_adapter_cidade);
            status = (TextView) itemView.findViewById(R.id.tv_adapter_status_name);
            lat = (TextView) itemView.findViewById(R.id.tv_adapter_lat);
            lon = (TextView) itemView.findViewById(R.id.tv_adapter_lon);

        }
    }

}
