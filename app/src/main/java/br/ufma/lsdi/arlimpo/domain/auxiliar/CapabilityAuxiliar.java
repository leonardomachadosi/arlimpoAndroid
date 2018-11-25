package br.ufma.lsdi.arlimpo.domain.auxiliar;

import java.io.Serializable;
import java.util.List;

import br.ufma.lsdi.arlimpo.domain.model.Capability;
import lombok.Data;

@Data
public class CapabilityAuxiliar  implements Serializable {

    private List<Capability> capabilities;
}
