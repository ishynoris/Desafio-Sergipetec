package desafio.sergipetec.desafio_sergipetec.veiculo;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import desafio.sergipetec.desafio_sergipetec.fabricante.Fabricante;
import desafio.sergipetec.desafio_sergipetec.modelo.Modelo;
import desafio.sergipetec.desafio_sergipetec.veiculo.Veiculo.VeiculoSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="vco_veiculo")
@JsonSerialize(using = VeiculoSerializer.class)
public class Veiculo {
	
	@Id
	@Column(name="vco_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="vco_ano", nullable=false)
	private Integer ano;

	@Column(name="vco_preco", nullable=false)
	private Double preco;

	@Column(name="vco_tipo", nullable=false)
	@Enumerated(EnumType.ORDINAL)
	private VeiculoEnum tipo;

	@Column(name="vco_cilindradas")
	private Integer cilindradas;

	@Column(name="vco_portas")
	private Integer quantidadePortas;

	@Column(name="vco_combustivel")
	@Enumerated(EnumType.ORDINAL)
	private TipoCombustivelEnum tipoCombustivel;

	@OneToOne
	@JoinColumn(name="fbe_id")
	private Fabricante fabricante;

	@OneToOne
	@JoinColumn(name="mdo_id")
	private Modelo modelo;

	public boolean isCarro() {
		return VeiculoEnum.isCarro(this.getTipo().codigo);
	}

	public boolean isMoto() {
		return VeiculoEnum.isMoto(this.getTipo().codigo);
	}


	public static class VeiculoSerializer extends JsonSerializer<Veiculo> {
		@Override
		public void serialize(Veiculo veiculo, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeStartObject();
			gen.writeNumberField("vco_id", veiculo.id);
			gen.writeNumberField("vco_ano", veiculo.ano);
			gen.writeNumberField("vco_preco", veiculo.preco);
			gen.writeNumberField("vco_tipo_cod", veiculo.tipo.codigo);
			gen.writeStringField("vco_tipo_text", veiculo.tipo.name());

			if (veiculo.isCarro()) {
				gen.writeObjectField("vco_portas", veiculo.quantidadePortas);
				gen.writeNumberField("vco_combustivel_cod", veiculo.tipoCombustivel.codigo);
				gen.writeStringField("vco_combustivel_text", veiculo.tipoCombustivel.name());
			}

			if (veiculo.isMoto()) {
				gen.writeStringField("vco_cilindradas", veiculo.cilindradas + "cc");
			}

			gen.writeObjectField("vco_fabricante", veiculo.fabricante);
			gen.writeObjectField("vco_modelo", veiculo.modelo);
			gen.writeBooleanField("vco_is_carro", veiculo.isCarro());
			gen.writeBooleanField("vco_is_moto", veiculo.isMoto());
			gen.writeEndObject();
		}
	}
}