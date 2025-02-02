package desafio.sergipetec.desafio_sergipetec.veiculo;

import java.util.HashMap;

public enum TipoCombustivelEnum {
	
	GASOLINA, ETANOL, DIESEL, FLEX;

	public final int codigo;

	private TipoCombustivelEnum() {
		this.codigo = this.ordinal();
	}

	public static TipoCombustivelEnum parse(String value) throws Exception {
		var map = new HashMap<Integer, TipoCombustivelEnum>();
		map.put(GASOLINA.codigo, GASOLINA);
		map.put(ETANOL.codigo, ETANOL);
		map.put(DIESEL.codigo, DIESEL);
		map.put(FLEX.codigo, FLEX);

		var codigo = Integer.parseInt(value);
		if (!map.containsKey(codigo)) {
			throw new Exception("Tipo de combustível inválido");
		}
		return map.get(codigo);
	}
}
