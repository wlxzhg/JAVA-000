public class Hello {
	public static void main(String[] args) {
		int a = 1;
		int b = 10;
		int c = a + b;

		if(c == 11) {
			System.out.println("1 + 10 = 11");
		}

		int sum = 0;
		for(int i = 1; i < 100; i++) {
			sum += i;
		}
		System.out.println(sum);
	}
}