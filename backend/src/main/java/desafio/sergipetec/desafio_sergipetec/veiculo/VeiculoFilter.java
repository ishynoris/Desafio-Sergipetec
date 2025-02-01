package desafio.sergipetec.desafio_sergipetec.veiculo;

import java.util.HashMap;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class VeiculoFilter implements Specification<Veiculo> {

	private HashMap<String, String> filter;

	public VeiculoFilter(HashMap<String, String> filter) {
		this.filter = filter;
	}

	@Override
	public Predicate toPredicate(Root<Veiculo> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		var predicate = builder.and();

		if (this.filter.containsKey("mdo_id")) {
			var modeloId = this.filter.get("mdo_id");
			predicate = builder.equal(root.get("modelo"), modeloId);
		}

		if (this.filter.containsKey("vco_tipo")) {
			var tipoVeiculo = this.filter.get("vco_tipo");
			predicate = builder.equal(root.get("tipo"), tipoVeiculo);
		}

		if (this.filter.containsKey("vco_ano")) {
			var ano = this.filter.get("vco_ano");
			predicate = builder.equal(root.get("ano"), ano);
		}

		return predicate;
	}
}
