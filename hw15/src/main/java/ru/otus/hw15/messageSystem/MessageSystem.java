package ru.otus.hw15.messageSystem;

import ru.otus.hw15.messageSystem.messages.Request;
import ru.otus.hw15.messageSystem.messages.Response;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class MessageSystem {

    private final AtomicLong ID_GENERATOR = new AtomicLong(0);

    private final ConcurrentHashMap<Address, Addressee> adressees = new ConcurrentHashMap<>();
    private final ConcurrentLinkedQueue<Request> requests = new ConcurrentLinkedQueue<>();
    private final ConcurrentHashMap<Long, Response> responses = new ConcurrentHashMap<>();
    private final Object lock = new Object();

    private boolean running = true;

    public MessageSystem() {
        start();
        System.out.println("Started message system");
    }

    private void start() {
        new Thread(() -> {
            while(running) {
                try {
                    if (requests.isEmpty()) {
                        synchronized (lock) {
                            lock.wait();
                        }
                        continue;
                    }
                    Request request = requests.poll();
                    Response response = request.exec(adressees.get(request.getTo()));
                    response.setId(request.getId());
                    responses.put(response.getId(), response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public long sendMessage(Request request) {
        request.setId(ID_GENERATOR.getAndIncrement());
        requests.add(request);
        synchronized (lock) {
            lock.notify();
        }
        return request.getId();
    }

    public void addAdressee(Addressee addressee) {
        adressees.put(addressee.getAddress(), addressee);
        System.out.println("Add address: "+ addressee.getAddress());
    }

    public Response tryToGetResponse(long id) {
        return responses.remove(id);
    }

    public Response getResponse(long id) throws TimeoutException, ExecutionException, InterruptedException {
        FutureTask<Response> ut = new FutureTask<>(() -> {
            Response response;
            while ((response = tryToGetResponse(id)) == null) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;
        });
        Thread thr = new Thread(ut);
        thr.start();
        try {
            return ut.get(10, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            thr.stop();
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        running = false;
        synchronized (lock) {
            lock.notify();
        }
    }
}
