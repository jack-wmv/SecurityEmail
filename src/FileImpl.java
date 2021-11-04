import java.io.*;
import java.rmi.*;
import java.util.*;
import java.rmi.server.UnicastRemoteObject;

public class FileImpl extends UnicastRemoteObject
        implements FileInterface {

    private String name;
    int[] array = new int[10];
    int i = 0;
    int id;
    String file;
    HashMap<Integer, String> keys = new HashMap<Integer, String>();
    File userList = new File("Users.txt");
    Writer output;
    Writer output1;
    String newAcc = "Balance: 0\n\nTransaction History\n---------------------";
    BufferedReader read;
    String accountInfo;
    String newLine = System.getProperty("line.separator");

    public FileImpl(String s) throws RemoteException{
        super();
        name = s;
    }

    public byte[] downloadFile(String fileName, int clientID){
        try {
            array[i] = clientID;
            File file = new File(fileName);
            byte buffer[] = new byte[(int)file.length()];
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(fileName));
            input.read(buffer,0,buffer.length);
            System.out.println("The client's IP is: " + getClientHost());
            System.out.println("The requested file is: " + fileName);
            String history = myDownloadHistory(clientID, fileName);
            System.out.println("Client ID: " + clientID + " downloaded: " + history);
            System.out.println("ID: " + array[i]);
            i = i + 1;
            input.close();
            return(buffer);
        } catch(Exception e){
            System.out.println("FileImpl: "+e.getMessage());
            e.printStackTrace();
            return(null);
        }
    }

    public String myDownloadHistory(int clientID, String fileName) throws RemoteException{
        id = clientID;
        file = ", " + fileName;

        if(keys.get(id) == null) {
            keys.put(clientID, fileName);
        }
        else{
            keys.put(clientID, keys.get(clientID) + file);
        }

        return(keys.get(clientID));

    }

    public boolean checkUser(int y) throws IOException{
        String user = Integer.toString(y);

        boolean found = false;
        Scanner scanner = new Scanner(userList);

        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            if(line.equals(user)){
                found = true;
                break;
            }
        }

        if(!found){
            return false;
        }
        else{
            return true;

        }

    }

    public void createUser(int y) throws IOException {
        //Creates new user and adds to database if the user did not exist already
        String newUser = Integer.toString(y);
        output = new BufferedWriter(new FileWriter("Users.txt", true));
        ((BufferedWriter) output).newLine();
        output.write(newUser);
        output.close();

        //creates new file to store user information
        String fileName = newUser+".txt";
        File userFile = new File(fileName);
        userFile.createNewFile();
        output1 = new BufferedWriter(new FileWriter(fileName, true));
        output1.write(newAcc);
        output1.close();

        /*y is the users account name, will also need to get user choice here for what they want to do next
        int z = br.readInt();
        if(z==3) {
            showAccount(y);
        }
        else if(z==2){
            //withdraw
            int wd = br.readInt();
            withdraw(y, wd);
        }
        else if(z==1){
            //deposit
            int dp = br.readInt();
            deposit(y, dp);
        }*/
    }

    public void showAccount(int x) throws IOException {
        String newUser = Integer.toString(x);
        String fileName = newUser+".txt";

        //view account
        read = new BufferedReader(new FileReader(fileName));
        accountInfo = "";
        String line = read.readLine();
        while(line != null){
            accountInfo = accountInfo + (newLine + line);
            line = read.readLine();
        }
        read.close();
        System.out.println(accountInfo);
    }

    public void withdraw(int x, int wd) throws IOException {
        System.out.println("acount: " +x +"\nwithdraw amount: " + wd);
        //substring(9) to remove balance: from first line of file to get current balance
        String newUser = Integer.toString(x);
        String fileName = newUser+".txt";

        //view account
        read = new BufferedReader(new FileReader(fileName));
        accountInfo = "";
        String line = read.readLine();
        String firstLine = line;
        firstLine = firstLine.substring(9);
        int balance = Integer.parseInt(firstLine);
        int newBalance = balance - wd;
        line = "Balance: " + newBalance;
        while(line != null){
            accountInfo = accountInfo + (line + newLine);
            line = read.readLine();
        }
        PrintWriter pw = new PrintWriter(fileName);
        pw.close();
        output1 = new BufferedWriter(new FileWriter(fileName, true));
        output1.write(accountInfo);
        output1.write("\nWithdraw: " + wd);
        output1.close();
        read.close();
    }

    public void deposit(int x, int dp) throws IOException {
        System.out.println("acount: " + x + "\ndeposit amount: " + dp);

        String newUser = Integer.toString(x);
        String fileName = newUser+".txt";

        //view account
        read = new BufferedReader(new FileReader(fileName));
        accountInfo = "";
        String line = read.readLine();
        String firstLine = line;
        firstLine = firstLine.substring(9);
        int balance = Integer.parseInt(firstLine);
        int newBalance = balance + dp;
        line = "Balance: " + newBalance;
        while(line != null){
            accountInfo = accountInfo + (line + newLine);
            line = read.readLine();
        }
        PrintWriter pw = new PrintWriter(fileName);
        pw.close();
        output1 = new BufferedWriter(new FileWriter(fileName, true));
        output1.write(accountInfo);
        output1.write("\nDeposit: " + dp);
        output1.close();
        read.close();
    }

    //this should be used to transfer funds from 1 user to another. Need to authenticate that receiving user exists,
    //need to add the transaction to the account history as type transfer, need to subtract amount transferred from balance.
    public void eTransfer(int x, int sum, int recUser) throws IOException {

    }
}