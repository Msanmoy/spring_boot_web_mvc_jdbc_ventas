package org.iesvdm.dao;

import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoDAO {

    public void create(Pedido pedido);

    public List<Pedido> getAll();
    public Optional<Pedido> find(int id);

    public void update(Pedido pedido);

    public void delete(long id);

    public List<Pedido> filtrarPedidoPorComercialId(int id);

    public List<PedidoDTO> filtrarPedidoPorComercialIdDTO(int id);

}

