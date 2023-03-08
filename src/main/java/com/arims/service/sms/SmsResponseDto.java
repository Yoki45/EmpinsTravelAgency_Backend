package com.arims.service.sms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SmsResponseDto {
	@JsonProperty("SMSMessageData")
	private SMSMessageData smsmessageData;

}
