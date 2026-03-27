package com.mgcss.l2g8.domain;

import java.util.Date;

import com.mgcss.l2g8.domain.enums.Estado;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Solicitud {
    
    private Long id;
    private Estado estado;
    private Date fechaCreacion;


    public void cerrarSolicitud() {
        if (estado == Estado.PROCESANDO) {
            estado = Estado.CERRADA;
        }
        else{
            throw new IllegalStateException("La solicitud no se puede cerrar porque no está en estado PROCESANDO.");
        }
    }

    


}
