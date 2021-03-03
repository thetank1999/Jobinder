/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ModelValidator {

    private static final Map<Class<? extends Annotation>, Function<Field, Function<Object, Map.Entry<String, String>>>> MAPPINGS = new HashMap<>();

    static {
        MAPPINGS.put(RegexMatch.class, (Function<Field, Function<Object, Map.Entry<String, String>>>) (Field field) -> (Object object) -> {
            try {
                field.setAccessible(true);
                boolean isValid = ((String) field.get(object)).matches(field.getAnnotation(RegexMatch.class).regex());
                return isValid ? null : new AbstractMap.SimpleEntry<>(field.getAnnotation(RegexMatch.class).name().isEmpty() ? field.getName() : field.getAnnotation(RegexMatch.class).name(), field.getAnnotation(RegexMatch.class).message());
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(ModelValidator.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        });

        MAPPINGS.put(IntMinMax.class, (Function<Field, Function<Object, Map.Entry<String, String>>>) (Field field) -> (Object object) -> {
            try {
                field.setAccessible(true);
                Integer fieldValue = ((Integer) field.get(object));
                boolean isValid = fieldValue <= field.getAnnotation(IntMinMax.class).max() && fieldValue >= field.getAnnotation(IntMinMax.class).min();
                return isValid ? null : new AbstractMap.SimpleEntry<>(field.getAnnotation(IntMinMax.class).name().isEmpty() ? field.getName() : field.getAnnotation(IntMinMax.class).name(), field.getAnnotation(IntMinMax.class).message());
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(ModelValidator.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        });
    }

    public Map<String, String> validate(Object object) {
        Map<String, String> constraints = new HashMap<>();
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            checkConstraints(field, object, constraints);
        }
        return constraints;
    }

    private void checkConstraints(Field field, Object object, Map<String, String> constraints) {
        MAPPINGS.keySet().stream().filter(annotationClazz -> (field.isAnnotationPresent(annotationClazz))).map(annotationClazz -> MAPPINGS.get(annotationClazz).apply(field).apply(object)).filter(constraint -> (constraint != null)).forEach(constraint -> {
            constraints.put(constraint.getKey(), constraint.getValue());
        });
    }
}
