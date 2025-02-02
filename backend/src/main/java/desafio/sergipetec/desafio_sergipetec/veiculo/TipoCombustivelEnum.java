package desafio.sergipetec.desafio_sergipetec.veiculo;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum TipoCombustivelEnum {
	
	GASOLINA, ETANOL, DIESEL, FLEX;

	public final int codigo;

	private TipoCombustivelEnum() {
		this.codigo = this.ordinal();
	}

	public int getCodigo() {
		return codigo;
	}

	public static TipoCombustivelEnum parse(String value) throws Exception {
		var map = baseMapper();
		var codigo = Integer.parseInt(value);
		if (!map.containsKey(codigo)) {
			throw new Exception("Tipo de combustível inválido");
		}
		return map.get(codigo);
	}

	public static Map<Integer, String> getFiltro() {
		var collector = Collectors.toMap(TipoCombustivelEnum::getCodigo, TipoCombustivelEnum::name);
		return baseMapper().values().stream().collect(collector);
	}

	private static HashMap<Integer, TipoCombustivelEnum> baseMapper() {
		var map = new HashMap<Integer, TipoCombustivelEnum>();
		map.put(GASOLINA.codigo, GASOLINA);
		map.put(ETANOL.codigo, ETANOL);
		map.put(DIESEL.codigo, DIESEL);
		map.put(FLEX.codigo, FLEX);
		return map;
	}
}