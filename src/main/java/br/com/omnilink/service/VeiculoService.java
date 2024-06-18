package br.com.omnilink.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.omnilink.model.Veiculo;
import br.com.omnilink.repository.VeiculoRepository;
import jakarta.transaction.Transactional;

@Service
@Validated
public class VeiculoService {
	
	@Autowired
	private VeiculoRepository veiculoRepository;
	
//	@Autowired
//	private ModelMapper modelMapper;
	
	@Transactional
	public Veiculo cadastrar(Veiculo veiculo) {
		return veiculoRepository.save(veiculo);
	}

	
	public List<Veiculo> listarTodos() {
		return veiculoRepository.findAll();
	}
	
	public Optional<Veiculo> listarPorId(Integer id) {
		return veiculoRepository.findById(id);
	}
	
	public Optional<Veiculo> buscarPorPlaca(String placa) {
		return veiculoRepository.findByPlaca(placa);
	}
	
	//TODO usando a lib ModelMapper para converter DTO x Entity
//	public VeiculoDTO listarPorPlacaDTO(String placa) {
//		VeiculoDTO VeiculoDTO = new VeiculoDTO();
//		Optional<Veiculo> veiculo = veiculoRepository.findByPlaca(placa);
//		
//		if (!veiculo.isEmpty()) {
//			VeiculoDTO = modelMapper.map(veiculo.get(), VeiculoDTO.class);			
//		} else {
//			VeiculoDTO = null;
//		}
//		
//		return VeiculoDTO;
//	}
	
	@Transactional
	public ResponseEntity<Veiculo> atualizar(Veiculo veiculo, String placa) {
		Optional<Veiculo> veiculoExistente = veiculoRepository.findByPlaca(placa);
		
		if (!veiculoExistente.isPresent()) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
		
		Veiculo veiculoExiste = veiculoExistente.get();
		
		atualizarVeiculo(veiculoExiste, veiculo);
		
		Veiculo veiculoAtualizado = veiculoRepository.save(veiculoExiste);
		
		return new ResponseEntity<>(veiculoAtualizado, HttpStatus.OK);
	}
	
	private void atualizarVeiculo(Veiculo veiculoExistente, Veiculo novosDados) {
	    veiculoExistente.setModelo(novosDados.getModelo());
	    veiculoExistente.setMarca(novosDados.getMarca());
	    veiculoExistente.setPlaca(novosDados.getPlaca());
	    veiculoExistente.setCor(novosDados.getCor());
	}
	
	@Transactional
	public Optional<Veiculo> remover(Veiculo veiculo) {
		veiculoRepository.delete(veiculo);
		return Optional.empty();
	}
	
}
