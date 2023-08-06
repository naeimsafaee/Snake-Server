package Utility;

public interface SocketObjectAndDataInterface<T> extends SocketInterface{
    void data(T object , SocketData socket);
}
