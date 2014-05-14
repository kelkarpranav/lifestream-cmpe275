package poke.ejb.server;

import java.io.Serializable;
public interface LifeStream extends Serializable {

	public boolean upload(int tag, byte[] im);
	
	public boolean getImage(int tag);
	
	public boolean deleteImage(int tag);
	
	public boolean addEntry(int id, byte[] img, int opn);
	
	
	public boolean checkUser(String username, String password);
	
	public boolean deleteUser(String username);
	
	public boolean addUser(String username, String password, String name, String emailid );
}
