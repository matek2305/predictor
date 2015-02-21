package domain.metamodel;

import domain.entity.Predictor;
import domain.entity.PredictorPoints;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Predictor.class)
public abstract class Predictor_ extends AbstractPredictorEntity_ {

	public static volatile SingularAttribute<Predictor, String> password;
	public static volatile SingularAttribute<Predictor, Date> tokenExpirationDate;
	public static volatile SingularAttribute<Predictor, Date> registrationDate;
	public static volatile SingularAttribute<Predictor, String> login;
	public static volatile SingularAttribute<Predictor, String> authenticationToken;
	public static volatile ListAttribute<Predictor, PredictorPoints> points;

}

