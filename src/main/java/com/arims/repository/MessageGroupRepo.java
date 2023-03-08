package com.arims.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arims.model.MessageGroup;

public interface MessageGroupRepo  extends JpaRepository<MessageGroup,Integer>{
	
}
