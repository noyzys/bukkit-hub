package dev.noyzys.arhelion.hub.api.helper.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: NoyZys on 20:48, 19.10.2019
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface CommandExtenderInfo {

    String name();

    String[] aliases() default {};
}

