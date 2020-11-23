package com.atm.banking.dao;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.atm.banking.utils.DateUtils;

import lombok.Data;

@Data
@MappedSuperclass
public class BaseDao {

	private long createdAt;
	
	private long updatedAt;
	
	  @PrePersist
	  protected void onCreate() {
	    createdAt =  DateUtils.getCurrentTimeInUTC();
	    updatedAt =  DateUtils.getCurrentTimeInUTC();
	  }

	  @PreUpdate
	  protected void onUpdate() {
	    updatedAt =  DateUtils.getCurrentTimeInUTC();
	  }

}
