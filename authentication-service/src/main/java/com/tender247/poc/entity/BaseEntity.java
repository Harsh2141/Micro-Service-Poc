package com.tender247.poc.entity;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.util.ObjectUtils;

import com.tender247.poc.util.EncryptDecryptUtil;

import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// @CreationTimestamp
	private LocalDateTime createdDate;

	// @UpdateTimestamp
	private LocalDateTime modifiedDate;

	public void setId(Object id) {
		if (ObjectUtils.isEmpty(id))
			this.id = null;
		else if (id instanceof String)
			this.id = Integer.valueOf(EncryptDecryptUtil.decrypt(String.valueOf(id)));
		else
			this.id = Integer.valueOf(String.valueOf(id));
	}

	@PrePersist
	private void prePersistData() {
		createdDate = LocalDateTime.now();
	}

	@PreUpdate
	private void preUpdateData() {
		modifiedDate = LocalDateTime.now();
	}

}
