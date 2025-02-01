package desafio.sergipetec.desafio_sergipetec.veiculo;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface VeiculoDAO extends JpaRepository<Veiculo, Long>, JpaSpecificationExecutor <Veiculo> {

	@Query(
		value = "SELECT * FROM vco_veiculo;",
		nativeQuery = true
	)
	@Override
	public List<Veiculo> findAll();

	@Query(
		nativeQuery = true,
		value = "SELECT * FROM vco_veiclo"
	)
	default public List<Veiculo> findByFilters(VeiculoFilter filtro) {
		Specification<Veiculo> spec = Specification.where(filtro);
		return this.findAll(spec);
	}
}
