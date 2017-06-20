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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author i615735
 */
@Entity
@Table(name = "tbl_queue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QueueEntity.findAll", query = "SELECT q FROM QueueEntity q"),
    @NamedQuery(name = "QueueEntity.findByQueueId", query = "SELECT q FROM QueueEntity q WHERE q.queueId = :queueId"),
    @NamedQuery(name = "QueueEntity.findByQueueName", query = "SELECT q FROM QueueEntity q WHERE q.queueName = :queueName")})
public class QueueEntity extends BaseEntity implements Comparable,Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "queue_id")
    private Long queueId;
    @Column(name = "queue_name")
    private String queueName;

    public QueueEntity() {
    }

    public QueueEntity(Long queueId) {
        this.queueId = queueId;
    }

    public Long getQueueId() {
        return queueId;
    }

    public void setQueueId(Long queueId) {
        this.queueId = queueId;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (queueId != null ? queueId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QueueEntity)) {
            return false;
        }
        QueueEntity other = (QueueEntity) object;
        if ((this.queueId == null && other.queueId != null) || (this.queueId != null && !this.queueId.equals(other.queueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.supportdesk.entity.QueueEntity[ queueId=" + queueId + " ]";
    }
    
    public int compareTo(Object o) {
		 if (!(o instanceof QueueEntity))
		      throw new ClassCastException("A QueueEntity object expected.");
		    return this.getQueueId().compareTo(((QueueEntity) o).getQueueId());  
	}	
    
}
