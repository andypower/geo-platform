//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.07.16 at 12:48:05 PM CEST 
//


package org.geosdi.geoplatform.xml.wps.v100;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;


/**
 * <p>Java class for ResponseDocumentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResponseDocumentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Output" type="{http://www.opengis.net/wps/1.0.0}DocumentOutputDefinitionType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="storeExecuteResponse" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="lineage" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="status" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseDocumentType", propOrder = {
    "output"
})
public class ResponseDocumentType
    implements ToString
{

    @XmlElement(name = "Output", required = true)
    protected List<DocumentOutputDefinitionType> output;
    @XmlAttribute(name = "storeExecuteResponse")
    protected Boolean storeExecuteResponse;
    @XmlAttribute(name = "lineage")
    protected Boolean lineage;
    @XmlAttribute(name = "status")
    protected Boolean status;

    /**
     * Gets the value of the output property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the output property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutput().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentOutputDefinitionType }
     * 
     * 
     */
    public List<DocumentOutputDefinitionType> getOutput() {
        if (output == null) {
            output = new ArrayList<DocumentOutputDefinitionType>();
        }
        return this.output;
    }

    public boolean isSetOutput() {
        return ((this.output!= null)&&(!this.output.isEmpty()));
    }

    public void unsetOutput() {
        this.output = null;
    }

    /**
     * Gets the value of the storeExecuteResponse property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isStoreExecuteResponse() {
        if (storeExecuteResponse == null) {
            return false;
        } else {
            return storeExecuteResponse;
        }
    }

    /**
     * Sets the value of the storeExecuteResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setStoreExecuteResponse(boolean value) {
        this.storeExecuteResponse = value;
    }

    public boolean isSetStoreExecuteResponse() {
        return (this.storeExecuteResponse!= null);
    }

    public void unsetStoreExecuteResponse() {
        this.storeExecuteResponse = null;
    }

    /**
     * Gets the value of the lineage property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isLineage() {
        if (lineage == null) {
            return false;
        } else {
            return lineage;
        }
    }

    /**
     * Sets the value of the lineage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLineage(boolean value) {
        this.lineage = value;
    }

    public boolean isSetLineage() {
        return (this.lineage!= null);
    }

    public void unsetLineage() {
        this.lineage = null;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isStatus() {
        if (status == null) {
            return false;
        } else {
            return status;
        }
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setStatus(boolean value) {
        this.status = value;
    }

    public boolean isSetStatus() {
        return (this.status!= null);
    }

    public void unsetStatus() {
        this.status = null;
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
        {
            List<DocumentOutputDefinitionType> theOutput;
            theOutput = (this.isSetOutput()?this.getOutput():null);
            strategy.appendField(locator, this, "output", buffer, theOutput);
        }
        {
            boolean theStoreExecuteResponse;
            theStoreExecuteResponse = (this.isSetStoreExecuteResponse()?this.isStoreExecuteResponse():false);
            strategy.appendField(locator, this, "storeExecuteResponse", buffer, theStoreExecuteResponse);
        }
        {
            boolean theLineage;
            theLineage = (this.isSetLineage()?this.isLineage():false);
            strategy.appendField(locator, this, "lineage", buffer, theLineage);
        }
        {
            boolean theStatus;
            theStatus = (this.isSetStatus()?this.isStatus():false);
            strategy.appendField(locator, this, "status", buffer, theStatus);
        }
        return buffer;
    }

    public void setOutput(List<DocumentOutputDefinitionType> value) {
        this.output = null;
        List<DocumentOutputDefinitionType> draftl = this.getOutput();
        draftl.addAll(value);
    }

}