package br.ufma.lsdi.arlimpo.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import br.ufma.lsdi.arlimpo.R;
import br.ufma.lsdi.arlimpo.domain.auxiliar.CapabilityDataAuxiliar;
import br.ufma.lsdi.arlimpo.util.DateUtil;


/**
 * Created by Leeo on 25/10/2016.
 */

public class DetalheDadosGraficoAdapter extends RecyclerView.Adapter<DetalheDadosGraficoAdapter.DetalheDadosGraficoViewHolder> {

    private LayoutInflater mLayoutInflater;
    final Dialog dialog;
    private final Context context;
    private static final String TAG = "DetalheDadosGeraisAdapter";
    private List<CapabilityDataAuxiliar> capabilityDataAuxiliars;

    public DetalheDadosGraficoAdapter(Context context, Set<CapabilityDataAuxiliar> dataAuxiliars) {
        this.capabilityDataAuxiliars = new ArrayList<>();
        this.capabilityDataAuxiliars.addAll(dataAuxiliars);
        this.context = context;
        dialog = new Dialog(context);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Collections.sort(capabilityDataAuxiliars, new Comparator<CapabilityDataAuxiliar>() {
            @Override
            public int compare(CapabilityDataAuxiliar o1, CapabilityDataAuxiliar o2) {
                Date date1;
                Date date2;

                date1 = DateUtil.convertTimestampData(o1.getTimestamp());
                date2 = DateUtil.convertTimestampData(o2.getTimestamp());
                if (date2.after(date1)) {
                    return 1;
                }
                if (date2.before(date1)) {
                    return -1;
                }

                return 0;
            }
        });
    }

    @Override
    public DetalheDadosGraficoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_lista_historico, parent, false);
        DetalheDadosGraficoViewHolder mvh = new DetalheDadosGraficoViewHolder(v);

        return mvh;
    }

    @Override
    public void onBindViewHolder(final DetalheDadosGraficoViewHolder holder, final int position) {

        String lista[] = new String[2];
        lista = capabilityDataAuxiliars.get(position).getTimestamp().split("T");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = formatter.parse(lista[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.data.setText(DateUtil.toDate(date, DateUtil.DATA_SEPARADO_POR_TRACO));
        holder.valor.setText(capabilityDataAuxiliars.get(position).getValue());
        if (capabilityDataAuxiliars.get(position).getValue().equals("PROPRIO")) {
            holder.valor.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            holder.valor.setTextColor(context.getResources().getColor(R.color.red));

            holder.imageView.setImageResource(R.mipmap.red_marker);
        }
    }

    @Override
    public int getItemCount() {
        return capabilityDataAuxiliars.size();
    }

    public static class DetalheDadosGraficoViewHolder extends RecyclerView.ViewHolder {
        private TextView data;
        private TextView valor;
        private ImageView imageView;

        public DetalheDadosGraficoViewHolder(final View itemView) {
            super(itemView);
            data = (TextView) itemView.findViewById(R.id.cardtitle);
            valor = (TextView) itemView.findViewById(R.id.tv_situacao);
            imageView = (ImageView) itemView.findViewById(R.id.cardimage);
        }
    }

}
