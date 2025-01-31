package desafio.sergipetec.desafio_sergipetec.veiculo.factory;

import java.security.InvalidParameterException;
import java.util.HashMap;

import desafio.sergipetec.desafio_sergipetec.fabricante.FabricanteDAO;
import desafio.sergipetec.desafio_sergipetec.modelo.ModeloDAO;
import desafio.sergipetec.desafio_sergipetec.veiculo.Veiculo;

public class MotoFactory extends VeiculoFactoryAbstract {

	protected MotoFactory(ModeloDAO modeloRepo, FabricanteDAO fabricanteRepo) {
		super(modeloRepo, fabricanteRepo);
	}

	@Override
	public Veiculo produce(HashMap<String, String> map) throws InvalidParameterException {
		var moto = super.produce(map);
		try {
			moto.setCilindradas(Integer.parseInt(map.get("vco_cilindradas")));
		} catch (Exception e) {
			throw new InvalidParameterException(e.getMessage());
		}
		return moto;
	}

	@Override
	public void validateRequired(HashMap<String, String> map) throws InvalidParameterException {
		super.validateRequired(map);

		if (!map.containsKey("vco_cilindradas")) {
			throw new InvalidParameterException("Informe as cilindradas da moto");
		}
	}
}
