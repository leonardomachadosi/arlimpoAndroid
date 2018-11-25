package br.ufma.lsdi.arlimpo.domain.auxiliar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

import br.ufma.lsdi.arlimpo.domain.model.Capability;

@JsonIgnoreProperties
public class CapabilityAuxiliar  implements Serializable {

    private List<Capability> capabilities;

    public List<Capability> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<Capability> capabilities) {
        this.capabilities = capabilities;
    }
}
