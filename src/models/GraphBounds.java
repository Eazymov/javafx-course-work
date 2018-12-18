package models;

public class GraphBounds {
    private final Integer minX;
    private final Integer maxX;
    private final Integer minY;
    private final Integer maxY;

    public GraphBounds(Integer minX, Integer maxX, Integer minY, Integer maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public Integer getMaxX() {
        return this.maxX;
    }

    public Integer getMinX() {
        return this.minX;
    }

    public Integer getMaxY() {
        return this.maxY;
    }

    public Integer getMinY() {
        return this.minY;
    }
}
