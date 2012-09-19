package creative.air.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "air_publisher", schema = "airjpa")
public class Publisher  extends AirEntity{
	private static final long serialVersionUID = 1989591437793375355L;

}
