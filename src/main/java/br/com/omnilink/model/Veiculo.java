package br.com.omnilink.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "TB_VEICULO")
public class Veiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String modelo;
	private String marca;
	private String placa;
	private String cor;
	
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cliente_id")
    @JsonBackReference
	private Cliente cliente;
    
    public Veiculo() {
    	
    }

	public Veiculo(Integer id, String modelo, String marca, String placa, String cor, Cliente cliente) {
		super();
		this.id = id;
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



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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