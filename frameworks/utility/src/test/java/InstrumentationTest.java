import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;

/**
 * Copyright 2011 the original author
 */

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class InstrumentationTest {
	   private static Instrumentation inst;

	   public static void premain(String options, Instrumentation inst) {
	      InstrumentationTest.inst = inst;

	      System.out.println("options= " + options);

	      // Get all classes currently loaded by VM
	      Class[] loaded = inst.getAllLoadedClasses();


	      //And print them!
	      for (Class clazz : loaded) {
	         System.out.println(clazz);
	      }
	   }

	   public static long sizeOf(Object o) {
	      assert inst != null;

	      return inst.getObjectSize(o);
	   }

	   public static void main(String[] args) {
	      System.out.println("Size of Object: " + sizeOf(new Object()));
	      System.out.println("Size of direct subclass: " + sizeOf(
	            new InstrumentationTest()));
	      System.out.println("Size of Calendar: " + sizeOf(Calendar.getInstance()));

	   }
	}