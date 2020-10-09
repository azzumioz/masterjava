
package ru.javaops.masterjava.xml.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for groupEnumType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="groupEnumType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="REGISTERING"/>
 *     &lt;enumeration value="CURRENT"/>
 *     &lt;enumeration value="FINISHED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "groupEnumType", namespace = "http://javaops.ru")
@XmlEnum
public enum GroupEnumType {

    REGISTERING,
    CURRENT,
    FINISHED;

    public String value() {
        return name();
    }

    public static GroupEnumType fromValue(String v) {
        return valueOf(v);
    }

}
