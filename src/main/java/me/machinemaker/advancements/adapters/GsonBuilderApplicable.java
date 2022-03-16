package me.machinemaker.advancements.adapters;

import com.google.gson.GsonBuilder;

/**
 * Something that can be applied to a {@link GsonBuilder}
 */
public interface GsonBuilderApplicable {

    /**
     * Apply to the builder
     *
     * @param builder the builder to apply to
     */
    void applyTo(GsonBuilder builder);
}
