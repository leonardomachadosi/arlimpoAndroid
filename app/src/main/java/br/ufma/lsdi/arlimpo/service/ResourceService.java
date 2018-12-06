package br.ufma.lsdi.arlimpo.service;

import br.ufma.lsdi.arlimpo.domain.helper.ResourceHelper;
import br.ufma.lsdi.arlimpo.domain.auxiliar.ResourceAuxiliar;
import br.ufma.lsdi.arlimpo.domain.model.Catalog;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ResourceService {

    @GET("catalog/resources/search?capability=Balneabilidade")
    Call<ResourceAuxiliar> getResources();

    @GET("catalog/resources/search?capability=PM10")
    Call<ResourceAuxiliar> getResourcesSensor();

    @POST("collector/resources/data/last")
    Call<ResourceHelper> getLastData(@Body Catalog catalog);

    @POST("collector/resources/data")
    Call<ResourceHelper> getAllDataByCapability(@Body Catalog catalog);

}
