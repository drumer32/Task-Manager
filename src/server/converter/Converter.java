package server.converter;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;

public class Converter {

    public static final Type DURATION_TYPE = new TypeToken<Duration>() {}.getType();

    public static final Type LOCAL_DATE_TIME_TYPE = new TypeToken<LocalDateTime>() {}.getType();

    public static GsonBuilder registerAll(GsonBuilder builder) {
        registerLocalDateTime(builder);
        registerDuration(builder);
        return builder;
    }

    public static GsonBuilder registerLocalDateTime(GsonBuilder builder) {
        builder.registerTypeAdapter(LOCAL_DATE_TIME_TYPE, new LocalDateTimeConverter());
        return builder;
    }

    public static GsonBuilder registerDuration(GsonBuilder builder) {
        builder.registerTypeAdapter(DURATION_TYPE, new DurationConverter());
        return builder;
    }
}
