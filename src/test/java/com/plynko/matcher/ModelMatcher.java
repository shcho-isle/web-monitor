package com.plynko.matcher;

/**
 * This class wrap every entity by Wrapper before assertEquals in order to compare them by comparator
 * Default comparator compare by String.valueOf(entity)
 *
 * @param <T> : Entity
 */
public class ModelMatcher<T> {

    private final Comparator<T> comparator;
    private final Class<T> entityClass;

    public interface Comparator<T> {
        boolean compare(T expected, T actual);
    }

    private ModelMatcher(Class<T> entityClass, Comparator<T> comparator) {
        this.entityClass = entityClass;
        this.comparator = comparator;
    }

    public static <T> ModelMatcher<T> of(Class<T> entityClass, Comparator<T> comparator) {
        return new ModelMatcher<>(entityClass, comparator);
    }
}
