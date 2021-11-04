import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileInterface extends Remote {
    public byte[] downloadFile(String fileName, int clientID) throws RemoteException;
    public String myDownloadHistory(int clientID, String fileName) throws RemoteException;
    public void createUser(int y) throws IOException;
    public void showAccount(int x) throws IOException;
    public void withdraw(int x, int wd) throws IOException;
    public void deposit(int x, int dp) throws IOException;
    public void eTransfer(int x, int sum, int recUser) throws IOException;
    public boolean checkUser(int y) throws IOException;

}