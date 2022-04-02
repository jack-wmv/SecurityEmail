import java.io.*;
import java.rmi.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BankClient{
    public static void main(String argv[]) {

        String[] fileList;
        Scanner in = new Scanner(System.in);
        int user, option, sendID;
        String email, subject, body;
        long message0, message1;
        boolean checkUser;

        if(argv.length != 1) {
            System.out.println("Usage: java FileClient machineName ID");
            System.exit(0);
        }
        try {
            while(true) {
                String name = "//" + argv[0] + "/FileServer";
                FileInterface fi = (FileInterface) Naming.lookup(name);

                System.out.println("CTRL + C to exit at anytime.");
                System.out.print("Enter Username: ");
                user = in.nextInt();

                checkUser = fi.checkUser(user);

                if(checkUser){
                    System.out.print("\nEmail options: \n1. Send Email\n2. Check Inbox\nPlease enter the corresponding number to your option: ");
                    option = in.nextInt();

                    if(option == 1 || option == 2){
                        switch(option){
                            case 1:
                                //sending email
                                System.out.print("\nEnter the ID of the user you want to email: ");
                                sendID = in.nextInt();
                                System.out.print("\nEnter the Subject (2 word minimum): ");
                                Scanner in2 = new Scanner(System.in);
                                subject = in2.nextLine();
                                subject = subject.toLowerCase();
                                System.out.print("\nEnter the Body of the Email: ");
                                Scanner in3 = new Scanner(System.in);
                                body = in3.nextLine();
                                
                                String[] splited = subject.split("\\s+");
    
                                System.out.println(fi.OTP(splited, subject, body, sendID));
    
    
                              //  System.out.println(fi.OTP(message0, message1));
    
                               // fi.sendEmail(sendID, subject, body);
                                break;
                            case 2:
                                //checking email
                                System.out.print("\nCurrent Inbox: ");
                                System.out.println(fi.inbox(user));
    
                                System.out.print("\nEnter the email you wish to read: ");
                                Scanner in4 = new Scanner(System.in);
                                email = in4.nextLine();
                                System.out.println(fi.read(user, email));
                                break;
                        }
                    }

                    else{
                        System.out.println("Menu option not valid!");
                    }
                }
                else{
                    //show options
                    System.out.println("User could not be authenticated.");
                }

            }
        } catch(Exception e) {
            System.err.println("FileServer exception: "+ e.getMessage());
            e.printStackTrace();
        }
    }
}
