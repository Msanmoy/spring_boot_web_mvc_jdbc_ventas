package org.iesvdm.controlador;

import java.util.Comparator;
import java.util.List;

import jakarta.validation.Valid;
import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.dao.ComercialDAOImpl;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.dao.PedidoDAOImpl;
import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.ClienteService;
import org.iesvdm.service.ComercialService;
import org.iesvdm.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
//Se puede fijar ruta base de las peticiones de este controlador.
//Los mappings de los métodos tendrían este valor /comerciales como
//prefijo.
//@RequestMapping("/comerciales")
public class ComercialController {

    @Autowired
    private ComercialService comercialService;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private PedidoDAOImpl pedidoDAOImpl;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ComercialDAOImpl comercialDAOImpl;


    // LISTAR
    @GetMapping("/comerciales")
    public String listar(Model model) {

        List<Comercial> listaComerciales =  comercialService.listAll();
        model.addAttribute("listaComerciales", listaComerciales);

        return "comerciales";

    }


    //CREAR
    @GetMapping("/comerciales/crear")
    public String crear (Model model) {

        Comercial comercial = new Comercial();

        model.addAttribute("comercial", comercial);

        return "crear-comerciales";
    }

    @PostMapping("/comerciales/crear")
    public String submitCrear(@Valid @ModelAttribute("comercial") Comercial comercial, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("comercial", comercial);
            return "crear-comerciales";
        }

        comercialService.newComercial(comercial);

        return "redirect:/comerciales";

    }


    // EDITAR
    @GetMapping("/comerciales/editar/{id}")
    public String editar (Model model , @PathVariable Integer id) {

        Comercial comercial = comercialService.one(id);

        if (comercial == null) {
            throw new RuntimeException("El comercial no existe");
        }

        model.addAttribute("comercial", comercial);

        return "editar-comerciales";
    }

    @PostMapping("/comerciales/editar/{id}")
    public String editarSubmit(@Valid @ModelAttribute("comercial") Comercial comercial, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("comercial", comercial);
            return "editar-comerciales";
        }

        comercialService.replaceComercial(comercial);

        return "redirect:/comerciales";

    }


    // BORRAR
    @PostMapping("/comerciales/borrar/{id}")
    public RedirectView submitBorrar ( @PathVariable Integer id) {

        comercialService.deleteComercial(id);

        return new RedirectView("/comerciales") ;
    }

    //VER DETALLE
    @GetMapping("/comerciales/{id}")
    public String verDetalle (Model model, @PathVariable Integer id) {

        Comercial comercial = comercialService.one(id);
        model.addAttribute("comercial", comercial);

        List<Pedido> listaPedidos = pedidoDAOImpl.filtrarPedidoPorComercialId(id);
        model.addAttribute("listaPedidos", listaPedidos);

        Cliente cliente = clienteService.one(id);
        model.addAttribute("cliente", cliente);

        ComercialDTO totalPedidos = comercialDAOImpl.totalPedidos(id);
        model.addAttribute("totalPedidos", totalPedidos);

        ComercialDTO mediaPrecioPedidos = comercialDAOImpl.mediaPrecioPedidos(id);
        model.addAttribute("mediaPrecioPedidos", mediaPrecioPedidos);

        List<PedidoDTO> listaPedidoDTO = pedidoDAOImpl.filtrarPedidoPorComercialIdDTO(id);
        model.addAttribute("listaPedidoDTO", listaPedidoDTO);

        PedidoDTO pedidoMaximo = listaPedidoDTO.stream().max(Comparator.comparingDouble(PedidoDTO::getTotal)).orElse(null);
        model.addAttribute("pedidoMaximo", pedidoMaximo);

        PedidoDTO pedidoMinimo = listaPedidoDTO.stream().min(Comparator.comparingDouble(PedidoDTO::getTotal)).orElse(null);
        model.addAttribute("pedidoMinimo", pedidoMinimo);

        List<ClienteDTO> listaCuantia = comercialService.listaCuantia(id);
        model.addAttribute("listaCuantia", listaCuantia);



        return "detalle-comerciales";
    }





}
