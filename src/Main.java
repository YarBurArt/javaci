import java.io.*;

import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Scanner;
import java.util.Arrays;
import java.io.PrintStream;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.File;
//str in book 83.5

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {

        Scanner in = new Scanner(System.in);
        PrintStream out = new PrintStream(System.out, true, "UTF-8");

        out.println("Hello user!");

        //использование темной магии сокетов
        //sucketServer.serv();
        //sucketClient.cli();


        selfReplace.getPaths();
        //try {selfReplace.selfCopy();} catch (IOException e) {throw new RuntimeException(e);}

        /* хз зачем
        out.print("Enter names: ");
        String name = in.nextLine();
        String[] names = name.split(" ");

        for (int i = 0; i < names.length; i++) {
            out.println(i+1 + " " + names[i]);
        }

        out.print("Search name: ");
        String s_name = in.nextLine();
        search(names, s_name);

        int[] arr_tsort = {4,1,7,0,8};
        System.out.println("test sort: \n" + Arrays.toString(arr_tsort) + " to " + Arrays.toString(sort(arr_tsort)));
        */


    }
    //нужна оптимизация памяти и скорости
    static void search(String[] words, String s_word) throws UnsupportedEncodingException {
        PrintStream out = new PrintStream(System.out, true, "UTF-8");
        for (int i = 0; i < words.length; i++) {
            if (Objects.equals(s_word, words[i])) {
                out.println(i + 1 + " " + words[i]);
                break;
            }
        }
    }

    //переписать под строки
    static int[] sort(int[] tosort){
        for (int left = 0; left < tosort.length; left++) {
            int value = tosort[left];
            int i = left - 1;
            for (; i >= 0; i--) {
                if (value < tosort[i]) {
                    tosort[i + 1] = tosort[i];
                } else { break; }
            }
            tosort[i + 1] = value;
        }
        return tosort;
    }

}

//темная магия сокетов
class sucketServer {

    private static Socket clientSocket;
    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void serv() {
        try {
            try {
                server = new ServerSocket(8888);
                System.out.println("server has started on port 8888");
                clientSocket = server.accept();
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    String word = in.readLine();
                    System.out.println(word);

                    out.write("input data: " + word + "\n");
                    out.flush();

                } finally {
                    clientSocket.close();
                    in.close();
                    out.close();
                }
            } finally {
                System.out.println("server stop");
                server.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
class sucketClient {

    private static Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void cli() {
        try {
            try {
                clientSocket = new Socket("localhost", 8888);
                reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                System.out.print("Enter text: ");
                String word = reader.readLine();
                out.write(word + "\n");
                out.flush();
                String serverWord = in.readLine();
                System.out.println(serverWord);
            } finally {
                System.out.println("closed ...");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}

class selfReplace {

    //проверка доступа к каталогам для копирования
    public static File[] getPaths(){

        File[] paths;
        paths = File.listRoots();

        for(File path:paths)
            System.out.println("Found Drive Name: "+path);

        return paths;
    }

    public static void selfCopy() throws IOException {
        //создать папки темп, для винды они часто используются
        File[] rootsdir = getPaths();
        for(File rootdir:rootsdir) {

            String basepath = rootdir.getAbsolutePath();
            File dir = new File( basepath +"/temp");
            boolean iscreate = dir.mkdir();

            if(iscreate) System.out.println(rootdir+"good");
            else System.out.println(rootdir+" error");

            try {
                Files.copy(Path.of("Main.class"), Path.of(basepath + "/temp"));
            } catch (IOException e){
                System.out.println(e.getMessage());
            }
        }

    }

}

