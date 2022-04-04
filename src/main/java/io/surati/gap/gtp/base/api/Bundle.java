package io.surati.gap.gtp.base.api;

/**
 * Bundle.
 * @since 0.1
 */
public interface Bundle {

    /**
     * Get code.
     * <p>It's unique but it can be modified.</p>
     * @return Code
     */
    String code();

    /**
     * Get some notes or description.
     * @return Notes
     */
    String notes();

    /**
     * Update.
     * @param code Code
     * @param notes Notes
     */
    void update(String code, String notes);

    Bundle EMPTY = new Bundle() {

        @Override
        public String code() {
            return null;
        }

        @Override
        public String notes() {
            return null;
        }

        @Override
        public void update(String code, String notes) {

        }
    };
}
