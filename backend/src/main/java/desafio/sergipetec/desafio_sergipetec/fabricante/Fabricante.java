package desafio.sergipetec.desafio_sergipetec.fabricante;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import desafio.sergipetec.desafio_sergipetec.fabricante.Fabricante.FabricanteSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Entity
@Table(name="fbe_fabricante")
@JsonSerialize(using = FabricanteSerializer.class)
public class Fabricante {

	@Id
	@Column(name="fbe_id", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="fbe_nome", nullable=false)
	private String nome;

	public static class FabricanteSerializer extends JsonSerializer<Fabricante> {
		@Override
		public void serialize(Fabricante fabricante, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeStartObject();
			gen.writeNumberField("fbe_id", fabricante.id);
			gen.writeStringField("fbe_nome", fabricante.nome);
			gen.writeEndObject();
		}
	}
}
