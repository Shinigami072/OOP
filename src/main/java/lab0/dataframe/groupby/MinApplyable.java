package lab0.dataframe.groupby;

import lab0.dataframe.DataFrame;
import lab0.dataframe.exceptions.DFApplyableException;
import lab0.dataframe.exceptions.DFColumnTypeException;
import lab0.dataframe.values.Value;

import java.util.HashSet;

public class MinApplyable implements Applyable {

    @Override
    public DataFrame apply(DataFrame df) throws DFApplyableException {
        try {

            DataFrame output = new DataFrame(df.getNames(), df.getTypes());

            HashSet<Integer> bannedColumns = new HashSet<>();

            int size = df.size();
            if (size > 0) {
                Value[] min = df.getRecord(0);

                for (int i = 1; i < size; i++) {
                    Value[] row = df.getRecord(i);

                    for (int kolumna = 0; kolumna < min.length; kolumna++) {

                        if (bannedColumns.contains(kolumna))
                            continue;

                        try {
                            if (row[kolumna].lte(min[kolumna]))
                                min[kolumna] = row[kolumna];
                        } catch (UnsupportedOperationException ignored) {
                            bannedColumns.add(kolumna);
                        }
                    }

                }
                output.addRecord(min);
            }

            if (bannedColumns.size() == output.getColCount())
                throw new RuntimeException("Really?1");

            String[] output_colnames = output.getNames();
            String[] colnames = new String[output.getColCount() - bannedColumns.size()];

            for (int i = 0, j = 0; i < output_colnames.length; i++) {
                if (!bannedColumns.contains(i))
                    colnames[j++] = output_colnames[i];
            }

            return output.get(colnames, false);

        } catch (DFColumnTypeException | CloneNotSupportedException e) {
            throw new DFApplyableException(e.getMessage());
        }
    }
}