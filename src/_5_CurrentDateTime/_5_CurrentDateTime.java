package _5_CurrentDateTime;

import java.time.LocalDateTime;

public class _5_CurrentDateTime {
	public static void main(String[] args) {
		LocalDateTime now = LocalDateTime.now();
		System.out.println("The local date is: " + now.toLocalDate());
		System.out.println("The local time is: " + now.toLocalTime());
	}
}
