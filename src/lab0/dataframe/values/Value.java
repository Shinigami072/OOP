package lab0.dataframe.values;


import java.lang.reflect.InvocationTargetException;

public abstract class Value implements Cloneable {

    public static ValueBuilder builder(Class<? extends Value> c) {
        return new ValueBuilder(c);
    }

    /**
     * getStoredValue
     *
     * @return Stored Value
     */
    public abstract Object getValue();

    /**
     * toString
     *
     * @return String Representation
     */
    public abstract String toString();

    /**
     * create
     *
     * @return Value parse string and create Value Representation
     */
    public abstract Value create(String s);

    /**
     * implementation dependent
     *
     * @return create new Value adding the two calues together
     */
    public abstract Value add(Value v);

    /**
     * implementation dependent
     * @return create new Value subtracting the two calues together
     */
    public abstract Value sub(Value v);

    /**
     * implementation dependent
     * @return create new Value multiplying the two calues together
     */
    public abstract Value mul(Value v);

    /**
     * implementation dependent
     * @return create new Value dic=viding the two calues together
     */
    public abstract Value div(Value v);

    /**
     * implementation dependent
     * @return create new Value being current Value to the power of the other
     */
    public abstract Value pow(Value v);

    /**
     * implementation dependent
     * @return return if both values are equal, if vaLues are not of same type always false
     */
    public abstract boolean eq(Value v);

    /**
     * implementation dependent
     * @return return if both values are less or  equal
     */
    public abstract boolean lte(Value v);

    /**
     * implementation dependent
     * @return return if both values are greater or  equal
     */
    public abstract boolean gte(Value v);

    /**
     * implementation dependent
     * @return return if both values are not  equal
     */
    public abstract boolean neq(Value v);

    public boolean equals(Object other) {
        if (!(other instanceof Value))
            return false;
        else
            return getValue().equals(((Value) other).getValue());
    }

    public int hashCode() {
        return getValue().hashCode();
    }

    @Override
    public Value clone() throws CloneNotSupportedException {
        return (Value) super.clone();
    }

    public static class ValueBuilder {
        Class<? extends Value> typ;

        ValueBuilder(Class<? extends Value> c) {
            typ = c;
        }

        public Value build(String data) {
            try {
                return (Value) typ.getMethod("create", String.class).invoke(typ.newInstance(), data);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            throw new RuntimeException();
        }
    }
}