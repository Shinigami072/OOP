package test;

import lab0.dataframe.values.DateTimeValue;
import lab0.dataframe.values.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testDeps.TESTValue;

import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTimeValueTest extends TESTValue {

    @BeforeEach
    void setUp() {
        int count = 10000;

        values = new Value[count];
        correct_values = new LocalDateTime[count];
        Random rand = new Random();

        for (int i = 0; i < count; i++) {
            correct_values[i] = LocalDateTime.parse(String.format("%04d-%02d-%02dT%02d:%02d:%02d",
                    rand.nextInt(2000)+1000,
                    rand.nextInt(12)+1,
                    rand.nextInt(20)+1,
                    rand.nextInt(24),
                    rand.nextInt(60),
                    rand.nextInt(60)
            ));
            if (i<count/2)
                values[i] = new DateTimeValue((LocalDateTime) correct_values[i]);
            else
                values[i]= Value.builder(DateTimeValue.class).build(correct_values[i].toString());
        }
    }


    @Test
    @Override
    public void  Test_lte(){
        for (int j = 0; j < values.length; j++)
            for (int i = 0; i < values.length; i++) {
                assertEquals(((LocalDateTime) correct_values[i]).isBefore((LocalDateTime) correct_values[j]), values[i].lte(values[j]));
            }
    }

    @Test
    @Override
    public void Test_gte(){
        for (int j = 0; j < values.length; j++)
            for (int i = 0; i < values.length; i++) {
                assertEquals(((LocalDateTime) correct_values[i]).isAfter((LocalDateTime) correct_values[j]), values[i].gte(values[j]));
            }
    }


}