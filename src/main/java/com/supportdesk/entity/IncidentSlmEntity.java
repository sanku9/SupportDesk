package com.supportdesk.entity;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author i615735
 */
@Entity
@Table(name = "tbl_incident_slm")
@XmlRootElement

public class IncidentSlmEntity extends BaseEntity implements Comparable, Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "incident_id")
    private String incidentId;
    @Column(name = "assigned_support_company")
    private String assignedSupportCompany;
    @Column(name = "assigned_support_organization")
    private String assignedSupportOrganization;
    @Column(name = "closed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closedDate;
    @Column(name = "effort_duration_hours")
    private Integer effortDurationHours;
    @Column(name = "escalated")
    private String escalated;
    @Column(name = "sla_breach_flag")
    private String slaBreachFlag = "No";
    @Column(name = "group_transfers")
    private Integer groupTransfers;
    @Column(name = "individual_transfers")
    private Integer individualTransfers;
    @Column(name = "last_date_duration_calculated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastDateDurationCalculated;
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Column(name = "last_resolved_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastResolvedDate;
    @Column(name = "measurement_status_response")
    private String measurementStatusResponse;
    @Column(name = "measurement_status_resolve")
    private String measurementStatusResolve;
    @Column(name = "ola_hold")
    private String olaHold;
    @Column(name = "overall_start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date overallStartDate;
    @Column(name = "overall_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date overallEndDate;
    @Column(name = "owner_support_company")
    private String ownerSupportCompany;
    @Column(name = "owner_support_organization")
    private String ownerSupportOrganization;
    @Column(name = "priority")
    private String priority;
    @Column(name = "reopened_count")
    private Integer reOpenedCount;
    @Column(name = "reopened_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reopenedDate;
    @Column(name = "reason_code")
    private String reasonCode;
    @Column(name = "reason_description")
    private String reasonDescription;
    @Column(name = "reported_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportedDate;
    @Column(name = "resolution")
    private String resolution;
    @Column(name = "responded_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date respondedDate;
    @Column(name = "sla_hold")
    private String slaHold;
    @Column(name = "slm_priority")
    private String slmPriority;
    @Column(name = "slm_response_status")
    private String slmResponseStatus;
    @Column(name = "slm_resolve_status")
    private String slmResolveStatus;
    @Column(name = "service_request_id")
    private String serviceRequestId;
    @Column(name = "incident_status")
    private String incidentStatus;
    @Column(name = "submit_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submitDate;
    @Column(name = "ticket_type")
    private String ticketType;
    @Column(name = "response_effort_in_sec")
    private Integer responseEffortInSec;
    @Column(name = "resolve_effort_in_sec")
    private Integer resolveEffortInSec;
    @Column(name = "assignee_name")
    private String assigneeName;
    @Column(name = "cust_first_name")
    private String custFirstName;
    @Column(name = "incident_summary")
    private String incidentSummary;
    @Column(name = "response_sla_due_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date responseSlaDueDate;
    @Column(name = "resolve_sla_due_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date resolveSlaDueDate;
    @Column(name = "sla_breach_reason")
    private String slaBreachReason;
    @Column(name = "comments")
    private String comments;
    @JoinColumn(name = "application_id", referencedColumnName = "appl_id")
    @ManyToOne(optional = false)
    private ApplicationEntity applicationId;
    @JoinColumn(name = "assigned_group", referencedColumnName = "queue_id")
    @ManyToOne(optional = false)
    private QueueEntity assignedGroup;
    @JoinColumn(name = "owner_group", referencedColumnName = "queue_id")
    @ManyToOne(optional = false)
    private QueueEntity ownerGroup;
    @OneToMany(mappedBy = "entityId", fetch=FetchType.EAGER)
    @Fetch(value=FetchMode.SELECT)
    private Collection<CommentEntity> commentEntityCollection;
    
    @Transient
    private long ageing;
    
    public long getAgeing() {
    	Date cal = Calendar.getInstance().getTime();
    	LocalDate today = LocalDate.now();
    	LocalDate dateAfter = getSubmitDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();;
    	ageing = Duration.between(dateAfter.atTime(0, 0),today.atTime(0, 0)).toDays();
    	System.out.println(ageing);
		return ageing;
	}

	public void setAgeing(long ageing) {
		this.ageing = ageing;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public IncidentSlmEntity() {
    }

    public IncidentSlmEntity(String incidentId) {
        this.incidentId = incidentId;
    }

    public String getAssignedSupportCompany() {
        return assignedSupportCompany;
    }

    public void setAssignedSupportCompany(String assignedSupportCompany) {
        this.assignedSupportCompany = assignedSupportCompany;
    }

    public String getAssignedSupportOrganization() {
        return assignedSupportOrganization;
    }

    public void setAssignedSupportOrganization(String assignedSupportOrganization) {
        this.assignedSupportOrganization = assignedSupportOrganization;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public Integer getEffortDurationHours() {
        return effortDurationHours;
    }

    public void setEffortDurationHours(Integer effortDurationHours) {
        this.effortDurationHours = effortDurationHours;
    }

    public String getEscalated() {
        return escalated;
    }

    public void setEscalated(String escalated) {
        this.escalated = escalated;
    }  

    public String getSlaBreachFlag() {
		return slaBreachFlag;
	}

	public void setSlaBreachFlag(String slaBreachFlag) {
		this.slaBreachFlag = slaBreachFlag;
	}

	public String getSlaBreachReason() {
		return slaBreachReason;
	}

	public void setSlaBreachReason(String slaBreachReason) {
		this.slaBreachReason = slaBreachReason;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getGroupTransfers() {
        return groupTransfers;
    }

    public void setGroupTransfers(Integer groupTransfers) {
        this.groupTransfers = groupTransfers;
    }

    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

    public Integer getIndividualTransfers() {
        return individualTransfers;
    }

    public void setIndividualTransfers(Integer individualTransfers) {
        this.individualTransfers = individualTransfers;
    }

    public Date getLastDateDurationCalculated() {
        return lastDateDurationCalculated;
    }

    public void setLastDateDurationCalculated(Date lastDateDurationCalculated) {
        this.lastDateDurationCalculated = lastDateDurationCalculated;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getLastResolvedDate() {
        return lastResolvedDate;
    }

    public void setLastResolvedDate(Date lastResolvedDate) {
        this.lastResolvedDate = lastResolvedDate;
    }

    public String getMeasurementStatusResponse() {
        return measurementStatusResponse;
    }

    public void setMeasurementStatusResponse(String measurementStatusResponse) {
        this.measurementStatusResponse = measurementStatusResponse;
    }

    public String getMeasurementStatusResolve() {
        return measurementStatusResolve;
    }

    public void setMeasurementStatusResolve(String measurementStatusResolve) {
        this.measurementStatusResolve = measurementStatusResolve;
    }

    public String getOlaHold() {
        return olaHold;
    }

    public void setOlaHold(String olaHold) {
        this.olaHold = olaHold;
    }

    public Date getOverallStartDate() {
        return overallStartDate;
    }

    public void setOverallStartDate(Date overallStartDate) {
        this.overallStartDate = overallStartDate;
    }

    public Date getOverallEndDate() {
        return overallEndDate;
    }

    public void setOverallEndDate(Date overallEndDate) {
        this.overallEndDate = overallEndDate;
    }

    public String getOwnerSupportCompany() {
        return ownerSupportCompany;
    }

    public void setOwnerSupportCompany(String ownerSupportCompany) {
        this.ownerSupportCompany = ownerSupportCompany;
    }

    public String getOwnerSupportOrganization() {
        return ownerSupportOrganization;
    }

    public void setOwnerSupportOrganization(String ownerSupportOrganization) {
        this.ownerSupportOrganization = ownerSupportOrganization;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Integer getReOpenedCount() {
		return reOpenedCount;
	}

	public void setReOpenedCount(Integer reOpenedCount) {
		this.reOpenedCount = reOpenedCount;
	}

	public Date getReopenedDate() {
        return reopenedDate;
    }

    public void setReopenedDate(Date reopenedDate) {
        this.reopenedDate = reopenedDate;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonDescription() {
        return reasonDescription;
    }

    public void setReasonDescription(String reasonDescription) {
        this.reasonDescription = reasonDescription;
    }

    public Date getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(Date reportedDate) {
        this.reportedDate = reportedDate;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Date getRespondedDate() {
        return respondedDate;
    }

    public void setRespondedDate(Date respondedDate) {
        this.respondedDate = respondedDate;
    }

    public String getSlaHold() {
        return slaHold;
    }

    public void setSlaHold(String slaHold) {
        this.slaHold = slaHold;
    }

    public String getSlmPriority() {
        return slmPriority;
    }

    public void setSlmPriority(String slmPriority) {
        this.slmPriority = slmPriority;
    }

    public String getSlmResponseStatus() {
        return slmResponseStatus;
    }

    public void setSlmResponseStatus(String slmResponseStatus) {
        this.slmResponseStatus = slmResponseStatus;
    }

    public String getSlmResolveStatus() {
        return slmResolveStatus;
    }

    public void setSlmResolveStatus(String slmResolveStatus) {
        this.slmResolveStatus = slmResolveStatus;
    }

    public String getServiceRequestId() {
        return serviceRequestId;
    }

    public void setServiceRequestId(String serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
    }

    public String getIncidentStatus() {
        return incidentStatus;
    }

    public void setIncidentStatus(String incidentStatus) {
        this.incidentStatus = incidentStatus;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public Integer getResponseEffortInSec() {
        return responseEffortInSec;
    }

    public void setResponseEffortInSec(Integer responseEffortInSec) {
        this.responseEffortInSec = responseEffortInSec;
    }

    public Integer getResolveEffortInSec() {
        return resolveEffortInSec;
    }

    public void setResolveEffortInSec(Integer resolveEffortInSec) {
        this.resolveEffortInSec = resolveEffortInSec;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getCustFirstName() {
        return custFirstName;
    }

    public void setCustFirstName(String custFirstName) {
        this.custFirstName = custFirstName;
    }

    public String getIncidentSummary() {
        return incidentSummary;
    }

    public void setIncidentSummary(String incidentSummary) {
        this.incidentSummary = incidentSummary;
    }

    public Date getResponseSlaDueDate() {
        return responseSlaDueDate;
    }

    public void setResponseSlaDueDate(Date responseSlaDueDate) {
        this.responseSlaDueDate = responseSlaDueDate;
    }

    public Date getResolveSlaDueDate() {
        return resolveSlaDueDate;
    }

    public void setResolveSlaDueDate(Date resolveSlaDueDate) {
        this.resolveSlaDueDate = resolveSlaDueDate;
    }

    public ApplicationEntity getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(ApplicationEntity applicationId) {
        this.applicationId = applicationId;
    }

    public QueueEntity getAssignedGroup() {
        return assignedGroup;
    }

    public void setAssignedGroup(QueueEntity assignedGroup) {
        this.assignedGroup = assignedGroup;
    }

    public QueueEntity getOwnerGroup() {
        return ownerGroup;
    }

    public void setOwnerGroup(QueueEntity ownerGroup) {
        this.ownerGroup = ownerGroup;
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
        hash += (incidentId != null ? incidentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IncidentSlmEntity)) {
            return false;
        }
        IncidentSlmEntity other = (IncidentSlmEntity) object;
        if ((this.incidentId == null && other.incidentId != null) || (this.incidentId != null && !this.incidentId.equals(other.incidentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.supportdesk.common.IncidentSlm[ incidentId=" + incidentId + " ]";
    }
    
    public int compareTo(Object o) {
		 if (!(o instanceof IncidentSlmEntity))
		      throw new ClassCastException("A IncidentSlmEntity object expected.");
		    return this.getIncidentId().compareTo(((IncidentSlmEntity) o).getIncidentId());  
	}
    
}

