package cache;

public interface SelectionStrategy {
    Integer getNextElementKey();
    Integer getFreeKey();
}
