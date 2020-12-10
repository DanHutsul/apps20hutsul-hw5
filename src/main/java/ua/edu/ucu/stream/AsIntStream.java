package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;

import java.util.ArrayList;

public class AsIntStream implements IntStream {
    private final ArrayList<Integer> itemList;

    private AsIntStream(int... values) {
        this.itemList = new ArrayList<>();
        for (int integer : values) {
            this.itemList.add(integer);
        }
    }

    public static IntStream of(int... values) {
        return new AsIntStream(values);
    }

    @Override
    public Double average() {
        if (this.count() == 0) {
            throw new IllegalArgumentException("Empty Stream");
        }
        int size = (int) this.count();
        int sum = 0;
        for (int value : this.itemList) {
            sum += value;
        }
        return (double) sum/size;
    }

    @Override
    public Integer max() {
        if (this.count() == 0) {
            throw new IllegalArgumentException("Empty Stream");
        }

        int maxValue = 0;
        for (int value : this.itemList) {
            if (value > maxValue) {
                maxValue = value;
            }
        }

        return maxValue;
    }

    @Override
    public Integer min() {
        if (this.count() == 0) {
            throw new IllegalArgumentException("Empty Stream");
        }

        int minValue = Integer.MAX_VALUE;
        for (int value : this.itemList) {
            if (value < minValue) {
                minValue = value;
            }
        }

        return minValue;
    }

    @Override
    public long count() {
        return this.itemList.size();
    }

    @Override
    public Integer sum() {
        if (this.count() == 0) {
            throw new IllegalArgumentException("Empty Stream");
        }
        int sum = 0;
        for (int value : this.itemList) {
            sum += value;
        }
        return sum;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        ArrayList<Integer> filteredList = new ArrayList<>();
        for (Integer value : this.itemList) {
            if (predicate.test(value)) {
                filteredList.add(value);
            }
        }
        int[] outputArray = filteredList.stream().mapToInt(i -> i).toArray();

        return of(outputArray);
    }

    @Override
    public void forEach(IntConsumer action) {
        for (Integer value : this.itemList) {
            action.accept(value);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        int[] mappedArray = new int[itemList.size()];
        for (int i = 0; i < itemList.size(); i++) {
            mappedArray[i] = mapper.apply(itemList.get(i));
        }
        return of(mappedArray);
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        ArrayList<Integer> flatMappedList = new ArrayList<>();
        for (Integer integer : itemList) {
            for (int num : func.applyAsIntStream(integer).toArray()) {
                flatMappedList.add(num);
            }
        }
        int[] outputArray = flatMappedList.stream().mapToInt(i -> i).toArray();

        return of(outputArray);
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int reduced = identity;
        for (int value: itemList) {
            reduced = op.apply(reduced, value);
        }
        return reduced;
    }

    @Override
    public int[] toArray() {
        int[] outputArray = new int[itemList.size()];
        for (int i = 0; i < itemList.size(); i++) {
            outputArray[i] = itemList.get(i);
        }
        return outputArray;
    }

}
