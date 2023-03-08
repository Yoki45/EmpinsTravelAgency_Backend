package com.arims.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arims.model.SmsMessage;

public interface SmsMessageRepo extends JpaRepository<SmsMessage,Integer> {
	
}
