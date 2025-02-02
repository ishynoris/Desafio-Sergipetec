package desafio.sergipetec.desafio_sergipetec.veiculo;

import java.util.HashMap;
import java.util.List;

import javax.management.InvalidAttributeValueException;
import javax.naming.directory.InvalidAttributesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import desafio.sergipetec.desafio_sergipetec.fabricante.Fabricante;
import desafio.sergipetec.desafio_sergipetec.fabricante.FabricanteDAO;
import desafio.sergipetec.desafio_sergipetec.modelo.Modelo;
import desafio.sergipetec.desafio_sergipetec.modelo.ModeloDAO;
import desafio.sergipetec.desafio_sergipetec.veiculo.factory.VeiculoFactory;
import desafio.sergipetec.desafio_sergipetec.veiculo.repository.VeiculoDAOInterface;
import jakarta.persistence.EntityNotFoundException;

@Service
public class VeiculoService {

	@Autowired private FabricanteDAO fabricanteDAO;
	@Autowired private ModeloDAO modeloDAO;

	private  VeiculoDAOInterface veiculoDAO;

	@Autowired
	public VeiculoService(VeiculoDAOInterface veiculoDAO) {
		this.veiculoDAO = veiculoDAO;
	}

	public List<Veiculo> getVeiculos() {
		var filtro = new VeiculoFilter(new HashMap<>());
		return this.veiculoDAO.findByFilters(filtro);
	}

	public List<Veiculo> getVeiculos(HashMap<String, String> form) throws NumberFormatException, InvalidAttributeValueException {
		var filtro = new VeiculoFilter(form);
		return this.veiculoDAO.findByFilters(filtro);
	}

	public Veiculo get(Integer id) {
		var veiculo = this.veiculoDAO.findById(id);
		if (!veiculo.isPresent()) {
			throw new EntityNotFoundException(
				String.format("Veículo (%d) não encontrado", id)
			);
		}
		return veiculo.get();
	}

	public Veiculo salvar(HashMap<String, String> form) throws InvalidAttributesException {
		var veiculo = this.produce(form);
		return this.veiculoDAO.save(veiculo);
	}

	public Veiculo atualizar(Veiculo veiculo, HashMap<String, String> form) throws InvalidAttributesException {
		var novoVeiculo = this.getFactory().replace(veiculo, form);
		return this.veiculoDAO.save(novoVeiculo);
	}

	public List<Fabricante> getFabricantes() {
		return this.fabricanteDAO.findAll();
	}

	public List<Modelo> getModelos() {
		return this.modeloDAO.findAll();
	}

	public Veiculo produce(HashMap<String, String> map) throws InvalidAttributesException {
		return this.getFactory().produces(map);
	}

	public VeiculoFactory getFactory() {
		return new VeiculoFactory().set(fabricanteDAO, modeloDAO);
	}
}
