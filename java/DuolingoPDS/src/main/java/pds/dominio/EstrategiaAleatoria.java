package pds.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
@Entity
@DiscriminatorValue("aleatoria")
public class EstrategiaAleatoria extends Estrategia{

	@Override
	public List<Pregunta> getPreguntas(BloqueContenido bloque) {
		List<Pregunta> preguntas =  new ArrayList<>(bloque.getPreguntas());
		Collections.shuffle(preguntas);
		return preguntas;
	}

}
