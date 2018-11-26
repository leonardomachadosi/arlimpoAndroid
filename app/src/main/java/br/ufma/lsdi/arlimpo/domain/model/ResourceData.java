package br.ufma.lsdi.arlimpo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufma.lsdi.arlimpo.domain.auxiliar.CapabilityDataAuxiliar;

@JsonIgnoreProperties
public class ResourceData implements Serializable {

    private String uuid;

    Map<String, List<CapabilityDataAuxiliar>> capabilities;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Map<String, List<CapabilityDataAuxiliar>> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Map<String, List<CapabilityDataAuxiliar>> capabilities) {
        this.capabilities = capabilities;
    }
}
