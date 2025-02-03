package desafio.sergipetec.desafio_sergipetec.veiculo.repository;

import java.util.List;

import desafio.sergipetec.desafio_sergipetec.veiculo.Veiculo;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoFilter;

public interface VeiculoDAOInterface {

	public Veiculo findById(Integer id);

	public List<Veiculo> findByFilters(VeiculoFilter filtro);

	public Veiculo save(Veiculo veiculo) throws Exception;

	public Veiculo update(Veiculo veiculo) throws Exception;

	public Veiculo delete(Integer veiculo) throws Exception;
}
