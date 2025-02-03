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
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoEnum;
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
		if (!this.hasValue("fbe_id", map)) {
			throw new InvalidParameterException("Informe o fabricante");
		}

		if (!this.hasValue("mdo_id", map)) {
			throw new InvalidParameterException("Informe o modelo");
		}

		if (!this.hasValue("vco_ano", map)) {
			throw new InvalidParameterException("Informe o ano");
		}

		if (!this.hasValue("vco_preco", map)) {
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
			veiculo.setTipo(VeiculoEnum.parse(Integer.parseInt(map.get("vco_tipo"))));
		} catch (Exception e) {
			throw new InvalidParameterException(e.getMessage());
		}
		return veiculo;
	}

	@Override
	public Veiculo replace(Veiculo veiculo, HashMap<String, String> map) throws InvalidParameterException {
		if (this.hasValue("vco_tipo", map)) {
			try {
				var tipo = VeiculoEnum.parse(Integer.parseInt(map.get("vco_tipo")));
				veiculo.setTipo(tipo);
			} catch (Exception e) {
				throw new InvalidParameterException(e.getMessage());
			}
		}

		if (this.hasValue("fbe_id", map)) {
			var fabricante = this.getFabricante(map);
			veiculo.setFabricante(fabricante);
		}

		if (this.hasValue("mdo_id", map)) {
			var modelo = this.getModelo(map);
			veiculo.setModelo(modelo);
		}

		if (this.hasValue("vco_ano", map)) {
			var ano = Integer.parseInt(map.get("vco_ano"));
			veiculo.setAno(ano);
		}

		if (this.hasValue("vco_preco", map)) {
			var preco = Double.valueOf(map.get("vco_preco"));
			veiculo.setPreco(preco);
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

	private boolean hasValue(String key, HashMap<String, String> map) {
		var value = map.get(key);
		return value != null && value.length() > 0;
	}
}
