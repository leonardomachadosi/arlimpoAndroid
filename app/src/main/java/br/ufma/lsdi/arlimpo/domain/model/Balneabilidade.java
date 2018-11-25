package br.ufma.lsdi.arlimpo.domain.model;

import java.io.Serializable;
import java.util.List;

import br.ufma.lsdi.arlimpo.domain.auxiliar.CapabilityDataAuxiliar;

public class Balneabilidade implements Serializable {

    private List<CapabilityDataAuxiliar> Balneabilidade;

    public List<CapabilityDataAuxiliar> getBalneabilidade() {
        return Balneabilidade;
    }

    public void setBalneabilidade(List<CapabilityDataAuxiliar> balneabilidade) {
        Balneabilidade = balneabilidade;
    }

}
