//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.04.18 at 03:12:11 PM CEST 
//


package org.geosdi.geoplatform.xml.iso19139v20070417.gmx;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.geosdi.geoplatform.xml.gml.v321.DictionaryType;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;


/**
 * Constraints: - 1) metadataProperty.card = 0 - 2) dictionaryEntry.card = 0
 * 
 * <p>Java class for CodeListDictionary_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CodeListDictionary_Type">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml/3.2}DictionaryType">
 *       &lt;sequence>
 *         &lt;element name="codeEntry" type="{http://www.isotc211.org/2005/gmx}CodeDefinition_PropertyType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CodeListDictionary_Type", propOrder = {
    "codeEntry"
})
@XmlSeeAlso({
    MLCodeListDictionaryType.class
})
public class CodeListDictionaryType
    extends DictionaryType
    implements ToString
{

    @XmlElement(required = true)
    protected List<CodeDefinitionPropertyType> codeEntry;

    /**
     * Gets the value of the codeEntry property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codeEntry property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCodeEntry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CodeDefinitionPropertyType }
     * 
     * 
     */
    public List<CodeDefinitionPropertyType> getCodeEntry() {
        if (codeEntry == null) {
            codeEntry = new ArrayList<CodeDefinitionPropertyType>();
        }
        return this.codeEntry;
    }

    public boolean isSetCodeEntry() {
        return ((this.codeEntry!= null)&&(!this.codeEntry.isEmpty()));
    }

    public void unsetCodeEntry() {
        this.codeEntry = null;
    }

    public String toString() {
        final ToStringStrategy strategy = JAXBToStringStrategy.INSTANCE;
        final StringBuilder buffer = new StringBuilder();
        append(null, buffer, strategy);
        return buffer.toString();
    }

    public StringBuilder append(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        strategy.appendStart(locator, this, buffer);
        appendFields(locator, buffer, strategy);
        strategy.appendEnd(locator, this, buffer);
        return buffer;
    }

    public StringBuilder appendFields(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        super.appendFields(locator, buffer, strategy);
        {
            List<CodeDefinitionPropertyType> theCodeEntry;
            theCodeEntry = (this.isSetCodeEntry()?this.getCodeEntry():null);
            strategy.appendField(locator, this, "codeEntry", buffer, theCodeEntry);
        }
        return buffer;
    }

    public void setCodeEntry(List<CodeDefinitionPropertyType> value) {
        this.codeEntry = null;
        List<CodeDefinitionPropertyType> draftl = this.getCodeEntry();
        draftl.addAll(value);
    }

}