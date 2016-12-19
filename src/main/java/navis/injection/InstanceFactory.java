package navis.injection;

import java.util.Stack;

public class InstanceFactory<T> implements Resettable {
    private final BuildFunction<T> builder;
    private ThreadLocal<Stack<BuildFunction<T>>> overrideBuilder = new ThreadLocal<Stack<BuildFunction<T>>>() {
        @Override
        protected Stack<BuildFunction<T>> initialValue() {
            return new Stack<BuildFunction<T>>();
        }
    };

    public InstanceFactory(BuildFunction<T> builder) {
        this.builder = builder;
    }

    public T make() {
        if (overrideBuilder.get().isEmpty())
            return builder.build();
        return overrideBuilder.get().peek().build();
    }

    public Resettable override(BuildFunction<T> builder) {
        this.overrideBuilder.get().push(builder);
        return this;
    }

    @Override
    public void close() {
        if (!overrideBuilder.get().isEmpty())
            overrideBuilder.get().pop();
    }
}

