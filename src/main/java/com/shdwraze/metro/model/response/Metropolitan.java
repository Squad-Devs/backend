package com.shdwraze.metro.model.response;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public record Metropolitan(String city, List<MetroLine> lines) implements Serializable {

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Metropolitan) obj;
        return Objects.equals(this.city, that.city) &&
                Objects.equals(this.lines, that.lines);
    }

    @Override
    public String toString() {
        return "Metropolitan[" +
                "city=" + city + ", " +
                "lines=" + lines + ']';
    }

}
