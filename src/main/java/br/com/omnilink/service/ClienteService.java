package br.com.omnilink.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.omnilink.model.Cliente;
import br.com.omnilink.model.Veiculo;
import br.com.omnilink.repository.ClienteRepository;
import br.com.omnilink.repository.VeiculoRepository;
import jakarta.transaction.Transactional;

@Service
@Validated
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private VeiculoRepository veiculoRepository;

	@Transactional
	public Cliente cadastrar(Cliente novoCliente) {
		if (novoCliente.getVeiculo() != null) {
			novoCliente.getVeiculo().forEach(veiculo -> veiculo.setCliente(novoCliente));
		}
		return clienteRepository.save(novoCliente);
	}

	public List<Cliente> listarTodos() {
		return clienteRepository.findAll();
	}

	public Cliente buscarPorCpf(Long cpf) {
		return clienteRepository.findByCpf(cpf);
	}
	
	public Optional<Cliente> buscarPorId(Integer id) {
		return clienteRepository.findById(id);
	}

	@Transactional
	public Cliente atualizar(Cliente cliente, Integer id) {
		Optional<Cliente> clienteExistente = clienteRepository.findById(id);
		atualizarCliente(cliente, clienteExistente.get());

		return clienteRepository.save(clienteExistente.get());
	}

	private void atualizarCliente(Cliente clienteNew, Cliente clienteExistente) {

		clienteExistente.setCpf(clienteNew.getCpf());
		clienteExistente.setNome(clienteNew.getNome());
		clienteExistente.setEndereco(clienteNew.getEndereco());

		// Atualizar veículos
		List<Veiculo> veiculosAtualizados = new ArrayList<>();
		for (Veiculo veiculoNew : clienteNew.getVeiculo()) {
			Optional<Veiculo> veiculoExistente = veiculoRepository.findByPlaca(veiculoNew.getPlaca());

			if (veiculoExistente.isPresent()) {
				Veiculo veiculoAtualizado = veiculoExistente.get();
				veiculoAtualizado.setModelo(veiculoNew.getModelo());
				veiculoAtualizado.setMarca(veiculoNew.getMarca());
				veiculoAtualizado.setCor(veiculoNew.getCor());
				veiculoAtualizado.setCliente(clienteExistente);
				veiculosAtualizados.add(veiculoAtualizado);
			} else {
				veiculoNew.setCliente(clienteExistente);
				veiculosAtualizados.add(veiculoNew);
			}
		}

		clienteExistente.setVeiculo(veiculosAtualizados);
	}

	@Transactional
	public Optional<Cliente> remover(Cliente cliente) {
		clienteRepository.delete(cliente);
		return Optional.empty();
	}

}
