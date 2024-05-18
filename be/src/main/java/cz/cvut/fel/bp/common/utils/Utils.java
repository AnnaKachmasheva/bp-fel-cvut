package cz.cvut.fel.bp.common.utils;

/**
 * @author annak
 * @since 2024-03-16
 */
public class Utils {

    public static void nonNull(Object o) {
       boolean isNull = (o == null);
       if (isNull) {
           throw new IllegalArgumentException("Parameter cannot be null.");
       }
   }

}
