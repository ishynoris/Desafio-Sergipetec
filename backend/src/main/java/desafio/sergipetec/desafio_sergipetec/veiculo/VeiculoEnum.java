package desafio.sergipetec.desafio_sergipetec.veiculo;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.management.InvalidAttributeValueException;


public enum VeiculoEnum {

	CARRO(1), MOTO(2);
	
	public final int codigo;

    private VeiculoEnum(int codigo) {
        this.codigo = codigo;
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public static boolean isCarro(int codigo) {
		return codigo == CARRO.codigo;
	}

	public static boolean isMoto(int codigo) {
		return codigo == MOTO.codigo;
	}

	public static VeiculoEnum parse(Integer codigo) throws InvalidAttributeValueException {
		var tipos = baseMapper();
		if (!tipos.containsKey(codigo)) {
			throw new InvalidAttributeValueException("Tipo do veículo inválido");
		}
		return tipos.get(codigo);
	}

	public static Map<Integer, String> getFiltro() {
		var collector = Collectors.toMap(VeiculoEnum::getCodigo, VeiculoEnum::name);
		return baseMapper().values().stream().collect(collector);
	}

	private static HashMap<Integer, VeiculoEnum> baseMapper() {
		var filtro = new HashMap<Integer, VeiculoEnum>();
		filtro.put(CARRO.codigo, CARRO);
		filtro.put(MOTO.codigo, MOTO);
		return filtro;
	}
}
