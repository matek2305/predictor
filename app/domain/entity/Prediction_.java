package domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Prediction.class)
public abstract class Prediction_ extends AbstractPredictorEntity_ {

	public static volatile SingularAttribute<Prediction, Integer> homeTeamScore;
	public static volatile SingularAttribute<Prediction, Integer> awayTeamScore;
	public static volatile SingularAttribute<Prediction, Match> match;
	public static volatile SingularAttribute<Prediction, Predictor> predictor;
	public static volatile SingularAttribute<Prediction, Integer> points;

}

