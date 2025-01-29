package desafio.sergipetec.desafio_sergipetec.veiculo.moto;

import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoAbstract;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoComCilindradas;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Moto extends VeiculoAbstract implements VeiculoComCilindradas {

	@Column(name="vco_cilindradas")
	private int cilindradas;

    @Override
    public int getCilindradas() {
        return this.cilindradas;
    }
}
