/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.utils;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author miaoqiang
 * @date 2018/6/25.
 */
public class ObjectUtils {

	private static final int	INITIAL_HASH			= 7;
	private static final int	MULTIPLIER				= 31;

	private static final String	EMPTY_STRING			= "";
	private static final String	NULL_STRING				= "null";
	private static final String	ARRAY_START				= "{";
	private static final String	ARRAY_END				= "}";
	private static final String	EMPTY_ARRAY				= ARRAY_START + ARRAY_END;
	private static final String	ARRAY_ELEMENT_SEPARATOR	= ", ";

	/**
	 * <p>
	 * Singleton used as a <code>null</code> placeholder where <code>null</code> has another meaning.
	 * </p>
	 * <p>
	 * For example, in a <code>HashMap</code> the {@link java.util.HashMap#get(java.lang.Object)} method returns
	 * <code>null</code> if the <code>Map</code> contains <code>null</code> or if there is no matching key. The
	 * <code>Null</code> placeholder can be used to distinguish between these two cases.
	 * </p>
	 * <p>
	 * Another example is <code>Hashtable</code>, where <code>null</code> cannot be stored.
	 * </p>
	 * <p>
	 * This instance is Serializable.
	 * </p>
	 */
	public static final Null	NULL					= new Null();

	/**
	 * <p>
	 * <code>ObjectUtils</code> instances should NOT be constructed in standard programming. Instead, the class should
	 * be used as <code>ObjectUtils.defaultIfNull("a","b");</code>.
	 * </p>
	 * <p>
	 * This constructor is public to permit tools that require a JavaBean instance to operate.
	 * </p>
	 */
	public ObjectUtils() {
		super();
	}

	/**
	 * 判断输入对象是否为空
	 *
	 * @param object
	 * @return
	 */
	public static boolean isNull(Object object) {
		if (object == null) {
			return true;
		}
		return false;
	}

	/**
	 * 判断输入对象不为空
	 *
	 * @param object
	 * @return
	 */
	public static boolean isNotNull(Object object) {
		return !isNull(object);
	}

