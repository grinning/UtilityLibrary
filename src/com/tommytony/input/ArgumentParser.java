package com.tommytony.input;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import com.tommytony.string.Checker;
/**
 * Parse arguments from the command line.
 * Released under the GNU LGPL.
 * @author connor
 * 
 */
public class ArgumentParser {
	private final Flag[] flags;
	/**
	 * Setup the argument parser.
	 * @param flags Flags to look for. 
	 */
	public ArgumentParser(Flag[] flags) {
		this.flags = flags;
	}
	/**
	 * Parse an array of arguments.
	 * @param args Arguments to parse (from main(String[] args) or some other input)
	 * @return List of Results. @see ArgumentParser.Result
	 * @throws InputMismatchException Cannot parse arguments
	 */
	public List<Result> parse(String[] args) throws InputMismatchException {
		List<Result> result = new ArrayList<Result>();
		String allargs = "";
		for (String arg : args) {
			arg.replace(" ", "%20");// Passing an argument in quotes makes a single arg have a space. Encode it.
			allargs += arg + ' ';
		}
		char[] charargs = allargs.toCharArray();
		boolean needSpace = false;
		boolean inDash = false;
		byte dashCount = 0;
		String tempFullName = "";
		Flag currentFlag = null;
		boolean flagNeedsArg = false;
		String tempArg = "";
		long count = 0;
		for (char c : charargs) {
			count++;
			if (needSpace && c != ' ') {
				// If the next char should be a space and it's not, error.
				/*debug*///System.out.println(needSpace+" "+inDash+" "+dashCount+" "+tempFullName+" "+flagNeedsArg+" "+tempArg+" "+count);
				throw new InputMismatchException("Position: " + count + ". Error: Expected a space");
			} else if (needSpace && c == ' ') {
				needSpace = false;
				continue;
			}
			///////////////////// DASH PARSING
			if (!inDash && c == '-') {
				// Start of flag with a dash (or two)
				if (!flagNeedsArg) {
					inDash = true;
					dashCount = 1;// Only 1 dash so far
					continue;
				}
			} else if (inDash && c == '-') {
				// Multiple dashes
				if (dashCount == 1) {
					dashCount = 2;
					tempFullName = "";
					continue;
				} else {
					throw new InputMismatchException("Position: " + count + ". Error: Too many dashes");
				}
			} else if (inDash) {
				// In a dash, but character typed
				if (dashCount == 1) { //////////////// Shortcut
					if (c == ' ') { // Space ending short flag dash
						inDash = false;
						dashCount = 0;
						continue;
					}
					if (isFlag(c)) {
						Flag cf = getFlag(c);
						for (Result res : result) {
							if (res.getName() == cf.getName()) {
								throw new InputMismatchException("Position: " + count + ". Error: Repeated flag");
							}
						}
						if (cf.getArgumentType() != ArgumentType.NONE) {
							// If short flag requires an argument, it ends the flag and requires a space
							inDash = false;
							dashCount = 0;
							needSpace = true;
							currentFlag = cf;
							flagNeedsArg = true;
							continue; //  Always continue after a needSpace
						} else { // No return type on this flag. Cave Johnson, we're done here
							result.add(new Result(cf.getName(), cf.getArgumentType(), true));
							continue;
						}
					} else {
						throw new InputMismatchException("Position: " + count + ". Error: Short flag '"+c+"' not found");
					}
				} else if (dashCount == 2) {///////////////// Full name
					if (c == ' ') { // Space ending full flag name
						if (isFlag(tempFullName)) {
							Flag cf = getFlag(tempFullName);
							for (Result res : result) {
								if (res.getName() == cf.getName()) {
									throw new InputMismatchException("Position: " + count + ". Error: Repeated flag");
								}
							}
							if (cf.getArgumentType() != ArgumentType.NONE) {
								// Need argument data
								currentFlag = cf;
								flagNeedsArg = true;
							} else { // No return type on this flag. Cave Johnson, we're done here
								result.add(new Result(cf.getName(), cf.getArgumentType(), true));
							}
							// All Long flags end the dash. Doesn't need a space because one was just typed to end the full flag
							tempFullName = "";
							inDash = false;
							dashCount = 0;
							continue;
						} else {
							throw new InputMismatchException("Position: " + count + ". Error: Full flag '"+tempFullName+"' not found");
						}
					} else {
						tempFullName += c;
					}
				}
			}
			////////////////// END DASH PARSING
			////////////////// BEGIN FLAG ARG PARSING
			if (flagNeedsArg) {
				if (c == ' ') { // Space ends flag
					switch (currentFlag.getArgumentType()) {
					case NUMBER:
						try {
							double res = Double.parseDouble(tempArg);
							result.add(new Result(currentFlag.getName(), currentFlag.getArgumentType(), res));
						} catch (NumberFormatException nfe) {
							throw new InputMismatchException("Position: " + count + ". Error: Argument expects a number");
						} catch (Exception e2) {
							err(count, e2.toString());
						}
						break;
					case CHAR:
						if (tempArg.equals("%20")) {// space is replaced by %20 at start of process
							result.add(new Result(currentFlag.getName(), currentFlag.getArgumentType(), " "));
							break;
						}
						if (tempArg.length() > 1) throw new InputMismatchException("Position: " + count + ". Error: Argument expects a single character");
						result.add(new Result(currentFlag.getName(), currentFlag.getArgumentType(), tempArg.charAt(0)));
						break;
					case STRING:
						result.add(new Result(currentFlag.getName(), currentFlag.getArgumentType(), tempArg.replace("%20", " ")));
						break;
					default:// Should not happen. Means argtype was none
						throw new RuntimeException("Argument Library Error (library was parsing arguments for a flag that should have no argument). " +
								"Report this error at https://github.com/grinning/UtilityLibrary/issues");
					}
					tempArg = "";
					flagNeedsArg = false;
					currentFlag = null;
					// No need space because one was just typed
				} else {
					tempArg += c;
				}
			}
			///////////////// END FLAG ARG PARSING
		}
		// Add not set arguments to the result
		for (Flag flag : flags) {
			boolean isFlagInResult = false;
			for (Result res : result) {
				if (res.getName() == flag.getName()) {
					isFlagInResult = true;
					continue;
				}
			}
			if (!isFlagInResult) {
				if (flag.getArgumentType() == ArgumentType.NONE) {
					result.add(new Result(flag.getName(), flag.getArgumentType(), false));
				} else {
					result.add(new Result(flag.getName(), flag.getArgumentType(), null));
				}
			}
		}
		return result;
	}
	private boolean err(long pos, String err) {
		throw new InputMismatchException("Position: " + pos + ". Error: " + err);
	}
	private Flag getFlag(String str) {
		for (Flag flag : flags) {
			if (flag.getName().equals(str)) return flag;
		}
		return null;
	}
	private Flag getFlag(char ch) {
		for (Flag flag : flags) {
			if (flag.getShortcut() == ch) return flag;
		}
		return null;
	}
	private boolean isFlag(String str) {
		if (getFlag(str) != null) return true;
		return false;
	}
	private boolean isFlag(char ch) {
		if (getFlag(ch) != null) return true;
		return false;
	}
	public Flag[] getFlags() {
		return flags;
	}
	public static class Flag {
		private final String name;
		private final char shortcut;
		private final ArgumentType argument;
		/**
		 * Creates an flag.
		 * @param name The long name of the flag. It must be alphanumeric and be more than 2 characters.
		 * @param shortcut The shortcut for the flag. It must be a alpha character.
		 * @param argument Expected type of argument to the flag.
		 * @throws IllegalArgumentException Error with the input
		 */
		public Flag(String name, char shortcut, ArgumentType argument) throws IllegalArgumentException {
			if (!Checker.isAlphanumeric(name)) throw new IllegalArgumentException("Name must be alphanumeric");
			if (name == null) throw new IllegalArgumentException("Name must not be null");
			if (name.length() < 2) throw new IllegalArgumentException("Name must be 2+ characters");
			this.name = name.toLowerCase();
			if (!Checker.isAlpha(Character.toString(shortcut))) throw new IllegalArgumentException("Shortcut must be alpha");
			this.shortcut = Character.toLowerCase(shortcut);
			if (argument == null) {
				this.argument = ArgumentType.NONE;
			} else {
				this.argument = argument;
			}
		}
		/**
		 * Creates an flag without a shortcut.
		 * @param name The long name of the flag. It must be alphanumeric and be more than 2 characters.
		 * @param argument Expected type of argument to the flag.
		 * @throws IllegalArgumentException Error with the input
		 */
		public Flag(String name, ArgumentType argument) {
			if (!Checker.isAlphanumeric(name)) throw new IllegalArgumentException("Name must be alphanumeric");
			if (name == null) throw new IllegalArgumentException("Name must not be null");
			if (name.length() < 2) throw new IllegalArgumentException("Name must be 2+ characters");
			this.name = name.toLowerCase();
			this.shortcut = Character.toLowerCase(' ');
			if (argument == null) {
				this.argument = ArgumentType.NONE;
			} else {
				this.argument = argument;
			}
		}
		public String getName() {
			return name;
		}
		public char getShortcut() {
			return shortcut;
		}
		public ArgumentType getArgumentType() {
			return argument;
		}
		
	}
	public static class Result {
		private final String name;
		private final ArgumentType argument;
		private final Object output;
		/**
		 * Creates a result.
		 * @param name The name of the flag
		 * @param argument The type of the output
		 * @param output The output. Should be true or false if argument is none. For other argument types, should be null if not specified by user.
		 */
		public Result(String name, ArgumentType argument, Object output) {
			this.name = name;
			this.argument = argument;
			this.output = output;
		}
		public String getName() {
			return name;
		}
		public ArgumentType getArgumentType() {
			return argument;
		}
		public Object getOutput() {
			return output;
		}
		
		
	}
	public static enum ArgumentType {
		NUMBER,
		STRING,
		CHAR,
		NONE
	}
}
