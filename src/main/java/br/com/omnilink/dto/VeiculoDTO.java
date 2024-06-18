package br.com.omnilink.dto;

import br.com.omnilink.model.Cliente;

public class VeiculoDTO {

	private String modelo;
	private String marca;
	private String placa;
	private String cor;
	private Cliente cliente;

	public VeiculoDTO() {

	}

	public VeiculoDTO(String modelo, String marca, String placa, String cor, Cliente cliente) {
		super();
		this.modelo = modelo;
		this.marca = marca;
		this.placa = placa;
		this.cor = cor;
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

}
