package br.ufma.lsdi.arlimpo.domain.auxiliar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

import br.ufma.lsdi.arlimpo.domain.model.Resource;
import br.ufma.lsdi.arlimpo.domain.model.ResourceData;

@JsonIgnoreProperties
public class ResorceHelper implements Serializable {

    List<ResourceData> resources;

    public List<ResourceData> getResources() {
        return resources;
    }

    public void setResources(List<ResourceData> resources) {
        this.resources = resources;
    }
}
