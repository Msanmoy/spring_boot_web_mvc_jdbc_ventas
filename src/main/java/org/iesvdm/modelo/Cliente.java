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

	@NotBlank(message = "Por favor, introduzca un nombre")
	@Size(max = 10, message = "El nombre tiene que tener una longitud maxima de 10 caracteres")
	private String nombre;

	@NotBlank(message = "Por favor, introduzca el primer apellido")
	@Size(max = 10, message = "El apellido tiene que tener una longitud maxima de 10 caracteres")
	private String apellido1;

	private String apellido2;

	@NotBlank(message = "Por favor, introduzca una ciudad")
	@Size(max = 50, message = "El campo Ciudad no puede sobreparar la longitud maxima de 50 caracteres")
	private String ciudad;

	@Min(value = 100, message = "El valor tiene que ser mayor de 100")
	@Max(value = 1000, message = "El valor tiene que ser menor de 1000")
	private int categoria;


	
}
