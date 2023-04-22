package patrickkinney.c195pa;

import java.sql.SQLException;

/** Functional Interface for LAMBDA expression.
 * Specifies LAMBDA return value is INTEGER with three input INTEGER parameters.
 *
 */
public interface appTotalCount {
    int calcTotal (int custId, int month, int total) throws SQLException;

}
