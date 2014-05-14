package poke.server.jpa;

/**
 * 
 */

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * A log entry. See Log for notes.
 * 
 * @author gash
 */
@Entity
@Table(name = "userTable")
@NamedQueries({
		@NamedQuery(name = "checkUser", query = "SELECT e from UserEntry e WHERE (e.username = :username and e.password = :password)"),
		@NamedQuery(name = "removeUser", query = "DELETE from UserEntry e WHERE e.username = :username") })

public class UserEntry implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected int id;

	@Column(nullable = false)
	protected char[] username;
	
	@Column(nullable = false)
	protected char[] password;
	
	@Column(nullable = false)
	protected char[] name;
	
	@Column(nullable = false)
	protected char[] emailid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public char[] getUsername() {
		return username;
	}

	public void setUsername(char[] username) {
		this.username = username;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public char[] getName() {
		return name;
	}

	public void setName(char[] name) {
		this.name = name;
	}

	public char[] getEmailid() {
		return emailid;
	}

	public void setEmailid(char[] emailid) {
		this.emailid = emailid;
	}


}
