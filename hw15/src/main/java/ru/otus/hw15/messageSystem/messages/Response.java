package ru.otus.hw15.messageSystem.messages;

import ru.otus.hw15.messageSystem.Address;

public abstract class Response<T> extends Message {

    private T result;

    public Response(Address from, Address to, T result) {
        super(from, to);
        this.result = result;
    }

    public T getResult() {
        return result;
    }
}
