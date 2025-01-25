package desafio.sergipetec.desafio_sergipetec.veiculo;

public interface VeiculoInterface {
	
	public int getId();

	public String getFabricante();

	public String getModelo();

	public int getAno();

	public Double getPreco();

	public VeiculoEnum getTipo();
}
