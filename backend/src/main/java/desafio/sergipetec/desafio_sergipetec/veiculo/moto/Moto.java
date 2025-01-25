package desafio.sergipetec.desafio_sergipetec.veiculo.moto;

import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoAbstract;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoComCilindradas;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoEnum;

public class Moto extends VeiculoAbstract implements VeiculoComCilindradas {

	private int cilindradas;

	protected Moto(String fabricante, String modelo, int ano, Double preco) {
		super(fabricante, modelo, ano, preco);
	}

	@Override
	public VeiculoEnum getTipo() {
		return VeiculoEnum.MOTO;
	}

    @Override
    public int getCilindradas() {
        return this.cilindradas;
    }

	public void setCilindradas(int cilindradas) {
		this.cilindradas = cilindradas;
	}
}
