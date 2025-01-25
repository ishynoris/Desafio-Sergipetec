package desafio.sergipetec.desafio_sergipetec.veiculo.factory;

import java.util.HashMap;

import javax.naming.directory.InvalidAttributesException;

import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoEnum;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoInterface;
import desafio.sergipetec.desafio_sergipetec.veiculo.carro.CarroFactory;

final public class VeiculoFactory {

	public static VeiculoInterface create(HashMap<String, String> map) throws InvalidAttributesException {
		var factory = getFactory(map);

		factory.validateRequired(map);
		return factory.produce(map);
	}

	private static VeiculoFactoryInterface getFactory(HashMap<String, String> map)  throws InvalidAttributesException {
		if (!map.containsKey("tipo")) {
			throw new InvalidAttributesException("Informe o tipo do veículo");
		}

		var tipo = Integer.parseInt(map.get("tipo"));
		if (VeiculoEnum.isCarro(tipo)) {
			return new CarroFactory();
		}

		if (VeiculoEnum.isMoto(tipo)) {

		}

		throw new InvalidAttributesException("Informe o tipo do veículo");
	}
}
