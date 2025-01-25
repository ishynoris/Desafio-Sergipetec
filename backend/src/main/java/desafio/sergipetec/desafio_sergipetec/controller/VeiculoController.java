package desafio.sergipetec.desafio_sergipetec.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoService;

@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

	@Autowired
	VeiculoService service;
	
	@GetMapping
	public String getAll() {
		return "getAll";
	}

	@GetMapping("/{id}")
	public String getById(@PathVariable int id) {
		return "getById: " + id;
	}

	@PostMapping
	public String save(@RequestBody HashMap<String, String> veiculo) {
		return "getById: " + veiculo.toString();
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
