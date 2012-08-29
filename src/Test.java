import com.tommytony.math.MathExt;
import com.tommytony.math.NegativeNumberException;
import com.tommytony.string.Format;


public class Test {

	public static void main(String[] args) throws NegativeNumberException {
		System.out.println(MathExt.factorial(5));
		System.out.println(MathExt.gcd(54, 24));
		System.out.println(Format.formatListNumbers(MathExt.factor(63)));
		System.out.print("The number 2 is prime: ");
		System.out.print(MathExt.isPrime(2));
		System.out.print('\n');
		System.out.println(Format.formatStringtoBasicSentence("i like cows very much"));
	}
}
