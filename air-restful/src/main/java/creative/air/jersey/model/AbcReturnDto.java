package creative.air.jersey.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author
 * Eric Han feuyeux@gmail.com
 * 05/08/2012
 * @since 0.0.1
 * @version 0.0.1
 */

/**
 * wrapping of a TestQueue in a ReturnDto for adding the error message layer on top of the data
 */
@XmlRootElement(name = "abcReturn")
public class AbcReturnDto {
    private int errorNumber;
    private String errorText;

    private AbcDto element;
    private List<AbcDto> elementList;

    public AbcReturnDto() {
        super();
    }

    public AbcReturnDto(int errorNumber, String errorText) {
        super();
        this.errorNumber = errorNumber;
        this.errorText = errorText;
    }

    public AbcReturnDto(AbcDto element) {
        super();
        this.element = element;
    }

    public AbcReturnDto(List<AbcDto> elementList) {
        super();
        this.elementList = elementList;
    }

    @XmlElement(name = "element")
    public AbcDto getElement() {
        return element;
    }

    public void setElement(AbcDto element) {
        this.element = element;
    }

    @XmlElement(name = "abc")
    @XmlElementWrapper(name = "elementList")
    public List<AbcDto> getElementList() {
        return elementList;
    }

    public void setElementList(List<AbcDto> elementList) {
        this.elementList = elementList;
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("[element=" + element + ", elementList=[");
        for (AbcDto element : elementList) {
            buff.append(element + " ");
        }
        buff.append("], errorNumber= " + errorNumber + ", errorText= " + errorText + "]");
        return buff.toString();
    }
}
