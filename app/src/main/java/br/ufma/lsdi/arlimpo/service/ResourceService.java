package br.ufma.lsdi.arlimpo.service;

import br.ufma.lsdi.arlimpo.domain.auxiliar.ResorceHelper;
import br.ufma.lsdi.arlimpo.domain.auxiliar.ResourceAuxiliar;
import br.ufma.lsdi.arlimpo.domain.model.Catalog;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ResourceService {

    @GET("catalog/resources/search?capability=Balneabilidade")
    Call<ResourceAuxiliar> getResources();

    @POST("collector/resources/data/last")
    Call<ResorceHelper> getLastData(@Body Catalog catalog);

}
