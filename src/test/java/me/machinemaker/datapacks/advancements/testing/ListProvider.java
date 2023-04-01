package me.machinemaker.datapacks.advancements.testing;

import com.google.common.base.Preconditions;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ListProvider<T> implements Provider<T> {

    private final List<T> options;
    private final boolean addNull;

    public ListProvider(final List<T> options) {
        this(options, false);
    }

    public ListProvider(final List<T> options, final boolean addNull) {
        Preconditions.checkArgument(!options.isEmpty(), "List is empty");
        this.options = options;
        this.addNull = addNull;
    }

    @Override
    public @Nullable T get() {
        if (this.addNull) {
            final int selected = this.integer(this.options.size() + 1);
            if (selected == this.options.size()) {
                return null;
            }
            return this.options.get(selected);
        } else {
            return this.randomElement(this.options);
        }
    }
}
