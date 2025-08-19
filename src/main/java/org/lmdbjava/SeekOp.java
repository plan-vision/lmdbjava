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

/**
 * Flags for use when performing a {@link Cursor#seek(org.lmdbjava.SeekOp)}.
 *
 * <p>Unlike most other LMDB enums, this enum is not bit masked.
 */
public enum SeekOp {

  /** Position at first key/data item. */
  MDB_FIRST(0),
  /** Position at first data item of current key. Only for {@link DbiFlags#MDB_DUPSORT}. */
  MDB_FIRST_DUP(1),
  /** Position at key/data pair. Only for {@link DbiFlags#MDB_DUPSORT}. */
  MDB_GET_BOTH(2),
  /** position at key, nearest data. Only for {@link DbiFlags#MDB_DUPSORT}. */
  MDB_GET_BOTH_RANGE(3),
  /** Return key/data at current cursor position. */
  MDB_GET_CURRENT(4),
  /**
   * Return key and up to a page of duplicate data items from current cursor position. Move cursor
   * to prepare for {@link #MDB_NEXT_MULTIPLE}. Only for {@link DbiFlags#MDB_DUPSORT}.
   */
  MDB_GET_MULTIPLE(5),
  /** Position at last key/data item. */
  MDB_LAST(6),
  /** Position at last data item of current key. Only for {@link DbiFlags#MDB_DUPSORT}. */
  MDB_LAST_DUP(7),
  /** Position at next data item. */
  MDB_NEXT(8),
  /** Position at next data item of current key. Only for {@link DbiFlags#MDB_DUPSORT}. */
  MDB_NEXT_DUP(9),
  /**
   * Return key and up to a page of duplicate data items from next cursor position. Move cursor to
   * prepare for {@link #MDB_NEXT_MULTIPLE}. Only for {@link DbiFlags#MDB_DUPSORT}.
   */
  MDB_NEXT_MULTIPLE(10),
  /** Position at first data item of next key. */
  MDB_NEXT_NODUP(11),
  /** Position at previous data item. */
  MDB_PREV(12),
  /** Position at previous data item of current key. {@link DbiFlags#MDB_DUPSORT}. */
  MDB_PREV_DUP(13),
  /** Position at last data item of previous key. */
  MDB_PREV_NODUP(14);

  private final int code;

  SeekOp(final int code) {
    this.code = code;
  }

  /**
   * Obtain the integer code for use by LMDB C API.
   *
   * @return the code
   */
  public int getCode() {
    return code;
  }
}
