package desafio.sergipetec.desafio_sergipetec.veiculo;

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
}
