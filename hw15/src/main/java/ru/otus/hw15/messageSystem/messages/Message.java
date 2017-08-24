package ru.otus.hw15.messageSystem.messages;


import ru.otus.hw15.messageSystem.Address;

public class Message {

    private final Address from;
    private final Address to;
    private long id;

    public Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
