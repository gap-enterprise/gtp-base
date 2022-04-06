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
 * Chapter.
 * <p>Used for management account.</p>
 *
 * @since 0.2
 */
public interface Chapter {

    /**
     * Get code.
     * <p>Must be unique but can be modified</p>
     * @return Code
     */
    String code();

    /**
     * Get name.
     * @return Name
     */
    String name();

    /**
     * Get some notes or description.
     * @return Notes
     */
    String notes();
    
    /**
     * Get the code and the name of a chapter.
     * @return Code - Name
     */
    String fullName();

    /**
     * Update.
     * @param code Code
     * @param name Name
     * @param notes Notes
     */
    void update(String code, String name, String notes);

    Chapter EMPTY = new Chapter() {

        @Override
        public String code() {
            return null;
        }

        @Override
        public String name() {
            return null;
        }

        @Override
        public String notes() {
            return null;
        }

        @Override
        public String fullName() {
            return null;
        }

        @Override
        public void update(String code, String name, String notes) {

        }
    };
}
