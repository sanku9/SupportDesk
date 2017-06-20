/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supportdesk.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author i615735
 */
@Entity
@Table(name = "tbl_sla")
@XmlRootElement
public class SlaEntity extends BaseEntity implements Comparable, Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "priority")
    private String priority;
    @Basic(optional = false)
    @Column(name = "resolve_sla_hours")
    private double resolveSlaHours;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "response_sla_hours")
    private Double responseSlaHours;
    @Column(name = "support_hours")
    private String supportHours;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sla_id")
    private Long slaId;
    @JoinColumn(name = "class_type_id", referencedColumnName = "class_id")
    @ManyToOne
    private ClassTypeEntity classTypeId;

    public SlaEntity() {
    }

    public SlaEntity(Long slaId) {
        this.slaId = slaId;
    }

    public SlaEntity(Long slaId, String priority, double resolveSlaHours) {
        this.slaId = slaId;
        this.priority = priority;
        this.resolveSlaHours = resolveSlaHours;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public double getResolveSlaHours() {
        return resolveSlaHours;
    }

    public void setResolveSlaHours(double resolveSlaHours) {
        this.resolveSlaHours = resolveSlaHours;
    }

    public Double getResponseSlaHours() {
        return responseSlaHours;
    }

    public void setResponseSlaHours(Double responseSlaHours) {
        this.responseSlaHours = responseSlaHours;
    }

    public String getSupportHours() {
        return supportHours;
    }

    public void setSupportHours(String supportHours) {
        this.supportHours = supportHours;
    }

    public Long getSlaId() {
        return slaId;
    }

    public void setSlaId(Long slaId) {
        this.slaId = slaId;
    }

    public ClassTypeEntity getClassTypeId() {
        return classTypeId;
    }

    public void setClassTypeId(ClassTypeEntity classTypeId) {
        this.classTypeId = classTypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (slaId != null ? slaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SlaEntity)) {
            return false;
        }
        SlaEntity other = (SlaEntity) object;
        if ((this.slaId == null && other.slaId != null) || (this.slaId != null && !this.slaId.equals(other.slaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.supportdesk.entity.lSlaEntity[ slaId=" + slaId + " ]";
    }
    
    public int compareTo(Object o) {
		if (!(o instanceof SlaEntity))
		      throw new ClassCastException("A SlaEntity object expected.");
		    return this.getSlaId().compareTo(((SlaEntity) o).getSlaId());
	}
}
