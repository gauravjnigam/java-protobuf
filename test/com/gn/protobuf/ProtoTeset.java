package com.gn.protobuf;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.junit.Test;

public class ProtoTeset {
	
	@Test
	public void testProto() {
		String email = "j@baeldung.com";
		int id = new Random().nextInt();
		String name = "Michael Program";
		String number = "01234567890";
		AddressBookProtos.Person person = getPerson(email, id, name, number);
		
		AddressBookProtos.AddressBook addressBook = 
				AddressBookProtos.AddressBook.newBuilder()
				.addPeople(person).addPeople(person).build();
		
		assertEquals(addressBook.getPeopleCount(),2);
		 
		assertEquals(person.getEmail(), email);
		assertEquals(person.getId(), id);
		assertEquals(person.getName(), name);
		assertEquals(person.getNumbers(0), number);
	}

	private AddressBookProtos.Person getPerson(String email, int id, String name, String number) {
		AddressBookProtos.Person person =
		  AddressBookProtos.Person.newBuilder()
		    .setId(id)
		    .setName(name)
		    .setEmail(email)
		    .addNumbers(number)
		    .build();
		return person;
	}
	
	@Test
	public void serializeDeserializeTest() {
		Path path = Paths.get("D:\\Gaurav\\Learning\\java\\git\\java-protobuf\\src\\person.txt");
		
//		File f = new File("D:\\\\Gaurav\\\\Learning\\\\java\\\\git\\\\java-protobuf\\\\src\\\\person.txt");
		String email = "j@baeldung.com";
		int id = new Random().nextInt();
		String name = "Michael Program";
		String number = "01234567890";
		AddressBookProtos.Person person = getPerson(email, id, name, number);
	
		AddressBookProtos.AddressBook addressBook 
		  = AddressBookProtos.AddressBook.newBuilder().addPeople(person).build();
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(path.toFile());
		
			addressBook.writeTo(fos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileInputStream fis;
		AddressBookProtos.AddressBook addressBookDes = null ;
		
		try {
			fis = new FileInputStream(path.toFile());
			addressBookDes 
			  = AddressBookProtos.AddressBook.newBuilder().mergeFrom(fis).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(addressBook);
		
		System.out.println(addressBookDes);
		
		assertEquals(addressBook, addressBookDes);
	}

}
