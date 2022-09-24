package com.tender247.poc.dto;

import java.time.LocalDateTime;

import org.springframework.util.ObjectUtils;

import com.tender247.poc.util.EncryptDecryptUtil;

import lombok.Data;

@Data
//@MappedSuperclass
public class BaseDto {

	private String id;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;

	public void setId(Object id) {
		if (ObjectUtils.isEmpty(id))
			this.id = null;
		else if (id instanceof Integer)
			this.id = EncryptDecryptUtil.encrypt(String.valueOf(id));
		else
			this.id = String.valueOf(id);
	}
}
