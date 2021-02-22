package uenderley.com.service;

import io.quarkus.redis.client.RedisClient;
import io.quarkus.redis.client.reactive.ReactiveRedisClient;
import io.smallrye.mutiny.Uni;

import io.vertx.mutiny.redis.client.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class KeyValueService {
    @Inject
    RedisClient redisClient; 

    @Inject
    ReactiveRedisClient reactiveRedisClient; 

    public Uni<Void> del(String key) {
        System.out.print(key);
        return reactiveRedisClient.del(Arrays.asList(key))
                .map(response -> null);
    }

    public String get(String key) {
        return redisClient.get(key).toString();
    }

    public void set(String key, Integer value) {
        redisClient.set(Arrays.asList(key, value.toString()));
    }

    public void increment(String key, Integer incrementBy) {
        redisClient.incrby(key, incrementBy.toString());
    }

    public Uni<List<String>> keys() {
        System.out.print("vish");
        return reactiveRedisClient
                .keys("*")
                .map(response -> {
                    System.out.print(response);

                    List<String> result = new ArrayList<>();
                    for (Response r : response) {
                        result.add(r.toString());
                    }
                    System.out.print(result);
                    return result;
                });
    }
}
