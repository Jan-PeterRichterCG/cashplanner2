
CREATE TABLE AccountingEntry(
  id BIGINT NOT NULL AUTO_INCREMENT,
  modificationCounter INTEGER NOT NULL,
  dateOfBookkeepingEntry DATE,
  ValueDate DATE,
  PostingText VARCHAR(255),
  Currency VARCHAR (3) NOT NULL,
  Amount NUMERIC (19,2) NOT NULL,
  CONSTRAINT PK_AccountEntry PRIMARY KEY(id)
);

