package me.machinemaker.datapacks.advancements.conditions.world;

import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.common.range.DoubleRange;

record DistanceConditionImpl(DoubleRange x, DoubleRange y, DoubleRange z, DoubleRange horizontal, DoubleRange absolute) implements DistanceCondition {

    static final DistanceCondition ANY = new DistanceConditionImpl(DoubleRange.conditionType().any(), DoubleRange.conditionType().any(), DoubleRange.conditionType().any(), DoubleRange.conditionType().any(), DoubleRange.conditionType().any());
    static final ConditionType<DistanceCondition> TYPE = ConditionType.create(DistanceCondition.class, ANY, DistanceConditionImpl.class);

    @Override
    public boolean isAny() {
        return this.equals(ANY);
    }

    @Override
    public DistanceCondition.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "DistanceCondition{ANY}";
        }
        return "DistanceCondition{" + "x=" + this.x + ", y=" + this.y + ", z=" + this.z + ", horizontal=" + this.horizontal + ", absolute=" + this.absolute + '}';
    }

    static final class BuilderImpl implements DistanceCondition.Builder {

        private DoubleRange x = DoubleRange.conditionType().any();
        private DoubleRange y = DoubleRange.conditionType().any();
        private DoubleRange z = DoubleRange.conditionType().any();
        private DoubleRange horizontal = DoubleRange.conditionType().any();
        private DoubleRange absolute = DoubleRange.conditionType().any();

        BuilderImpl() {
        }

        BuilderImpl(final DistanceCondition condition) {
            this.x = condition.x();
            this.y = condition.y();
            this.z = condition.z();
            this.horizontal = condition.horizontal();
            this.absolute = condition.absolute();
        }

        @Override
        public DoubleRange x() {
            return this.x;
        }

        @Override
        public DistanceCondition.Builder x(final DoubleRange x) {
            this.x = x;
            return this;
        }

        @Override
        public DoubleRange y() {
            return this.y;
        }

        @Override
        public DistanceCondition.Builder y(final DoubleRange y) {
            this.y = y;
            return this;
        }

        @Override
        public DoubleRange z() {
            return this.z;
        }

        @Override
        public DistanceCondition.Builder z(final DoubleRange z) {
            this.z = z;
            return this;
        }

        @Override
        public DoubleRange horizontal() {
            return this.horizontal;
        }

        @Override
        public DistanceCondition.Builder horizontal(final DoubleRange horizontal) {
            this.horizontal = horizontal;
            return this;
        }

        @Override
        public DoubleRange absolute() {
            return this.absolute;
        }

        @Override
        public DistanceCondition.Builder absolute(final DoubleRange absolute) {
            this.absolute = absolute;
            return this;
        }

        @Override
        public DistanceCondition build() {
            return new DistanceConditionImpl(this.x, this.y, this.z, this.horizontal, this.absolute);
        }
    }


}
