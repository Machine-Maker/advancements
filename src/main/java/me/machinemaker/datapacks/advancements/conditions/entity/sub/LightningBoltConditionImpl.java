package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import com.google.gson.TypeAdapterFactory;
import me.machinemaker.datapacks.common.adapters.factories.InterfaceImplAdapterFactory;
import me.machinemaker.datapacks.advancements.conditions.entity.EntityCondition;
import me.machinemaker.datapacks.common.range.IntegerRange;

record LightningBoltConditionImpl(
    IntegerRange blocksSetOnFire,
    EntityCondition entityStruck
) implements EntitySubConditionImpl, LightningBoltCondition {

    static final String TYPE = "lightning";
    static final TypeAdapterFactory FACTORY = InterfaceImplAdapterFactory.simple(LightningBoltCondition.class, LightningBoltConditionImpl.class);

    @Override
    public LightningBoltCondition.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @Override
    public String serializedType() {
        return TYPE;
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
