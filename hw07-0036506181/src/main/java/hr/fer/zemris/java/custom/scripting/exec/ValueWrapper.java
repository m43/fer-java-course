package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

/**
 * Class models an wrapper for storing values of any type. Wrapper also provides
 * the functionality of basic arithmetic operations and comparison of values
 * that can be interpreted as an integer or double.
 * 
 * @author Frano Rajič
 */
public class ValueWrapper {

	/**
	 * Read-write value that is stored. Can be null
	 */
	private Object value;

	/**
	 * Concrete strategy for adding integers
	 */
	public static final BiFunction<Integer, Integer, Integer> INTEGERS_ADDITION = (x, y) -> x + y;

	/**
	 * Concrete strategy for adding doubles
	 */
	public static final BiFunction<Double, Double, Double> DOUBLE_ADDITION = (x, y) -> x + y;

	/**
	 * Concrete strategy for integer division
	 */
	public static final BiFunction<Integer, Integer, Integer> INTEGERS_DIVISION = (x, y) -> x / y;

	/**
	 * Concrete strategy for double division
	 */
	public static final BiFunction<Double, Double, Double> DOUBLE_DIVISION = (x, y) -> x / y;

	/**
	 * Concrete strategy for integer subtraction
	 */
	public static final BiFunction<Integer, Integer, Integer> INTEGERS_SUBTRACTION = (x, y) -> x - y;

	/**
	 * Concrete strategy for double subtraction
	 */
	public static final BiFunction<Double, Double, Double> DOUBLE_SUBTRACTION = (x, y) -> x - y;

	/**
	 * Concrete strategy for integer multiplication
	 */
	public static final BiFunction<Integer, Integer, Integer> INTEGERS_MULTIPLICATION = (x, y) -> x * y;

	/**
	 * Concrete strategy for double multiplication
	 */
	public static final BiFunction<Double, Double, Double> DOUBLE_MULTIPLICATION = (x, y) -> x * y;

	/**
	 * Construct an instance with given initial value.
	 * 
	 * @param value To be set as initial value stored
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Get the value stored in the wrapper
	 * 
	 * @return the value wrapped
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Set a new value to be wrapped
	 * 
	 * @param value the value to be set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Help method used to convert given object to valid operand for arithmetic
	 * operations defined for this wrapper
	 * 
	 * @param o The object to covert. Either Integer, Double, null or String
	 *          parsable to Integer or Double
	 * @return the converted Number object (either an Integer or an Double)
	 * @throws RuntimeException if an invalid operand is given
	 */
	private static Number convertValue(Object o) {
		if (o == null) {
			return Integer.valueOf(0);
		}

		if (o instanceof Integer) {
			return (Integer) o;
		}

		if (o instanceof Double) {
			return (Double) o;
		}

		if (o instanceof String) {
			String s = (String) o;
			try {
				if (s.toLowerCase().contains("e") || s.contains(".")) {
					return Double.valueOf(s);
				} else {
					return Integer.valueOf(s);
				}
			} catch (NumberFormatException e) {
				throw new RuntimeException(
						"One operand object cannot be used as an operand - given an string object that cannot be parsed to a number");
			}
		}

		throw new RuntimeException("One operand object cannot be used as an operand");
	}

	/**
	 * Increase the value currently stored by given wrapper. Both operands (the
	 * original value as well as the given second operand) need to be instances of
	 * Integer or Double or null values or a String object that is parsable as an
	 * Integer or Double
	 * 
	 * @param incValue The second operand for the operation, namely the value to
	 *                 increase by
	 */
	public void add(Object incValue) {
		performOperationAndUpdate(incValue, DOUBLE_ADDITION, INTEGERS_ADDITION);
	}

	/**
	 * Decrease the value currently stored by given Wrapper. Both operands (the
	 * original value as well as the given second operand) need to be instances of
	 * Integer or Double or null values or a String object that is parsable as an
	 * Integer or Double
	 * 
	 * @param decValue The second operand for the operation, namely the value to
	 *                 decrease by
	 */
	public void subtract(Object decValue) {
		performOperationAndUpdate(decValue, DOUBLE_SUBTRACTION, INTEGERS_SUBTRACTION);
	}

	/**
	 * Multiply the value currently stored by given wrapper. Both operands (the
	 * original value as well as the given second operand) need to be instances of
	 * Integer or Double or null values or a String object that is parsable as an
	 * Integer or Double
	 * 
	 * @param mulValue The second operand for the operation, namely the value to
	 *                 multiply with
	 */
	public void multiply(Object mulValue) {
		performOperationAndUpdate(mulValue, DOUBLE_MULTIPLICATION, INTEGERS_MULTIPLICATION);
	}

	/**
	 * Divide the value currently stored by given wrapper. Both operands (the
	 * original value as well as the given second operand) need to be instances of
	 * Integer or Double or null values or a String object that is parsable as an
	 * Integer or Double
	 * 
	 * @param divValue The second operand for the operation, namely the value to
	 *                 divide by
	 */
	public void divide(Object divValue) {
		performOperationAndUpdate(divValue, DOUBLE_DIVISION, INTEGERS_DIVISION);
	}

	/**
	 * Method to perform one of the given operations depending on the result type.
	 * Either both operands will be regarded as doubles or as integers. The first
	 * operand is the value stored currently in the instance, and the second is
	 * given as argument.
	 * 
	 * @param operand          the second operand for the operation
	 * @param doubleOperation  The operation to be applied if one of the numbers is
	 *                         of type Double
	 * @param integerOperation The operation to be done if both operands are
	 *                         Integers
	 */
	private void performOperationAndUpdate(Object operand, BiFunction<Double, Double, Double> doubleOperation,
			BiFunction<Integer, Integer, Integer> integerOperation) {
		Number first = convertValue(value);
		Number second = convertValue(operand);

		if (first instanceof Double || second instanceof Double) {
			value = doubleOperation.apply(first.doubleValue(), second.doubleValue());
		} else {
			value = integerOperation.apply(first.intValue(), second.intValue());
		}

	}

	/**
	 * This method does not perform any change in the stored data. It performs
	 * numerical comparison between the currently stored value in the ValueWrapper
	 * object and given argument.
	 * 
	 * @param withValue the number to compare with
	 * @return an integer less than zero if the currently stored value is smaller
	 *         than the argument, an integer greater than zero if the currently
	 *         stored value is larger than the argument, or an integer 0 if they are
	 *         equal.
	 */
	public int numCompare(Object withValue) {
		Number operand1 = convertValue(value);
		Number operand2 = convertValue(withValue);

		if (operand1 instanceof Double || operand2 instanceof Double)
			return Double.compare(operand1.doubleValue(), operand2.doubleValue());
		else
			return Integer.compare(operand1.intValue(), operand2.intValue());
	}

}
