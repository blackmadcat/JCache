public class CounterSolution {

    private static Integer counter;
    private final int from;
    private final int to;

    public CounterSolution(int from, int to) {
        this.from = from;
        this.to = to;
        counter = from;
    }

    public Integer getAndUpdateCounter() {
        Integer result = counter;
        updateCounter();
        return result;
    }

    private void updateCounter() {
        counter++;
        if (counter >= this.to) {
            counter = this.from;
        }
    }


}
