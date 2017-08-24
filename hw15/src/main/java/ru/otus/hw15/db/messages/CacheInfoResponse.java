package ru.otus.hw15.db.messages;

import ru.otus.hw15.app.dto.CacheInfo;
import ru.otus.hw15.messageSystem.Address;
import ru.otus.hw15.messageSystem.messages.Response;

/**
 * Created by Stanislav on 24.08.2017
 */
public class CacheInfoResponse extends Response<CacheInfo> {
    public CacheInfoResponse(Address to, Address from, CacheInfo cacheInfo) {
        super(from, to, cacheInfo);
    }
}
