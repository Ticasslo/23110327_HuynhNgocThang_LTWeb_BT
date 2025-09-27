package ngocthang.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "rating")
@NamedQuery(name = "Rating.findAll", query = "SELECT r FROM Rating r")
public class Rating implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RatingId id;

	@Column(name = "rating")
	private Byte rating;

	@Column(name = "review_text", columnDefinition = "TEXT")
	private String reviewText;

	public Rating() {
	}

	public Rating(RatingId id, Byte rating, String reviewText) {
		this.id = id;
		this.rating = rating;
		this.reviewText = reviewText;
	}

	public RatingId getId() {
		return id;
	}

	public void setId(RatingId id) {
		this.id = id;
	}

	public Byte getRating() {
		return rating;
	}

	public void setRating(Byte rating) {
		this.rating = rating;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	@Override
	public String toString() {
		return "Rating [userid=" + (id != null ? id.getUserId() : null) + ", bookid="
				+ (id != null ? id.getBookId() : null) + ", rating=" + rating + "]";
	}

	@Embeddable
	public static class RatingId implements Serializable {
		private static final long serialVersionUID = 1L;

		@Column(name = "userid")
		private int userId;

		@Column(name = "bookid")
		private int bookId;

		public RatingId() {
		}

		public RatingId(int userId, int bookId) {
			this.userId = userId;
			this.bookId = bookId;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public int getBookId() {
			return bookId;
		}

		public void setBookId(int bookId) {
			this.bookId = bookId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + userId;
			result = prime * result + bookId;
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
			RatingId other = (RatingId) obj;
			if (userId != other.userId)
				return false;
			if (bookId != other.bookId)
				return false;
			return true;
		}
	}
}


