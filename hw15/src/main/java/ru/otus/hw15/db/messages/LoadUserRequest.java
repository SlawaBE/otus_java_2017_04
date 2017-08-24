package ru.otus.hw15.db.messages;


import ru.otus.hw15.app.dataset.UserDataSet;
import ru.otus.hw15.app.DBService;
import ru.otus.hw15.messageSystem.Address;
import ru.otus.hw15.messageSystem.messages.Response;

public class LoadUserRequest extends RequestToDb {

    private long userId;

    public LoadUserRequest(Address from, Address to) {
        super(from, to);
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public Response exec(DBService dbService) {
            UserDataSet user = (UserDataSet) (dbService).load(userId);
            return new LoadUserResponse(getTo(), getFrom(), user);
    }
}
