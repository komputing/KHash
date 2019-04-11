package org.komputing.khash.keccak.extensions

/**
 * Parses the string argument as a signed integer in the radix
 * specified by the second argument. The characters in the string
 * must all be digits of the specified radix (as determined by
 * whether {@link java.lang.Character#digit(char, int)} returns a
 * nonnegative value), except that the first character may be an
 * ASCII minus sign {@code '-'} ({@code '\u005Cu002D'}) to
 * indicate a negative value or an ASCII plus sign {@code '+'}
 * ({@code '\u005Cu002B'}) to indicate a positive value. The
 * resulting integer value is returned.
 *
 * <p>An exception of type {@code NumberFormatException} is
 * thrown if any of the following situations occurs:
 * <ul>
 * <li>The first argument is {@code null} or is a string of
 * length zero.
 *
 * <li>The radix is either smaller than
 * {@link java.lang.Character#MIN_RADIX} or
 * larger than {@link java.lang.Character#MAX_RADIX}.
 *
 * <li>Any character of the string is not a digit of the specified
 * radix, except that the first character may be a minus sign
 * {@code '-'} ({@code '\u005Cu002D'}) or plus sign
 * {@code '+'} ({@code '\u005Cu002B'}) provided that the
 * string is longer than length 1.
 *
 * <li>The value represented by the string is not a value of type
 * {@code int}.
 * </ul>
 *
 * <p>Examples:
 * <blockquote><pre>
 * "0".parseInt(10) returns 0
 * "473".parseInt(10) returns 473
 * "+42".parseInt(10) returns 42
 * "-0".parseInt(10) returns 0
 * "-FF".parseInt(16) returns -255
 * "1100110".parseInt(2) returns 102
 * "2147483647".parseInt(10) returns 2147483647
 * "-2147483648".parseInt(10) returns -2147483648
 * "2147483648".parseInt(10) throws a NumberFormatException
 * "99".parseInt(8) throws a NumberFormatException
 * "Kona".parseInt(10) throws a NumberFormatException
 * "Kona".parseInt(27) returns 411787
 * </pre></blockquote>
 *
 * @param radix the radix to be used while parsing {@code s}.
 * @return the integer represented by the string argument in the
 *             specified radix.
 * @exception NumberFormatException if the {@code String}
 *             does not contain a parsable {@code int}.
 */
internal expect fun String.parseInt(radix: Int): Int
