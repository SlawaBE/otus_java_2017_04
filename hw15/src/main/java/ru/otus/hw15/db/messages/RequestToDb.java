package ru.otus.hw15.db.messages;

import ru.otus.hw15.messageSystem.exception.MessageSystemException;
import ru.otus.hw15.app.DBService;
import ru.otus.hw15.messageSystem.Address;
import ru.otus.hw15.messageSystem.Addressee;
import ru.otus.hw15.messageSystem.messages.Request;
import ru.otus.hw15.messageSystem.messages.Response;

/**
 * Created by Stanislav on 24.08.2017
 */
public abstract class RequestToDb extends Request {

    public RequestToDb(Address from, Address to) {
        super(from, to);
    }

    @Override
    public Response exec(Addressee addressee) {
        if (addressee instanceof DBService) {
            return exec((DBService) addressee);
        }
        throw new MessageSystemException("Адресат не может обработать данное сообщение");
    }

    public abstract Response exec(DBService dbService);
}
