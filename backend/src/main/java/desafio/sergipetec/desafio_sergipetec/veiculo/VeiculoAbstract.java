package desafio.sergipetec.desafio_sergipetec.veiculo;

public abstract class VeiculoAbstract implements VeiculoInterface {
	
	private int id;
	private String fabricante;
	private String modelo;
	private int ano;
	private Double preco;

	public VeiculoAbstract(
		String fabricante
		, String modelo
		, int ano
		, Double preco
	) {
		this.fabricante = fabricante;
		this.modelo = modelo;
		this.ano = ano;
		this.preco = preco;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public String getFabricante() {
		return this.fabricante;
	}

	public String getModelo() {
		return this.modelo;
	}

	public int getAno() {
		return this.ano;
	}

	public Double getPreco() {
		return  this.preco;
	}

	public boolean isCarro() {
		var tipo = this.getTipo().type;
		return VeiculoEnum.isCarro(tipo);
	}

	public boolean isMoto() {
		var tipo = this.getTipo().type;
		return VeiculoEnum.isMoto(tipo);
	}
}
