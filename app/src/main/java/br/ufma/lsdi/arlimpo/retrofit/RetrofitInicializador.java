package br.ufma.lsdi.arlimpo.retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.ufma.lsdi.arlimpo.service.ResourceService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInicializador {
    private Retrofit retrofit;

    public RetrofitInicializador() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://cidadesinteligentes.lsdi.ufma.br/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    public ResourceService getResources() {
        return retrofit.create(ResourceService.class);
    }


}
