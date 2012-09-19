package creative.air.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "air_category", schema = "airjpa")
public class Category extends AirEntity {
	private static final long serialVersionUID = 5347507906853913006L;

}
