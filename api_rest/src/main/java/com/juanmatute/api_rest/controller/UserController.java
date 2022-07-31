package com.juanmatute.api_rest.controller;

import com.juanmatute.api_rest.entity.User;
import com.juanmatute.api_rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //Crear
    @PostMapping
    public ResponseEntity<?> create (@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    //Leer
    @GetMapping("/{id}")
    public ResponseEntity<?> read (@PathVariable(value = "id") Long userId){
        Optional<User> oUser = userService.findById(userId);

        if(!oUser.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oUser);
    }

    //actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> update (@RequestBody User userDetails, @PathVariable(value = "id") Long userId){
        Optional<User> user = userService.findById(userId);

        if (!user.isPresent()){
            return ResponseEntity.notFound().build();
        }

        user.get().setNombre(userDetails.getNombre());
        user.get().setContra(userDetails.getContra());
        user.get().setEmail(userDetails.getEmail());
        user.get().setEstado(userDetails.getEstado());

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user.get()));
    }

    //eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable(value = "id") Long userId){
        if (!userService.findById(userId).isPresent()){
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(userId);
        return ResponseEntity.ok().build();
    }

    //read all user
    @GetMapping
    public List<User> readAll(){
        List<User> users = StreamSupport
                .stream(userService.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return users;
    }
}
