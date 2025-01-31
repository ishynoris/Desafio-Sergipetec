package desafio.sergipetec.desafio_sergipetec.veiculo;

import java.util.HashMap;

public enum VeiculoEnum {

	CARRO(1), MOTO(2);
	
	public final int codigo;

    private VeiculoEnum(int codigo) {
        this.codigo = codigo;
    }

	public static boolean isCarro(int codigo) {
		return codigo == CARRO.codigo;
	}

	public static boolean isMoto(int codigo) {
		return codigo == MOTO.codigo;
	}

	public static HashMap<Integer, String> getFiltro() {
		var filtro = new HashMap<Integer, String>();
		filtro.put(CARRO.codigo, CARRO.name());
		filtro.put(MOTO.codigo, MOTO.name());
		return filtro;
	}
}
