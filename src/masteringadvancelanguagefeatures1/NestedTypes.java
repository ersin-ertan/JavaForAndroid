package masteringadvancelanguagefeatures1;

public class NestedTypes {
	
	private static int i = 0;
	private static void m1(){
		System.out.println(i);
	}
	
	static void m2(){
		StaticClass.accessWrapperClassStaticField(); // since accessWraperClassStaticfield is static, must reference with class name
	}
	
	static class StaticClass{
		static void accessWrapperClassStaticField(){ // may access only static fields and methods
			i = 1;
			m1();
		}
		void accessWrapperClassStaticMethod(){
			m2();			
		}
	}
	
	private int ii = 0;
	private void m11(){
		System.out.println(ii);
	}
	
	void m22(){
		// need an instance to ref
	}
	
	class MemberClass{
		void accessWrapperClassField(){
			ii = 1;
			m11();
		}
		
		void accessWrapperClassMethod(){
			m22();
		}
	}
		
	public static void main(String...strings){
		Driver d = new Driver();
		d.create();
		
		AnonymousClass a = 
		new AnonymousClass() {
			private int var = 1;
			
			String retString(){return "a string";}
			
			@Override
			void method() {
				System.out.println("CReated anon class" + var);
				
			}
		};
		
		// or to call the method directly with the creation
		
		new AnonymousClass() {
			private int var = 1;
			
			String retString(){return "a string";}
			
			@Override
			void method() {
				System.out.println("CReated a new anon class" + var);	
			}
		}.method();
		
		a.method(); // method is now an interface, we see the same anon class but with two different overrided methods
		
		String s;
		System.out.println(s = new AnonymousClass() { // example of creating an anon class then populating a string with the return variable of a method called from the class, inside a function
			
			@Override
			void method() {
				System.out.println("CReated a new anon class" + 3);	
			}
			
			@Override
			String retString(){
				return "a string";
			}
		}.retString());
	
		// still main scope here *reminder*
	
		final int b = 8;
		
		class LocalClass{
			int i = b + 1; // cannot refer to a non-final variable defined in an other method, because the data may change
			
			public void printI(){
				System.out.println(i);
			}
		}
		
		new LocalClass().printI(); // instances of inner class contain implicit references to their outer class, the way that they can reference the variables, prolonging the existence of an inner class
		// such as storing its reference in a static variable, can result in a memory leak in which the outer instance may be referencing a large graph of objects that connect be garbage collector do to the inner class reference.	
	}
}

abstract class AnonymousClass{
	abstract void method();
	abstract String retString();
	
	static int i = 0;
	
	abstract interface inter{ // can only be declared in top level and in auto static
		void meth();
		int ii = i; 
		class AutoStaticClass extends AnonymousClass implements inter{ // wtf sauce, inner class in interface accessing public method from top level interface, which is overriden in the class itself
			
			public void method(){
				meth();
			}

			@Override
			public void meth() {
				System.out.println(i);
				as.meth(); // ref the created class in the class structure
			}

			@Override
			String retString() {
				return Integer.toString(as.i);
			}
		} // rare case
		AutoStaticClass as = new AutoStaticClass(); // really rare, static public class constructed in the interface
	}
}

class Driver{
	public void create(){
		NestedTypes nestedTypes = new NestedTypes();
		nestedTypes.new MemberClass().accessWrapperClassField(); // this is for inner class creation
		
		NestedTypes.MemberClass mc = new NestedTypes().new MemberClass(); // must call the new parent class first, then the subclass via the .new
		mc.accessWrapperClassField();		
	}
}
