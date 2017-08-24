package ru.otus.hw15.db.messages;

import ru.otus.hw15.app.DBService;
import ru.otus.hw15.messageSystem.Address;
import ru.otus.hw15.messageSystem.messages.Response;

/**
 * Created by Stanislav on 24.08.2017
 */
public class CacheInfoRequest extends RequestToDb {


    public CacheInfoRequest(Address from, Address to) {
        super(from, to);
    }

    @Override
    public Response exec(DBService dbService) {
        return new CacheInfoResponse(getTo(), getFrom(), dbService.getCacheInfo());
    }
}
