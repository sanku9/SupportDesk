package com.supportdesk.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tbl_user")
@XmlRootElement
public class UserEntity extends BaseEntity implements Comparable, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    private Long userId;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "alstom_id")
    private String alstomId;
    @Column(name = "id")
    private String d;
    @Column(name = "contact")
    private String contact;
    @Column(name = "default_queue_id")
    private Integer defaultQueueId;
    @Column(name = "is_admin")
    private Boolean isAdmin;
    @Column(name = "is_active")
    private Boolean isActive;
    @Transient
    private String fullName;

    public UserEntity() {
    }

    public UserEntity(Long userId) {
        this.userId = userId;
    }

    public UserEntity(Long userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlstomId() {
		return alstomId;
	}

	public void setAlstomId(String alstomId) {
		this.alstomId = alstomId;
	}

	public String getd() {
		return d;
	}

	public void setd(String d) {
		this.d = d;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Integer getDefaultQueueId() {
        return defaultQueueId;
    }

    public void setDefaultQueueId(Integer defaultQueueId) {
        this.defaultQueueId = defaultQueueId;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.supportdesk.entity.User[ userId=" + userId + " ]";
    }
    
    public int compareTo(Object o) {
		 if (!(o instanceof UserEntity))
		      throw new ClassCastException("A UserEntity object expected.");
		    return this.getUserId().compareTo(((UserEntity) o).getUserId());  
	}	
    
}
