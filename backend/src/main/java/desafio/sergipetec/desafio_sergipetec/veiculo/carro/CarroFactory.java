package desafio.sergipetec.desafio_sergipetec.veiculo.carro;

import java.security.InvalidParameterException;
import java.util.HashMap;

import desafio.sergipetec.desafio_sergipetec.veiculo.TipoCombustivelEnum;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoInterface;
import desafio.sergipetec.desafio_sergipetec.veiculo.factory.VeiculoFactoryAbstract;

public class CarroFactory extends VeiculoFactoryAbstract {

	@Override
	public VeiculoInterface produce(HashMap<String, String> map) throws InvalidParameterException {
		this.validateRequired(map);

		var carro = new Carro(
			map.get("fabricante")
			, map.get("modelo")
			, Integer.parseInt(map.get("ano"))
			, Double.valueOf(map.get("preco"))
		);

		try {
			carro.setQuantidadePortas(Integer.parseInt(map.get("portas")));
			carro.setTipoCombustivel(TipoCombustivelEnum.parse(map.get("combustivel")));
		} catch (Exception e) {
			throw new InvalidParameterException(e.getMessage());
		}

		return carro;
	}

	@Override
	protected void validateAditionalRequired(HashMap<String, String> map) throws InvalidParameterException {
		if (!map.containsKey("portas")) {
			throw new InvalidParameterException("Informe a quantidade de portas");
		}

		if (!map.containsKey("combustivel")) {
			throw new InvalidParameterException("Informe o tipo de combustivel");
		}
	}
}
