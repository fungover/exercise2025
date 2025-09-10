package org.example;

public class App {
    public static void main(String[] args) {
        String dir = "north";
        int newY = 0;
        int newX = 0;

        switch (dir.toLowerCase()) {
            case "north":
                newY--;
                break;
            case "south":
                newY++;
                break;
            case "east":
                newX++;
                break;
            default:
                System.out.println("Invalid direction");
                break;
        }
        new App().move(Direction.WEST);
    }

    public int move(Direction direction){
        return switch(direction) {
            case NORTH -> 1;
            case SOUTH -> 2;
            case EAST -> 3;
            case WEST -> 4;
            case NORTHEAST -> 5;
        };
    }

    public void fight(EnemyType enemyType){
        String fightMessage = switch (enemyType) {
            case EnemyType.FlyingEnemy flyingEnemy-> "Use your bow and arrow";
            case EnemyType.WalkingEnemy walkingEnemy->"Use your sword";
        };
    }

    enum Direction {
        NORTH, SOUTH, EAST, WEST, NORTHEAST
    }

    sealed interface EnemyType permits EnemyType.FlyingEnemy, EnemyType.WalkingEnemy {
        record FlyingEnemy() implements EnemyType{}
        record WalkingEnemy() implements EnemyType{}
    }

    public record Person(String name, int age){
        public Person{
            if( name == null || name.isEmpty())
                throw new IllegalArgumentException();
            if( age < 0 || age > 130)
                throw new IllegalArgumentException();
        }
    }

    final class PersonService {
        //Don't allow instance creation
        private PersonService(){}
        public static boolean isAllowedToVote(Person person){
            return person.age >= 18;
        }
    }
}
