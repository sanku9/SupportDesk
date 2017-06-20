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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author i615735
 */
@Entity
@Table(name = "tbl_application")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ApplicationEntity.findAll", query = "SELECT a FROM ApplicationEntity a"),
    @NamedQuery(name = "ApplicationEntity.findByApplId", query = "SELECT a FROM ApplicationEntity a WHERE a.applId = :applId"),
    @NamedQuery(name = "ApplicationEntity.findByApplName", query = "SELECT a FROM ApplicationEntity a WHERE a.applName = :applName"),
    @NamedQuery(name = "ApplicationEntity.findByApplType", query = "SELECT a FROM ApplicationEntity a WHERE a.applType = :applType"),
    @NamedQuery(name = "ApplicationEntity.findByApplClassType", query = "SELECT a FROM ApplicationEntity a WHERE a.applClassType = :applClassType")})
public class ApplicationEntity implements Comparable, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "appl_id")
    private Long applId;
    @Column(name = "appl_name")
    private String applName;
    @Column(name = "appl_type")
    private String applType;
    @JoinColumn(name = "appl_class_type", referencedColumnName = "class_id")
    @ManyToOne
    private ClassTypeEntity applClassType;
    /*@OneToMany(mappedBy = "applicationId", fetch = FetchType.LAZY)
    private Collection<WorkOrderEntity> workOrderEntityCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "applicationId", fetch = FetchType.LAZY)
    private Collection<IncidentEntity> incidentEntityCollection;*/

    public ApplicationEntity() {
    }

    public ApplicationEntity(Long applId) {
        this.applId = applId;
    }

    public Long getApplId() {
        return applId;
    }

    public void setApplId(Long applId) {
        this.applId = applId;
    }

    public String getApplName() {
        return applName.replace("&amp;", "&");
    }

    public void setApplName(String applName) {
        this.applName = applName;
    }

    public String getApplType() {
        return applType;
    }

    public void setApplType(String applType) {
        this.applType = applType;
    }

    public ClassTypeEntity getApplClassType() {
        return applClassType;
    }

    public void setApplClassType(ClassTypeEntity applClassType) {
        this.applClassType = applClassType;
    }

   /* @XmlTransient
    public Collection<WorkOrderEntity> getWorkOrderEntityCollection() {
        return workOrderEntityCollection;
    }

    public void setWorkOrderEntityCollection(Collection<WorkOrderEntity> workOrderEntityCollection) {
        this.workOrderEntityCollection = workOrderEntityCollection;
    }

    @XmlTransient
    public Collection<IncidentEntity> getIncidentEntityCollection() {
        return incidentEntityCollection;
    }

    public void setIncidentEntityCollection(Collection<IncidentEntity> incidentEntityCollection) {
        this.incidentEntityCollection = incidentEntityCollection;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (applId != null ? applId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ApplicationEntity)) {
            return false;
        }
        ApplicationEntity other = (ApplicationEntity) object;
        if ((this.applId == null && other.applId != null) || (this.applId != null && !this.applId.equals(other.applId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.supportdesk.entity.ApplicationEntity[ applId=" + applId + " ]";
    }
    
    public int compareTo(Object o) {
		if (!(o instanceof ApplicationEntity))
		      throw new ClassCastException("A ApplicationEntity object expected.");
		    return this.getApplId().compareTo(((ApplicationEntity) o).getApplId());
	}
}
