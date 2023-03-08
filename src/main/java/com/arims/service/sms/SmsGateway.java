package com.arims.service.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.arims.dto.ApiResponseStatus;
import com.arims.model.MessageGroup;
import com.arims.model.SmsMessage;
import com.arims.repository.MessageGroupRepo;
import com.arims.repository.SmsMessageRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
@Service
public class SmsGateway {
	private final Logger logger = LoggerFactory.getLogger(SmsGateway.class);
	@Value("${africas.username}")
	private String username;
	@Value("${africas.apikey}")
	private String apiKey;
	private String environment;
	@Autowired
	private SmsMessageRepo messageRepo;
	
	@Autowired
	private MessageGroupRepo messageGroupRepo;


	public List<SmsMessage> sendAfricasTalkingSms(MessageGroup messageGroup, List<MessageDto> messageDtos) {
		return messageDtos.stream().flatMap(messageDto -> {
			Optional<SmsResponseDto> response = Optional
					.ofNullable(this.sendSMS(messageDto));
			List<SmsMessage> messages = new ArrayList<>();
			if (response.isPresent()) {
				messageGroup.setApiResponseStatusCode(201);
				messageGroup.setApiResponseStatus(ApiResponseStatus.SUCCESS);

				messages = response.get().getSmsmessageData().getRecipients().stream()

						.map(message -> {
							SmsMessage messageRecipient = new SmsMessage();
							messageRecipient.setSmsCount(messageRecipient.getSmsCount() + 1);
							messageRecipient.setMessageGroup(messageGroup);
							messageRecipient.setText(messageDto.getMessage());
							messageRecipient.setMessageGroup(messageGroup);
							messageRecipient.setText(messageDto.getMessage());
							messageRecipient.setProcessed(true);
							messageRecipient.setDeliveryStatus(message.getStatus());
							messageRecipient.setDeliveryStatusCode(message.getStatusCode());
						
							Optional<String> messageId = Optional
									.ofNullable(message.getMessageId());

							if (messageId.isPresent() && !messageId.get().isEmpty()
									&& message.getStatusCode() < 400) {
								messageRecipient.setMessageId(messageId.get());
								double cost = Double.valueOf(message.getCost().substring(3));
								messageRecipient.setActualCost(cost);
							}
							
							

							return messageRepo.save(messageRecipient);
						}).collect(Collectors.toList());

				messageGroupRepo.save(messageGroup);

			} else {
				messageGroup.setApiResponseStatusCode(500);
				messageGroup.setApiResponseStatus(ApiResponseStatus.FAILED);
				messageGroupRepo.save(messageGroup);

			}

			return messages.stream();

		}).collect(Collectors.toList());

	}

	
	public String formatAfricasPhoneNumbers(String number) {
		number = number.replaceAll("\\s+", "");
		if (number.contains("\\+") && number.length() == 13) {
			return number;
		} else if (number.length() == 12) {
			return "+" + number;
		} else if (number.length() == 10) {
			return "+254" + number.substring(1);
		} else {
			return number;
		}
	}



	public SmsResponseDto sendSMS(MessageDto messageDto) {
		messageDto.setUsername(this.username);

		URL url;
		try {
			url = new URL(getSmsUrl());

			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("Accept", "application/json");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setRequestProperty("apiKey", getApiKey());

			// encode payload
			Map<String, String> params = new HashMap<>();
			params.put("username", messageDto.getUsername());
			params.put("to", messageDto.getTo());
			params.put("message", messageDto.getMessage());
			params.put("from", messageDto.getFrom());

			String encodedPayload = params.keySet()
					.stream()
					.map(key -> key + "=" + encodeValue(params.get(key)).replaceAll("\\+", "%20"))
					.collect(Collectors.joining("&"));

			byte[] out = encodedPayload.getBytes(StandardCharsets.UTF_8);

			OutputStream stream = http.getOutputStream();
			stream.write(out);

			int responseCode = http.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_CREATED) { // 201 = success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						http.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				ObjectMapper mapper = new ObjectMapper();

				SmsResponseDto sms = mapper.readValue(response.toString(), SmsResponseDto.class);
				return sms;
			} else {

				if (messageDto.getMessageId() != null) {
					SmsMessage messageRecipient = messageRepo.findById(messageDto.getMessageId()).orElse(new SmsMessage());
					messageRecipient.setRetries(messageRecipient.getRetries() + 1);
					messageRecipient.setText(messageDto.getMessage());
					messageRecipient.setMobileNumber(messageDto.getTo());
					messageRecipient.setDeliveryStatus(ApiResponseStatus.FAILED.toString());
					messageRecipient.setProcessed(true);
					messageRecipient.setDeliveryInformation(ApiResponseStatus.FAILED.toString());
					RecipientsDto messageResponse = new RecipientsDto();
					messageResponse.setNumber(messageDto.getTo());
					messageResponse.setStatus(messageRecipient.getDeliveryStatus());
					messageResponse.setStatusCode(500);
				
					messageRepo.save(messageRecipient);
				}

				logger.info("Failed send message from " + messageDto.getUsername() + " to " + messageDto.getTo());

			}
			http.disconnect();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String encodeValue(String value) {
		try {
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			return "";
		}
	}

	private String getSmsUrl() {
		return (getEnvironment() == "sandbox") ? "https://api.sandbox.africastalking.com/version1/messaging"
				: "https://api.africastalking.com/version1/messaging";
	}

}
