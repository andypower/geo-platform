//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.04.17 at 10:27:36 PM CEST 
//


package org.geosdi.geoplatform.xml.gml.v311;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;


/**
 * An engineering coordinate reference system applied to locations in images. Image coordinate reference systems are treated as a separate sub-type because a separate user community exists for images with its own terms of reference. 
 * 
 * <p>Java class for ImageCRSType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImageCRSType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml}AbstractReferenceSystemType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{http://www.opengis.net/gml}usesCartesianCS"/>
 *           &lt;element ref="{http://www.opengis.net/gml}usesObliqueCartesianCS"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://www.opengis.net/gml}usesImageDatum"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImageCRSType", propOrder = {
    "usesCartesianCS",
    "usesObliqueCartesianCS",
    "usesImageDatum"
})
public class ImageCRSType
    extends AbstractReferenceSystemType
    implements ToString
{

    protected CartesianCSRefType usesCartesianCS;
    protected ObliqueCartesianCSRefType usesObliqueCartesianCS;
    @XmlElement(required = true)
    protected ImageDatumRefType usesImageDatum;

    /**
     * Gets the value of the usesCartesianCS property.
     * 
     * @return
     *     possible object is
     *     {@link CartesianCSRefType }
     *     
     */
    public CartesianCSRefType getUsesCartesianCS() {
        return usesCartesianCS;
    }

    /**
     * Sets the value of the usesCartesianCS property.
     * 
     * @param value
     *     allowed object is
     *     {@link CartesianCSRefType }
     *     
     */
    public void setUsesCartesianCS(CartesianCSRefType value) {
        this.usesCartesianCS = value;
    }

    public boolean isSetUsesCartesianCS() {
        return (this.usesCartesianCS!= null);
    }

    /**
     * Gets the value of the usesObliqueCartesianCS property.
     * 
     * @return
     *     possible object is
     *     {@link ObliqueCartesianCSRefType }
     *     
     */
    public ObliqueCartesianCSRefType getUsesObliqueCartesianCS() {
        return usesObliqueCartesianCS;
    }

    /**
     * Sets the value of the usesObliqueCartesianCS property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObliqueCartesianCSRefType }
     *     
     */
    public void setUsesObliqueCartesianCS(ObliqueCartesianCSRefType value) {
        this.usesObliqueCartesianCS = value;
    }

    public boolean isSetUsesObliqueCartesianCS() {
        return (this.usesObliqueCartesianCS!= null);
    }

    /**
     * Gets the value of the usesImageDatum property.
     * 
     * @return
     *     possible object is
     *     {@link ImageDatumRefType }
     *     
     */
    public ImageDatumRefType getUsesImageDatum() {
        return usesImageDatum;
    }

    /**
     * Sets the value of the usesImageDatum property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageDatumRefType }
     *     
     */
    public void setUsesImageDatum(ImageDatumRefType value) {
        this.usesImageDatum = value;
    }

    public boolean isSetUsesImageDatum() {
        return (this.usesImageDatum!= null);
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
            CartesianCSRefType theUsesCartesianCS;
            theUsesCartesianCS = this.getUsesCartesianCS();
            strategy.appendField(locator, this, "usesCartesianCS", buffer, theUsesCartesianCS);
        }
        {
            ObliqueCartesianCSRefType theUsesObliqueCartesianCS;
            theUsesObliqueCartesianCS = this.getUsesObliqueCartesianCS();
            strategy.appendField(locator, this, "usesObliqueCartesianCS", buffer, theUsesObliqueCartesianCS);
        }
        {
            ImageDatumRefType theUsesImageDatum;
            theUsesImageDatum = this.getUsesImageDatum();
            strategy.appendField(locator, this, "usesImageDatum", buffer, theUsesImageDatum);
        }
        return buffer;
    }

}