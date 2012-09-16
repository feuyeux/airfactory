package creative.air.datastructure.map;
/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 16/09/2012
 * @since  0.0.1
 * @version 0.0.1
 */
public class StringInt {

	private int value;

	public StringInt(int value) {
		this.value = value;
	}
	
	//equals相等时，要求hashCode必须相等
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() == obj.getClass()) {
			StringInt other = (StringInt) obj;
			return this.value == other.value && this.value == other.value;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return value % 2;
	}
}
