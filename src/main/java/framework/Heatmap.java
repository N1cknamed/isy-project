package framework;

import java.util.concurrent.atomic.AtomicInteger;

public class Heatmap {

    private final AtomicInteger[] values;
    private final int width;
    private final int height;

    public Heatmap(int width, int height) {
        this.values = new AtomicInteger[width * height];
        for (int i = 0; i < values.length; i++) {
            values[i] = new AtomicInteger(0);
        }
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getValue(int x, int y) {
        return values[x + y * width].get();
    }

    public void increase(int x, int y) {
        values[x + y * width].incrementAndGet();
    }

    public void decrease(int x, int y) {
        values[x + y * width].decrementAndGet();
    }

    public void reset(int x, int y) {
        values[x + y * width].set(0);
    }

    public void normalize(int newMax) {
        int max = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int value = values[x + y * width].get();
                if (value > max) {
                    max = value;
                }
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double value = values[x + y * width].get();
                value = (value / max) * newMax;
                values[x + y * width].set((int) value);
            }
        }
    }

    @Override
    public synchronized String toString() {
        int charsPerValue = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int chars = String.valueOf(getValue(x, y)).length();
                if (chars > charsPerValue) {
                    charsPerValue = chars;
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                String value = String.valueOf(getValue(x, y));
                sb.append(value);
                for (int i = 0; i < charsPerValue - value.length(); i++) {
                    sb.append(' ');
                }

                sb.append(' ');
            }

            if (y != height - 1) {
                sb.append('\n');
            }
        }

        return sb.toString();
    }
}
