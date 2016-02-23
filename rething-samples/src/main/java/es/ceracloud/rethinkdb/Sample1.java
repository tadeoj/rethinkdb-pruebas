package es.ceracloud.rethinkdb;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

public class Sample1 {

	public static final RethinkDB r = RethinkDB.r;
	private Connection conn;

	public Sample1() {
		initConnection();
		createTable();
		insertDocuments();
		queryDocuments();
	}

	private void initConnection() {
		conn = r.connection().hostname("localhost").port(28015).connect();
	}

	private void createTable() {
		Object result = r.db("test").tableCreate("authors").run(conn);
		System.out.println(result);
	}

	private void insertDocuments() {
		Object result = r.table("authors")
				.insert(r.array(
						r.hashMap("name", "William Adama").with("tv_show", "Battlestar Galactica").with("posts",
								r.array(r.hashMap("title", "Decommissioning speech").with("content",
										"The Cylon War is long over..."),
						r.hashMap("title", "We are at war").with("content", "Moments ago, this ship received..."),
						r.hashMap("title", "The new Earth").with("content",
								"The discoveries of the past few days..."))),
				r.hashMap("name", "Laura Roslin").with("tv_show", "Battlestar Galactica").with("posts",
						r.array(r.hashMap("title", "The oath of office").with("content", "I, Laura Roslin, ..."),
								r.hashMap("title", "They look like us").with("content",
										"The Cylons have the ability..."))),
						r.hashMap("name", "Jean-Luc Picard").with("tv_show", "Star Trek TNG").with("posts",
								r.array(r.hashMap("title", "Civil rights").with("content",
										"There are some words I've known since...")))))
				.run(conn);
		System.out.println(result);
	}
	
	private void queryDocuments() {
		Cursor<?> cursor = r.table("authors").run(conn);
		for (Object doc : cursor) {
		    System.out.println(doc);
		}
	}

	public static void main(String[] args) {
		new Sample1();
	}

}
