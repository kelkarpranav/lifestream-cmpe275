/**
 * 
 */
package poke.server.jpa;

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
@Table(name = "image")
@NamedQueries({
		@NamedQuery(name = "getImage", query = "SELECT e from ImageEntry e WHERE e.id = :id"),
		@NamedQuery(name = "removeImage", query = "DELETE from ImageEntry e WHERE e.id = :id") })

public class ImageEntry implements Serializable {
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	protected int id;

	@Column(nullable = false)
	
	protected byte[] data;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}


}
