package string;

import org.junit.Assert;
import org.junit.Test;

public class StringTest {
	
	@Test
	public void test1(){
		String str = new String("yejing");
		Assert.assertEquals("yejing", str);
	}
	
	@Test
	public void test2(){
		String str = new String("yejing");
		Assert.assertEquals(new String("yejing"), str);
	}
	
	
	@Test
	public void test3(){
		
		// 都是new，用equals
		// 都是直接创建，无论equals还是==都可以
		// 一方位new，一方为直接创建，用equals
		
		String str = new String("yejing");
		String str2 = "yejing";
		String str3 = "yejing";
		String str4 = new String("yejing");
		
		System.out.println(str == str2); // false
		System.out.println(str3 == str2); // true
		System.out.println(str2.equals(str3));// true
		System.out.println(str == str4);// false
		System.out.println(str.equals(str4));// true
		
	}
	
}
