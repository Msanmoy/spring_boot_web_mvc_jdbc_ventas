package org.iesvdm.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//Anotación lombok para logging (traza) de la aplicación
@Slf4j
@Repository
//Utilizo lombok para generar el constructor
@AllArgsConstructor
public class ComercialDAOImpl implements ComercialDAO {

	//JdbcTemplate se inyecta por el constructor de la clase automáticamente
	//
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void create(Comercial comercial) {
		//Desde java15+ se tiene la triple quote """ para bloques de texto como cadenas.
		String sqlInsert = """
							INSERT INTO comercial (nombre, apellido1, apellido2, comision, email) 
							VALUES  (     ?,         ?,         ?,       ?,		?)
							""";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		//Con recuperación de id generado
		int rows = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] { "id" });
			int idx = 1;
			ps.setString(idx++, comercial.getNombre());
			ps.setString(idx++, comercial.getApellido1());
			ps.setString(idx++, comercial.getApellido2());
			ps.setString(idx++, String.valueOf(comercial.getComision()));
			ps.setString(idx++, comercial.getEmail());
			return ps;
		},keyHolder);

		comercial.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());

		//Sin recuperación de id generado
//		int rows = jdbcTemplate.update(sqlInsert,
//							cliente.getNombre(),
//							cliente.getApellido1(),
//							cliente.getApellido2(),
//							cliente.getCiudad(),
//							cliente.getCategoria()
//					);

		log.info("Insertados {} registros.", rows);



	}

	@Override
	public List<Comercial> getAll() {
		
		List<Comercial> listComercial = jdbcTemplate.query(
                "SELECT * FROM comercial",
                (rs, rowNum) -> new Comercial(rs.getInt("id"), 
                							  rs.getString("nombre"), 
                							  rs.getString("apellido1"),
                							  rs.getString("apellido2"), 
                							  rs.getBigDecimal("comision"),
						 					  rs.getString("email"))
                						 	
        );
		
		log.info("Devueltos {} registros.", listComercial.size());
		
        return listComercial;
	}

	@Override
	public Optional<Comercial> find(int id) {
		Comercial comercial =  jdbcTemplate
				.queryForObject("SELECT * FROM comercial WHERE id = ?"
								, (rs, rowNum) -> new Comercial(rs.getInt("id"),
            						 						rs.getString("nombre"),
            						 						rs.getString("apellido1"),
            						 						rs.getString("apellido2"),
            						 						rs.getBigDecimal("comision"),
															rs.getString("email"))
						,id
								);
		
		if (comercial != null) {
			return Optional.of(comercial);}
		else { 
			log.info("Comercial no encontrado.");
			return Optional.empty(); }
	}

	@Override
	public void update(Comercial comercial) {
		int rows = jdbcTemplate.update("""
										UPDATE comercial SET
														nombre = ?,
														apellido1 = ?,
														apellido2 = ?,
														comision = ?,
														email = ?
												WHERE id = ?
										""", comercial.getNombre()
										, comercial.getApellido1()
										, comercial.getApellido2()
										, comercial.getComision()
										, comercial.getEmail()
										, comercial.getId());
		
		log.info("Update de Comercial con {} registros actualizados.", rows);

	}

	@Override
	public void delete(long id) {
		int rows2 = jdbcTemplate.update("DELETE FROM pedido WHERE id_comercial = ?" ,id);
		int rows = jdbcTemplate.update("DELETE FROM comercial WHERE id = ?", id);

		log.info("Delete de Comercial con {} registros eliminados.", rows);

	}

	@Override
	public ComercialDTO totalPedidos(int id) {
		String sql = """
				SELECT count(*) as totalPedidos FROM pedido where id_comercial = ?
				""";

		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ComercialDTO.class), id);
	}

	@Override
	public ComercialDTO mediaPrecioPedidos(int id) {
		String sql = """
        SELECT COALESCE(ROUND(AVG(total),2), 0.0) AS mediaPrecioPedidos 
        FROM pedido 
        WHERE id_comercial = ?
    """;

		Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);

		// Obtener el valor de la media y asegurarnos de que no sea null
		Double mediaPrecioPedidos = (result.get("mediaPrecioPedidos") != null)
				? ((Number) result.get("mediaPrecioPedidos")).doubleValue()
				: 0.0;

		ComercialDTO comercial = new ComercialDTO();
		comercial.setMediaPrecioPedidos(mediaPrecioPedidos);

		return comercial;
	}

	@Override
	public List<ClienteDTO> listaCuantia(int id) {

		String query = "SELECT c.nombre, ROUND(SUM(p.total), 2)  AS cuantia FROM pedido p JOIN cliente c ON c.id = p.id_cliente WHERE p.id_comercial = ? GROUP BY c.id, c.nombre ORDER BY cuantia DESC;";

		return  jdbcTemplate.query(query, new BeanPropertyRowMapper<>(ClienteDTO.class), id);

	}

}
