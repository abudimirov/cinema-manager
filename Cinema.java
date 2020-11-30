package cinema;
import java.util.Scanner;

public class Cinema {



    private static void printSeats(char[][] cinemaPlan) {
        System.out.println("Cinema:");
        for (int i = 0; i < cinemaPlan.length; i++) {
            for (int j = 0; j < cinemaPlan[i].length; j++) {
                System.out.printf("%c ", cinemaPlan[i][j]);
            }
            System.out.printf("\n");
        }
    }

    private static void showMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    private static char[][] makeNewCinema(int rows, int seatsPerRow) {
        char[][] cinemaPlan = new char[rows + 1][seatsPerRow + 1];

        cinemaPlan[0][0] = ' ';

        for (int i = 1; i < cinemaPlan[0].length; i++) {
            cinemaPlan[0][i] = Character.forDigit(i, 10);
        }
        for (int i = 1; i < cinemaPlan.length; i++) {
            cinemaPlan[i][0] = Character.forDigit(i, 10);
            for (int j = 1; j < cinemaPlan[i].length; j++) {
                cinemaPlan[i][j] = 'S';
            }
        }

        return cinemaPlan;
    }

    private static int countTotalIncome(int rows, int seatsPerRow) {
        if (rows * seatsPerRow < 60) {
            return rows * seatsPerRow * 10;
        } else {
            return rows / 2 * seatsPerRow * 10 + (rows - rows / 2) * seatsPerRow * 8;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean working = true;
        int currentIncome = 0;
        int seatsTaken = 0;


        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = scanner.nextInt();
        char[][] cinemaPlan = makeNewCinema(rows, seatsPerRow);
        int totalIncome = countTotalIncome(rows, seatsPerRow);
        int allSeats = seatsPerRow * rows;

        menu:  while (working) {
            showMenu();
            int action = scanner.nextInt();

            switch (action) {
                case 0:
                    working = false;
                    break menu;
                case 1:
                    printSeats(cinemaPlan);
                    break;
                case 2:
                    buySeat:            while (true) {
                        try {
                            System.out.println("Enter a row number:");
                            int seatX = scanner.nextInt();
                            System.out.println("Enter a seat number in that row:");
                            int seatY = scanner.nextInt();

                            if (seatX > seatsPerRow || seatY > rows) {
                                System.out.println("Wrong input!");
                                continue buySeat;
                            }
                            if (cinemaPlan[seatX][seatY] == 'B') {
                                System.out.println("That ticket has already been purchased!");
                                continue buySeat;
                            } else {
                                int price = 0;

                                if (rows * seatsPerRow <= 60) {
                                    price = 10;
                                } else if (seatX <= rows / 2) {
                                    price = 10;
                                } else {
                                    price = 8;
                                }

                                System.out.printf("Ticket price: $%d\n", price);
                                currentIncome += price;
                                seatsTaken++;

                                cinemaPlan[seatX][seatY] = 'B';
                                continue menu;
                            }
                        } catch (NumberFormatException e){
                            System.out.println("Wrong input!");
                            continue buySeat;
                        }
                    }
                case 3:
                    double percentage = ((double) seatsTaken / (seatsPerRow * rows)) * 100.0;
                    System.out.printf("Number of purchased tickets: %d\n", seatsTaken);
                    System.out.printf("Percentage: %.2f%%\n", percentage);
                    System.out.printf("Current income: $%d\n", currentIncome);
                    System.out.printf("Total income: $%d\n", totalIncome);
                    continue menu;
                default:
                    break menu;
            }
        }
    }
}