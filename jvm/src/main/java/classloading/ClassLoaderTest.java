package classloading;

import java.io.IOException;
import java.io.InputStream;

public class ClassLoaderTest {
    public static void main(String[] args){
        ClassLoader myLoader2 = ClassLoader.getSystemClassLoader();
        ClassLoader myLoader = new ClassLoader(){
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try{
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if(is == null) return super.loadClass(name);
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name,b,0,b.length);
                } catch(IOException e){
                    throw new ClassNotFoundException(name);
                }
            }
        };
        try {
            Object obj = myLoader.loadClass("classloading.ClassLoaderTest").newInstance();
            Object obj2 = myLoader2.loadClass("classloading.ClassLoaderTest").newInstance();

            System.out.println(obj.getClass());
            System.out.println(obj instanceof classloading.ClassLoaderTest);
            System.out.println(obj2.getClass());
            System.out.println(obj2 instanceof classloading.ClassLoaderTest);
        } catch (IllegalAccessException e){}
        catch (InstantiationException e){}
        catch(ClassNotFoundException e){}


    }
}
