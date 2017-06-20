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
@Table(name = "tbl_work_order")
@XmlRootElement
public class WorkOrderEntity extends BaseEntity implements Comparable, Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "work_order_id")
    private String workOrderId;
    @Column(name = "company")
    private String company;
    @Column(name = "status")
    private String status;
    @Column(name = "summary")
    private String summary;
    @Column(name = "priority")
    private String priority;
    @Column(name = "submit_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submitDate;
    @Column(name = "completed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date completedDate;
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Column(name = "alstom_business_entity")
    private String alstomBusinessEntity;
    @Column(name = "assignee_support_company")
    private String assigneeSupportCompany;
    @Column(name = "assignee_support_organization")
    private String assigneeSupportOrganization;
    @Column(name = "associated_request_id")
    private String associatedRequestId;
    @Column(name = "request_id")
    private String requestId;
    @Column(name = "detailed_description")
    private String detailedDescription;
    @Column(name = "next_target_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nextTargetDate;
    @Column(name = "reassign_counter")
    private Integer reassignCounter;
    @Column(name = "assignee")
    private String assignee;
    @Column(name = "slm_status")
    private String slmStatus;
    @Column(name = "service_level")
    private String serviceLevel;
    @Column(name = "cust_first_name")
    private String custFirstName;
    @Column(name = "cust_last_name")
    private String custLastName;
    @Column(name = "breachreason")
    private String breachreason;
    @Column(name = "wo_comment")
    private String woComment;
    @Column(name = "ticket_type")
    private String ticketType;
    @JoinColumn(name = "application_id", referencedColumnName = "appl_id")
    @ManyToOne
    private ApplicationEntity applicationId;
    @JoinColumn(name = "assignee_support_group_name", referencedColumnName = "queue_id")
    @ManyToOne
    private QueueEntity assigneeSupportGroupName;
    @OneToMany(mappedBy = "entityId", fetch=FetchType.EAGER)
    @Fetch(value=FetchMode.SELECT)
    private Collection<CommentEntity> commentEntityCollection;
    
    public WorkOrderEntity() {
    }

    public WorkOrderEntity(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
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

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getAlstomBusinessEntity() {
        return alstomBusinessEntity;
    }

    public void setAlstomBusinessEntity(String alstomBusinessEntity) {
        this.alstomBusinessEntity = alstomBusinessEntity;
    }

    public String getAssigneeSupportCompany() {
        return assigneeSupportCompany;
    }

    public void setAssigneeSupportCompany(String assigneeSupportCompany) {
        this.assigneeSupportCompany = assigneeSupportCompany;
    }

    public String getAssigneeSupportOrganization() {
        return assigneeSupportOrganization;
    }

    public void setAssigneeSupportOrganization(String assigneeSupportOrganization) {
        this.assigneeSupportOrganization = assigneeSupportOrganization;
    }

    public String getAssociatedRequestId() {
        return associatedRequestId;
    }

    public void setAssociatedRequestId(String associatedRequestId) {
        this.associatedRequestId = associatedRequestId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public Date getNextTargetDate() {
        return nextTargetDate;
    }

    public void setNextTargetDate(Date nextTargetDate) {
        this.nextTargetDate = nextTargetDate;
    }

    public Integer getReassignCounter() {
        return reassignCounter;
    }

    public void setReassignCounter(Integer reassignCounter) {
        this.reassignCounter = reassignCounter;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getSlmStatus() {
        return slmStatus;
    }

    public void setSlmStatus(String slmStatus) {
        this.slmStatus = slmStatus;
    }

    public String getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(String serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public String getCustFirstName() {
        return custFirstName;
    }

    public void setCustFirstName(String custFirstName) {
        this.custFirstName = custFirstName;
    }

    public String getCustLastName() {
        return custLastName;
    }

    public void setCustLastName(String custLastName) {
        this.custLastName = custLastName;
    }

    public String getBreachreason() {
        return breachreason;
    }

    public void setBreachreason(String breachreason) {
        this.breachreason = breachreason;
    }

    public String getWoComment() {
        return woComment;
    }

    public void setWoComment(String woComment) {
        this.woComment = woComment;
    }

    public ApplicationEntity getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(ApplicationEntity applicationId) {
        this.applicationId = applicationId;
    }

    public QueueEntity getAssigneeSupportGroupName() {
        return assigneeSupportGroupName;
    }

    public void setAssigneeSupportGroupName(QueueEntity assigneeSupportGroupName) {
        this.assigneeSupportGroupName = assigneeSupportGroupName;
    }

    public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
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
        hash += (workOrderId != null ? workOrderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkOrderEntity)) {
            return false;
        }
        WorkOrderEntity other = (WorkOrderEntity) object;
        if ((this.workOrderId == null && other.workOrderId != null) || (this.workOrderId != null && !this.workOrderId.equals(other.workOrderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.supportdesk.entity.WorkOrderEntity[ workOrderId=" + workOrderId + " ]";
    }
    
    public int compareTo(Object o) {
		 if (!(o instanceof WorkOrderEntity))
		      throw new ClassCastException("A WorkOrderEntity object expected.");
		    return this.getWorkOrderId().compareTo(((WorkOrderEntity) o).getWorkOrderId());  
	}

}
