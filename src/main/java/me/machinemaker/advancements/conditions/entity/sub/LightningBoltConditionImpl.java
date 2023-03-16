package me.machinemaker.advancements.conditions.entity.sub;

import com.google.gson.TypeAdapterFactory;
import me.machinemaker.advancements.adapters.builders.Builders;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.adapters.factories.ConditionAdapterFactory;
import me.machinemaker.advancements.conditions.ConditionType;
import me.machinemaker.advancements.conditions.entity.EntityCondition;
import me.machinemaker.advancements.conditions.range.IntegerRange;

record LightningBoltConditionImpl(
    IntegerRange blocksSetOnFire,
    EntityCondition entityStruck
) implements EntitySubConditionImpl, LightningBoltCondition {

    static final LightningBoltCondition ANY = new LightningBoltConditionImpl(IntegerRange.conditionType().any(), EntityCondition.conditionType().any());
    static final ConditionType<LightningBoltCondition> INTERNAL_TYPE = ConditionType.create(LightningBoltCondition.class, ANY, LightningBoltConditionImpl::requiredGson);
    static final TypeAdapterFactory FACTORY = ConditionAdapterFactory.record(INTERNAL_TYPE, LightningBoltConditionImpl.class);
    static final GsonBuilderApplicable REQUIRED_GSON = Builders.collection(
        IntegerRange.requiredGson(),
        EntityCondition.requiredGson(),
        Builders.factory(FACTORY)
    );

    private static GsonBuilderApplicable requiredGson() {
        return REQUIRED_GSON;
    }

    @Override
    public LightningBoltCondition.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @Override
    public String toString() {
        if (this.equals(ANY)) {
            return "LightningBoltCondition{ANY}";
        }
        return "LightningBoltCondition{" +
            "blocksSetOnFire=" + this.blocksSetOnFire +
            ", entityStruck=" + this.entityStruck +
            '}';
    }

    static final class BuilderImpl implements LightningBoltCondition.Builder {

        private IntegerRange blocksSetOnFire = IntegerRange.conditionType().any();
        private EntityCondition entityStruck = EntityCondition.conditionType().any();

        BuilderImpl() {
        }

        public BuilderImpl(final LightningBoltCondition condition) {
            this.blocksSetOnFire = condition.blocksSetOnFire();
            this.entityStruck = condition.entityStruck();
        }

        @Override
        public IntegerRange blocksSetOnFire() {
            return this.blocksSetOnFire;
        }

        @Override
        public LightningBoltCondition.Builder blocksSetOnFire(final IntegerRange blocksSetOnFire) {
            this.blocksSetOnFire = blocksSetOnFire;
            return this;
        }

        @Override
        public EntityCondition entityStruck() {
            return this.entityStruck;
        }

        @Override
        public LightningBoltCondition.Builder entityStruck(final EntityCondition entityStruck) {
            this.entityStruck = entityStruck;
            return this;
        }

        @Override
        public LightningBoltCondition build() {
            return new LightningBoltConditionImpl(this.blocksSetOnFire, this.entityStruck);
        }
    }
}
