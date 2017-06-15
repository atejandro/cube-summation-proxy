package com.atejandro.examples.proxy.web.client;

import com.atejandro.examples.api.*;
import com.atejandro.examples.proxy.config.CubeSummationConfig;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by atejandro on 12/06/17.
 */
@Component
public class CubeSummationClient {

    private static final Logger logger = Logger.getLogger(CubeSummationClient.class.getName());

    private final ManagedChannel channel;

    private final CubeSummationServiceGrpc.CubeSummationServiceBlockingStub cubeStub;

    private final UserServiceGrpc.UserServiceBlockingStub userStub;
    @Autowired
    public CubeSummationClient(CubeSummationConfig config) {
        this.channel = ManagedChannelBuilder
                .forAddress(config.getUrl(), config.getPort())
                .usePlaintext(true)
                .build();
        this.cubeStub = CubeSummationServiceGrpc.newBlockingStub(channel);
        this.userStub = UserServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public ResponseEntity<String> createCube(long userId, int size) {
        CubeResponse response =  cubeStub.createCube(
            CreateCubeRequest.newBuilder()
                    .setCubeSize(size)
                    .setUserId(userId)
                    .build());

        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
    }

    public ResponseEntity<String> listCube(long userId) {
         ListCubeResponse response = cubeStub.listCube(ListCubeRequest.newBuilder().setUserId(userId).build());
        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
    }

    public ResponseEntity<String> updateCube(int x, int y, int z, long value, long cubeId){
        UpdateCubeReponse response = cubeStub.updateCube(UpdateCubeRequest.newBuilder()
                .setCoord(Coordinate.newBuilder()
                            .setX(x).setY(y).setZ(z)
                            .build())
                .setValue(value)
                .setCubeId(cubeId)
                .build());

        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
    }

    public ResponseEntity<String> queryCube(int x0, int y0, int z0, int x, int y, int z, long cubeId){
        QueryCubeResponse response =
                cubeStub.queryCube(QueryCubeRequest.newBuilder()
                .setCubeId(cubeId)
                .setFrom(Coordinate.newBuilder()
                        .setX(x0).setY(y0).setZ(z0)
                        .build())
                .setTo(Coordinate.newBuilder()
                        .setX(x).setY(y).setZ(z)
                        .build())
                .build());

        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
    }

    public ResponseEntity<String> createUser(String name, String surename, String email){
        UserResponse response =
                userStub.createUser(CreateUserRequest.newBuilder()
                                .setUser(User.newBuilder()
                                    .setName(name)
                                    .setSurname(surename)
                                    .setEmail(email))
                            .build());

        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
    }

    public ResponseEntity<String> updateUser(Long userId, String name, String surename, String email){
        UserResponse response =
                userStub.updateUser(UpdateUserRequest.newBuilder()
                .setId(userId)
                .setUser(User.newBuilder()
                        .setName(name)
                        .setSurname(surename)
                        .setEmail(email))
                .build());

        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
    }


}
