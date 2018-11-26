package br.ufma.lsdi.arlimpo.domain.auxiliar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.ufma.lsdi.arlimpo.domain.model.Resource;

@JsonIgnoreProperties
public class ResourceAuxiliar implements Serializable {

    private List<Resource> resources;

    public List<Resource> getResources() {
        if (resources == null) {
            resources = new ArrayList<>();
        }
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
