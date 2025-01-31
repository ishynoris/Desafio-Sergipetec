package desafio.sergipetec.desafio_sergipetec.controller.filtros;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import desafio.sergipetec.desafio_sergipetec.modelo.Modelo;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoEnum;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoService;

@Service
public class FiltrosVeiculoService {

	private VeiculoService service;

	@Autowired
	public FiltrosVeiculoService(VeiculoService service) {
		this.service = service;
	}

	public HashMap<Integer, String> getTipos() {
		return VeiculoEnum.getFiltro();
	}

	public HashMap<Integer, String> getModelos() {
		var modelos = this.service.getModelos();
		var map = new HashMap<Integer, String>();

		for (Modelo modelo : modelos) {
			map.put(modelo.getId(), modelo.getNome());
		}
		return map;
	}
}
