package lab0.dataframe.values;

public class StringValue extends Value {

    private final String val;

    StringValue() {
        val = "";
    }
    public StringValue(String value){
        val=value;
    }

    @Override
    public String getValue() {
        return val;
    }

    @Override
    public String toString() {
        return val;
    }

    @Override
    public StringValue create(String value) {
        return new StringValue(value);
    }

    @Override
    public StringValue add(Value v) {
        return new StringValue(val+v.toString());

    }

    /**
     * Unsupported
     *
     * @throws UnsupportedOperationException not supported
     */
    @Override
    public StringValue sub(Value v) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported
     *
     * @throws UnsupportedOperationException not supported
     */
    @Override
    public StringValue mul(Value v) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported
     *
     * @throws UnsupportedOperationException not supported
     */
    @Override
    public StringValue div(Value v) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported
     *
     * @throws UnsupportedOperationException not supported
     */
    @Override
    public StringValue pow(Value v) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if values.equals is true
     * is false if v is null or not StringValue
     * @param v to be compared
     * @return if is equal
     */
    @Override
    public boolean eq(Value v) {
        if(v instanceof StringValue)
            return getValue().equals(v.getValue());
        else
            return false;
    }

    /**compares alphabetically using to sting method
     * returns if is before or the same
     */
    @Override
    public boolean lte(Value v) {
        return val.compareTo(v.toString())<=0;
    }

    /**compares alphabetically using to sting method
     * returns if is after or the same
     */
    @Override
    public boolean gte(Value v) {
        return val.compareTo(v.toString())>=0;

    }

    @Override
    public boolean neq(Value v) {
        return !eq(v);
    }
}
