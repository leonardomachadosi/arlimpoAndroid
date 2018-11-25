package br.ufma.lsdi.arlimpo.service;

import br.ufma.lsdi.arlimpo.domain.auxiliar.ResourceAuxiliar;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ResourceService {

    @GET("catalog/resources")
    Call<ResourceAuxiliar> getResources();

}
