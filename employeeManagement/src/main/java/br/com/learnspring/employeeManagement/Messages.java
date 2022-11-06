package br.com.learnspring.employeeManagement;

public enum Messages {
	
	DELETE_EMPLOYEE_SUCESS(0, "Registry deleted successfully."),
	DELETE_EMPLOYEE_ERROR(1, "An error occurred when deleting the registry. Please try again.");
	
	private final int error;
	private final String message;
	
	Messages(int error, String message){
		this.error = error;
		this.message = message;	
	}

	public int getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}
	
	

}
