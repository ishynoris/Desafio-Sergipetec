package desafio.sergipetec.desafio_sergipetec.veiculo.carro;

import desafio.sergipetec.desafio_sergipetec.veiculo.TipoCombustivelEnum;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoAbstract;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoComCombustivelInterface;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoComPortasInterface;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoEnum;

public class Carro 
	extends  VeiculoAbstract
	implements VeiculoComCombustivelInterface, VeiculoComPortasInterface {
	
	private int portas;
	private TipoCombustivelEnum combustivel;
	
	protected Carro(String fabricante, String modelo, int ano, Double preco) {
		super(fabricante, modelo, ano, preco);
	}

	@Override
	public VeiculoEnum getTipo() {
		return VeiculoEnum.CARRO;
	}

	@Override
	public int getQuantidadePortas() {
		return this.portas;
	}

	@Override
	public TipoCombustivelEnum getTipoCombustivel() {
		return this.combustivel;
	}

	public void setQuantidadePortas(int portas) {
		this.portas = portas;
	}

	public void setTipoCombustivel(TipoCombustivelEnum combustivel) {
		this.combustivel = combustivel;
	}
}
