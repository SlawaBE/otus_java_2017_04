package ru.otus.hw5.mytestframework;

/**
 * Created by Stanislav on 29.08.2017
 */
public class Statistics {

    private int success = 0;
    private int fail = 0;
    private int total = 0;

    public Statistics() {
    }

    public void incrementSuccess() {
        success++;
    }

    public void incrementFail() {
        fail++;
    }

    public void incrementTotal() {
        total++;
    }

    public int getSuccess() {
        return success;
    }

    public int getFail() {
        return fail;
    }

    public int getTotal() {
        return total;
    }
}
