package br.ufma.lsdi.arlimpo.domain.model;

import java.io.Serializable;

public class ResourceData implements Serializable {

    private String uuid;
    private Balneabilidade capabilities;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Balneabilidade getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Balneabilidade capabilities) {
        this.capabilities = capabilities;
    }
}
