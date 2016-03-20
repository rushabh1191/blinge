package com.blinge.deliveryguy.helpers;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.HashMap;

/**
 * Created by rushabh on 20/03/16.
 */
public class BlingeParseObject extends ParseObject {

    public BlingeParseObject(){

    }

    public BlingeParseObject(ParseProxyObject object) {

        setObjectId(object.getObjectId());
        HashMap<String, Object> hashMap = object.getValues();

        for (String key : hashMap.keySet()) {
            Class classType = hashMap.get(key).getClass();
            if (classType == byte[].class || classType == String.class || classType == Float.class ||
                    classType == Integer.class || classType == Boolean.class || classType == HashMap.class) {
                put(key, hashMap.get(key));
            } else if (classType == ParseUser.class) {
                ParseProxyObject parseUserObject = new ParseProxyObject((ParseObject) hashMap.get(key));
                put(key, parseUserObject);
            } else {
                // You might want to add more conditions here, for embedded ParseObject, ParseFile, etc.
            }
        }

    }
}
