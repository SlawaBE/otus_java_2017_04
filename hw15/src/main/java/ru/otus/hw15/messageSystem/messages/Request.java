package ru.otus.hw15.messageSystem.messages;

import ru.otus.hw15.messageSystem.Address;
import ru.otus.hw15.messageSystem.Addressee;

public abstract class Request extends Message {
    public abstract Response exec(Addressee addressee);

    public Request(Address from, Address to) {
        super(from, to);
    }

}
