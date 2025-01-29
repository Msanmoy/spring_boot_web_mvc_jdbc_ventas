package org.iesvdm.controlador;

import java.util.List;

import org.iesvdm.dao.PedidoDAOImpl;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.ClienteService;
import org.iesvdm.service.ComercialService;
import org.iesvdm.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public RedirectView submitCrear(@ModelAttribute("comercial") Comercial comercial) {

        comercialService.newComercial(comercial);

        return new RedirectView("/comerciales") ;

    }


    // EDITAR
    @GetMapping("/comerciales/editar/{id}")
    public String editar (Model model , @PathVariable Integer id) {

        Comercial comercial = comercialService.one(id);

        model.addAttribute("comercial", comercial);

        return "editar-comerciales";
    }

    @PostMapping("/comerciales/editar/{id}")
    public RedirectView editarSubmit(@ModelAttribute("comercial") Comercial comercial) {
        comercialService.replaceComercial(comercial);

        return new RedirectView("/comerciales");

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


        return "detalle-comerciales";
    }





}
