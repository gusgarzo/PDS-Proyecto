package pds.dominio;

import java.util.List;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_estrategia", discriminatorType = DiscriminatorType.STRING)
public abstract class Estrategia {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	public abstract List<Pregunta> getPreguntas(BloqueContenido bloque);
	
}
