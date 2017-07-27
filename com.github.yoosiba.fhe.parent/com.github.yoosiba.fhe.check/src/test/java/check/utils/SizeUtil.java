package check.utils;

/** Utility that transforms long bytes to human readable form. */
public class SizeUtil {

    /** Transforms provided long up to exabyte. */
    public static String bytesToHuman(long value) {
        long abs = Math.abs(value);
        if (abs < 1024) {
            return value + " B";
        }
        int z = (63 - Long.numberOfLeadingZeros(abs)) / 10;
        return String.format("%.1f %sB", (double) value / (1L << (z * 10)), " KMGTPE".charAt(z));
    }

}
