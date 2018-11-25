package br.ufma.lsdi.arlimpo.domain.auxiliar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties
public class Data implements Serializable {

    private List<CapabilityDataAuxiliar> data;

    public List<CapabilityDataAuxiliar> getData() {
        return data;
    }

    public void setData(List<CapabilityDataAuxiliar> data) {
        this.data = data;
    }
}
