package Utility;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Tools {

    public static Type getGeneric(SocketInterface socketInterface){

        Type generic = SocketData.class;

        Type[] genericInterfaces = socketInterface.getClass().getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
                for (Type genericType : genericTypes) {
                    generic = genericType;
                }
            }
        }
        return generic;
    }


    public static Object ParseJsonData(String received, Type type) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(received);
        return gson.fromJson(object, type);
    }


}
