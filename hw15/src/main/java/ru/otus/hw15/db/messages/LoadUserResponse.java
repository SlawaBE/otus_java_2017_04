package ru.otus.hw15.db.messages;


import ru.otus.hw15.app.dataset.UserDataSet;
import ru.otus.hw15.messageSystem.Address;
import ru.otus.hw15.messageSystem.messages.Response;

public class LoadUserResponse extends Response<UserDataSet> {

    private UserDataSet user;

    public LoadUserResponse(Address from, Address to, UserDataSet user) {
        super(from, to, user);
    }

}
