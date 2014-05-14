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
package poke.demo;

import poke.client.ClientConnection;

public class Jab {
	private String tag;
	private int count;

	public Jab(String tag) {
		this.tag = tag;
	}
		// Code changed by illuminati starts
	public void check()
	{
		System.out.println("Hello");
	}

	public void run() {
		
		System.out.println("Loading the network with requests");
		String location = "San Diego";
		ClientConnection cc1 = ClientConnection
				.initConnection("localhost", 5570);
		for (int i = 0; i < 10; i++) {
			
			count++;
			cc1.addUser("user"+i, "password"+i, "abhi"+i, "abhi"+i+"@gmail.com", location)	;
			//cc1.DeleteUser("user1", location);
			//cc1.checkUser("user1", "password1", location);
			//cc1.upload(count,tag, location);
			} 	// Code changed by illuminati ends
		/*
		ClientConnection cc2 = ClientConnection
				.initConnection("localhost", 5571);
		for (int i = 0; i < 10; i++) {
			count++;
			cc2.poke(tag, count,location);
			}
		ClientConnection cc3 = ClientConnection
				.initConnection("localhost", 5572);
		for (int i = 0; i < 10; i++) {
			count++;
			cc3.poke(tag, count, location);
			}
		ClientConnection cc4 = ClientConnection
				.initConnection("localhost", 5573);
		for (int i = 0; i < 10; i++) {
			count++;
			cc4.poke(tag, count, location);
			}
			*/
		// Sending after some time
		
			// Code changed by illuminati starts
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 10; i < 20; i++) {
			count++;
			//cc1.upload(count,tag, location);
			cc1.checkUser("user1", "password1", location);
			}
		
		
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 1; i < 10; i++) {
			count++;
			cc1.findImage(i,tag, count,location);
			}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 1; i < 10; i++) {
			count++;
			cc1.DeleteImage(i,tag, count,location);
			}
	}
	
	public void SignUp(String username, String password, String name, String emailid )
	{	
		System.out.println("Serving Requests");
		
		ClientConnection cc1 = ClientConnection
				.initConnection("localhost", 5570);
		cc1.addUser(username, password, name, emailid, "San Jose");
	}
		// Code changed by illuminati ends
	public static void main(String[] args) {
		try {
			Jab jab = new Jab("jab");
			jab.run();

			Thread.sleep(5000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
