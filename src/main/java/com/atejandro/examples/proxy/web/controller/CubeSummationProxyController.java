package com.atejandro.examples.proxy.web.controller;

import com.atejandro.examples.proxy.web.client.CubeSummationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by atejandro on 11/06/17.
 */
@RestController("/user")
public class CubeSummationProxyController {

    @Autowired
    CubeSummationClient cube;


    @RequestMapping("/")
    public ResponseEntity<String> index() {
        return new ResponseEntity<String>("Welcome to the cube summation service!", HttpStatus.OK);
    }

    @RequestMapping(value ="/user/", method = RequestMethod.POST)
    public ResponseEntity<String> createUser(@RequestParam String name, @RequestParam String surename,
                                             @RequestParam String email){
        return cube.createUser(name, surename, email);
    }

    @RequestMapping(value ="/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> listUsers(@PathVariable("id") long userId, @RequestParam String name,
                                            @RequestParam String surename, @RequestParam String email){
        return cube.updateUser(userId, name, surename, email);
    }

    @RequestMapping(value = "/user/{id}/cube/", method = RequestMethod.GET)
    public ResponseEntity<String> listCube(@PathVariable("id") long id){
        return cube.listCube(id);
    }

    @RequestMapping(value ="/user/{id}/cube/", method = RequestMethod.POST)
    public ResponseEntity<String> createCube(@PathVariable("id") long id, @RequestParam int size){
            return cube.createCube(id, size);
    }

    @RequestMapping(value = "/user/{userId}/cube/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> queryCube(@PathVariable("userId") long userId, @PathVariable("id") long cubeId,
                            @RequestParam int x0, @RequestParam int y0, @RequestParam int z0,
                            @RequestParam int x, @RequestParam int y, @RequestParam int z){
        return cube.queryCube(x0, y0, z0, x, y, z, cubeId);
    }

    @RequestMapping(value = "/user/{userId}/cube/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> updateCube(@PathVariable("userId") long userId, @PathVariable("id") long cubeId,
                                            @RequestParam int x, @RequestParam int y, @RequestParam int z,
                                             @RequestParam long value){
        return cube.updateCube(x, y, z, value, cubeId);
    }
}
