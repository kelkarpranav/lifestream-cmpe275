package poke.server.imageStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eye.Comm.Document;
import eye.Comm.Image;
import eye.Comm.NameSpace;

public class InMemoryStorage implements Storage {

	private static String sNoName = "";
	private HashMap<Integer, byte[]> data = new HashMap<Integer, byte[]>();
	protected static Logger logger = LoggerFactory.getLogger("server");
	
	@Override
	public boolean addImage(int tag, byte[] img) {
		if (img == null)
			return false;
		
		if(data.get((new Integer(tag))) == null)
		{
			data.put((new Integer(tag)),img);
			logger.info("Contect" +data.get(new Integer(tag)));
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean removeImage(int tag) {
		
		if((data.get((new Integer(tag)))) != null)
		{	logger.info("Inside");
			data.remove((new Integer(tag)));
			return true;
		}
		else
		{	logger.info("Outside");
			return false;
		}
	}

	@Override
	public boolean updateImage(int tag, byte[] img) {
		return addImage(tag,img);
	}

	/*
	@Override
	public List<Document> findDocuments(String namespace, Document criteria) {
		// TODO locating documents can be have several implementations that
		// allow for exact matching to not equal to gt to lt

		// return the namespace as queries are not implemented
		DataNameSpace list = data.get(namespace);
		if (list == null)
			return null;
		else
			return new ArrayList<Document>(list.data.values());
	}
   */
	
	@Override
	public void init(Properties cfg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

}
