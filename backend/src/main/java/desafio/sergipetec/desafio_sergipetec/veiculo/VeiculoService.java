package desafio.sergipetec.desafio_sergipetec.veiculo;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class VeiculoService {

	VeiculoRepository repo;

	public List<VeiculoInterface> getAll() {
		return this.repo.findAll();
	}

	
}
