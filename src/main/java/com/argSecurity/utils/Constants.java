/**
 * Benetech trainning app Copyrights reserved
 */

package com.argSecurity.utils;

public class Constants {

	public static enum appUserData {
		INVALID_DATA("Invalid data"), 
		INVALID_REQUEST("Invalid request"),
		INVALID_PASSWORD("Invalid password"), 
		INCORRECT_OLD_PASSWORD("Incorrect old password"),
		PASSWORDS_MISMATCH("Passwords do not match"),
		CREATED("Resource created"),
		UPDATED("Resource updated"),
		DELETED("Resource deleted"),
		GENERAL_ERROR("General Error"),
		RESOURCE_CREATION_FAILED("Resource creation failed"),
		RESOURCE_EDITION_FAILED("Resource edition failed"),
		RESOURCE_DELETION_FAILED("Resource deletion failed");

		private String message;

		public String getValue() {
			return this.message;
		}

		appUserData(String role) {
			this.message = role;
		}
	}
	
	public static enum generics {
		INVALID_DATA("Invalid data"),
		GENERAL_ERROR("General Error");

		private String message;

		/**
		 * 
		 * @return
		 */
		public String getValue() {
			return this.message;
		}

		/**
		 * 
		 * @param role
		 */
		generics(String role) {
			this.message = role;
		}
	}
	
	/**
	 * 
	 * @author 
	 *
	 */
	public static class MessagesCodes{
		public final static int OK = 0;
		public final static int EMAIL_VERIFICATION_FAIL = 301;
		public final static int EMAIL_VERIFICATION_EXPIRED = 302;
		public final static int TOKEN_EXPIRED = 304;
		public final static int PASSWORD_MISMATCH = 331;
		public final static int INVALID_PASSWORD = 332;
		public final static int INVALID_DATA = 333;
		public final static int GENERAL_ERROR = 334;
		public final static int CREATION_ERROR = 335;
		public final static int EDITION_ERROR = 336;
		public final static int DELETION_ERROR = 337;
		public final static int EXCEPTION = 338;
		

	}
	
	/**
	 * 
	 * @author 
	 *
	 */
	public static class AppMessages{

		public final static String  INVALID_USER = "Invalid user especified";
		public final static String  EMAIL_VERIFICATION_FAIL = "Invalid email address";
		public final static String  EMAIL_RESET_SUBJECT = "Password reset request";
		public final static String  FORGET_PASSWORD_EXCEPTION = "Password reset request";
		
		public final static String  FORGET_PASSWORD_MESSAGE = "password has been reset";
		public final static String  EXPIRED_TOKEN = "temporal password expired";
		public final static String  PASSWORD_CHANGED = "password changed";
		
		public final static String  WRONG_DATA = "User or password invalid";
		public final static String  VALID_USER = "Valid user";
		public final static String  REWARD_ADDED = "reward added";
		
		public final static String  INVALID_ACTION = "Invalid action or condition provided";

		public static String  EMAIL_MESSAGE_TEMPLATE = "Your password has been reset."
															+ " Please login with the following username and password:"
															+ " Username: {username}"
															+ " Password: {Password}";

	}

}