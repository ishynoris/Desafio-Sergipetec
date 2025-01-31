package desafio.sergipetec.desafio_sergipetec.fabricante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FabricanteService {

	private FabricanteDAO repo;

	@Autowired
	public FabricanteService(FabricanteDAO repo) {
		this.repo = repo;
	}

	public Fabricante get(Integer fabricanteId) throws EntityNotFoundException {
		var fabricante = this.repo.findById(fabricanteId);

		if (!fabricante.isPresent()) {
			throw new EntityNotFoundException(String.format(
				"Fabricante (%d) não encontrado", 
				fabricanteId
			));
		}
		return fabricante.get();
	}
}
