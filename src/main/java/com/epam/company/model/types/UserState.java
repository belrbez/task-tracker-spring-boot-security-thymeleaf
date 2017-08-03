package com.epam.company.model.types;

public enum UserState {

	UNKNOWN(-1),
	EMPTY(0),
	AWAIT_VERIFICATION(1),
	REJECTED(2),
	VERIFIED(10),
	ACTIVE(200),
	INACTIVE(202),
	DELETED(404),
	LOCKED(503),;
	
	private int state;
	
	private UserState(final int state){
		this.state = state;
	}
	
	public int getState(){
		return this.state;
	}

	@Override
	public String toString(){
		return Integer.toString(this.state);
	}

	public String getName(){
		return this.name();
	}


}
