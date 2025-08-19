


void main() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Längd i meter: ");
    double length = Double.parseDouble(scanner.nextLine());

    System.out.println("Vikt i kilogram: ");
    double weight = Double.parseDouble(scanner.nextLine());

    double bmi = weight / (length * length);

    System.out.println("BMI: " + bmi);

    //Någon som vill köra resten

    if(bmi < 18.5) {
        System.out.println("Undervikt");
    } else if ( bmi < 25) {
        System.out.println("Normalvikt");
    } else {
        System.out.println("Övervikt");
    }
}






/*
Uppgift 4: BMI-kalkylator
Användaren matar in längd i meter och vikt i kilogram
Programmet beräknar BMI: vikt / (längd * längd)
Skriv ut BMI och ge en tolkning:

BMI < 18.5 → Undervikt
"It`s Britney Bitch"

BMI 18.5–24.9 → Normalvikt

BMI ≥ 25 → Övervikt*/
