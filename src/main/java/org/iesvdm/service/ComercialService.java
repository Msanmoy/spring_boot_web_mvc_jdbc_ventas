package org.iesvdm.service;

import java.util.List;
import java.util.Optional;

import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComercialService {

    @Autowired
    private ComercialDAO comercialDAO;

    public List<Comercial> listAll() {
        return comercialDAO.getAll();
    }

    public Comercial one(Integer id) {
        Optional<Comercial> optCom = comercialDAO.find(id);
        return optCom.orElse(null);
    }

    public void newComercial(Comercial cliente) {
        comercialDAO.create(cliente);
    }

    public void replaceComercial(Comercial cliente) {
        comercialDAO.update(cliente);
    }

    public void deleteComercial(int id) {
        comercialDAO.delete(id);
    }

    public List<ClienteDTO> listaCuantia(int id) {
        return comercialDAO.listaCuantia(id);
    }

}