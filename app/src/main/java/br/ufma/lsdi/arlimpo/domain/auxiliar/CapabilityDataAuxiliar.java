package br.ufma.lsdi.arlimpo.domain.auxiliar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import br.ufma.lsdi.arlimpo.domain.model.Resource;

@JsonIgnoreProperties
public class CapabilityDataAuxiliar implements Serializable {

    private String value;
    private String timestamp;
    private Double lat;
    private Double lon;
    private Resource resource;
    private String name;
    private String label;
    private Long index;

    public CapabilityDataAuxiliar(Map<String, Object> capability) {
        try {

            this.value = (String) capability.get("value");
        } catch (Exception e) {
            this.value = String.valueOf((Double) capability.get("value"));
        }
        this.timestamp = (String) capability.get("date");
        this.lat = (Double) capability.get("lat");
        this.lon = (Double) capability.get("lon");
        Map<String, Object> resourceMap = (Map<String, Object>) capability.get("resource");

        Resource resource = new Resource();

        if (resourceMap != null) {
            for (String key : resourceMap.keySet()) {
                resource.setUuid((String) resourceMap.get("uuid"));
                resource.setDescription((String) resourceMap.get("description"));
                resource.setLat((Double) resourceMap.get("lat"));
                resource.setLon((Double) resourceMap.get("lon"));
            }
        }

        this.resource = resource;

    }


    public CapabilityDataAuxiliar() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }
}
