import java.util.Scanner;

public class BattleShip {
    public static int numRows = 7;
    public static int numCols = 7;
    public static int kapaluser;
    public static int kapalkomputer;
    public static String[][] grid = new String[numRows][numCols];
    public static int[][] missedGuesses = new int[numRows][numCols];
    private static BattleShip BattleShips;

    public static void main(String[] args){
        System.out.println("##### BERSIAPLAH UNTUK BERPERANG#####");
        System.out.println("############APAKAH KAMU SIAP?#############\n");

        Map();

        kordinat();

        lawan();

        do {
            Battle();
        }while(BattleShips.kapaluser != 0 && kapalkomputer != 0);

        gameOver();

        historypenembakan();
    }

    public static void Map(){
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();

        for(int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = " ";
                if (j == 0)
                    System.out.print(i + "=" + grid[i][j]);
                else if (j == grid[i].length - 1)
                    System.out.print(grid[i][j] + "=" + i);
                else
                    System.out.print(grid[i][j]);
            }
            System.out.println();
        }

        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();
    }

    public static void kordinat(){
        Scanner input = new Scanner(System.in);

        System.out.println("\nPERSIAPKAN KAPAL PERANG!");
        BattleShip.kapaluser = 5;
        for (int i = 1; i <= BattleShip.kapaluser; ) {
            System.out.print("INPUT POSISI X" + i + ": ");
            int x = input.nextInt();
            System.out.print("INPUT POSISI Y" + i + ": ");
            int y = input.nextInt();

            if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " "))
            {
                grid[x][y] =   "+";
                i++;
            }
            else if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && grid[x][y] == "+")
                System.out.println(" "+ x +"KORDINAT."+ y + " TELAH TERPAKAI ");
            else if((x < 0 || x >= numRows) || (y < 0 || y >= numCols))
                System.out.println("TITIK KORDINAT HANYA TERSISA 6");
        }
//        printOceanMap();
    }

    public static void lawan(){
        System.out.println("\nLawan Menyiapkan Kapal Perang");
        //Saat kapal komputer menurunkan kapalnya
        BattleShips.kapalkomputer = 5;
        for (int i = 1; i <= BattleShips.kapalkomputer; ) {
            int x = (int)(Math.random() * 7);
            int y = (int)(Math.random() * 7);

            if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " "))
            {
                grid[x][y] =   "x";
                System.out.println(i + ". KAPAL DITURUNKAN");
                i++;
            }
        }
//        printOceanMap();
    }

    public static void Battle(){
        playerTurn();

        computerTurn();

//        printOceanMap();

        System.out.println();
        System.out.println("KAPAL SAYA: " + BattleShips.kapaluser + " | KAPAL LAWAN: " + BattleShips.kapalkomputer);
        System.out.println();
    }


    public static void playerTurn(){
        historypenembakan();
        System.out.println("\nGILIRAN KAMU!");
        int x = -1, y = -1;
        do {
            Scanner input = new Scanner(System.in);
            System.out.print("INPUT POSISI X: ");
            x = input.nextInt();
            System.out.print("INPUT POSISI Y: ");
            y = input.nextInt();

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) //valid gues
            {


                if (grid[x][y] == "x") //if computer ship is already there; computer loses ship
                {
                    System.out.println("LAWAN TERKENA SERANGAN ANDA");
                    grid[x][y] = "x"; //Hit mark
                    --BattleShips.kapalkomputer;
                }
                else if (grid[x][y] == "+") {
                    System.out.println("KAMU MENGENAI KAPAL KAMU SENDIRI:(");
                    grid[x][y] = "!";
                    --BattleShips.kapaluser;
//                    ++BattleShips.computerShips;
                }

                else if (grid[x][y] == " ") {
                    System.out.println("SERANGAN MELESET");

                }

            }
            else if ((x < 0 || x >= numRows) || (y < 0 || y >= numCols))  //invalid guess
                System.out.println("KORDINAT TIDAK TERDETEKSI");


        }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols));  //keep re-prompting till valid guess
    }

    public static void historypenembakan(){
        System.out.println("");
    }

    public static void computerTurn(){
        System.out.println("\nGILIRAN LAWAN UNTUK MENEMBAK");
        //Guess co-ordinates
        int x = -1, y = -1;
        do {
            x = (int)(Math.random() * 7);
            y = (int)(Math.random() * 7);

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) //valid guess
            {
                if (grid[x][y] == "+") //if player ship is already there; player loses ship
                {
                    System.out.println("LAWAN MENGENAI ANDA");
                    grid[x][y] = "x";
                    --BattleShips.kapaluser;
                }
                else if (grid[x][y] == "x") {
                    System.out.println("LAWAN MENYERANG KAPALNYA SENDIRI");
                    grid[x][y] = "!";
                }
                else if (grid[x][y] == " ") {
                    System.out.println("KOMPUTER MELESET");
                    //Saving missed guesses for computer
                    if(missedGuesses[x][y] != 1)
                        missedGuesses[x][y] = 1;
                }
            }
        }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols));  //keep re-prompting till valid guess
    }

    public static void gameOver(){
        System.out.println("KAPAL SAYA: " + BattleShips.kapaluser + " | Kapal Lawan: " + BattleShips.kapalkomputer);
        if(BattleShips.kapaluser > 0 && BattleShips.kapalkomputer<= 0)
            System.out.println("SELAMAT KAMU MENANG(^_^)");
        else
            System.out.println("KAMU KALAH, COBA LAGI (-_-)");
        System.out.println();
    }

    public static void printOceanMap(){
        System.out.println();
        //First section of Ocean Map
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();

        //Middle section of Ocean Map
        for(int x = 0; x < grid.length; x++) {
            System.out.print(x + "|");

            for (int y = 0; y < grid[x].length; y++){
                System.out.print(grid[x][y]);
            }

            System.out.println("|" + x);
        }

        //Last section of Ocean Map
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();
    }

}

