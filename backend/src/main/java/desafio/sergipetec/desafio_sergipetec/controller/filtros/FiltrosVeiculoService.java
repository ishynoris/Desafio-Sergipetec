package desafio.sergipetec.desafio_sergipetec.controller.filtros;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

	public Map<Integer, String> getTipos() {
		return VeiculoEnum.getFiltro();
	}

	public Map<Integer, String> getModelos() {
		var modelos = this.service.getModelos();
		var collector = Collectors.toMap(Modelo::getId, Modelo::getNome);
		return modelos.stream().collect(collector);
	}
}
