package poke.ejb.server;

import java.io.Serializable;
public interface Request extends Serializable {

	public boolean upload(int tag, byte[] im);
	
	public boolean getImage(int tag);
	
	public boolean deleteImage(int tag);
	
	public boolean addEntry(int id, byte[] img, int opn);
	
	
	public void SignIn(String username, String password);
	
	public void deleteUser(String username);
	
	public void SignUp(String username, String password, String name, String emailid );
	
}
