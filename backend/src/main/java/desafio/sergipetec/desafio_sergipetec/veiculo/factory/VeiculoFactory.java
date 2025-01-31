package desafio.sergipetec.desafio_sergipetec.veiculo.factory;

import java.util.HashMap;

import javax.naming.directory.InvalidAttributesException;

import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoEnum;
import desafio.sergipetec.desafio_sergipetec.fabricante.FabricanteDAO;
import desafio.sergipetec.desafio_sergipetec.modelo.ModeloDAO;
import desafio.sergipetec.desafio_sergipetec.veiculo.Veiculo;

final public class VeiculoFactory {

	private HashMap<Integer, VeiculoFactoryInterface> factories;

	public VeiculoFactory() {
		this.factories = new HashMap<>();
	}
	
	public VeiculoFactory set(FabricanteDAO fabricanteDAO, ModeloDAO modeloDAO) {
		this.factories.put(VeiculoEnum.CARRO.codigo, new CarroFactory(modeloDAO, fabricanteDAO));
		this.factories.put(VeiculoEnum.MOTO.codigo, new MotoFactory(modeloDAO, fabricanteDAO));
		return this;
	}

	public Veiculo produces(HashMap<String, String> map) throws InvalidAttributesException {
		if (!map.containsKey("vco_tipo")) {
			throw new InvalidAttributesException("Informe o tipo do veículo");
		}

		var tipo = Integer.parseInt(map.get("vco_tipo"));
		if (!this.factories.containsKey(tipo)) {
			throw new InvalidAttributesException("Tipo de veículo indefinido");
		}

		var factory = this.factories.get(tipo);
		factory.validateRequired(map);
		return factory.produce(map);
	}
}
