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

import br.com.omnilink.dto.VeiculoDTO;
import br.com.omnilink.model.Veiculo;
import br.com.omnilink.service.VeiculoService;

/**
 * Classe controladora
 * 
 * @author Allan Dellon
 */

@RestController
@RequestMapping(value = "/api")
public class VeiculoController {
	
	@Autowired
	private VeiculoService veiculoService;
	
	@PostMapping(value = "/veiculos")
	public ResponseEntity<Veiculo> cadastrarVeiculo(@RequestBody Veiculo veiculo) {
		try {
			
			Optional<Veiculo> veiculoOptional = veiculoService.buscarPorPlaca(veiculo.getPlaca());
			HttpStatus httpStatus = null;
			Veiculo veiculoNovo = new Veiculo();
		
			if(!veiculoOptional.isPresent()) {
				httpStatus = HttpStatus.CREATED;
				veiculoNovo = veiculoService.cadastrar(veiculo);
			}else {
				veiculoNovo = veiculoOptional.get();
				httpStatus = HttpStatus.CONFLICT;
			}
			
			return new ResponseEntity<Veiculo>(veiculoNovo, httpStatus);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Veiculo>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/veiculos")
	public ResponseEntity<List<Veiculo>> listarTodosVeiculos() {
		List<Veiculo> veiculo = veiculoService.listarTodos();

		if (veiculo.isEmpty()) {
			return new ResponseEntity<List<Veiculo>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Veiculo>>(veiculo, HttpStatus.OK);
		}
	}
	
//	@GetMapping(value = "/veiculos/{id}")
//	public ResponseEntity<Optional<Veiculo>> listarVeiculoPorId(@PathVariable Integer id) {
//		Optional<Veiculo> veiculo = veiculoService.listarPorId(id);
//		
//		if (veiculo.isEmpty()) {
//			return new ResponseEntity<Optional<Veiculo>>(HttpStatus.NOT_FOUND);
//		} else {
//			return new ResponseEntity<Optional<Veiculo>>(veiculo, HttpStatus.OK);			
//		}
//	}
	
	//com MapStructs
	//TODO adicionar plugin do mapstruct no pom para funcionar
	@GetMapping(value = "/veiculos/{placa}")
	public ResponseEntity<VeiculoDTO> listarVeiculosPorPlaca(@PathVariable String placa) {
		  Optional<Veiculo> veiculoOptional = veiculoService.buscarPorPlaca(placa);
	        
	        if (veiculoOptional.isEmpty()) {
	            return ResponseEntity.notFound().build();
	        } else {
	            Veiculo veiculo = veiculoOptional.get();
	            VeiculoDTO veiculoDTO = new VeiculoDTO();
	            veiculoDTO.setModelo(veiculo.getModelo());
	            veiculoDTO.setMarca(veiculo.getMarca());
	            veiculoDTO.setPlaca(veiculo.getPlaca());
	            veiculoDTO.setCor(veiculo.getCor());
	            veiculoDTO.setCliente(veiculo.getCliente());
	            
	            return ResponseEntity.ok(veiculoDTO);
	        }
	}
	
//	@GetMapping(value = "/veiculos/{placa}")
//	public ResponseEntity<Optional<Veiculo>> listarVeiculoPorPlaca(@PathVariable String placa) {
//		Optional<Veiculo> veiculo = veiculoService.listarPorPlaca(placa);
//		
//		if (veiculo.isEmpty()) {
//			return new ResponseEntity<Optional<Veiculo>>(HttpStatus.NOT_FOUND);
//		} else {
//			return new ResponseEntity<Optional<Veiculo>>(veiculo, HttpStatus.OK);			
//		}
//	}
	
	
	@PutMapping(value = "/veiculos/{placa}")
	public ResponseEntity<Veiculo> atualizarVeiculo(@RequestBody Veiculo veiculo, @PathVariable String placa) {		
	    try {
	        return veiculoService.atualizar(veiculo, placa);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@DeleteMapping(value = "/veiculos/{placa}")
	public ResponseEntity<String> removerVeiculoPorId(@PathVariable String placa) {
		Optional<Veiculo> veiculo = veiculoService.buscarPorPlaca(placa);
		
		if (veiculo == null) {
			return new ResponseEntity<String>("Veiculo de placa " + placa + " não encontrado", HttpStatus.NOT_FOUND);
		} else {
			try {
				veiculoService.remover(veiculo.get());
				return ResponseEntity.noContent().build();
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>("Erro ao remover o veiculo de placa " + veiculo.get().getPlaca(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

}
