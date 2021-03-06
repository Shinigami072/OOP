package lab0.dataframe.values;

public class IntegerValue extends NumericValue {

    private Integer value;

    IntegerValue() {
    }
    public IntegerValue(int i){
        value=i;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();

    }

    @Override
    public IntegerValue create(String s) {
        return new IntegerValue(Integer.parseInt(s.trim()));
    }

    /**
     * Works only on numeric types,
     * the result of addition is cast to an int
     * @param v Value to add
     * @return IntegerValue containing result
     * @throws UnsupportedOperationException not implemented
     */
    @Override
    public IntegerValue add(Value v) throws UnsupportedOperationException{
        if(! (v instanceof NumericValue))
            throw new UnsupportedOperationException();

        return new IntegerValue(value + ((NumericValue) v).getValue().intValue());
    }

    /**
     * Works only on numeric types,
     * the result of subtraction is cast to an int
     * @param v Value to add
     * @return IntegerValue containing result
     * @throws UnsupportedOperationException not implemented
     */
    @Override
    public IntegerValue sub(Value v) throws UnsupportedOperationException{
        if(! (v instanceof NumericValue))
            throw new UnsupportedOperationException();

        return new IntegerValue(value - ((NumericValue) v).getValue().intValue());
    }

    /**
     * Works only on numeric types,
     * the result of multiplication is cast to an int
     * @param v Value to add
     * @return IntegerValue containing result
     * @throws UnsupportedOperationException not implemented
     */
    @Override
    public IntegerValue mul(Value v) throws UnsupportedOperationException{
        if(! (v instanceof NumericValue))
            throw new UnsupportedOperationException();

        return new IntegerValue(value * ((NumericValue) v).getValue().intValue());
    }

    /**
     * Works only on numeric types,
     * the result of division is cast to an int
     * @param v Value to add
     * @return IntegerValue containing result
     * @throws UnsupportedOperationException not implemented
     */
    @Override
    public IntegerValue div(Value v) throws UnsupportedOperationException{
        if(! (v instanceof NumericValue))
            throw new UnsupportedOperationException();

        if (((NumericValue) v).getValue().intValue() == 0)
            throw new ArithmeticException("divided by 0");

        return new IntegerValue(value / ((NumericValue) v).getValue().intValue());
    }

    /**
     * returns integer Value
     *
     * @param v is interpreted as double
     * @return this^v
     * @throws UnsupportedOperationException not implemented
     */
    @Override
    public IntegerValue pow(Value v) throws UnsupportedOperationException {
        if(! (v instanceof NumericValue))
            throw new UnsupportedOperationException();

        return new IntegerValue((int) Math.pow(value, ((NumericValue) v).getValue().doubleValue()));
    }

}
