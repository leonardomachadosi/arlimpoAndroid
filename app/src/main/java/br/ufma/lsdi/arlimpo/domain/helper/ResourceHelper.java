package br.ufma.lsdi.arlimpo.domain.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

import br.ufma.lsdi.arlimpo.domain.helper.GetDataContextResource;
import br.ufma.lsdi.arlimpo.domain.model.Resource;
import br.ufma.lsdi.arlimpo.domain.model.ResourceData;

@JsonIgnoreProperties
public class ResourceHelper implements Serializable {

    List<GetDataContextResource> resources;

    public List<GetDataContextResource> getResources() {
        return resources;
    }

    public void setResources(List<GetDataContextResource> resources) {
        this.resources = resources;
    }
}
