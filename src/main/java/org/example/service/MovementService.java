package org.example.service;

import org.example.entities.Position;
import org.example.entities.Character;

public class MovementService {

    public void move(Character character, Position newPosition) {
        character.setPosition(newPosition);
        System.out.println(character.getName() + " moves to " + newPosition);
    }
}
