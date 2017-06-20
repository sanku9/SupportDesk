package com.supportdesk.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "tbl_change_request")
@XmlRootElement
public class ChangeRequestEntity extends BaseEntity implements Comparable, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "change_id")
    private String changeId;
    @Column(name = "risk_level")
    private String riskLevel;
    @Column(name = "status")
    private String status;
    @Column(name = "summary")
    private String summary;
    @Column(name = "note")
    private String note;
    @Column(name = "priority")
    private String priority;
    @Column(name = "scheduled_start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledStartDate;
    @Column(name = "actual_start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualStartDate;
    @Column(name = "scheduled_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledEndDate;
    @Column(name = "actual_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualEndDate;
    @Column(name = "requested_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestedEndDate;
    @Column(name = "submit_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submitDate;
    @Column(name = "completed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date completedDate;
    @Column(name = "target_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date targetDate;
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Column(name = "change_coordinator")
    private String changeCoordinator;
    @Column(name = "change_manager")
    private String changeManager;
    @Column(name = "manager_group")
    private String managerGroup;
    @Column(name = "request_id")
    private String requestId;
    @Column(name = "total_time_spent")
    private Integer totalTimeSpent;
    @Column(name = "service_level")
    private String serviceLevel;
    @JoinColumn(name = "application_id", referencedColumnName = "appl_id")
    @ManyToOne
    private ApplicationEntity applicationId;
    @JoinColumn(name = "change_coordinator_group", referencedColumnName = "queue_id")
    @ManyToOne
    private QueueEntity changeCoordinatorGroup;
    @OneToMany(mappedBy = "entityId", fetch=FetchType.EAGER)
    @Fetch(value=FetchMode.SELECT)
    private Collection<CommentEntity> commentEntityCollection;
    
    public ChangeRequestEntity() {
    }

    public ChangeRequestEntity(String changeId) {
        this.changeId = changeId;
    }

    public String getChangeId() {
        return changeId;
    }

    public void setChangeId(String changeId) {
        this.changeId = changeId;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getScheduledStartDate() {
        return scheduledStartDate;
    }

    public void setScheduledStartDate(Date scheduledStartDate) {
        this.scheduledStartDate = scheduledStartDate;
    }

    public Date getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public Date getScheduledEndDate() {
        return scheduledEndDate;
    }

    public void setScheduledEndDate(Date scheduledEndDate) {
        this.scheduledEndDate = scheduledEndDate;
    }

    public Date getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public Date getRequestedEndDate() {
        return requestedEndDate;
    }

    public void setRequestedEndDate(Date requestedEndDate) {
        this.requestedEndDate = requestedEndDate;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getChangeCoordinator() {
        return changeCoordinator;
    }

    public void setChangeCoordinator(String changeCoordinator) {
        this.changeCoordinator = changeCoordinator;
    }

    public String getChangeManager() {
        return changeManager;
    }

    public void setChangeManager(String changeManager) {
        this.changeManager = changeManager;
    }

    public String getManagerGroup() {
        return managerGroup;
    }

    public void setManagerGroup(String managerGroup) {
        this.managerGroup = managerGroup;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getTotalTimeSpent() {
        return totalTimeSpent;
    }

    public void setTotalTimeSpent(Integer totalTimeSpent) {
        this.totalTimeSpent = totalTimeSpent;
    }

    public String getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(String serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public ApplicationEntity getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(ApplicationEntity applicationId) {
        this.applicationId = applicationId;
    }

    public QueueEntity getChangeCoordinatorGroup() {
        return changeCoordinatorGroup;
    }

    public void setChangeCoordinatorGroup(QueueEntity changeCoordinatorGroup) {
        this.changeCoordinatorGroup = changeCoordinatorGroup;
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
        hash += (changeId != null ? changeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChangeRequestEntity)) {
            return false;
        }
        ChangeRequestEntity other = (ChangeRequestEntity) object;
        if ((this.changeId == null && other.changeId != null) || (this.changeId != null && !this.changeId.equals(other.changeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.supportdesk.entity.ChangeRequestEntity[ changeId=" + changeId + " ]";
    }
    
    public int compareTo(Object o) {
		 if (!(o instanceof ChangeRequestEntity))
		      throw new ClassCastException("A ChangeRequestEntity object expected.");
		    return this.getChangeId().compareTo(((ChangeRequestEntity) o).getChangeId());  
	}
}
