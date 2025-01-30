package org.iesvdm.controlador;

import java.util.List;

import jakarta.validation.Valid;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
//Se puede fijar ruta base de las peticiones de este controlador.
//Los mappings de los métodos tendrían este valor /clientes como
//prefijo.
//@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;


	// LISTAR
	@GetMapping("/clientes")
	public String listar(Model model) {

		List<Cliente> listaClientes =  clienteService.listAll();
		model.addAttribute("listaClientes", listaClientes);

		return "clientes";

	}


	//CREAR
	@GetMapping("/clientes/crear")
	public String crear (Model model) {

		Cliente cliente = new Cliente();

		model.addAttribute("cliente", cliente);

		return "crear-clientes";
	}

	@PostMapping("/clientes/crear")
	public String submitCrear(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("cliente", cliente);
			return "crear-clientes";
		}

		clienteService.newCliente(cliente);

		return "redirect:/clientes";

	}


	// EDITAR
	@GetMapping("/clientes/editar/{id}")
	public String editar (Model model , @PathVariable Integer id) {

		Cliente cliente = clienteService.one(id);

		model.addAttribute("cliente", cliente);

		return "editar-clientes";
	}

	@PostMapping("/clientes/editar/{id}")
	public String editarSubmit(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("cliente", cliente);
			return "editar-clientes";
		}

		clienteService.replaceCliente(cliente);

		return "redirect:/clientes";

	}


	// BORRAR
	@PostMapping("/clientes/borrar/{id}")
	public RedirectView submitBorrar ( @PathVariable Integer id) {

		clienteService.deleteCliente(id);

		return new RedirectView("/clientes") ;
	}

	//VER DETALLE
	@GetMapping("/clientes/{id}")
	public String verDetalle (Model model, @PathVariable Integer id) {

		Cliente cliente = clienteService.one(id);

		model.addAttribute("cliente", cliente);

		return "detalle-clientes";
	}



}
