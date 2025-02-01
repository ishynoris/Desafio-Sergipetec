package desafio.sergipetec.desafio_sergipetec.veiculo.factory;

import java.security.InvalidParameterException;
import java.util.HashMap;

import desafio.sergipetec.desafio_sergipetec.fabricante.FabricanteDAO;
import desafio.sergipetec.desafio_sergipetec.modelo.ModeloDAO;
import desafio.sergipetec.desafio_sergipetec.veiculo.TipoCombustivelEnum;
import desafio.sergipetec.desafio_sergipetec.veiculo.Veiculo;

public class CarroFactory extends VeiculoFactoryAbstract {


	protected CarroFactory(ModeloDAO modeloRepo, FabricanteDAO fabricanteRepo) {
		super(modeloRepo, fabricanteRepo);
	}


	@Override
	public Veiculo produce(HashMap<String, String> map) throws InvalidParameterException {
		var carro = super.produce(map);

		try {
			carro.setQuantidadePortas(Integer.parseInt(map.get("vco_portas")));
			carro.setTipoCombustivel(TipoCombustivelEnum.parse(map.get("vco_combustivel")));
		} catch (Exception e) {
			throw new InvalidParameterException(e.getMessage());
		}

		return carro;
	}

	@Override
	public Veiculo replace(Veiculo veiculo, HashMap<String, String> map) throws InvalidParameterException {
		var novoVeiculo = super.replace(veiculo, map);

		if (map.containsKey("vco_portas")) {
			var portas = Integer.parseInt(map.get("vco_portas"));
			veiculo.setQuantidadePortas(portas);
		}
		if (map.containsKey("vco_combustivel")) {
			try {
				var combustivel = TipoCombustivelEnum.parse(map.get("vco_combustivel"));
				veiculo.setTipoCombustivel(combustivel);
			} catch (Exception e) {
				throw new InvalidParameterException(e.getMessage());
			}
		}
		return novoVeiculo;
	}

	@Override
	public void validateRequired(HashMap<String, String> map) throws InvalidParameterException {
		super.validateRequired(map);

		if (!map.containsKey("vco_portas")) {
			throw new InvalidParameterException("Informe a quantidade de portas");
		}

		if (!map.containsKey("vco_combustivel")) {
			throw new InvalidParameterException("Informe o tipo de combustivel");
		}
	}
}
