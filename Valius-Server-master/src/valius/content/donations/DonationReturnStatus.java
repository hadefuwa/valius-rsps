package valius.content.donations;

public enum DonationReturnStatus {
	NONE(""),
	SUCCESS("success"),
	NOTHING_TO_CLAIM("empty"),
	DATABASE_CONNECT_ERROR("connection"),
	API_KEY_INVALID("invalid"),
	CONNECT_ERROR("404"),
	;
	
	private DonationReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
	
	
	private String returnStatus;

	/**
	 * @return The code returned by the web API for the given status
	 */
	public String getReturnStatus() {
		return returnStatus;
	}
	
	/**
	 * Gets the <code>DonationReturnStatus</code> based on the input string
	 * @param input The input string returned by the web API
	 * @return The status with the matching returnStatus, otherwise <code>NONE</code>
	 */
	public static DonationReturnStatus getByString(String input) {
		for(DonationReturnStatus status : DonationReturnStatus.values()) {
			if(status.returnStatus.equals(input))
				return status;
		}
		return NONE;
	}
}
