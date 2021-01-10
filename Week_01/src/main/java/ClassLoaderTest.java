import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassLoaderTest extends ClassLoader {

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //遍历搜索 java.class.path
        InputStream in = null;
        String classpaths = System.getProperty("java.class.path");
        String splitChar = System.getProperty("os.name").toLowerCase().contains("win") ? ";" : ":";
        String[] classpathArrays = classpaths.split(splitChar);
        for (String classpath : classpathArrays) {
            File file = new File(classpath + File.separatorChar + name + ".xlass");
            if(file.exists()) {
                try {
                    in = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if(in == null) throw new ClassNotFoundException(name);

        //解码
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int readByte = 0;
        while(true) {
            try {
                if (!((readByte = in.read(buf)) != -1)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < readByte; i++) {
                byte b = buf[i];
                out.write(255 - b);
            }
        }

        //加载
        return defineClass(name, out.toByteArray(), 0, out.size());
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ClassLoaderTest clt = new ClassLoaderTest();
        Class<?> hello = clt.findClass("Hello");
        Object o = hello.getDeclaredConstructor().newInstance();
        Method helloMethod = hello.getDeclaredMethod("hello");
        helloMethod.invoke(o);
    }
}
