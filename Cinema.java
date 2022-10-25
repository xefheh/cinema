package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rowsCount = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int colsCount = scanner.nextInt();
        Hall hall = new Hall(rowsCount, colsCount);
        Menu menu = new Menu();

        loop: while(true) {
            menu.print();
            switch(scanner.nextInt()) {
                case 1:
                    menu.showSeats(hall);
                    break;
                case 2:
                    menu.buySeat(hall, scanner);
                    break;
                case 3:
                    menu.showStatistic(hall);
                    break;
                case 0:
                    break loop;
                default:
                    System.out.println("Wrong input!");
            }
        }
    }
}

class Menu {

    public void print() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    public void showStatistic(Hall hall) {
        System.out.println("Number of purchased tickets: " + hall.getTicketsCount());
        System.out.println("Percentage: " +
                String.format("%.2f%%",
                        (double)hall.getTicketsCount() / hall.getAllSeats() * 100.0));
        System.out.println("Current income: $" + hall.getCurrentIncome());
        System.out.println("Total income: $" + hall.TOTAL_INCOME);
    }


    public void buySeat(Hall hall, Scanner scanner) {
        while(true) {
            System.out.println("Enter a row number:");
            int rowId = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int colId = scanner.nextInt();
            if(hall.buySeat(rowId, colId)) {
                break;
            }
        }
    }

    public void showSeats(Hall hall) {
        hall.print();
    }
}

class Hall {

    public final int TOTAL_INCOME;

    private final String[][] seats;

    private int currentIncome;

    private int ticketsCount;

    public int getAllSeats() { return getRows() * getCols(); }
    public int getRows() {
       return seats.length;
    }

    public int getCols() {
        return seats[0].length;
    }

    public int getCurrentIncome() { return currentIncome; }

    public int getTicketsCount() { return ticketsCount; }

    public Hall(int rowsCount, int colsCount) {
        seats = new String[rowsCount][colsCount];
        for(int i = 0; i < rowsCount; i++) {
            for(int j = 0; j < colsCount; j++) {
                seats[i][j] = "S";
            }
        }
        if(getAllSeats() < 60) {
            TOTAL_INCOME = getRows() * getCols() * 10;
        } else {
            TOTAL_INCOME = getRows() / 2 * getCols() * 10
                    + (getRows() - getRows() / 2) * getCols() * 8;
        }
        currentIncome = 0;
        ticketsCount = 0;
    }

    public boolean buySeat(int rowId, int colId) {
        if (rowId > getRows() || colId > getCols()) {
            System.out.println("Wrong input!");
            return false;
        } else if (seats[rowId - 1][colId - 1].equals("B")) {
            System.out.println("That ticket has already been purchased!");
            return false;
        }
        int income;
        if (getAllSeats()< 60) {
            income = 10;
        } else if (rowId > getRows() / 2) {
            income = 8;
        } else {
            income = 10;
        }
        currentIncome += income;
        seats[rowId - 1][colId - 1] = "B";
        ticketsCount++;
        System.out.println("Ticket price: $" + income);
        return true;
    }

    public void print() {
        System.out.println("Cinema:");
        int rowsCount = getRows();
        int colsCount = getCols();
        for(int i = 1; i <= colsCount; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        for(int i = 0; i < rowsCount; i++) {
            System.out.print(i + 1);
            for(int j = 0; j < colsCount; j++) {
                System.out.print(" " + seats[i][j]);
            }
            System.out.println();
        }
    }
}
