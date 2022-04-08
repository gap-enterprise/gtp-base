/*
 * Copyright (c) 2022 Surati

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.surati.gap.gtp.base.api;

/**
 * All lines.
 *
 * @since 0.2
 */
public interface Lines {

    /**
     * Get a line.
     * @param code Code
     * @return Line
     */
    Line get(String code);

    /**
     * Iterate them all.
     * @return List of Lines
     */
    Iterable<Line> iterate();

    /**
     * Add a line.
     * @param code Code
     * @param name Name
     * @param notes Notes
     */
    void add(String code, String name, String notes);

    /**
     * Remove an line.
     * @param code Code
     */
    void remove(String code);

    /**
     * Checks if has line with code.
     * @param code Code
     * @return Has or not
     */
	boolean has(String code);

}
