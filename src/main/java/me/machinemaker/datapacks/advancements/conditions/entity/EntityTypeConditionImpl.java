package me.machinemaker.datapacks.advancements.conditions.entity;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import me.machinemaker.datapacks.advancements.adapters.factories.HierarchyAdapterFactory;
import me.machinemaker.datapacks.advancements.adapters.util.GsonContext;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.tags.EntityTag;
import org.bukkit.entity.EntityType;

interface EntityTypeConditionImpl extends EntityTypeCondition {

    EntityTypeCondition ANY = (EntityTypeConditionImpl) ctx -> JsonNull.INSTANCE;
    ConditionType<EntityTypeCondition> TYPE = ConditionType.create(EntityTypeCondition.class, ANY, new AdapterFactory());

    @Override
    default boolean isAny() {
        return this.equals(ANY);
    }

    JsonElement serialize(JsonSerializationContext ctx);

    record TypeImpl(
        org.bukkit.entity.EntityType type
    ) implements EntityTypeConditionImpl, EntityTypeCondition.Type {

        @Override
        public JsonElement serialize(final JsonSerializationContext ctx) {
            return ctx.serialize(this.type);
        }

        static EntityTypeCondition.Type deserialize(final JsonDeserializationContext ctx, final String string) {
            Preconditions.checkArgument(!string.startsWith("#"), string + " starts with a '#'");
            return EntityTypeCondition.type(ctx.deserialize(new JsonPrimitive(string), EntityType.class));
        }

        @Override
        public String toString() {
            if (this.isAny()) {
                return "EntityTypeCondition$Type{ANY}";
            }
            return "EntityTypeCondition$Type{" +
                "type=" + this.type +
                '}';
        }
    }

    record TagImpl(
        EntityTag tag
    ) implements EntityTypeConditionImpl, EntityTypeCondition.Tag {

        @Override
        public JsonElement serialize(final JsonSerializationContext ctx) {
            return new JsonPrimitive("#" + ctx.serialize(this.tag).getAsString());
        }

        static EntityTypeCondition.Tag deserialize(final JsonDeserializationContext ctx, final String string) {
            Preconditions.checkArgument(string.startsWith("#"), string + " does not start with '#'");
            return EntityTypeCondition.tag(ctx.deserialize(new JsonPrimitive(string.substring(1)), EntityTag.class));
        }

        @Override
        public String toString() {
            if (this.isAny()) {
                return "EntityTypeCondition$Tag{ANY}";
            }
            return "EntityTypeCondition$Tag{" +
                "tag=" + this.tag +
                '}';
        }
    }

    class AdapterFactory extends HierarchyAdapterFactory<EntityTypeCondition> {

        AdapterFactory() {
            super(EntityTypeCondition.class);
        }

        @Override
        public void write(final Gson gson, final JsonWriter out, final EntityTypeCondition value) throws IOException {
            if (value.isAny()) {
                out.nullValue();
            } else {
                Streams.write(((EntityTypeConditionImpl) value).serialize(new GsonContext(gson)), out);
            }
        }

        @Override
        public EntityTypeCondition read(final Gson gson, final JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return ANY;
            } else if (in.peek() == JsonToken.STRING) {
                final String string = in.nextString();
                final GsonContext context = new GsonContext(gson);
                if (string.startsWith("#")) {
                    return TagImpl.deserialize(context, string);
                } else {
                    return TypeImpl.deserialize(context, string);
                }
            } else {
                throw new JsonParseException("Expected %s, got %s".formatted(JsonToken.STRING, in.peek()));
            }
        }
    }
}
