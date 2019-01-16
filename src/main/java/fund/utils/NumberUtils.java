package fund.utils;

public final class NumberUtils {

	private NumberUtils() {
	}

	public static String toKoreanNumber(int n, boolean isMoney, boolean allNumToKorean) {
		String num = String.valueOf(n);
		int len = num.length();

		// initialize depends on allNumToKorean.
		// if allNumToKorean is false, the 'number part' will be remain a just
		// number.
		String[] han1 = allNumToKorean ? new String[] { "", "1", "2", "3", "4", "5", "6", "7", "8", "9" }
				: new String[] { "", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구" };
		String[] han2 = { "", "십", "백", "천" };
		String[] han3 = { "", "만", "억", "조", "경" };
		
		boolean isLastValueFromHan3 = false;
		

		StringBuffer result = new StringBuffer();

		for (int i = len - 1; i >= 0; i--) {
			result.append(han1[Integer.parseInt(num.substring(len - i - 1, len - i))]);

			if (Integer.parseInt(num.substring(len - i - 1, len - i)) > 0) {
				result.append(han2[i % 4]);
				isLastValueFromHan3 = false;
			}
			if (i % 4 == 0 && !isLastValueFromHan3) {
				result.append(han3[i / 4]);
				isLastValueFromHan3 = true;
			}
		}

		return isMoney ? result.append("원").toString() : result.toString();
	}
}
