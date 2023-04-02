package me.machinemaker.datapacks.advancements.conditions.entity;

import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.conditions.misc.TagCondition;
import net.kyori.adventure.key.Key;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record DamageSourceConditionImpl(
        List<TagCondition<Key>> tags, // TODO damage source API
        EntityCondition directEntity,
        EntityCondition sourceEntity
) implements DamageSourceCondition {

    static final DamageSourceCondition ANY = new DamageSourceConditionImpl(Collections.emptyList(), EntityCondition.conditionType().any(), EntityCondition.conditionType().any());
    static final ConditionType<DamageSourceCondition> TYPE = ConditionType.create(DamageSourceCondition.class, ANY, DamageSourceConditionImpl.class);

    @Override
    public boolean isAny() {
        return this.equals(ANY);
    }

    @Override
    public DamageSourceCondition.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "DamageSourceCondition{ANY}";
        }
        return "DamageSourceCondition{" +
                "tags=" + this.tags +
                ", directEntity=" + this.directEntity +
                ", sourceEntity=" + this.sourceEntity +
                '}';
    }

    static final class BuilderImpl implements DamageSourceCondition.Builder {

        private final List<TagCondition<Key>> tags = new ArrayList<>();
        private EntityCondition directEntity = EntityCondition.conditionType().any();
        private EntityCondition sourceEntity = EntityCondition.conditionType().any();

        BuilderImpl() {
        }

        private BuilderImpl(final DamageSourceCondition condition) {
            this.tags.addAll(condition.tags());
            this.directEntity = condition.directEntity();
            this.sourceEntity = condition.sourceEntity();
        }

        @Override
        public List<TagCondition<Key>> tags() {
            return this.tags;
        }

        @Override
        public DamageSourceCondition.Builder tags(final List<TagCondition<Key>> tags) {
            this.tags.clear();
            this.tags.addAll(tags);
            return this;
        }

        @Override
        public EntityCondition directEntity() {
            return this.directEntity;
        }

        @Override
        public DamageSourceCondition.Builder directEntity(final EntityCondition directEntity) {
            this.directEntity = directEntity;
            return this;
        }

        @Override
        public EntityCondition sourceEntity() {
            return this.sourceEntity;
        }

        @Override
        public DamageSourceCondition.Builder sourceEntity(final EntityCondition sourceEntity) {
            this.sourceEntity = sourceEntity;
            return this;
        }

        @Override
        public DamageSourceCondition build() {
            return new DamageSourceConditionImpl(this.tags, this.directEntity, this.sourceEntity);
        }
    }
}
