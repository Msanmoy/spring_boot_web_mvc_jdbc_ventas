package org.iesvdm.modelo;

import jakarta.validation.constraints.*;
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

	@NotBlank(message = "{msg.error.notBlank}")
	@Size(max = 30, message = "{msg.error.size30}")
	private String nombre;

	@NotBlank(message = "{msg.error.notBlank}")
	@Size(max = 30, message = "{msg.error.size30}")
	private String apellido1;

	private String apellido2;

	@DecimalMax(value = "0.946", inclusive = true, message = "{msg.error.comisionMax}")
	@DecimalMin(value = "0.276", inclusive = true, message = "{msg.error.comisionMin}")
	private BigDecimal comision;

	@NotBlank(message = "{msg.error.notBlank}")
	@Email(message = "{msg.error.email}", regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}")
	private String email;
	
}
