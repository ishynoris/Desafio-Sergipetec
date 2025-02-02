package desafio.sergipetec.desafio_sergipetec.veiculo.repository;

import java.util.List;
import java.util.Optional;

import desafio.sergipetec.desafio_sergipetec.veiculo.Veiculo;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoFilter;

public interface VeiculoDAOInterface {

	public Optional<Veiculo> findById(Integer id);

	public List<Veiculo> findByFilters(VeiculoFilter filtro);

	public Veiculo save(Veiculo veiculo);
}
