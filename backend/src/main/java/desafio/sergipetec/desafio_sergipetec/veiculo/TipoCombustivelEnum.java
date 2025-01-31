package desafio.sergipetec.desafio_sergipetec.veiculo;

import java.util.HashMap;

public enum TipoCombustivelEnum {
	
	GASOLINA(1), ETANOL(2), DIESEL(3), FLEX(4);

	public final int codigo;

	private TipoCombustivelEnum(int codigo) {
		this.codigo = codigo;
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
