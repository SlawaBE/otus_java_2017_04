package ru.otus.hw15.messageSystem.messages;

import ru.otus.hw15.messageSystem.Address;

public class TextResponse extends Response<String> {

    public TextResponse(Address from, Address to, String text) {
        super(from, to, text);
    }

}
