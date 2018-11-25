package br.ufma.lsdi.arlimpo.domain.auxiliar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import br.ufma.lsdi.arlimpo.domain.model.Resource;

@JsonIgnoreProperties
public class CapabilityDataAuxiliar implements Serializable {

    private String value;
    private String timestamp;
    private Double lat;
    private Double lon;
    private Resource resource;

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
}
