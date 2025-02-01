package desafio.sergipetec.desafio_sergipetec.veiculo;

import java.util.HashMap;

import javax.management.InvalidAttributeValueException;

import org.springframework.data.jpa.domain.Specification;

public class VeiculoFilter {

	private HashMap<String, String> filter;

	public VeiculoFilter(HashMap<String, String> filter) {
		this.filter = filter;
	}

	public Specification<Veiculo> getSpecs() throws InvalidAttributeValueException, NumberFormatException {
		Specification<Veiculo> specs = Specification.where(null);

		if (this.filter.containsKey("mdo_id")) {
			var param = Integer.parseInt(this.filter.get("mdo_id"));
			specs = specs.and(this.withModeloId(param));
		}

		if (this.filter.containsKey("vco_tipo")) {
			var codigo = Integer.parseInt(this.filter.get("vco_tipo"));
			var param = VeiculoEnum.parse(codigo);
			specs = specs.and(this.withTipo(param));
		}

		if (this.filter.containsKey("vco_ano")) {
			var param = Integer.parseInt(this.filter.get("vco_ano"));
			specs = specs.and(this.withAno(param));
		}
		return specs;
	}

	public Specification<Veiculo> withModeloId(Integer modeloId) {
		return (root, query, builder) -> {
			var expression = root.get("modelo").get("id");
			return builder.equal(expression, modeloId);
		};
	}

	public Specification<Veiculo> withTipo(VeiculoEnum tipo) {
		return (root, query, builder) -> builder.equal(root.get("tipo"), tipo.codigo);
	}

	public Specification<Veiculo> withAno(Integer ano) {
		return (root, query, builder) -> builder.equal(root.get("ano"), ano);
	}
}
