package desafio.sergipetec.desafio_sergipetec.veiculo.factory;

import java.security.InvalidParameterException;
import java.util.HashMap;


public abstract class VeiculoFactoryAbstract implements VeiculoFactoryInterface {

	protected abstract void validateAditionalRequired(HashMap<String, String> map) throws InvalidParameterException;
	
	@Override
	public void validateRequired(HashMap<String, String> map) throws InvalidParameterException {
		if (!map.containsKey("fabricante")) {
			throw new InvalidParameterException("Informe o fabricante");
		}

		if (!map.containsKey("modelo")) {
			throw new InvalidParameterException("Informe o modelo");
		}

		if (!map.containsKey("ano")) {
			throw new InvalidParameterException("Informe o ano");
		}

		if (!map.containsKey("preco")) {
			throw new InvalidParameterException("Informe o preço");
		}

		this.validateAditionalRequired(map);
	}
}
