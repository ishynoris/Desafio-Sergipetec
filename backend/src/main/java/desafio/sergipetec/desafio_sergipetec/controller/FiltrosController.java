package desafio.sergipetec.desafio_sergipetec.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import desafio.sergipetec.desafio_sergipetec.controller.filtros.FiltrosVeiculoService;
import desafio.sergipetec.desafio_sergipetec.modelo.Modelo;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoService;

@RestController
@RequestMapping("/filtros")
public class FiltrosController {

	@Autowired private FiltrosVeiculoService filtroService;
	@Autowired private VeiculoService veiculoService;

	@CrossOrigin
	@GetMapping
	public @ResponseBody HashMap<String, Object> getAll() {

		var fabricanteModelo = new HashMap<Integer, HashMap<Integer, String>>();
		var modelos = this.veiculoService.getModelos();
		for (Modelo modelo : modelos) {
			var fabricanteId = modelo.getFabricante().getId();

			var modeloPorFabricante = fabricanteModelo.containsKey(fabricanteId)
				? fabricanteModelo.get(fabricanteId)
				: new HashMap<Integer, String>();
			modeloPorFabricante.put(modelo.getId(), modelo.getNome());
			fabricanteModelo.put(fabricanteId, modeloPorFabricante);
		}

		HashMap<String, Object> filtros = new HashMap<>();
		filtros.put("tipos", this.filtroService.getTipos());
		filtros.put("combustivel", this.filtroService.getCombustivel());
		filtros.put("fabricantes", this.filtroService.getFabricantes());
		filtros.put("modelos", this.filtroService.getModelos());
		filtros.put("fabricante_modelo", fabricanteModelo);

		return filtros;
	}
}
