package com.training_system.entity.enums;

/**
 * represents the status of a password reset token 
 * has only one valid status "VALID"
 */
public enum PasswordResetTokenStatus {

	VALID(1),INVALID(2) , EXPIRED(3) , ALREADY_USED(4),NO_TOKEN(5) ;
	
	private PasswordResetTokenStatus(int value) {
        this.value = value;
    }
	
    private int value;
    
    public int getValue() {
        return value;
    }
    
    public static PasswordResetTokenStatus fromValue(int value) {
    		for (PasswordResetTokenStatus status : PasswordResetTokenStatus.values()) {
            if(status.getValue()==value) {
                return status;
            }
        }
        throw new IllegalArgumentException(String.format("No PasswordResetTokenStatus with value: %d", value));
    }
	
}
