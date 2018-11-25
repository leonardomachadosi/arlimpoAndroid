package br.ufma.lsdi.arlimpo.domain.auxiliar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

import br.ufma.lsdi.arlimpo.domain.model.Resource;
public class ResourceAuxiliar implements Serializable {

    private List<Resource> resources;

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
