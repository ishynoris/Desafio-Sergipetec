package desafio.sergipetec.desafio_sergipetec.controller;

import java.util.HashMap;
import java.util.List;

import javax.management.InvalidAttributeValueException;
import javax.naming.directory.InvalidAttributesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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

	@CrossOrigin
	@GetMapping
	public @ResponseBody HashMap<String, Object> getAll() {
		HashMap<String, Object> filtros = new HashMap<>();
		filtros.put("tipos", this.filtroService.getTipos());
		filtros.put("modelos", this.filtroService.getModelos());

		HashMap<String, Object> bodyResponse = new HashMap<>();
		bodyResponse.put("veiculos", this.service.getVeiculos());
		bodyResponse.put("filtros", filtros);

		return bodyResponse;
	}

	@CrossOrigin
	@GetMapping("/pesquisar")
	public @ResponseBody List<Veiculo> getAll(@RequestParam HashMap<String, String> form) throws NumberFormatException, InvalidAttributeValueException {
		return this.service.getVeiculos(form);
	}

	@CrossOrigin
	@GetMapping("/{id}")
	public Veiculo getById(@PathVariable int id) {
		return this.service.get(id);
	}

	@CrossOrigin
	@PostMapping
	public HashMap<String, Object> save(@RequestBody HashMap<String, String> form) throws InvalidAttributesException, Exception {
		var response = new HashMap<String, Object>();
		var success = true;
		try {
			var veiculo = this.service.salvar(form);
			response.put("veiculo", veiculo);
		} catch (Exception e) {
			success = false;
			response.put("message", e.getMessage());
		}

		response.put("success", success);
		return response;
	}

	@CrossOrigin
	@PutMapping("/{id}")
	public HashMap<String, Object> update(@PathVariable int id, @RequestBody HashMap<String, String> form) throws InvalidAttributesException, Exception {
		var response = new HashMap<String, Object>();
		var success = true;
		try {
			var veiculo = this.service.get(id);
			veiculo = this.service.atualizar(veiculo, form);
			response.put("veiculo", veiculo);
		} catch (Exception e) {
			success = false;
			response.put("message", e.getMessage());
		}

		response.put("success", success);
		return response;
	}

	@CrossOrigin
	@DeleteMapping("/{id}")
	public HashMap<String, Object> delete(@PathVariable int id) {
		var response = new HashMap<String, Object>();
		var success = true;
		
		try {
			var veiculo = this.service.apagar(id);
			response.put("veiculo", veiculo);
		} catch (Exception e) {
			success = false;
			response.put("message", e.getMessage());
		}

		response.put("success", success);
		return response;
	}
}
