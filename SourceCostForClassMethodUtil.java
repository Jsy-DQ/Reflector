package util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import util.exception.runtime.FirstIntegerIndexOutOfBoundsException;
import util.exception.runtime.IllegalFirstParamException;


/**
 * @author Jsy.DQ
 * too lazy to write code
 * gitHubTest
 */
public class SourceCostForClassMethodUtil {
	private static Runtime r = Runtime.getRuntime();
	//example 
	public static void main(String[] args) {
		
		long temp=System.currentTimeMillis();
		Object[][][] paramsAndClasses=getParamsAndClasses(new Object[]{1,"321",123l,3213f});
		Object[][] paramsObj=paramsAndClasses[0];
		Class<?>[][] paramsClass=(Class[][]) paramsAndClasses[1];
	}

	// 首个为数字
	public static Object[][][] getParamsAndClasses(Object... params) {
		Object[] obj = params;
		if (obj == null)
			return null;
		if (obj.getClass().equals(int.class))
			throw new IllegalFirstParamException(
					"the type of firstParam must be int.class to differentiate constructorParams and methodParams");
		if ((int) obj[0] > obj.length - 1 || (int) obj[0] < 0)
			throw new FirstIntegerIndexOutOfBoundsException("IndexOutOfRange");
		// 构造方法参数还是方法参数|参数还是参数类型 |第几个
		Object[][][] paramsAndClasses = new Object[2][][];
		paramsAndClasses[0]=new Object[2][];
		//定义第二维的类型，强转准备
		paramsAndClasses[1]=new Class[2][];
		if ((int) obj[0] == 0) {
			paramsAndClasses[0][0] = null;
			paramsAndClasses[1][0] = null;
		}
		paramsAndClasses[0][0] = new Object[(int) obj[0]];
		paramsAndClasses[1][0] = new Class[(int) obj[0]];
		if ((int) obj[0] != 0) {
			for (int i = 0; i < paramsAndClasses[0][0].length; i++) {
				paramsAndClasses[0][0][i] = obj[i + 1];
				paramsAndClasses[1][0][i] = obj[i + 1].getClass();
			}
		}
		if ((int) obj[0] == obj.length - 1) {
			paramsAndClasses[0][1] = null;
			paramsAndClasses[1][1] = null;
		} else {
			paramsAndClasses[0][1] = new Object[obj.length - (int) obj[0] - 1];
			paramsAndClasses[1][1] = new Class[obj.length - (int) obj[0] - 1];
		}
		if ((int) obj[0] != obj.length - 1)
			for (int i = 0; i < paramsAndClasses[0][1].length; i++) {
				paramsAndClasses[0][1][i] = obj[(int) obj[0] + i];
				paramsAndClasses[1][1][i] = obj[(int) obj[0] + i].getClass();
			}

		return paramsAndClasses;
	}
	
	public static Object doDeclearedReflect(Class<?> targetClass, String methodName, Object... params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException{
		if(targetClass==null||methodName==null)
			throw new NullPointerException();
		Object[][][] paramsAndClasses=getParamsAndClasses(params);
		Object[][] paramsObj=paramsAndClasses[0];
		Class<?>[][] paramsClass=(Class[][]) paramsAndClasses[1];
		return targetClass.getDeclaredMethod(methodName, paramsClass[1]).invoke(targetClass.getConstructor(paramsClass[0]).newInstance(paramsObj[0]), paramsObj[1]);
	}
	public static Object doDeclearedReflect(Object targetObj, String methodName, Object... params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException{
		if(targetObj==null||methodName==null)
			throw new NullPointerException();
		Class<?> targetClass=targetObj.getClass();
		Object[][][] paramsAndClasses=getParamsAndClasses(params);
		Object[][] paramsObj=paramsAndClasses[0];
		Class<?>[][] paramsClass=(Class[][]) paramsAndClasses[1];
		return targetClass.getDeclaredMethod(methodName, paramsClass[1]).invoke(targetObj, paramsObj[1]);
	}

	

}
