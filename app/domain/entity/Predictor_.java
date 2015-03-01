package domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Predictor.class)
public abstract class Predictor_ extends AbstractPredictorEntity_ {

	public static volatile SingularAttribute<Predictor, String> password;
	public static volatile SingularAttribute<Predictor, Date> tokenExpirationDate;
	public static volatile SingularAttribute<Predictor, Date> registrationDate;
	public static volatile SingularAttribute<Predictor, String> login;
	public static volatile SingularAttribute<Predictor, String> authenticationToken;
	public static volatile SetAttribute<Predictor, PredictorPoints> points;

}

