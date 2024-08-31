package com.training_system.entity.enums;

public enum ProductType {

	COURSE_ENROLLMENT_PAYMENT(1),SUBSCRIPTION_PAYMENT(2);
	
	private ProductType(int value){
		this.value = value;
	}
	
	private int value ;
	
	public int getValue(){
		return this.value;
	}
	
	public static ProductType fromValue(int value) {
		for(ProductType type : ProductType.values()) {
			if(type.getValue()==value) {
				return type;
			}
		}
		throw new IllegalArgumentException(String.format("No Payment Type with value: %d", value));
	}
	
}
