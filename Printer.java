import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Printer{
    
    static int TEMPO = 0; // SONG TEMPO GOES HERE
    static double MILLISECONDS_PER_BEAT = 60000.0 / TEMPO; 

    private final static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    private static Scanner lyrics;
    private static Scanner printLyrics;
    static double restLength = 0.0;

    public static void print() throws InterruptedException, FileNotFoundException{
        Sound sound = new Sound("audio.wav");
        sound.play();
        lyrics = new Scanner(new File("toPrint.jwc"));
        try {
            String tempoCheck = lyrics.nextLine();
            tempoCheck = tempoCheck.replace("$TEMPO=", "");
            System.out.println(tempoCheck);
            TEMPO = Integer.parseInt(tempoCheck);
        } catch (Exception e) {
            System.err.println("Error getting tempo, check your syntax. Expected: $TEMPO=value");
            System.exit(1);
        }
        MILLISECONDS_PER_BEAT = 60000.0 / TEMPO;
        printLyrics = new Scanner(new File("toPrint.jwc"));
        printLyrics.nextLine();
        System.out.println("\033[H\033[2J");
        processLine(lyrics.nextLine());
        lyrics.close();
    }

    public static void processLine(String line){
        int[] lineSpeed;

        String lineData = line.substring(line.indexOf("\\\\") + 3);
        String[] parsedData = lineData.split(", ");
        restLength += Double.parseDouble(parsedData[0]);
        String speeds = parsedData[1]; // example of speed formating: [75 95 200]
        speeds = speeds.replaceAll("\\[", "");
        speeds = speeds.replaceAll("\\]", "");
        String[] speedList = speeds.split(" ");
        lineSpeed = new int[speedList.length];
        for(int i = 0; i < speedList.length; i++)
        {
            lineSpeed[i] = Integer.parseInt(speedList[i]);
        }

        boolean clearScreen = false;
        if(parsedData.length > 2){
            if(parsedData[2].contains("clear")){
                clearScreen = true;
            }
        }
        scheduleLine(restLength, lineSpeed, clearScreen);
    }
    
    public static void scheduleLine(double restLength, int[] lineSpeed, boolean clearScreen) {
        long restTimeMillis = (long) (restLength * MILLISECONDS_PER_BEAT);
        executor.schedule(() -> {
            try {
                type(lineSpeed, clearScreen);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, restTimeMillis, TimeUnit.MILLISECONDS);

        if(lyrics.hasNextLine()){
            processLine(lyrics.nextLine());
        }
    }

    public static void shutdown() {
        executor.shutdown();
    }

    public static void type(int[] lineSpeed, boolean clearScreen) throws FileNotFoundException, InterruptedException{
        String unformatted = printLyrics.nextLine();
        unformatted = unformatted.substring(0, unformatted.indexOf("\\\\") - 1);
        String[] toPrint = unformatted.split("\\^\\^");
        for(int i = 0; i < toPrint.length; i++){
            for(int x = 0; x < toPrint[i].length(); x++){
                System.out.print(toPrint[i].charAt(x));
                Thread.sleep(lineSpeed[i]);
            }
        }
        System.out.println();
        if(clearScreen){
            System.out.print("\033[H\033[2J");
        }
        if(!printLyrics.hasNextLine()){
            System.out.print("\033[H\033[2J");
            System.exit(0);
        }
    }
}