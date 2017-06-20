/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supportdesk.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author i615735
 */
@Entity
@Table(name = "tbl_task")
@XmlRootElement
public class TaskEntity extends BaseEntity implements Comparable, Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "task_id")
    private String taskId;
    @Column(name = "task")
    private String task;
	@Column(name = "status")
    private String status;
    @Column(name = "assignee")
    private String assignee;
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    @ManyToOne
    private UserEntity createdBy;
    @JoinColumn(name = "updated_by", referencedColumnName = "user_id")
    @ManyToOne
    private UserEntity updatedBy;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "effort_hours")
    private String effortHours;
    @OneToMany(mappedBy = "entityId", fetch=FetchType.EAGER)
    @Fetch(value=FetchMode.SELECT)
    private Collection<CommentEntity> commentEntityCollection;

    public TaskEntity() {
    }

    public TaskEntity(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEffortHours() {
        return effortHours;
    }

    public void setEffortHours(String effortHours) {
        this.effortHours = effortHours;
    }

    @XmlTransient
    public Collection<CommentEntity> getCommentEntityCollection() {
        return commentEntityCollection;
    }

    public void setCommentEntityCollection(Collection<CommentEntity> entityCommentEntityCollection) {
        this.commentEntityCollection = entityCommentEntityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taskId != null ? taskId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TaskEntity)) {
            return false;
        }
        TaskEntity other = (TaskEntity) object;
        if ((this.taskId == null && other.taskId != null) || (this.taskId != null && !this.taskId.equals(other.taskId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.supportdesk.entity.TaskEntity[ taskId=" + taskId + " ]";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public UserEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserEntity updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public int compareTo(Object o) {
		if (!(o instanceof TaskEntity))
		      throw new ClassCastException("A TaskEntity object expected.");
		    return this.getTaskId().compareTo(((TaskEntity) o).getTaskId());
	}
    
}
