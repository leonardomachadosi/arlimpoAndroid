package br.ufma.lsdi.arlimpo.domain.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

import br.ufma.lsdi.arlimpo.domain.auxiliar.CapabilityDataAuxiliar;

@JsonIgnoreProperties
public class Novo implements Serializable {

    private List<CapabilityDataAuxiliar> Balneabilidade;

    public List<CapabilityDataAuxiliar> getBalneabilidade() {
        return Balneabilidade;
    }

    public void setBalneabilidade(List<CapabilityDataAuxiliar> balneabilidade) {
        Balneabilidade = balneabilidade;
    }
}
