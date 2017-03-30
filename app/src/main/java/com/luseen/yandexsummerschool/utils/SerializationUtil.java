package com.luseen.yandexsummerschool.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Chatikyan on 30.03.2017.
 */

public class SerializationUtil {

    // deserialize to Object from given file
    public static <T> Object deserialize(Class<T> clazz) throws IOException, ClassNotFoundException {
        String fileName = generateFileName(clazz);
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();
        ois.close();
        return object;
    }

    // serialize the given object and save it to file
    public static void serialize(Object object) throws IOException {
        String fileName = generateFileName(object.getClass());
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        fos.close();
    }

    public static String generateFileName(Class clazz) {
        return clazz.getSimpleName() + ".ser";
    }
}
