package project.moviewebsite.models;

/**
 * Represents an enum Gender for an actor.
 */
public enum Gender {

    FEMALE, MALE, UNBINARY, UNKNOWN;

    /**
     * Return a string representation of the Gender
     *
     * @return a formatted string
     */
    public String toString() {
        switch (this) {
            case MALE: return "Male";
            case FEMALE: return "Female";
            case UNBINARY: return "Unbinary";
            default: return "Unknown";
        }
    }

}
