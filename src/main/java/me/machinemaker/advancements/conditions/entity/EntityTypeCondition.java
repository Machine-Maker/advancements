package me.machinemaker.advancements.conditions.entity;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import me.machinemaker.advancements.GsonHelper;
import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.adapters.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.tags.EntityTag;
import org.bukkit.Tag;
import org.bukkit.entity.EntityType;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@JsonAdapter(EntityTypeCondition.Adapter.class)
public abstract class EntityTypeCondition implements Condition<EntityTypeCondition> {

    public static final GsonBuilderApplicable BUILDER_APPLICABLE = Adapters.of(Adapters.ENTITY_TYPE_ADAPTER, Adapters.ENTITY_TAG_ADAPTER);
    public static final EntityTypeCondition ANY = new EntityTypeCondition() {

        @Override
        JsonElement serializeToJson(GsonHelper helper) {
            return JsonNull.INSTANCE;
        }

        @Override
        public EntityTypeCondition any() {
            return this;
        }
    };

    @Contract(value = "_ -> new", pure = true)
    public static EntityTypeCondition of(EntityType type) {
        return new TypeCondition(type);
    }

    @Contract(value = "_ -> new", pure = true)
    public static EntityTypeCondition of(Tag<EntityType> tag) {
        return new TagCondition(tag instanceof EntityTag entityTag ? entityTag : new EntityTag(tag));
    }

    @ApiStatus.Internal
    public static Collection<Class<? extends EntityTypeCondition>> types() {
        return Set.of(EntityTypeCondition.class, TypeCondition.class, TagCondition.class);
    }

    abstract JsonElement serializeToJson(GsonHelper helper);

    @Override
    public EntityTypeCondition any() {
        return ANY;
    }

    @JsonAdapter(EntityTypeCondition.Adapter.class)
    public static class TypeCondition extends EntityTypeCondition {
        private final EntityType type;

        TypeCondition(EntityType type) {
            this.type = type;
        }

        @Override
        JsonElement serializeToJson(GsonHelper helper) {
            return helper.toJsonTree(this.type);
        }

        static TypeCondition deserializeFromJson(GsonHelper helper, String string) {
            Preconditions.checkArgument(!string.startsWith("#"), string + " starts with a '#'");
            return new TypeCondition(helper.fromJsonTree(new JsonPrimitive(string), EntityType.class));
        }

        @Override
        public String toString() {
            if (this.isAny()) {
                return "TypeCondition{ANY}";
            }
            return "TypeCondition{" +
                    "type=" + this.type +
                    '}';
        }

        @Override
        public boolean equals(@Nullable Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TypeCondition that = (TypeCondition) o;
            return type == that.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(type);
        }
    }

    @JsonAdapter(EntityTypeCondition.Adapter.class)
    public static class TagCondition extends EntityTypeCondition {

        private final EntityTag tag;

        TagCondition(EntityTag tag) {
            this.tag = tag;
        }

        @Override
        JsonElement serializeToJson(GsonHelper helper) {
            return new JsonPrimitive("#" + helper.toJsonTree(this.tag).getAsString());
        }

        static TagCondition deserializeFromJson(GsonHelper helper, String string) {
            Preconditions.checkArgument(string.startsWith("#"), string + " does not start with '#'");
            return new TagCondition(helper.fromJsonTree(new JsonPrimitive(string.substring(1)), EntityTag.class));
        }

        @Override
        public String toString() {
            if (this.isAny()) {
                return "TagCondition{ANY}";
            }
            return "TagCondition{" +
                    "tag=" + this.tag +
                    '}';
        }

        @Override
        public boolean equals(@Nullable Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TagCondition that = (TagCondition) o;
            return tag.equals(that.tag);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tag);
        }
    }

    static class Adapter extends TypeAdapter<EntityTypeCondition> {

        private final GsonHelper HELPER = new GsonHelper(BUILDER_APPLICABLE);

        @Override
        public void write(JsonWriter out, EntityTypeCondition value) throws IOException {
            HELPER.toWriter(out, value.serializeToJson(HELPER));
        }

        @Override
        public EntityTypeCondition read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.STRING) {
                String string = in.nextString();
                if (string.startsWith("#")) {
                    return TagCondition.deserializeFromJson(HELPER, string);
                } else {
                    return TypeCondition.deserializeFromJson(HELPER, string);
                }
            } else {
                throw new JsonParseException(in.peek() + " was not expected here");
            }
        }
    }
}
