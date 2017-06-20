/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supportdesk.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author i615735
 */
@Entity
@Table(name = "tbl_class_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClassTypeEntity.findAll", query = "SELECT c FROM ClassTypeEntity c"),
    @NamedQuery(name = "ClassTypeEntity.findByClassId", query = "SELECT c FROM ClassTypeEntity c WHERE c.classId = :classId"),
    @NamedQuery(name = "ClassTypeEntity.findByClassType", query = "SELECT c FROM ClassTypeEntity c WHERE c.classType = :classType")})
public class ClassTypeEntity implements Comparable, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "class_id")
    private Long classId;
    @Basic(optional = false)
    @Column(name = "class_type")
    private String classType;
    @Basic(optional = true)
    @Column(name = "service_hour_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date serviceHourStart;
    @Basic(optional = true)
    @Column(name = "service_hour_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date serviceHourEnd;
   /* @OneToMany(mappedBy = "classTypeId", fetch = FetchType.LAZY)
    private Collection<SlaEntity> SlaEntityCollection;*/
    @Transient
    private String serviceHourStartAsString;
    @Transient
    private String serviceHourEndAsString;

    public ClassTypeEntity() {
    }

    public ClassTypeEntity(Long classId) {
        this.classId = classId;
    }

    public ClassTypeEntity(Long classId, String classType) {
        this.classId = classId;
        this.classType = classType;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

  /*  @XmlTransient
    public Collection<SlaEntity> getSlaEntityCollection() {
        return SlaEntityCollection;
    }

    public void setSlaEntityCollection(Collection<SlaEntity> SlaEntityCollection) {
        this.SlaEntityCollection = SlaEntityCollection;
    }*/  
   
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (classId != null ? classId.hashCode() : 0);
        return hash;
    }   

	public Date getServiceHourStart() {
		return serviceHourStart;
	}

	public void setServiceHourStart(Date serviceHourStart) {
		this.serviceHourStart = serviceHourStart;
	}
	
	public void setServiceHourStart(String serviceHourStart) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date date = sdf.parse(serviceHourStart);
		this.serviceHourStart = date;
	}

	public Date getServiceHourEnd() {
		return serviceHourEnd;
	}

	public void setServiceHourEnd(Date serviceHourEnd) {
		this.serviceHourEnd = serviceHourEnd;
	}
	
	public void setServiceHourEnd(String serviceHourEnd) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date date = sdf.parse(serviceHourEnd);
		this.serviceHourStart = date;
	}

	public String getServiceHourStartAsString() {
		if(this.serviceHourStart == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(this.serviceHourStart);
	}

	public void setServiceHourStartAsString(String serviceHourStartAsString) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(serviceHourStartAsString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.serviceHourStart = date;
	}

	public String getServiceHourEndAsString() {
		if(this.serviceHourEnd == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(this.serviceHourEnd);
	}

	public void setServiceHourEndAsString(String serviceHourEndAsString) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(serviceHourEndAsString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.serviceHourEnd = date;
	}

	@Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClassTypeEntity)) {
            return false;
        }
        ClassTypeEntity other = (ClassTypeEntity) object;
        if ((this.classId == null && other.classId != null) || (this.classId != null && !this.classId.equals(other.classId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.supportdesk.entity.ClassTypeEntity[ classId=" + classId + " ]";
    }

	public int compareTo(Object o) {
		if (!(o instanceof ClassTypeEntity))
		      throw new ClassCastException("A ClassTypeEntity object expected.");
		    return this.getClassId().compareTo(((ClassTypeEntity) o).getClassId());
	}
    
}
