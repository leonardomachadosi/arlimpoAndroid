package br.ufma.lsdi.arlimpo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties
public class Capability implements Serializable {

    private Long id;
    private String name;
    private int function;
    private String description;
    private String capability_type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFunction() {
        return function;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCapability_type() {
        return capability_type;
    }

    public void setCapability_type(String capability_type) {
        this.capability_type = capability_type;
    }
}
