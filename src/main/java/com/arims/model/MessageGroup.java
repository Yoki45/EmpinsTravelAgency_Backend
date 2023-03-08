package com.arims.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import com.arims.dto.ApiResponseStatus;
import com.arims.util.Utils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author chando
 */
@Entity
@Table(name = "message_group")
@Data
public class MessageGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "name", nullable = false)
    @NotNull()
    private String name;
    @Column(name = "api_response_status")
    @Enumerated(EnumType.STRING)
    private ApiResponseStatus apiResponseStatus;
    @JoinColumn(name = "from_user", referencedColumnName = "id")
    @ManyToOne
    private User user;
    @Column(name = "api_response_status_code")
    private Integer apiResponseStatusCode;

    private Integer code;

    private String title;

    @Column(name = "sent", columnDefinition = "integer default 0")
    @NotNull()
    private Integer sent = 0;
    @Column(name = "delivered", columnDefinition = "integer default 0")
    @NotNull()
    private Integer delivered = 0;
    @Column(name = "sms_count", columnDefinition = "integer default 0")
    @NotNull()
    private Integer smsCount = 0;
    @Column(name = "sms_size", columnDefinition = "integer default 0")
    @NotNull()
    private Integer smsSize = 0;
    @Column(name = "deleted")
    @NotNull()
    private Boolean deleted = false;
    @Column(name = "sms_actual_cost")
    private Double smsActualCost = 0.0;

    @Column(name = "additional_info", columnDefinition = "TEXT")
    private String additionalInfo;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull()
    private Date creationDate = Utils.getCurrentDate();

    @JoinColumn(name = "category", referencedColumnName = "category_id", nullable = false)
    @ManyToOne
    @NotNull(message = "message category cannot be null")
    private MessageCategory category;

    @Column(name = "failed", nullable = false, columnDefinition = "integer default 0")
    @NotNull()
    private Integer failed = 0;

    public MessageGroup() {
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupId != null ? groupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MessageGroup)) {
            return false;
        }
        MessageGroup other = (MessageGroup) object;
        return (this.groupId != null || other.groupId == null)
                && (this.groupId == null || this.groupId.equals(other.groupId));
    }

    @Override
    public String toString() {
        return "com.zeraki.bulksmsapi.entities.Message[ messageid=" + groupId + " ]";
    }

}