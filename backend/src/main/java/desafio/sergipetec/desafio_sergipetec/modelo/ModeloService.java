package desafio.sergipetec.desafio_sergipetec.modelo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ModeloService {

	private ModeloDAO repo;
	
	@Autowired
	public ModeloService (ModeloDAO repo) {
		this.repo = repo;
	}

	public Modelo get(Integer modeloId) {
		var modelo = this.repo.findById(modeloId);

		if (!modelo.isPresent()) {
			throw new EntityNotFoundException(
				String.format("Modelo (%d) não encontrado", modeloId)
			);
		}
		return modelo.get();
	}
}
