package poke.server.imageStorage;

import java.util.List;
import java.util.Properties;

import eye.Comm.Image;

public interface Storage {


	void init(Properties cfg);

	void release();

	boolean addImage(int tag, byte[] img);

	boolean removeImage(int tag);

	boolean updateImage(int tag, byte[] img);
	
	//List<Document> findDocuments(String namespace, Document criteria);
}
