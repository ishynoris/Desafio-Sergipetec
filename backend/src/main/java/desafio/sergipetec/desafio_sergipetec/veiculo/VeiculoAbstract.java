package desafio.sergipetec.desafio_sergipetec.veiculo;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="vco_veiculo")
public abstract class VeiculoAbstract implements VeiculoInterface {
	
	@Id
	@Column(name="vco_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="vco_fabricante", nullable=false)
	private String fabricante;

	@Column(name="vco_modelo", nullable=false)
	private String modelo;

	@Column(name="vco_ano", nullable=false)
	private int ano;

	@Column(name="vco_preco", nullable=false)
	private Double preco;

	@Column(name="vco_tipo", nullable=false)
	@Enumerated(EnumType.ORDINAL)
	private VeiculoEnum tipo;

	public boolean isCarro() {
		return VeiculoEnum.isCarro(this.getTipo().type);
	}

	public boolean isMoto() {
		return VeiculoEnum.isMoto(this.getTipo().type);
	}
}
