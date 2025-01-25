package desafio.sergipetec.desafio_sergipetec.veiculo;

import java.util.HashMap;

public enum TipoCombustivelEnum {
	
	GASOLINA(1), ETANOL(2), DIESEL(3), FLEX(4);

	public final int type;

	private TipoCombustivelEnum(int type) {
		this.type = type;
	}

	public static TipoCombustivelEnum parse(String value) throws Exception {
		var map = new HashMap<Integer, TipoCombustivelEnum>();
		map.put(GASOLINA.type, GASOLINA);
		map.put(ETANOL.type, ETANOL);
		map.put(DIESEL.type, DIESEL);
		map.put(FLEX.type, FLEX);

		var type = Integer.parseInt(value);
		if (!map.containsKey(type)) {
			throw new Exception("Tipo de combustível inválido");
		}
		return map.get(type);
	}
}
