package desafio.sergipetec.desafio_sergipetec.veiculo.factory;

import java.security.InvalidParameterException;
import java.util.HashMap;

import desafio.sergipetec.desafio_sergipetec.veiculo.Veiculo;

public interface VeiculoFactoryInterface {
	
	public void validateRequired(HashMap<String, String> map) throws InvalidParameterException;

	public Veiculo produce(HashMap<String, String> map) throws InvalidParameterException;
}
