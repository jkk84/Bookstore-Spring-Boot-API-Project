CREATE TABLE book (
  isbn               VARCHAR PRIMARY KEY,
  title              VARCHAR(200) NOT NULL,
  description        VARCHAR(2000) NOT NULL,
  publication_date   YEAR,
  edition            INT,
  price              NUMERIC(6, 2),
  author             VARCHAR(100) NOT NULL
);

CREATE TABLE comment (
  id          BIGINT AUTO_INCREMENT PRIMARY KEY,
  book_isbn   BIGINT NOT NULL,
  content     VARCHAR(2000) NULL,
  created     timestamp
);

ALTER TABLE comment
    ADD CONSTRAINT comment_book_id
    FOREIGN KEY (book_isbn) REFERENCES book(isbn)