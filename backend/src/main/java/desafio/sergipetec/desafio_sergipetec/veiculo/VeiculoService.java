package desafio.sergipetec.desafio_sergipetec.veiculo;

import java.util.HashMap;
import java.util.List;

import javax.naming.directory.InvalidAttributesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import desafio.sergipetec.desafio_sergipetec.fabricante.FabricanteDAO;
import desafio.sergipetec.desafio_sergipetec.modelo.Modelo;
import desafio.sergipetec.desafio_sergipetec.modelo.ModeloDAO;
import desafio.sergipetec.desafio_sergipetec.veiculo.factory.VeiculoFactory;

@Service
public class VeiculoService {

	@Autowired private FabricanteDAO fabricanteRepo;
	@Autowired private ModeloDAO modeloRepo;

	private  VeiculoDAO veiculoDAO;

	@Autowired
	public VeiculoService(VeiculoDAO veiculoDAO) {
		this.veiculoDAO = veiculoDAO;
	}

	public List<Veiculo> getVeiculos() {
		return this.veiculoDAO.findAll();
	}

	public List<Modelo> getModelos() {
		return this.modeloRepo.findAll();
	}

	public Veiculo produce(HashMap<String, String> map) throws InvalidAttributesException {
		return new VeiculoFactory()
			.set(this.fabricanteRepo, this.modeloRepo)
			.produces(map);
	}
}
