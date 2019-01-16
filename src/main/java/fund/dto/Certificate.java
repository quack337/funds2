package fund.dto;

import fund.utils.NumberUtils;
import lombok.Data;

@Data
public class Certificate {

	int id;
	int type;
	String certificateNo;
	int userId;
	String createDate;
	String personNo;
	String personName;
	String department;
	int amount;
	String body;
	String userName;
	Integer corporateId;
	String corporateName;
	String representative;

	public String getAmountToKorean() {
		return NumberUtils.toKoreanNumber(amount, true, true);
	}

	public String getCorporateWithBlank() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < corporateName.length(); i++) {
			sb.append(corporateName.charAt(i));
			sb.append("  ");
		}
		return sb.toString();
	}

	public String getRepresentativeWithBlank() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < representative.length(); i++) {
			sb.append(representative.charAt(i));
			sb.append("  ");
		}
		return sb.toString();
	}

	public String getStatus() {
		switch (corporateId) {
		case 1: {
			return "총 장";
		}
		case 2: {
			return "이 사 장";
		}
		case 3: {
			return "단 장";
		}
		}
		return "";
	}
}
