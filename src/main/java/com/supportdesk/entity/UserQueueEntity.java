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
@Table(name = "tbl_user_queue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserQueueEntity.findAll", query = "SELECT u FROM UserQueueEntity u"),
    @NamedQuery(name = "UserQueueEntity.findByUserQueueId", query = "SELECT u FROM UserQueueEntity u WHERE u.userQueueId = :userQueueId"),
    @NamedQuery(name = "UserQueueEntity.findByUserId", query = "SELECT u FROM UserQueueEntity u WHERE u.userId = :userId"),
    @NamedQuery(name = "UserQueueEntity.findByQueueId", query = "SELECT u FROM UserQueueEntity u WHERE u.queueId = :queueId")})
public class UserQueueEntity implements Comparable, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_queue_id")
    private Long userQueueId;
    @JoinColumn(name = "queue_id", referencedColumnName = "queue_id")
    @ManyToOne(optional = false)
    private QueueEntity queueId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private UserEntity userId;

    public UserQueueEntity() {
    }

    public UserQueueEntity(Long userQueueId) {
        this.userQueueId = userQueueId;
    }

    public Long getUserQueueId() {
        return userQueueId;
    }

    public void setUserQueueId(Long userQueueId) {
        this.userQueueId = userQueueId;
    }

    public QueueEntity getQueueId() {
        return queueId;
    }

    public void setQueueId(QueueEntity queueId) {
        this.queueId = queueId;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userQueueId != null ? userQueueId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserQueueEntity)) {
            return false;
        }
        UserQueueEntity other = (UserQueueEntity) object;
        if ((this.userQueueId == null && other.userQueueId != null) || (this.userQueueId != null && !this.userQueueId.equals(other.userQueueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.supportdesk.entity.UserQueueEntity[ userQueueId=" + userQueueId + " ]";
    }
    
    public int compareTo(Object o) {
		if (!(o instanceof UserQueueEntity))
		      throw new ClassCastException("A UserQueueEntity object expected.");
		    return this.getUserQueueId().compareTo(((UserQueueEntity) o).getUserQueueId());
	}
    
}
