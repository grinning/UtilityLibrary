package com.tommytony.math;

import java.util.concurrent.RecursiveAction;

import java.math.BigInteger;

public class PowerTask extends RecursiveAction {

	private final long num;
    private final int iters;
    private static final long serialVersionUID = -2941734825678642051L;
	
	public PowerTask(int num, int iters) {
		this.num = (long) num;
		this.iters = iters;
	}
	
	@Override
	protected void compute() {
		BigInteger temp = BigInteger.ONE;
		BigInteger num = BigInteger.valueOf(this.num);
		for(int i = 0; i < this.iters; i++) {
			temp = temp.multiply(num);
		}
		MathExt.addToPow(num);
	}

}
