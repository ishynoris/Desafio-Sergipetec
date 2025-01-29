package desafio.sergipetec.desafio_sergipetec.veiculo.carro;

import desafio.sergipetec.desafio_sergipetec.veiculo.TipoCombustivelEnum;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoAbstract;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoComCombustivelInterface;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoComPortasInterface;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Carro 
	extends  VeiculoAbstract
	implements VeiculoComCombustivelInterface, VeiculoComPortasInterface {
	
	@Column(name="vco_portas")
	private int quantidadePortas;

	@Column(name="vco_combustivel")
	@Enumerated(EnumType.ORDINAL)
	private TipoCombustivelEnum tipoCombustivel;

	@Override
	public int getQuantidadePortas() {
		return this.quantidadePortas;
	}

	@Override
	public TipoCombustivelEnum getTipoCombustivel() {
		return this.tipoCombustivel;
	}
}
