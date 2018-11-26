package br.ufma.lsdi.arlimpo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties
public class DataBalneabilidade implements Serializable {

    private Object Balneabilidade;

    public Object getBalneabilidade() {
        return Balneabilidade;
    }

    public void setBalneabilidade(Object balneabilidade) {
        Balneabilidade = balneabilidade;
    }
}
