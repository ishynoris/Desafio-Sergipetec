package desafio.sergipetec.desafio_sergipetec.veiculo.factory;

import java.security.InvalidParameterException;
import java.util.HashMap;

import desafio.sergipetec.desafio_sergipetec.fabricante.Fabricante;
import desafio.sergipetec.desafio_sergipetec.fabricante.FabricanteDAO;
import desafio.sergipetec.desafio_sergipetec.fabricante.FabricanteService;
import desafio.sergipetec.desafio_sergipetec.modelo.Modelo;
import desafio.sergipetec.desafio_sergipetec.modelo.ModeloDAO;
import desafio.sergipetec.desafio_sergipetec.modelo.ModeloService;
import desafio.sergipetec.desafio_sergipetec.veiculo.Veiculo;
import jakarta.persistence.EntityNotFoundException;

public abstract class VeiculoFactoryAbstract implements VeiculoFactoryInterface {

	protected ModeloService modeloService;
	protected FabricanteService fabricanteService;

	protected VeiculoFactoryAbstract(ModeloDAO modeloRepo, FabricanteDAO fabricanteRepo) {
		this.modeloService = new ModeloService(modeloRepo);
		this.fabricanteService = new FabricanteService(fabricanteRepo);
	}

	@Override
	public void validateRequired(HashMap<String, String> map) throws InvalidParameterException {
		if (!map.containsKey("fbe_id")) {
			throw new InvalidParameterException("Informe o fabricante");
		}

		if (!map.containsKey("mdo_id")) {
			throw new InvalidParameterException("Informe o modelo");
		}

		if (!map.containsKey("vco_ano")) {
			throw new InvalidParameterException("Informe o ano");
		}

		if (!map.containsKey("vco_preco")) {
			throw new InvalidParameterException("Informe o preço");
		}

		var fabricante = this.getFabricante(map);
		var modelo = this.getModelo(map);
		if (!modelo.percente(fabricante)) {
			throw new InvalidParameterException(String.format(
				"O modelo %s não percente ao fabricante %s",
				modelo.getNome(),
				fabricante.getNome()
			));
		}
	}

	@Override
	public Veiculo produce(HashMap<String, String> map) throws InvalidParameterException {
		var veiculo = new Veiculo();
		try {
			veiculo.setFabricante(this.getFabricante(map));
			veiculo.setModelo(this.getModelo(map));
			veiculo.setAno(Integer.parseInt(map.get("vco_ano")));
			veiculo.setPreco(Double.valueOf(map.get("vco_preco")));
		} catch (Exception e) {
			throw new InvalidParameterException(e.getMessage());
		}
		return veiculo;
	}

	protected Fabricante getFabricante(HashMap<String, String> map) throws EntityNotFoundException {
		var fabricanteId = Integer.parseInt(map.get("fbe_id"));
		return this.fabricanteService.get(fabricanteId);
	}

	protected Modelo getModelo(HashMap<String, String> map) throws EntityNotFoundException {
		var modeloId = Integer.parseInt(map.get("mdo_id"));
		return this.modeloService.get(modeloId);
	}
}
