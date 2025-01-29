package org.iesvdm.service;

import java.util.List;
import java.util.Optional;

import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoDAO pedidoDAO;

    public List<Pedido> listAll() {
        return pedidoDAO.getAll();
    }

    public Pedido one(Integer id) {
        Optional<Pedido> optCom = pedidoDAO.find(id);
        return optCom.orElse(null);
    }

}