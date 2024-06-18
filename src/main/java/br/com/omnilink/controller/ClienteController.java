package br.com.omnilink.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.omnilink.model.Cliente;
import br.com.omnilink.service.ClienteService;

/**
 * Classe controladora
 * 
 * @author Allan Dellon
 */

@RestController
@RequestMapping(value = "/api")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	

	@PostMapping(value = "/clientes")
	public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
		try {
			
			Cliente clienteNovo = clienteService.buscarPorCpf(cliente.getCpf());
			
			HttpStatus httpStatus = null;
		
			if(clienteNovo==null) {
				httpStatus = HttpStatus.CREATED;
				clienteNovo = clienteService.cadastrar(cliente);
			}else {
				httpStatus = HttpStatus.CONFLICT;
			}
			
			return new ResponseEntity<Cliente>(clienteNovo, httpStatus);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Cliente>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/clientes")
	public ResponseEntity<List<Cliente>> listarTodosClientes() {
		List<Cliente> cliente = clienteService.listarTodos();

		if (cliente.isEmpty()) {
			return new ResponseEntity<List<Cliente>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Cliente>>(cliente, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/clientes/{id}")
	public ResponseEntity<Optional<Cliente>> listarClientePorId(@PathVariable Integer id) {
		Optional<Cliente> cliente = clienteService.buscarPorId(id);
		
		if (cliente.isEmpty()) {
			return new ResponseEntity<Optional<Cliente>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Optional<Cliente>>(cliente, HttpStatus.OK);			
		}
	}
	
	@PutMapping(value = "/clientes/{id}")
	public ResponseEntity<Cliente> atualizarCliente(@RequestBody Cliente cliente, @PathVariable Integer id) {		
		try {
			Cliente clienteUpdate = clienteService.atualizar(cliente, id);
			return new ResponseEntity<Cliente>(clienteUpdate, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Cliente>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(value = "/clientes/{id}")
	public ResponseEntity<String> removerClientePorId(@PathVariable Integer id) {
		Optional<Cliente> cliente = clienteService.buscarPorId(id);
		
		if (cliente == null) {
			return new ResponseEntity<String>("Funcionário com id " + id + " não encontrado", HttpStatus.NOT_FOUND);
		} else {
			try {
				clienteService.remover(cliente.get());
				return ResponseEntity.noContent().build();
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>("Erro ao remover o Cliente com o nome " + cliente.get().getNome()	, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

}
