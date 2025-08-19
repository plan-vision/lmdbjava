/*
 * Copyright © 2016-2025 The LmdbJava Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lmdbjava;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/** Indicates an enum that can provide integers for each of its values. */
public interface MaskedFlag {

  /**
   * Obtains the integer value for this enum which can be included in a mask.
   *
   * @return the integer value for combination into a mask
   */
  int getMask();

  /**
   * Indicates if the flag must be propagated to the underlying C code of LMDB or not.
   *
   * @return the boolean value indicating the propagation
   */
  default boolean isPropagatedToLmdb() {
    return true;
  }

  /**
   * Fetch the integer mask for all presented flags.
   *
   * @param <M> flag type
   * @param flags to mask (null or empty returns zero)
   * @return the integer mask for use in C
   */
  @SafeVarargs
  static <M extends MaskedFlag> int mask(final M... flags) {
    return mask(false, flags);
  }

  /**
   * Fetch the integer mask for all presented flags.
   *
   * @param <M> flag type
   * @param flags to mask (null or empty returns zero)
   * @return the integer mask for use in C
   */
  static <M extends MaskedFlag> int mask(final Stream<M> flags) {
    return mask(false, flags);
  }

  /**
   * Fetch the integer mask for the presented flags.
   *
   * @param <M> flag type
   * @param onlyPropagatedToLmdb if to include only the flags which are also propagate to the C code
   *     or all of them
   * @param flags to mask (null or empty returns zero)
   * @return the integer mask for use in C
   */
  @SafeVarargs
  static <M extends MaskedFlag> int mask(final boolean onlyPropagatedToLmdb, final M... flags) {
    return flags == null ? 0 : mask(onlyPropagatedToLmdb, Arrays.stream(flags));
  }

  /**
   * Fetch the integer mask for all presented flags.
   *
   * @param <M> flag type
   * @param onlyPropagatedToLmdb if to include only the flags which are also propagate to the C code
   *     or all of them
   * @param flags to mask
   * @return the integer mask for use in C
   */
  static <M extends MaskedFlag> int mask(
      final boolean onlyPropagatedToLmdb, final Stream<M> flags) {
    final Predicate<M> filter = onlyPropagatedToLmdb ? MaskedFlag::isPropagatedToLmdb : f -> true;

    return flags == null
        ? 0
        : flags
            .filter(Objects::nonNull)
            .filter(filter)
            .map(M::getMask)
            .reduce(0, (f1, f2) -> f1 | f2);
  }

  /**
   * Indicates whether the passed flag has the relevant masked flag high.
   *
   * @param flags to evaluate (usually produced by {@link #mask(org.lmdbjava.MaskedFlag...)}
   * @param test the flag being sought (required)
   * @return true if set.
   */
  static boolean isSet(final int flags, final MaskedFlag test) {
    requireNonNull(test);
    return (flags & test.getMask()) == test.getMask();
  }
}
