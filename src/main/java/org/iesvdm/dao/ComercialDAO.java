package org.iesvdm.dao;

import java.util.List;
import java.util.Optional;

import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.modelo.Comercial;

public interface ComercialDAO {
	
	void create(Comercial cliente);
	
	List<Comercial> getAll();
	Optional<Comercial>  find(int id);
	
	void update(Comercial cliente);
	
	void delete(long id);

	ComercialDTO totalPedidos(int id);

	ComercialDTO mediaPrecioPedidos(int id);

	List<ClienteDTO>listaCuantia(int id);
}
