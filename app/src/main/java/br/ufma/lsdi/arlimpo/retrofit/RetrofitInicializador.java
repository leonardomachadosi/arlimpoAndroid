package br.ufma.lsdi.arlimpo.retrofit;


import br.ufma.lsdi.arlimpo.service.ResourceService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInicializador {
    private Retrofit retrofit;

    public RetrofitInicializador() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://cidadesinteligentes.lsdi.ufma.br/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

    }

    public ResourceService getResources() {
        return retrofit.create(ResourceService.class);
    }



}
