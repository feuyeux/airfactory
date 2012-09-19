package creative.air.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "air_book", schema = "airjpa")
public class Book extends AirEntity{
	private static final long serialVersionUID = 6513858067628439504L;
}
