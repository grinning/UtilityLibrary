import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
		System.out.println(MathExt.factorial(0));
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		int num = rand.nextInt(30) + 2;
		int pow = rand.nextInt(6, 20);
		System.out.println("Concurrent power of " + num + " to the " + pow + " is " + MathExt.concurrentPower(num, pow));
		System.out.print("\n\n");
		System.out.println("###############");
		System.out.println("# Benchmarks  #");
		System.out.println("###############");
		
		long start = System.nanoTime();
		List<Integer> nums = MathExt.factor(60392830);
		long time = System.nanoTime() - start;
		System.out.println("Factoring 60392830 took " + time + "ns");
		System.out.println("Factors: " + Format.formatListNumbers(nums));
	    start = System.nanoTime();
	    BigInteger fact = MathExt.factorial(19);
	    time = System.nanoTime() - start;
	    System.out.println("Calculating the factorial of 19 took " + time + "ns");
	    System.out.println("19! = " + fact);
	}
}
