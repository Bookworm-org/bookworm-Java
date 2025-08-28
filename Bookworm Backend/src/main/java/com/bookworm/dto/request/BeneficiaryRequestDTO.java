package com.bookworm.dto.request;

public class BeneficiaryRequestDTO {
	private String benName;
	private String benEmail;
	private String benPan;

	public BeneficiaryRequestDTO() {
	}

	public BeneficiaryRequestDTO(String benName, String benEmail, String benPan) {
		this.benName = benName;
		this.benEmail = benEmail;
		this.benPan = benPan;
	}

	public String getBenName() {
		return benName;
	}

	public void setBenName(String benName) {
		this.benName = benName;
	}

	public String getBenEmail() {
		return benEmail;
	}

	public void setBenEmail(String benEmail) {
		this.benEmail = benEmail;
	}

	public String getBenPan() {
		return benPan;
	}

	public void setBenPan(String benPan) {
		this.benPan = benPan;
	}
}