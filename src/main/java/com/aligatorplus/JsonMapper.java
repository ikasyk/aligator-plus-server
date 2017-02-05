package com.aligatorplus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Project AligatorPlus
 * Created by igor, 05.02.17 21:05
 */

public class JsonMapper extends ObjectMapper {
    public JsonMapper() {
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }
}
