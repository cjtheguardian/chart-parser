package com.robinhowlett.chartparser.tracks;

/**
 * Stores a track's code, canonical code (for continuity), country, and name
 */
public class Track {

    private String code;
    // the original code if a track has changed code or uses multiple codes for sponsorship reasons
    // e.g. PHA for PRX, HOL for BHP and OTH, SA for OSA etc.
    private String canonical;
    private String country;
    private String name;

    public String getCode() {
        return code.trim();
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCanonical() {
        return (canonical != null || !canonical.trim().isEmpty() ? canonical.trim() : getCode());
    }

    public void setCanonical(String canonical) {
        this.canonical = canonical;
    }

    public String getCountry() {
        return country.trim();
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Track{" +
                "code='" + code + '\'' +
                ", canonical='" + canonical + '\'' +
                ", country='" + country + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (code != null ? !code.equals(track.code) : track.code != null) return false;
        if (canonical != null ? !canonical.equals(track.canonical) : track.canonical != null)
            return false;
        if (country != null ? !country.equals(track.country) : track.country != null) return false;
        return name != null ? name.equals(track.name) : track.name == null;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (canonical != null ? canonical.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
