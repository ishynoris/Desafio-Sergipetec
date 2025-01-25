package desafio.sergipetec.desafio_sergipetec.veiculo.carro;

import desafio.sergipetec.desafio_sergipetec.veiculo.TipoCombustivelEnum;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoAbstract;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoComCombustivelInterface;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoComPortasInterface;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Carro 
	extends  VeiculoAbstract
	implements VeiculoComCombustivelInterface, VeiculoComPortasInterface {
	
	@Column(name="vco_portas")
	private int portas;

	@Column(name="vco_combustivel")
	@Enumerated(EnumType.ORDINAL)
	private TipoCombustivelEnum combustivel;

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
