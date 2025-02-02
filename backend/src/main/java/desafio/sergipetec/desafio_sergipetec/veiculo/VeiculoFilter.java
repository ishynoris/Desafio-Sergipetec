package desafio.sergipetec.desafio_sergipetec.veiculo;

import java.util.HashMap;
import java.util.Optional;

public class VeiculoFilter {

	private HashMap<String, String> filter;

	public VeiculoFilter(HashMap<String, String> filter) {
		this.filter = filter;
	}

	public Optional<Integer> getVeiculoId() {
		var moduloId = this.hasParam("vco_id")
			? Integer.parseInt(this.filter.get("vco_id"))
			: null;
		return Optional.ofNullable(moduloId);
	}

	public Optional<Integer> getModeloId() {
		var moduloId = this.hasParam("mdo_id")
			? Integer.parseInt(this.filter.get("mdo_id"))
			: null;
		return Optional.ofNullable(moduloId);
	}

	public Optional<String> getTipo() {
		var tipo = this.hasParam("vco_tipo")
			? this.filter.get("vco_tipo")
			: null;
		return Optional.ofNullable(tipo);
	}

	public Optional<Integer> getAno() {
		var ano = this.hasParam("vco_ano")
			? Integer.parseInt(this.filter.get("vco_ano"))
			: null;
		return Optional.ofNullable(ano);
	}

	private boolean hasParam(String campo) {
		var param = this.filter.get(campo);
		return param != null && param.length() > 0;
	}
}
