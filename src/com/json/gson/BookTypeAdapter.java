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

		in.beginObject();// beginObject()����{,���ϣ�����ѵ���һ��������󣬶�Ӧ��ʹ��beginArray()
		while (in.hasNext()) {
			String nextName = in.nextName();
			switch (nextName) {//�����ʹ��skipValue�������ֶ���һ�������У���Ϊ�Ѿ�in.nextName()�ˣ���û�ط�in.nextString()
			case "isbn":
				book.setIsbn(in.nextString());
				break;
			case "title":
				book.setTitle(in.nextString() + " get by adapter");
				break;
			case "authors":
				book.setAuthors(in.nextString().split(";"));
				break;
			default : in.skipValue();//���������json���ж����ֶ���������������򱨴�Expected a name but was STRING����Ϊ˳�������Ե�ʣ��ٵ�in.nextName()��ʱ��õ�����ֵ���챨��
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
