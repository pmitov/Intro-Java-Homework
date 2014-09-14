package _8_SortArrayOfStrings;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class _8_SortArrayOfStrings {
	public static void main(String[] args) {
		Locale.setDefault(Locale.ROOT);
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		input.nextLine();
		String[] stringArray = new String[n];
		for (int i = 0; i < n; i++) {
			stringArray[i] = input.nextLine();
		}

		Arrays.sort(stringArray);
		
		for (int i = 0; i < n; i++) {
			System.out.println(stringArray[i]);
		}
	}
}
