package desafio.sergipetec.desafio_sergipetec.modelo;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import desafio.sergipetec.desafio_sergipetec.fabricante.Fabricante;
import desafio.sergipetec.desafio_sergipetec.modelo.Modelo.ModeloSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="mdo_modelo")
@JsonSerialize(using = ModeloSerializer.class)
public class Modelo {

	@Id
	@Column(name="mdo_id", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="mdo_nome", nullable=false)
	private String nome;

	@OneToOne
	@JoinColumn(name="fbe_id")
	private Fabricante fabricante;

	public boolean percente(Fabricante fabricante) {
		return this.fabricante.getId() == fabricante.getId();
	}

	public static class ModeloSerializer extends JsonSerializer<Modelo> {
		@Override
		public void serialize(Modelo modelo, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeStartObject();
			gen.writeNumberField("mdo_id", modelo.id);
			gen.writeStringField("mdo_nome", modelo.nome);
			
			gen.writeNumberField("fbe_id", modelo.fabricante.getId());
			gen.writeStringField("fbe_nome", modelo.fabricante.getNome());

			gen.writeEndObject();
		}
	}
}
