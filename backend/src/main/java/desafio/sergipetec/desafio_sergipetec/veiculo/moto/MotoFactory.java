package desafio.sergipetec.desafio_sergipetec.veiculo.moto;

import java.security.InvalidParameterException;
import java.util.HashMap;

import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoInterface;
import desafio.sergipetec.desafio_sergipetec.veiculo.factory.VeiculoFactoryAbstract;

public class MotoFactory extends VeiculoFactoryAbstract {

	@Override
	public VeiculoInterface produce(HashMap<String, String> map) throws InvalidParameterException {
		var moto = new Moto(
			map.get("fabricante")
			, map.get("modelo")
			, Integer.parseInt(map.get("ano"))
			, Double.valueOf(map.get("preco"))
		);

		try {
			moto.setCilindradas(Integer.parseInt(map.get("cilindradas")));
		} catch (NumberFormatException e) {
			throw new InvalidParameterException(e.getMessage());
		}

		return moto;
	}

	@Override
	protected void validateAditionalRequired(HashMap<String, String> map) throws InvalidParameterException {
		if (!map.containsKey("cilindradas")) {
			throw new InvalidParameterException("Informe as cilindradas da moto");
		}
	}
}
