package org.example;

public class Names {

    static void main() {
        //Läs in flera namn tills användaren anger "Avsluta"
        String name = "";
        String[] names = new String[10];
        int nameCount = 0;

        while (!name.equals("Avsluta")) {
            System.out.println("Ange ett namn: ");
            name = System.console().readLine();
            if( !name.equals("Avsluta") && name.length() > 2)
                names[nameCount++] = name;
        }

        System.out.println("You entered the following names:");
        for(int i = 0; i < nameCount; i++) {
            System.out.println(names[i]);
        }


    }
}
