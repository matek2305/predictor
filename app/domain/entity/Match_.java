package domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Match.class)
public abstract class Match_ extends AbstractPredictorEntity_ {

	public static volatile SingularAttribute<Match, String> comments;
	public static volatile SingularAttribute<Match, Integer> homeTeamScore;
	public static volatile SingularAttribute<Match, String> awayTeamName;
	public static volatile SingularAttribute<Match, Integer> awayTeamScore;
	public static volatile SingularAttribute<Match, Competition> competition;
	public static volatile SingularAttribute<Match, String> homeTeamName;
	public static volatile SingularAttribute<Match, Date> startDate;
	public static volatile SetAttribute<Match, Prediction> predictions;
	public static volatile SingularAttribute<Match, String> status;

}

