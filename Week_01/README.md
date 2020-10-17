学习笔记
## 课后作业：
### 第一题
自己写一个简单的 Hello.java，里面需要涉及基本类型，四则运行，if 和 for，然后自己分析一下对应的字节码，有问题群里讨论
Hello.java代码：
```
public class Hello {
	public static void main(String[] args) {
		int a = 1;
		int b = 10;
		int c = a + b;

		if(c == 11) {
			System.out.println("1 + 10 = 11");
		}

		int sum = 0;
		for(int i = 1; i < 100; i++) {
			sum += i;
		}
		System.out.println(sum);
	}
}
```
javac Hello.java 得到Hello.class文件
javap -c Hello.class得到如下结果：
```
Compiled from "Hello.java"
public class Hello {
  public Hello();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: iconst_1                        //将1放入操作数栈                          
       1: istore_1                        //将栈顶的1放入变量表槽位1，变量a
       2: bipush        10                //将10放入操作数栈
       4: istore_2                        //将栈顶的10放入变量表槽位2,变量b
       5: iload_1                         //将1载入操作数栈
       6: iload_2                         //将10载入操作数栈
       7: iadd                            //执行相加操作
       8: istore_3                        //将结果放入变量表槽位3，变量c
       9: iload_3                         //将变量c载入栈
      10: bipush        11                //将数字11载入栈
      12: if_icmpne     23                //如果c不等于11跳到23行执行
      15: getstatic     #2                  // Field java/lang/System. out:Ljava/io/PrintStream;
      18: ldc           #3                  // String 1 + 10 = 11
      20: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      23: iconst_0                        //将0入栈
      24: istore        4                 //将栈顶的0放入槽位4，即变量sum
      26: iconst_1                        //将1入栈
      27: istore        5                 //将栈顶的1放入槽位5，即变量i
      29: iload         5                 //这3句比较i和100的大小，相等时跳到100行执行
      31: bipush        100
      33: if_icmpge     49
      36: iload         4                 //sum入栈
      38: iload         5                 //i入栈
      40: iadd                            //sum+i
      41: istore        4                 //将结果赋给sum
      43: iinc          5, 1              //i++
      46: goto          29                //跳转到29行继续执行
      49: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
      52: iload         4
      54: invokevirtual #5                  // Method java/io/PrintStream.println:(I)V
      57: return
}
```

### 第二题
自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内
容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。

```
public class MyClassloader extends ClassLoader {
	public static void main(String[] args) {
		try {
			Class clazz = new MyClassloader().findClass("Hello"); //加载类
			Object obj = clazz.newInstance();                     //实例化一个对象
			clazz.getMethod("hello").invoke(obj);                 //使用反射执行其hello方法
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException  e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] bytes = readClassFile(name + ".xlass");
		return defineClass(name,bytes,0,bytes.length);
	}

	private byte[] readClassFile(String name) {
		byte[] buf = null;
		try {
			File file = new File(name);
			int length = (int)file.length();  //获取文件字节长度
			InputStream input = new FileInputStream(name);
			buf = new byte[length];
			input.read(buf);
			for(int i = 0; i < length; i++) {
				buf[i] = (byte)(255 - buf[i]); //处理得到正确的字节码
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return buf;
	}
}
```

### 第三题
画一张图，展示 Xmx、Xms、Xmn、Meta、DirectMemory、Xss 这些内存参数的关系
![内存参数的关系.png](https://raw.githubusercontent.com/wlxzhg/JAVA-000/main/Week_01/%E5%86%85%E5%AD%98%E5%8F%82%E6%95%B0%E7%9A%84%E5%85%B3%E7%B3%BB.png)