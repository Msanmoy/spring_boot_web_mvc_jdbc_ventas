package org.iesvdm.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
//Para generar un constructor con lombok con todos los args
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pedido {
    private long id;
    private double total;
    private Date fecha;
    private int id_cliente;
    private int id_comercial;
}
