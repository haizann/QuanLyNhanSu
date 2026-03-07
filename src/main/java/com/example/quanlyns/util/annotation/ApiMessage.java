package com.example.quanlyns.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// cho annotation nay hd trong qtr run du an
@Retention(RetentionPolicy.RUNTIME)

// pham vi cua annotation nay la method
@Target(ElementType.METHOD)
public @interface ApiMessage {
    String value();

}
