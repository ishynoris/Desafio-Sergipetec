package desafio.sergipetec.desafio_sergipetec.veiculo;

public enum VeiculoEnum {

	CARRO(1), MOTO(2);
	
	public final int type;

    private VeiculoEnum(int type) {
        this.type = type;
    }

	public static boolean isCarro(int type) {
		return type == CARRO.type;
	}

	public static boolean isMoto(int type) {
		return type == MOTO.type;
	}
}
