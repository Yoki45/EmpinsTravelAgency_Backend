package com.arims.model;

import com.arims.util.Utils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.util.Date;

@Entity
@Table(name = "sms_message")
@Data

@JsonIgnoreProperties(ignoreUnknown = true)
public class SmsMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "message_id", unique = true)
	private String messageId;

	private int deliveryStatusCode;

	private String deliveryStatus;

	@Column(name = "actual_cost")
	private Double actualCost = 0.0;
	@Column(name = "sms_count")
	private Integer smsCount=0;

	@Lob
	@Column(name = "text")
	@NotNull()

	private String text;

	@JoinColumn(name = "to_user", referencedColumnName = "id")
    @ManyToOne
    private User user;
	private int messageErrorCode;
	private String messageErrorDescription;
	private String mobileNumber;
	private String deliveryInformation;

	private String failureReason;


	private boolean processed = false;

	@Column(name = "retries", columnDefinition = "integer default 1")
	
	private int retries;
	@Column(name = "deleted")
	@NotNull()
	
	private Boolean deleted = false;
	
	private Boolean summaryMessage=false;
	
	@NotNull()
	@Column(name = "size", columnDefinition = "integer default 1")
	private int size;
	
	private String networkCode;
	
	private String subject;
	
	
	
	@NotNull()
	private Date creationDate = Utils.getCurrentDate();
	@JoinColumn(name = "message_group", referencedColumnName = "group_id")
	@NotNull()
	@ManyToOne

	private MessageGroup messageGroup;

}
