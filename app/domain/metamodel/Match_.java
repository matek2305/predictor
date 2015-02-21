package domain.metamodel;

import domain.entity.Competition;
import domain.entity.Match;
import domain.entity.Match.Status;
import domain.entity.Prediction;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

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
	public static volatile ListAttribute<Match, Prediction> predictions;
	public static volatile SingularAttribute<Match, Status> status;

}

