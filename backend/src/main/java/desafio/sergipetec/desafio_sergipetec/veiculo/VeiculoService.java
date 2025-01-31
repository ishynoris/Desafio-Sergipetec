package desafio.sergipetec.desafio_sergipetec.veiculo;

import java.util.HashMap;
import java.util.List;

import javax.naming.directory.InvalidAttributesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import desafio.sergipetec.desafio_sergipetec.fabricante.FabricanteDAO;
import desafio.sergipetec.desafio_sergipetec.modelo.ModeloDAO;
import desafio.sergipetec.desafio_sergipetec.veiculo.factory.VeiculoFactory;

@Service
public class VeiculoService {

	@Autowired private FabricanteDAO fabricanteRepo;
	@Autowired private ModeloDAO modeloRepo;

	private  VeiculoDAO repo;

	@Autowired
	public VeiculoService(VeiculoDAO repo) {
		this.repo = repo;
	}

	public List<Veiculo> getVeiculos() {
		return this.repo.findAll();
	}

	public Veiculo produce(HashMap<String, String> map) throws InvalidAttributesException {
		return new VeiculoFactory()
			.set(this.fabricanteRepo, this.modeloRepo)
			.produces(map);
	}
}
