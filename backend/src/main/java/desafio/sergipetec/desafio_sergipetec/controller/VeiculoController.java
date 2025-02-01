package desafio.sergipetec.desafio_sergipetec.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import desafio.sergipetec.desafio_sergipetec.controller.filtros.FiltrosVeiculoService;
import desafio.sergipetec.desafio_sergipetec.veiculo.Veiculo;
import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoService;

@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

	@Autowired private VeiculoService service;
	@Autowired private FiltrosVeiculoService filtroService;
	
	@GetMapping
	public @ResponseBody HashMap<String, Object> getAll() throws Exception {
		HashMap<String, Object> bodyResponse = new HashMap<>();
		HashMap<String, Object> filtros = new HashMap<>();

		try {
			bodyResponse.put("veiculos", this.service.getVeiculos());

			filtros.put("tipos", this.filtroService.getTipos());
			filtros.put("modelos", this.filtroService.getModelos());
			bodyResponse.put("filtros", filtros);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return bodyResponse;
	}

	@GetMapping("/filtro")
	public @ResponseBody List<Veiculo> getAll(@RequestParam HashMap<String, String> form) {
		return this.service.getVeiculos(form);
	}

	@GetMapping("/{id}")
	public String getById(@PathVariable int id) {
		return "getById: " + id;
	}

	@PostMapping
	public String save(@RequestBody HashMap<String, String> map) {
		return "getById: " + map.toString();
	}

	@PutMapping("/{id}")
	public String update(@PathVariable int id, @RequestBody HashMap<String, String> veiculo) {
		return "update: " + veiculo.toString();
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable int id) {
		return "delete " + id;
	}
}
