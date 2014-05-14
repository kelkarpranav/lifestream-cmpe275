/*
 * copyright 2012, gash
 * 
 * Gash licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package poke.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poke.server.imageStorage.InMemoryStorage;
import poke.server.storage.Storage;
import poke.server.storage.jdbc.DatabaseStorage;
import poke.server.resources.Resource;
import poke.server.resources.ResourceUtil;
import eye.Comm.PayloadReply;
import eye.Comm.Request;
import eye.Comm.Response;
import eye.Comm.Header.ReplyStatus;

public class DocumentResource implements Resource {
	
	// code added by Team illuminati starts
	protected static Logger logger = LoggerFactory.getLogger("server");
	
	public DocumentResource()
	{}
	// code added by Team illuminati ends
	
	@Override
	public Response process(Request request) {
		// TODO Auto-generated method stub
			// Code changed by illuminati starts
		if(request.getHeader().getRoutingId().getNumber() == eye.Comm.Header.Routing.DOCADD.getNumber() )
		{	boolean status;
			logger.info("Addding Image for: " + request.getBody().getImage().getId());
			Storage storage = new DatabaseStorage();
		
			status = storage.addImage(request.getBody().getImage().getId(), request.getBody().getImage().getContent().toByteArray());
			return buildResponse(request, status);
		}
		else if(request.getHeader().getRoutingId().getNumber() == eye.Comm.Header.Routing.DOCREMOVE.getNumber())
		{	
			boolean status;
			logger.info("Deleting Image for: " + request.getBody().getImage().getId());
			Storage storage = new DatabaseStorage();
	
			status = storage.removeImage(request.getBody().getImage().getId());
			return buildResponse(request, status);
			
		}
		else if(request.getHeader().getRoutingId().getNumber() == eye.Comm.Header.Routing.DOCFIND.getNumber())
		{	
			boolean status;
			logger.info("Finding Image for: " + request.getBody().getImage().getId());
			Storage storage = new DatabaseStorage();
	
			status = storage.getImage(request.getBody().getImage().getId());
			return buildResponse(request, status);
		}
		if(request.getHeader().getRoutingId().getNumber() == eye.Comm.Header.Routing.USERADD.getNumber() )
		{	boolean status;
			logger.info("Addding User for: " + request.getBody().getUser().getUsername());
			Storage storage = new DatabaseStorage();
			
			String username = request.getBody().getUser().getUsername();
			String password = request.getBody().getUser().getPassword();
			String name = request.getBody().getUser().getName();
			String emailId = request.getBody().getUser().getEmailId();
			
			status = storage.addUser(username, password, name, emailId);
			return buildResponse(request, status);
		}
		else if(request.getHeader().getRoutingId().getNumber() == eye.Comm.Header.Routing.USERREMOVE.getNumber())
		{	
			boolean status;
			logger.info("Deleting Image for: " + request.getBody().getImage().getId());
			Storage storage = new DatabaseStorage();
	
			String username = request.getBody().getUser().getUsername();
			
			status = storage.deleteUser(username);
			return buildResponse(request, status);
			
		}
		else if(request.getHeader().getRoutingId().getNumber() == eye.Comm.Header.Routing.USERCHECK.getNumber())
		{	
			boolean status;
			logger.info("Finding Image for: " + request.getBody().getImage().getId());
			Storage storage = new DatabaseStorage();
	
			String username = request.getBody().getUser().getUsername();
			String password = request.getBody().getUser().getPassword();
			
			status = storage.checkUser(username, password);
			return buildResponse(request, status);
		}
	
		else  	// Code changed by illuminati ends
		return  null;
	}
		// Code changed by illuminati starts
	public Response buildResponse(Request request,boolean status)
	{
		Response.Builder r = Response.newBuilder();
		if(status)
        r.setHeader(ResourceUtil.buildHeaderFrom(request.getHeader(),ReplyStatus.SUCCESS, null));
		else
		r.setHeader(ResourceUtil.buildHeaderFrom(request.getHeader(),ReplyStatus.FAILURE, null));	
       
        PayloadReply.Builder p = PayloadReply.newBuilder();
			// Code changed by illuminati ends
        //p.setFinger(d.build());
        // added by Team illuminati starts
       
        // added by Team illuminati ends
        
        r.setBody(p.build());
       
       
        Response reply = r.build();
		
        return reply;
	}

}
