package br.ufma.lsdi.arlimpo.domain.auxiliar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import br.ufma.lsdi.arlimpo.domain.model.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties
@NoArgsConstructor
@AllArgsConstructor
public class CapabilityDataAuxiliar implements Serializable {

    private String value;
    private String timestamp;
    private Double lat;
    private Double lon;
    private Resource resource;


}
