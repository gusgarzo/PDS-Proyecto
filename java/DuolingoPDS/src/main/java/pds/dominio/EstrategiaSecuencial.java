package pds.dominio;

import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("secuencial")
public class EstrategiaSecuencial extends Estrategia{

	@Override
	public List<Pregunta> getPreguntas(BloqueContenido bloque) {		
		return bloque.getPreguntas();
	}
	
}
