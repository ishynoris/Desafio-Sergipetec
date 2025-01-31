package desafio.sergipetec.desafio_sergipetec.veiculo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VeiculoDAO extends JpaRepository<Veiculo, Long> {

	@Query(
		value = "SELECT * FROM vco_veiculo;",
		nativeQuery = true
	)
	@Override
	public List<Veiculo> findAll();	
}
