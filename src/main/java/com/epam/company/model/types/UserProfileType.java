package com.epam.company.model.types;

public enum UserProfileType {
	UNKNOWN(-1),
	EMPTY(0),
	USER(10),
	HR(99),
	ADMIN(999);

	private final long userProfileType;
	
	private UserProfileType(long userProfileType){
		this.userProfileType = userProfileType;
	}
	
	public long getUserProfileType(){
		return userProfileType;
	}
	
}
