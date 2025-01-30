package org.iesvdm.modelo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//La anotación @Data de lombok proporcionará el código de:
//getters/setters, toString, equals y hashCode
//propio de los objetos POJOS o tipo Beans
@Data
//Para generar un constructor con lombok con todos los args
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {
	
	private long id;

	@NotBlank(message = "{msg.error.notBlank}")
	@Size(max = 10, message = "{msg.error.size10}")
	private String nombre;

	@NotBlank(message = "{msg.error.notBlank}")
	@Size(max = 10, message = "{msg.error.size10}")
	private String apellido1;

	private String apellido2;

	@NotBlank(message = "{msg.error.notBlank}")
	@Size(max = 50, message = "{msg.error.size50}")
	private String ciudad;

	@Min(value = 100, message = "{msg.error.categoriaMin}")
	@Max(value = 1000, message = "{msg.error.categoriaMax}")
	private int categoria;


	
}
