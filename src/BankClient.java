import java.io.*;
import java.rmi.*;
import java.util.Scanner;

public class BankClient{
    public static void main(String argv[]) {

        String[] fileList;
        Scanner in = new Scanner(System.in);
        int user, option, depositNum, withdraw, recUser;
        boolean checkUser;

        if(argv.length != 2) {
            System.out.println("Usage: java FileClient machineName ID");
            System.exit(0);
        }
        try {
            while(true) {
                int clientID = Integer.parseInt(argv[1]);
                String name = "//" + argv[0] + "/FileServer";
                FileInterface fi = (FileInterface) Naming.lookup(name);

                System.out.println("CTRL + C to exit at anytime.");
                System.out.print("Enter Username: ");
                user = in.nextInt();

                checkUser = fi.checkUser(user);

                if(checkUser){
                    //create user?
                    System.out.print("\nBanking options currently are: \\n1. Deposit\\n2. Withdraw\\n3. View account\\n4. Download Account History \\n5. Send Money to Another User. \\nPlease enter the corresponding number to your option: ");
                    option = in.nextInt();

                    switch(option){
                        case 1:
                            //deposit
                            System.out.print("\nHow much would you like to deposit? ");
                            depositNum = in.nextInt();
                            fi.deposit(user, depositNum);
                            break;
                        case 2:
                            //withdraw
                            System.out.print("\nHow much would you like to withdraw? ");
                            withdraw = in.nextInt();
                            fi.withdraw(user, withdraw);
                            break;
                        case 3:
                            //view account
                            break;
                        case 4:
                            //download account history file
                            break;
                        case 5:
                            //e-transfer
                            System.out.print("\nEnter user you would like to send money to: ");
                            recUser = in.nextInt();
                            System.out.print("\nHow much would you like to send? ");
                            withdraw = in.nextInt();
                            fi.eTransfer(user, withdraw, recUser);
                            break;
                    }
                }
                else{
                    //show options
                }

            /* The below is the code from lab 2 that was used to download a file
            byte[] filedata = fi.downloadFile(argv[0], clientID);
            File file = new File(argv[0]);
            BufferedOutputStream output = new
                    BufferedOutputStream(new FileOutputStream(file.getName()));
            output.write(filedata,0,filedata.length);
            System.out.println("Client Ready - remote stub active...");
            output.flush();
            output.close();
            File files = new File("C:\\Users\\j9lso\\Documents\\Distributed Systems\\Labs\\Lab Two\\FileApp\\Server\\files");
            fileList = files.list();
            for(String str : fileList){
                System.out.println(str);
            }
            System.out.println("File downloaded successfully.");*/

            }
        } catch(Exception e) {
            System.err.println("FileServer exception: "+ e.getMessage());
            e.printStackTrace();
        }
    }
}
