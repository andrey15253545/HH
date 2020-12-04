package common.storage;

public interface Storage {

    void add(Class clazz, Object object);

    Object get(Class clazz);

}
