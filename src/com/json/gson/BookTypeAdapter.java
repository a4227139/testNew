package com.json.gson;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.json.model.Book;

public class BookTypeAdapter extends TypeAdapter<Book> {

	@Override
	public Book read(final JsonReader in) throws IOException {
		final Book book = new Book();

		in.beginObject();// beginObject()消费{,如果希望消费的是一个数组对象，对应的使用beginArray()
		while (in.hasNext()) {
			String nextName = in.nextName();
			switch (nextName) {//如果不使用skipValue，以下字段少一个都不行，因为已经in.nextName()了，但没地方in.nextString()
			case "isbn":
				book.setIsbn(in.nextString());
				break;
			case "title":
				book.setTitle(in.nextString() + " get by adapter");
				break;
			case "authors":
				book.setAuthors(in.nextString().split(";"));
				break;
			default : in.skipValue();//如果解析的json中有多于字段则必须跳过，否则报错Expected a name but was STRING，因为顺序解析的缘故，再到in.nextName()的时候得到的是值，遂报错
			}
		}
		in.endObject();
		return book;
	}

	@Override
	public void write(final JsonWriter out, final Book book) throws IOException {
		out.beginObject();
		out.name("isbn").value(book.getIsbn());
		out.name("title").value(book.getTitle());
		out.name("authors").value(StringUtils.join(book.getAuthors(), ";"));
		out.name("source").value(" by adapter");
		out.endObject();
	}
}
