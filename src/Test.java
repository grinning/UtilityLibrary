import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import com.tommytony.input.ArgumentParser;
import com.tommytony.input.ArgumentParser.ArgumentType;
import com.tommytony.input.ArgumentParser.Flag;
import com.tommytony.io.CStdLib;
import com.tommytony.io.CStyleMemory;
import com.tommytony.io.SystemIO;
import com.tommytony.math.MathExt;
import com.tommytony.math.NegativeNumberException;
import com.tommytony.string.Format;
import com.tommytony.string.UnsafeStringAppender;


public class Test implements Runnable {

	Thread current;
	
	public Test(Thread t) {
		this.current = t;
	}
	
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
	    SystemIO.copyToClipboard("banana");
	    System.out.println(SystemIO.getClipboardContents());
	    long add = CStdLib.malloc(4);
	    System.out.println(add);
	    CStdLib.unsafe.putInt(add, 50);
	    System.out.println(CStdLib.unsafe.getInt(add));
	    CStdLib.free(add);
<<<<<<< HEAD
	    CStyleMemory memBank = new CStyleMemory(1024); //1kb of sexylicious manually allocated memory
	    int pointer = memBank.malloc(4);
	    memBank.put(pointer, new byte[] {(byte)0, (byte)0, (byte)0, (byte)5});
	    memBank.free(pointer);
	    memBank.cleanup();
=======
	    UnsafeStringAppender app = new UnsafeStringAppender("dog", 20);
	    app.append(" cat");
	    System.out.println(app.toString());
	    app.deallocate();
	    System.out.println((int) 'c');
	    System.out.println((int) 'b');
	    System.out.println((int) 'C');
	    System.out.println((int) '0');
	    System.out.println((int) '1');
	    Flag[] flags = {
	    		new Flag("help", 'h', ArgumentType.NONE),
	    		new Flag("verbose", 'v', ArgumentType.NONE),
	    		new Flag("name", 'n', ArgumentType.STRING),
	    		new Flag("size", 'x', ArgumentType.NUMBER),
	    		new Flag("recursive", 'r', ArgumentType.NONE),
	    		new Flag("delete", 'd', ArgumentType.NONE)
	    };
	    String fakeargs = "-vn *.bak --size -5 -r --delete";
	    ArgumentParser ap = new ArgumentParser(flags);
	    List<ArgumentParser.Result> argres;
	    System.out.println("find " + fakeargs + ": ");
	    try {
			argres = ap.parse(fakeargs.split(" "));
		    for (int i = 0; i < argres.size(); i++) {
		    	ArgumentParser.Result res = argres.get(i);
		    	System.out.println(res.getName() + ": " + res.getOutput());
		    }
		} catch (InputMismatchException e) {
			System.err.println(e.getMessage());
		}
>>>>>>> 36011960e64c161954d1ed104ada98fb2e9993c6
	    System.gc();
	    System.exit(0);
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000L);
			CStdLib.unsafe.unpark(this.current);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
