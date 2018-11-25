package br.ufma.lsdi.arlimpo.domain.auxiliar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import br.ufma.lsdi.arlimpo.domain.model.Resource;

@JsonIgnoreProperties
public class ResourceDataAuxiliar implements Serializable {
    private Resource data;

    public Resource getData() {
        return data;
    }

    public void setData(Resource data) {
        this.data = data;
    }
}
