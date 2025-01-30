package org.iesvdm.modelo;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comercial {

	private int id;

	@NotBlank(message = "Campo Obligatorio")
	@Size(max = 30, message = "El nombre no puede sobrepasar la longitud maxima de 30 caracteres")
	private String nombre;

	@NotBlank(message = "Campo Obligatorio")
	@Size(max = 30, message = "El Apellido no puede sobrepasar la longitud maxima de 30 caracteres")
	private String apellido1;

	private String apellido2;

	@DecimalMax(value = "0.946", inclusive = true, message = "La comisión no puede ser menor a 0.276")
	@DecimalMin(value = "0.276", inclusive = true, message = "La comisión no puede ser mayor a 0.946")
	private BigDecimal comision;
	
}
