package ru.otus.hw15.db.messages;

import ru.otus.hw15.app.dataset.UserDataSet;
import ru.otus.hw15.app.DBService;
import ru.otus.hw15.messageSystem.Address;
import ru.otus.hw15.messageSystem.messages.Response;
import ru.otus.hw15.messageSystem.messages.TextResponse;

public class CreateUserRequest extends RequestToDb {

    private UserDataSet user;

    public CreateUserRequest(Address from, Address to) {
        super(from, to);
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    @Override
    public Response exec(DBService dbService) {
        TextResponse response;
        try {
            dbService.save(user);
            response = new TextResponse(getTo(), getFrom(), "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            response = new TextResponse(getTo(), getFrom(), "FAIL" + e.getMessage());
        }
        return response;
    }
}
