package br.com.omnilink.dto;

import java.util.List;

import br.com.omnilink.model.Cliente;
import br.com.omnilink.model.Veiculo;

public class ClienteDTO {
	
	
	private Long cpf;
	private String nome;
	private String endereco;
	private List<Veiculo> veiculo;
	
	public ClienteDTO() {
		
	}
	
	public ClienteDTO(Cliente cliente) {
		cpf = cliente.getCpf();
		nome = cliente.getNome();
		endereco = cliente.getEndereco();
		veiculo = cliente.getVeiculo();
	}
	
	

	public List<Veiculo> getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(List<Veiculo> veiculo) {
		this.veiculo = veiculo;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	

}
