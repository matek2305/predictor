package domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Competition.class)
public abstract class Competition_ extends AbstractPredictorEntity_ {

	public static volatile SetAttribute<Competition, PredictorPoints> predictorsPoints;
	public static volatile SingularAttribute<Competition, String> name;
	public static volatile SingularAttribute<Competition, String> description;
	public static volatile SingularAttribute<Competition, String> securityCode;
	public static volatile SingularAttribute<Competition, Predictor> admin;
	public static volatile SetAttribute<Competition, Match> matches;

}

