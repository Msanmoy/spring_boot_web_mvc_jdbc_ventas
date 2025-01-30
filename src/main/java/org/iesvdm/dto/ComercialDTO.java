package org.iesvdm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComercialDTO {

    private int totalPedidos = 0;
    private double mediaPrecioPedidos = 0.0;

}
