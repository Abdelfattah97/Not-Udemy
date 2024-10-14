package com.training_system.entity.enums;

public enum PasswordRecoveryMethod {

	EMAIL(1), SMS(2);
	
	private PasswordRecoveryMethod(int value){
        this.value = value;
    }
	
    private int value;
    
    public int getValue() {
        return value;
    }
    
    public static PasswordRecoveryMethod fromValue(int value) {
    		for (PasswordRecoveryMethod method : PasswordRecoveryMethod.values()) {
            if(method.getValue()==value) {
                return method;
            }
        }
        throw new IllegalArgumentException(String.format("No PasswordResetMethod with value: %d", value));
    }
	
}
