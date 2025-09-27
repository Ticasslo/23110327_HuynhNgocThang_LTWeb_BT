package ngocthang.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "book_author")
@NamedQuery(name = "BookAuthor.findAll", query = "SELECT ba FROM BookAuthor ba")
public class BookAuthor implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BookAuthorId id;

	public BookAuthor() {
		super();
	}

	public BookAuthor(BookAuthorId id) {
		this.id = id;
	}

	public BookAuthorId getId() {
		return id;
	}

	public void setId(BookAuthorId id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "BookAuthor [bookid=" + (id != null ? id.getBookId() : null) + ", author_id="
				+ (id != null ? id.getAuthorId() : null) + "]";
	}

	@Embeddable
	public static class BookAuthorId implements Serializable {
		private static final long serialVersionUID = 1L;

		@Column(name = "bookid")
		private int bookId;

		@Column(name = "author_id")
		private int authorId;

		public BookAuthorId() {
		}

		public BookAuthorId(int bookId, int authorId) {
			this.bookId = bookId;
			this.authorId = authorId;
		}

		public int getBookId() {
			return bookId;
		}

		public void setBookId(int bookId) {
			this.bookId = bookId;
		}

		public int getAuthorId() {
			return authorId;
		}

		public void setAuthorId(int authorId) {
			this.authorId = authorId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + bookId;
			result = prime * result + authorId;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BookAuthorId other = (BookAuthorId) obj;
			if (bookId != other.bookId)
				return false;
			if (authorId != other.authorId)
				return false;
			return true;
		}
	}
}


