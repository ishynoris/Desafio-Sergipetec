package desafio.sergipetec.desafio_sergipetec.veiculo.factory;

import java.io.IOException;
import java.util.HashMap;

import javax.naming.directory.InvalidAttributesException;

import com.fasterxml.jackson.core.JsonGenerator;

import desafio.sergipetec.desafio_sergipetec.veiculo.VeiculoEnum;
import desafio.sergipetec.desafio_sergipetec.fabricante.FabricanteDAO;
import desafio.sergipetec.desafio_sergipetec.modelo.ModeloDAO;
import desafio.sergipetec.desafio_sergipetec.veiculo.Veiculo;

final public class VeiculoFactory {

	private HashMap<Integer, VeiculoFactoryInterface> factories;

	public VeiculoFactory() {
		this.factories = new HashMap<>();
	}
	
	public VeiculoFactory set(FabricanteDAO fabricanteDAO, ModeloDAO modeloDAO) {
		this.factories.put(VeiculoEnum.CARRO.codigo, new CarroFactory(modeloDAO, fabricanteDAO));
		this.factories.put(VeiculoEnum.MOTO.codigo, new MotoFactory(modeloDAO, fabricanteDAO));
		return this;
	}

	public Veiculo produces(HashMap<String, String> map) throws InvalidAttributesException {
		var factory = this.getFactory(map);
		factory.validateRequired(map);
		return factory.produce(map);
	}

	public Veiculo replace(Veiculo veiculo, HashMap<String, String> map) throws InvalidAttributesException  {
		var factory = this.getFactory(map);
		return factory.replace(veiculo, map);
	}

	private VeiculoFactoryInterface getFactory(HashMap<String, String> map) throws InvalidAttributesException {
		if (!map.containsKey("vco_tipo")) {
			throw new InvalidAttributesException("Informe o tipo do veículo");
		}

		var tipo = Integer.parseInt(map.get("vco_tipo"));
		if (!this.factories.containsKey(tipo)) {
			throw new InvalidAttributesException("Tipo de veículo indefinido");
		}

		return this.factories.get(tipo);
	}

	public static HashMap<String, String> toMap(Veiculo veiculo) {
		var map = new HashMap<String, String>();	
		map.put("vco_id", convert(veiculo.getId()));
		map.put("fbe_id", convert(veiculo.getFabricanteId()));
		map.put("mdo_id", convert(veiculo.getModeloId()));
		map.put("vco_ano", convert(veiculo.getAno()));
		map.put("vco_preco", convert(veiculo.getPreco()));
		map.put("vco_tipo", convert(veiculo.getTipoId()));
		map.put("vco_portas", convert(veiculo.getQuantidadePortas()));
		map.put("vco_combustivel", convert(veiculo.getCombustivelId()));
		map.put("vco_cilindradas", convert(veiculo.getCilindradas()));
		return map;
	}

	public static void jsonSerialize(JsonGenerator gen, Veiculo veiculo) throws IOException {
		var tipo = veiculo.getTipo();

		gen.writeStartObject();
		gen.writeNumberField("vco_id", veiculo.getId());
		gen.writeNumberField("vco_ano", veiculo.getAno());
		gen.writeNumberField("vco_preco", veiculo.getPreco());
		gen.writeNumberField("vco_tipo_cod", tipo.codigo);
		gen.writeStringField("vco_tipo_text", tipo.name());
		gen.writeStringField("vco_descricao", veiculo.toString());

		if (veiculo.isCarro()) {
			var combustivel = veiculo.getTipoCombustivel();
			gen.writeObjectField("vco_portas", veiculo.getQuantidadePortas());
			gen.writeNumberField("vco_combustivel_cod", combustivel.codigo);
			gen.writeStringField("vco_combustivel_text", combustivel.name());
		}

		if (veiculo.isMoto()) {
			gen.writeStringField("vco_cilindradas", veiculo.getCilindradas() + "cc");
		}

		gen.writeObjectField("vco_fabricante", veiculo.getFabricante());
		gen.writeObjectField("vco_modelo", veiculo.getModelo());
		gen.writeBooleanField("vco_is_carro", veiculo.isCarro());
		gen.writeBooleanField("vco_is_moto", veiculo.isMoto());
		gen.writeEndObject();
	}

	private static String convert(Number val) {
		return val == null ? null : String.valueOf(val);
	}
}
