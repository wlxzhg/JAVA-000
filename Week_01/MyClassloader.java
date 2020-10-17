import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class MyClassloader extends ClassLoader {
	public static void main(String[] args) {
		try {
			Class clazz = new MyClassloader().findClass("Hello");
			Object obj = clazz.newInstance();
			clazz.getMethod("hello").invoke(obj);
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
			int length = (int)file.length();
			InputStream input = new FileInputStream(name);
			buf = new byte[length];
			input.read(buf);
			for(int i = 0; i < length; i++) {
				buf[i] = (byte)(255 - buf[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return buf;
	}
}