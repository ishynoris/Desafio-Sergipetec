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
		return this.veiculoDAO.findById(id);
	}

	public Veiculo salvar(HashMap<String, String> form) throws InvalidAttributesException, Exception {
		var veiculo = this.produce(form);
		return this.veiculoDAO.save(veiculo);
	}

	public Veiculo apagar(Integer id) throws InvalidAttributesException, Exception {
		return this.veiculoDAO.delete(id);
	}


	public Veiculo atualizar(Veiculo veiculo, HashMap<String, String> form) throws InvalidAttributesException, Exception {
		var novoVeiculo = this.getFactory().replace(veiculo, form);
		return this.veiculoDAO.update(novoVeiculo);
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
