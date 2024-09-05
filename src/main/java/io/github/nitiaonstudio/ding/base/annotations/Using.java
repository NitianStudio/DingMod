package io.github.nitiaonstudio.ding.base.annotations;

import io.github.nitiaonstudio.ding.data.lang.Languages;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Using {
    Languages[] value() default { Languages.en_us };
}