	/**
	 * 全部非null
	 *
	 * @param o
	 * @return
	 */
	public static boolean isAllNotNull(Object... o) {
		boolean flag = true;
		if (o.length > 0) {
			for (int i = 0, j = o.length; i < j; i++) {
				if (isNull(o[i])) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 至少一个非null
	 *
	 * @param o
	 * @return
	 */
	public static boolean isNotAllNull(Object... o) {
		boolean flag = false;
		if (o.length > 0) {
			for (int i = 0, j = o.length; i < j; i++) {
				if (isNotNull(o[i])) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	// Defaulting
	// -----------------------------------------------------------------------
	/**
	 * 如果对象为<code>null</code>，则返回指定默认对象，否则返回对象本身。
	 *
	 * <pre>
	 * ObjectUtil.defaultIfNull(null, null)      = null
	 * ObjectUtil.defaultIfNull(null, "")        = ""
	 * ObjectUtil.defaultIfNull(null, "zz")      = "zz"
	 * ObjectUtil.defaultIfNull("abc", *)        = "abc"
	 * ObjectUtil.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
	 * </pre>
	 *
	 * @param object 要测试的对象
	 * @param defaultValue 默认值
	 * @return 对象本身或默认对象
	 */
	public static Object defaultIfNull(Object object, Object defaultValue) {
		return object != null ? object : defaultValue;
	}

	/**
	 * 取得对象的原始的hash值, 如果对象为<code>null</code>, 则返回<code>0</code>。
	 * <p>
	 * 该方法使用<code>System.identityHashCode</code>来取得hash值，该值不受对象本身的<code>hashCode</code>方法的影响。
	 * </p>
	 *
	 * @param object 对象
	 * @return hash值
	 */
	public static int identityHashCode(Object object) {
		return (object == null) ? 0 : System.identityHashCode(object);
	}

	// Identity ToString
	// -----------------------------------------------------------------------

	/**
	 * 取得对象自身的identity，如同对象没有覆盖<code>toString()</code>方法时，<code>Object.toString()</code>的原始输出。
	 *
	 * <pre>
	 * ObjectUtil.identityToString(null, "NULL")            = "NULL"
	 * ObjectUtil.identityToString("", "NULL")              = "java.lang.String@1e23"
	 * ObjectUtil.identityToString(Boolean.TRUE, "NULL")    = "java.lang.Boolean@7fa"
	 * ObjectUtil.identityToString(new int[0], "NULL")      = "int[]@7fa"
	 * ObjectUtil.identityToString(new Object[0], "NULL")   = "java.lang.Object[]@7fa"
	 * </pre>
	 *
	 * @param object 对象
	 * @param nullStr 如果对象为<code>null</code>，则返回该字符串
	 * @return 对象的identity，如果对象是<code>null</code>，则返回指定字符串
	 */
	public static String identityToString(Object object, String nullStr) {
		if (object == null) {
			return nullStr;
		}
		return identityToString(object);
	}

	/**
	 * <p>
	 * Gets the toString that would be produced by <code>Object</code> if a class did not override toString itself.
	 * <code>null</code> will return <code>null</code>.
	 * </p>
	 *
	 * <pre>
	 * ObjectUtils.identityToString(null)         = null
	 * ObjectUtils.identityToString("")           = "java.lang.String@1e23"
	 * ObjectUtils.identityToString(Boolean.TRUE) = "java.lang.Boolean@7fa"
	 * </pre>
	 *
	 * @param object the object to create a toString for, may be <code>null</code>
	 * @return the default toString text, or <code>null</code> if <code>null</code> passed in
	 */
	public static String identityToString(Object object) {
		if (object == null) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		identityToString(buffer, object);
		return buffer.toString();
	}

	/**
	 * <p>
	 * Appends the toString that would be produced by <code>Object</code> if a class did not override toString itself.
	 * <code>null</code> will throw a NullPointerException for either of the two parameters.
	 * </p>
	 *
	 * <pre>
	 * ObjectUtils.identityToString(buf, "")            = buf.append("java.lang.String@1e23"
	 * ObjectUtils.identityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa"
	 * ObjectUtils.identityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa")
	 * </pre>
	 *
	 * @param buffer the buffer to append to
	 * @param object the object to create a toString for
	 * @since 2.4
	 */
	public static void identityToString(StringBuffer buffer, Object object) {
		if (object == null) {
			throw new NullPointerException("Cannot get the toString of a null identity");
		}
		buffer.append(object.getClass().getName()).append('@')
				.append(Integer.toHexString(System.identityHashCode(object)));
	}

	/**
	 * 检查两个对象是否属于相同类型。<code>null</code>将被看作任意类型。
	 *
	 * @param object1 对象1
	 * @param object2 对象2
	 * @return 如果两个对象有相同的类型，则返回<code>true</code>
	 */
	public static boolean isSameType(Object object1, Object object2) {
		if ((object1 == null) || (object2 == null)) {
			return true;
		}

		return object1.getClass().equals(object2.getClass());
	}

	// Min/Max
	// -----------------------------------------------------------------------
	/**
	 * Null safe comparison of Comparables.
	 *
	 * @param c1 the first comparable, may be null
	 * @param c2 the second comparable, may be null
	 * @return <ul>
	 *         <li>If both objects are non-null and unequal, the lesser object.
	 *         <li>If both objects are non-null and equal, c1.
	 *         <li>If one of the comparables is null, the non-null object.
	 *         <li>If both the comparables are null, null is returned.
	 *         </ul>
	 */
	public static Object min(Comparable c1, Comparable c2) {
		if (c1 != null && c2 != null) {
			return c1.compareTo(c2) < 1 ? c1 : c2;
		} else {
			return c1 != null ? c1 : c2;
		}
	}

	/**
	 * Null safe comparison of Comparables.
	 *
	 * @param c1 the first comparable, may be null
	 * @param c2 the second comparable, may be null
	 * @return <ul>
	 *         <li>If both objects are non-null and unequal, the greater object.
	 *         <li>If both objects are non-null and equal, c1.
	 *         <li>If one of the comparables is null, the non-null object.
	 *         <li>If both the comparables are null, null is returned.
	 *         </ul>
	 */
	public static Object max(Comparable c1, Comparable c2) {
		if (c1 != null && c2 != null) {
			return c1.compareTo(c2) >= 0 ? c1 : c2;
		} else {
			return c1 != null ? c1 : c2;
		}
	}

	// ------------------------Spring----------------------------
	/**
	 * Return whether the given throwable is a checked exception: that is, neither a RuntimeException nor an Error.
	 *
	 * @param ex the throwable to check
	 * @return whether the throwable is a checked exception
	 * @see java.lang.Exception
	 * @see java.lang.RuntimeException
	 * @see java.lang.Error
	 */
	public static boolean isCheckedException(Throwable ex) {
		return !(ex instanceof RuntimeException || ex instanceof Error);
	}

	/**
	 * Check whether the given exception is compatible with the exceptions declared in a throws clause.
	 *
	 * @param ex the exception to checked
	 * @param declaredExceptions the exceptions declared in the throws clause
	 * @return whether the given exception is compatible
	 */
	public static boolean isCompatibleWithThrowsClause(Throwable ex, Class[] declaredExceptions) {
		if (!isCheckedException(ex)) {
			return true;
		}
		if (declaredExceptions != null) {
			int i = 0;
			while (i < declaredExceptions.length) {
				if (declaredExceptions[i].isAssignableFrom(ex.getClass())) {
					return true;
				}
				i++;
			}
		}
		return false;
	}

	/**
	 * Determine whether the given object is an array: either an Object array or a primitive array.
	 *
	 * @param obj the object to check
	 */
	public static boolean isArray(Object obj) {
		return (obj != null && obj.getClass().isArray());
	}

	/**
	 * Determine whether the given array is empty: i.e. <code>null</code> or of zero length.
	 *
	 * @param array the array to check
	 */
	public static boolean isEmpty(Object[] array) {
		return (array == null || array.length == 0);
	}

	/**
	 * Check whether the given array contains the given element.
	 *
	 * @param array the array to check (may be <code>null</code>, in which case the return value will always be
	 *            <code>false</code>)
	 * @param element the element to check for
	 * @return whether the element has been found in the given array
	 */
	public static boolean containsElement(Object[] array, Object element) {
		if (array == null) {
			return false;
		}
		for (Object arrayEle : array) {
			if (nullSafeEquals(arrayEle, element)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Append the given Object to the given array, returning a new array consisting of the input array contents plus the
	 * given Object.
	 *
	 * @param array the array to append to (can be <code>null</code>)
	 * @param obj the Object to append
	 * @return the new array (of the same component type; never <code>null</code>)
	 */
	public static Object[] addObjectToArray(Object[] array, Object obj) {
		Class compType = Object.class;
		if (array != null) {
			compType = array.getClass().getComponentType();
		} else if (obj != null) {
			compType = obj.getClass();
		}
		int newArrLength = (array != null ? array.length + 1 : 1);
		Object[] newArr = (Object[]) Array.newInstance(compType, newArrLength);
		if (array != null) {
			System.arraycopy(array, 0, newArr, 0, array.length);
		}
		newArr[newArr.length - 1] = obj;
		return newArr;
	}

	/**
	 * Convert the given array (which may be a primitive array) to an object array (if necessary of primitive wrapper
	 * objects).
	 * <p>
	 * A <code>null</code> source value will be converted to an empty Object array.
	 *
	 * @param source the (potentially primitive) array
	 * @return the corresponding object array (never <code>null</code>)
	 * @throws IllegalArgumentException if the parameter is not an array
	 */
	public static Object[] toObjectArray(Object source) {
		if (source instanceof Object[]) {
			return (Object[]) source;
		}
		if (source == null) {
			return new Object[0];
		}
		if (!source.getClass().isArray()) {
			throw new IllegalArgumentException("Source is not an array: " + source);
		}
		int length = Array.getLength(source);
		if (length == 0) {
			return new Object[0];
		}
		Class wrapperType = Array.get(source, 0).getClass();
		Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
		for (int i = 0; i < length; i++) {
			newArray[i] = Array.get(source, i);
		}
		return newArray;
	}

	/**
	 * Determine if the given objects are equal, returning <code>true</code> if both are <code>null</code> or
	 * <code>false</code> if only one is <code>null</code>.
	 * <p>
	 * Compares arrays with <code>Arrays.equals</code>, performing an equality check based on the array elements rather
	 * than the array reference.
	 *
	 * @param o1 first Object to compare
	 * @param o2 second Object to compare
	 * @return whether the given objects are equal
	 * @see java.util.Arrays#equals
	 */
	public static boolean nullSafeEquals(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		if (o1.equals(o2)) {
			return true;
		}
		if (o1.getClass().isArray() && o2.getClass().isArray()) {
			if (o1 instanceof Object[] && o2 instanceof Object[]) {
				return Arrays.equals((Object[]) o1, (Object[]) o2);
			}
			if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
				return Arrays.equals((boolean[]) o1, (boolean[]) o2);
			}
			if (o1 instanceof byte[] && o2 instanceof byte[]) {
				return Arrays.equals((byte[]) o1, (byte[]) o2);
			}
			if (o1 instanceof char[] && o2 instanceof char[]) {
				return Arrays.equals((char[]) o1, (char[]) o2);
			}
			if (o1 instanceof double[] && o2 instanceof double[]) {
				return Arrays.equals((double[]) o1, (double[]) o2);
			}
			if (o1 instanceof float[] && o2 instanceof float[]) {
				return Arrays.equals((float[]) o1, (float[]) o2);
			}
			if (o1 instanceof int[] && o2 instanceof int[]) {
				return Arrays.equals((int[]) o1, (int[]) o2);
			}
			if (o1 instanceof long[] && o2 instanceof long[]) {
				return Arrays.equals((long[]) o1, (long[]) o2);
			}
			if (o1 instanceof short[] && o2 instanceof short[]) {
				return Arrays.equals((short[]) o1, (short[]) o2);
			}
		}
		return false;
	}

	/**
	 * Return as hash code for the given object; typically the value of <code>{@link Object#hashCode()}</code>. If the
	 * object is an array, this method will delegate to any of the <code>nullSafeHashCode</code> methods for arrays in
	 * this class. If the object is <code>null</code>, this method returns 0.
	 *
	 * @see #nullSafeHashCode(Object[])
	 * @see #nullSafeHashCode(boolean[])
	 * @see #nullSafeHashCode(byte[])
	 * @see #nullSafeHashCode(char[])
	 * @see #nullSafeHashCode(double[])
	 * @see #nullSafeHashCode(float[])
	 * @see #nullSafeHashCode(int[])
	 * @see #nullSafeHashCode(long[])
	 * @see #nullSafeHashCode(short[])
	 */
	public static int nullSafeHashCode(Object obj) {
		if (obj == null) {
			return 0;
		}
		if (obj.getClass().isArray()) {
			if (obj instanceof Object[]) {
				return nullSafeHashCode((Object[]) obj);
			}
			if (obj instanceof boolean[]) {
				return nullSafeHashCode((boolean[]) obj);
			}
			if (obj instanceof byte[]) {
				return nullSafeHashCode((byte[]) obj);
			}
			if (obj instanceof char[]) {
				return nullSafeHashCode((char[]) obj);
			}
			if (obj instanceof double[]) {
				return nullSafeHashCode((double[]) obj);
			}
			if (obj instanceof float[]) {
				return nullSafeHashCode((float[]) obj);
			}
			if (obj instanceof int[]) {
				return nullSafeHashCode((int[]) obj);
			}
			if (obj instanceof long[]) {
				return nullSafeHashCode((long[]) obj);
			}
			if (obj instanceof short[]) {
				return nullSafeHashCode((short[]) obj);
			}
		}
		return obj.hashCode();
	}

	/**
	 * Return a hash code based on the contents of the specified array. If <code>array</code> is <code>null</code>, this
	 * method returns 0.
	 */
	public static int nullSafeHashCode(Object[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + nullSafeHashCode(array[i]);
		}
		return hash;
	}

	/**
	 * Return a hash code based on the contents of the specified array. If <code>array</code> is <code>null</code>, this
	 * method returns 0.
	 */
	public static int nullSafeHashCode(boolean[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + hashCode(array[i]);
		}
		return hash;
	}

	/**
	 * Return a hash code based on the contents of the specified array. If <code>array</code> is <code>null</code>, this
	 * method returns 0.
	 */
	public static int nullSafeHashCode(byte[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + array[i];
		}
		return hash;
	}

	/**
	 * Return a hash code based on the contents of the specified array. If <code>array</code> is <code>null</code>, this
	 * method returns 0.
	 */
	public static int nullSafeHashCode(char[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + array[i];
		}
		return hash;
	}

	/**
	 * Return a hash code based on the contents of the specified array. If <code>array</code> is <code>null</code>, this
	 * method returns 0.
	 */
	public static int nullSafeHashCode(double[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + hashCode(array[i]);
		}
		return hash;
	}

	/**
	 * Return a hash code based on the contents of the specified array. If <code>array</code> is <code>null</code>, this
	 * method returns 0.
	 */
	public static int nullSafeHashCode(float[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + hashCode(array[i]);
		}
		return hash;
	}

	/**
	 * Return a hash code based on the contents of the specified array. If <code>array</code> is <code>null</code>, this
	 * method returns 0.
	 */
	public static int nullSafeHashCode(int[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + array[i];
		}
		return hash;
	}

	/**
	 * Return a hash code based on the contents of the specified array. If <code>array</code> is <code>null</code>, this
	 * method returns 0.
	 */
	public static int nullSafeHashCode(long[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + hashCode(array[i]);
		}
		return hash;
	}

	/**
	 * Return a hash code based on the contents of the specified array. If <code>array</code> is <code>null</code>, this
	 * method returns 0.
	 */
	public static int nullSafeHashCode(short[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + array[i];
		}
		return hash;
	}

	/**
	 * Return the same value as <code>{@link Boolean#hashCode()}</code>.
	 *
	 * @see Boolean#hashCode()
	 */
	public static int hashCode(boolean bool) {
		return bool ? 1231 : 1237;
	}

	/**
	 * Return the same value as <code>{@link Double#hashCode()}</code>.
	 *
	 * @see Double#hashCode()
	 */
	public static int hashCode(double dbl) {
		long bits = Double.doubleToLongBits(dbl);
		return hashCode(bits);
	}

	/**
	 * Return the same value as <code>{@link Float#hashCode()}</code>.
	 *
	 * @see Float#hashCode()
	 */
	public static int hashCode(float flt) {
		return Float.floatToIntBits(flt);
	}

	/**
	 * Return the same value as <code>{@link Long#hashCode()}</code>.
	 *
	 * @see Long#hashCode()
	 */
	public static int hashCode(long lng) {
		return (int) (lng ^ (lng >>> 32));
	}

	// ---------------------------------------------------------------------
	// Convenience methods for toString output
	// ---------------------------------------------------------------------
	/**
	 * Return a content-based String representation if <code>obj</code> is not <code>null</code>; otherwise returns an
	 * empty String.
	 * <p>
	 * Differs from {@link #nullSafeToString(Object)} in that it returns an empty String rather than "null" for a
	 * <code>null</code> value.
	 *
	 * @param obj the object to build a display String for
	 * @return a display String representation of <code>obj</code>
	 * @see #nullSafeToString(Object)
	 */
	public static String getDisplayString(Object obj) {
		if (obj == null) {
			return EMPTY_STRING;
		}
		return nullSafeToString(obj);
	}

	/**
	 * Determine the class name for the given object.
	 * <p>
	 * Returns <code>"null"</code> if <code>obj</code> is <code>null</code>.
	 *
	 * @param obj the object to introspect (may be <code>null</code>)
	 * @return the corresponding class name
	 */
	public static String nullSafeClassName(Object obj) {
		return (obj != null ? obj.getClass().getName() : NULL_STRING);
	}

	/**
	 * Return a String representation of the specified Object.
	 * <p>
	 * Builds a String representation of the contents in case of an array. Returns <code>"null"</code> if
	 * <code>obj</code> is <code>null</code>.
	 *
	 * @param obj the object to build a String representation for
	 * @return a String representation of <code>obj</code>
	 */
	public static String nullSafeToString(Object obj) {
		if (obj == null) {
			return NULL_STRING;
		}
		if (obj instanceof String) {
			return (String) obj;
		}
		if (obj instanceof Object[]) {
			return nullSafeToString((Object[]) obj);
		}
		if (obj instanceof boolean[]) {
			return nullSafeToString((boolean[]) obj);
		}
		if (obj instanceof byte[]) {
			return nullSafeToString((byte[]) obj);
		}
		if (obj instanceof char[]) {
			return nullSafeToString((char[]) obj);
		}
		if (obj instanceof double[]) {
			return nullSafeToString((double[]) obj);
		}
		if (obj instanceof float[]) {
			return nullSafeToString((float[]) obj);
		}
		if (obj instanceof int[]) {
			return nullSafeToString((int[]) obj);
		}
		if (obj instanceof long[]) {
			return nullSafeToString((long[]) obj);
		}
		if (obj instanceof short[]) {
			return nullSafeToString((short[]) obj);
		}
		String str = obj.toString();
		return str;
	}

	/**
	 * Return a String representation of the contents of the specified array.
	 * <p>
	 * The String representation consists of a list of the array's elements, enclosed in curly braces (<code>"{}"</code>
	 * ). Adjacent elements are separated by the characters <code>", "</code> (a comma followed by a space). Returns
	 * <code>"null"</code> if <code>array</code> is <code>null</code>.
	 *
	 * @param array the array to build a String representation for
	 * @return a String representation of <code>array</code>
	 */
	public static String nullSafeToString(Object[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			} else {
				sb.append(ARRAY_ELEMENT_SEPARATOR);
			}
			sb.append(String.valueOf(array[i]));
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * Return a String representation of the contents of the specified array.
	 * <p>
	 * The String representation consists of a list of the array's elements, enclosed in curly braces (<code>"{}"</code>
	 * ). Adjacent elements are separated by the characters <code>", "</code> (a comma followed by a space). Returns
	 * <code>"null"</code> if <code>array</code> is <code>null</code>.
	 *
	 * @param array the array to build a String representation for
	 * @return a String representation of <code>array</code>
	 */
	public static String nullSafeToString(boolean[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			} else {
				sb.append(ARRAY_ELEMENT_SEPARATOR);
			}

			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * Return a String representation of the contents of the specified array.
	 * <p>
	 * The String representation consists of a list of the array's elements, enclosed in curly braces (<code>"{}"</code>
	 * ). Adjacent elements are separated by the characters <code>", "</code> (a comma followed by a space). Returns
	 * <code>"null"</code> if <code>array</code> is <code>null</code>.
	 *
	 * @param array the array to build a String representation for
	 * @return a String representation of <code>array</code>
	 */
	public static String nullSafeToString(byte[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			} else {
				sb.append(ARRAY_ELEMENT_SEPARATOR);
			}
			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * Return a String representation of the contents of the specified array.
	 * <p>
	 * The String representation consists of a list of the array's elements, enclosed in curly braces (<code>"{}"</code>
	 * ). Adjacent elements are separated by the characters <code>", "</code> (a comma followed by a space). Returns
	 * <code>"null"</code> if <code>array</code> is <code>null</code>.
	 *
	 * @param array the array to build a String representation for
	 * @return a String representation of <code>array</code>
	 */
	public static String nullSafeToString(char[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			} else {
				sb.append(ARRAY_ELEMENT_SEPARATOR);
			}
			sb.append("'").append(array[i]).append("'");
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * Return a String representation of the contents of the specified array.
	 * <p>
	 * The String representation consists of a list of the array's elements, enclosed in curly braces (<code>"{}"</code>
	 * ). Adjacent elements are separated by the characters <code>", "</code> (a comma followed by a space). Returns
	 * <code>"null"</code> if <code>array</code> is <code>null</code>.
	 *
	 * @param array the array to build a String representation for
	 * @return a String representation of <code>array</code>
	 */
	public static String nullSafeToString(double[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			} else {
				sb.append(ARRAY_ELEMENT_SEPARATOR);
			}

			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * Return a String representation of the contents of the specified array.
	 * <p>
	 * The String representation consists of a list of the array's elements, enclosed in curly braces (<code>"{}"</code>
	 * ). Adjacent elements are separated by the characters <code>", "</code> (a comma followed by a space). Returns
	 * <code>"null"</code> if <code>array</code> is <code>null</code>.
	 *
	 * @param array the array to build a String representation for
	 * @return a String representation of <code>array</code>
	 */
	public static String nullSafeToString(float[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			} else {
				sb.append(ARRAY_ELEMENT_SEPARATOR);
			}

			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * Return a String representation of the contents of the specified array.
	 * <p>
	 * The String representation consists of a list of the array's elements, enclosed in curly braces (<code>"{}"</code>
	 * ). Adjacent elements are separated by the characters <code>", "</code> (a comma followed by a space). Returns
	 * <code>"null"</code> if <code>array</code> is <code>null</code>.
	 *
	 * @param array the array to build a String representation for
	 * @return a String representation of <code>array</code>
	 */
	public static String nullSafeToString(int[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			} else {
				sb.append(ARRAY_ELEMENT_SEPARATOR);
			}
			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * Return a String representation of the contents of the specified array.
	 * <p>
	 * The String representation consists of a list of the array's elements, enclosed in curly braces (<code>"{}"</code>
	 * ). Adjacent elements are separated by the characters <code>", "</code> (a comma followed by a space). Returns
	 * <code>"null"</code> if <code>array</code> is <code>null</code>.
	 *
	 * @param array the array to build a String representation for
	 * @return a String representation of <code>array</code>
	 */
	public static String nullSafeToString(long[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			} else {
				sb.append(ARRAY_ELEMENT_SEPARATOR);
			}
			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * Return a String representation of the contents of the specified array.
	 * <p>
	 * The String representation consists of a list of the array's elements, enclosed in curly braces (<code>"{}"</code>
	 * ). Adjacent elements are separated by the characters <code>", "</code> (a comma followed by a space). Returns
	 * <code>"null"</code> if <code>array</code> is <code>null</code>.
	 *
	 * @param array the array to build a String representation for
	 * @return a String representation of <code>array</code>
	 */
	public static String nullSafeToString(short[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			} else {
				sb.append(ARRAY_ELEMENT_SEPARATOR);
			}
			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	// Null
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Class used as a null placeholder where <code>null</code> has another meaning.
	 * </p>
	 * <p>
	 * For example, in a <code>HashMap</code> the {@link java.util.HashMap#get(java.lang.Object)} method returns
	 * <code>null</code> if the <code>Map</code> contains <code>null</code> or if there is no matching key. The
	 * <code>Null</code> placeholder can be used to distinguish between these two cases.
	 * </p>
	 * <p>
	 * Another example is <code>Hashtable</code>, where <code>null</code> cannot be stored.
	 * </p>
	 */
	public static class Null implements Serializable {
		/**
		 * Required for serialization support. Declare serialization compatibility with Commons Lang 1.0
		 *
		 * @see java.io.Serializable
		 */
		private static final long	serialVersionUID	= 7092611880189329093L;

		/**
		 * Restricted constructor - singleton.
		 */
		Null() {
			super();
		}

		/**
		 * <p>
		 * Ensure singleton.
		 * </p>
		 *
		 * @return the singleton value
		 */
		private Object readResolve() {
			return ObjectUtils.NULL;
		}
	}

}
