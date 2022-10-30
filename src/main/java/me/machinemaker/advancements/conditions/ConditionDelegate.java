// package me.machinemaker.advancements.conditions;
//
// import com.google.common.reflect.AbstractInvocationHandler;
// import org.checkerframework.checker.nullness.qual.NonNull;
// import org.checkerframework.checker.nullness.qual.Nullable;
// import org.checkerframework.framework.qual.DefaultQualifier;
//
// import java.lang.reflect.Method;
// import java.util.function.Supplier;
//
// @SuppressWarnings("UnstableApiUsage")
// @DefaultQualifier(NonNull.class)
// public class ConditionDelegate<C extends Condition<C>> extends AbstractInvocationHandler {
//
//     private final Supplier<C> conditionSupplier;
//
//     public ConditionDelegate(Supplier<C> conditionSupplier) {
//         this.conditionSupplier = conditionSupplier;
//     }
//
//     @Override
//     protected @Nullable Object handleInvocation(Object proxy, Method method, @Nullable Object[] args) throws Throwable {
//         return this.condition().getClass().getDeclaredMethod(method.getName(), method.getParameterTypes()).invoke(this.condition(), args);
//     }
//
//     private C condition() {
//         return this.conditionSupplier.get();
//     }
//
//     @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
//     @Override
//     public boolean equals(@Nullable Object obj) {
//         return this.condition().equals(obj);
//     }
//
//     @Override
//     public int hashCode() {
//         return this.condition().hashCode();
//     }
//
//     @Override
//     public String toString() {
//         return this.condition().toString();
//     }
// }
