package desafio.sergipetec.desafio_sergipetec.veiculo.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import desafio.sergipetec.desafio_sergipetec.veiculo.Veiculo;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class VeiculoDAO implements VeiculoDAOInterface {

	private static final String BASE_SELECT = "SELECT * FROM vco_veiculo";

	@Autowired 
	private EntityManager manager;
	
	@Override
	public Veiculo save(Veiculo veiculo) {
		var sql = "INSERT INTO vco_veiculo"
			+ " (fbe_id, mdo_id, vco_ano, vco_preco, vco_tipo, vco_portas, vco_combustivel, vco_cilindradas)"
			+ " VALUES (:fbe_id, :mdo_id, :vco_ano, :vco_preco, :vco_tipo, :vco_portas, :vco_combustivel, :vco_cilindradas)";
		var params = veiculo.toMap();
		params.remove("vco_id");

		var query = this.createNativeQuery(sql, params);
		query.executeUpdate();
		return veiculo;
	}

	@Override
	public Optional<Veiculo> findById(Integer id) {
		var sql = BASE_SELECT + " WHERE vco_id = :id";
		var params = new HashMap<String, String>();
		params.put("id", id.toString());

		var query = this.createNativeQuery(sql, params);
		var veiculos = this.execute(query);
		return Optional.ofNullable(veiculos.isEmpty() ? null : veiculos.get(0));
	}

	@Override
	public List<Veiculo> findByFilters(VeiculoFilter filtro) {
		var sql = BASE_SELECT + " WHERE 1 = 1";
		var parameters = new HashMap<String, String>();

		var modeloId = filtro.getModeloId();
		if (modeloId.isPresent()) {
			sql += " AND mdo_id = :modeloId";
			parameters.put("modeloId", String.valueOf(modeloId.get()));
		}

		var tipo = filtro.getTipo();
		if (tipo.isPresent()) {
			sql += " AND vco_tipo = :tipo";
			parameters.put("tipo", tipo.get());
		}

		var ano = filtro.getAno();
		if (ano.isPresent()) {
			sql += " AND vco_ano = :vco_ano";
			parameters.put("vco_ano", String.valueOf(ano.get()));
		}

		var query = this.createNativeQuery(sql, parameters);
		return this.execute(query);
	}

	private List<Veiculo> execute(Query query) {
		var veiculos = new ArrayList<Veiculo>();

		List<?> list = query.getResultList();
		list.forEach(item -> veiculos.add((Veiculo) item));
		return veiculos;
	}

	private Query createNativeQuery(String sql) {
		return this.manager.createNativeQuery(sql, Veiculo.class);
	}

	private Query createNativeQuery(String sql, HashMap<String, String> params) {
		var query = this.createNativeQuery(sql);
		params.forEach((key, value) -> query.setParameter(key, value));
		return query;
	}
}
