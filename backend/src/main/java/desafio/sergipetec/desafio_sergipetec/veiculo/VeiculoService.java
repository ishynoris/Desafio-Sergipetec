package desafio.sergipetec.desafio_sergipetec.veiculo;

import java.util.HashMap;
import java.util.List;

import javax.management.InvalidAttributeValueException;
import javax.naming.directory.InvalidAttributesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import desafio.sergipetec.desafio_sergipetec.fabricante.FabricanteDAO;
import desafio.sergipetec.desafio_sergipetec.modelo.Modelo;
import desafio.sergipetec.desafio_sergipetec.modelo.ModeloDAO;
import desafio.sergipetec.desafio_sergipetec.veiculo.factory.VeiculoFactory;
import jakarta.persistence.EntityNotFoundException;

@Service
public class VeiculoService {

	@Autowired private FabricanteDAO fabricanteDAO;
	@Autowired private ModeloDAO modeloDAO;

	private  VeiculoDAO veiculoDAO;

	@Autowired
	public VeiculoService(VeiculoDAO veiculoDAO) {
		this.veiculoDAO = veiculoDAO;
	}

	public List<Veiculo> getVeiculos() {
		return this.veiculoDAO.findAll();
	}

	public List<Veiculo> getVeiculos(HashMap<String, String> form) throws NumberFormatException, InvalidAttributeValueException {
		var filtro = new VeiculoFilter(form);
		return this.veiculoDAO.findByFilters(filtro);
	}

	public Veiculo get(Integer id) {
		var veiculo = this.veiculoDAO.findById(Long.valueOf(id));
		if (!veiculo.isPresent()) {
			throw new EntityNotFoundException(
				String.format("Veículo (%d) não encontrado", id)
			);
		}
		return veiculo.get();
	}

	public List<Modelo> getModelos() {
		return this.modeloDAO.findAll();
	}

	public Veiculo produce(HashMap<String, String> map) throws InvalidAttributesException {
		return new VeiculoFactory()
			.set(this.fabricanteDAO, this.modeloDAO)
			.produces(map);
	}
}
