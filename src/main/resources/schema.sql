CREATE TABLE USERS (
    USER_ID BIGINT NOT NULL AUTO_INCREMENT,
    DISPLAY_NAME VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(50) NOT NULL,
	GOOGLE_ID VARCHAR(255),
	PRIMARY KEY (USER_ID) 
);
CREATE SEQUENCE "USERS_SEQ" 
	MINVALUE 3 MAXVALUE 999999999 INCREMENT BY 1 
	START WITH 2000 NOCACHE NOCYCLE;

CREATE TABLE LISTS (
    LIST_ID BIGINT NOT NULL AUTO_INCREMENT,
	USER_ID BIGINT NOT NULL,
    LIST_NAME VARCHAR(255) NOT NULL,
    IS_COMPLETE BOOLEAN DEFAULT FALSE,
	PRIMARY KEY (LIST_ID) ,
    FOREIGN KEY (USER_ID) REFERENCES USERS(USER_ID)
);
CREATE SEQUENCE "LISTS_SEQ" 
	MINVALUE 3 MAXVALUE 999999999 INCREMENT BY 1 
	START WITH 2000 NOCACHE NOCYCLE;

CREATE TABLE ITEMS (
    ITEM_ID BIGINT NOT NULL AUTO_INCREMENT,
	LIST_ID BIGINT NOT NULL,
	ITEM_ORDER INT NOT NULL,
    ITEM_DESCRIPTION text(5000) NOT NULL,
	DUE_DATE DATE,
    IS_COMPLETE BOOLEAN DEFAULT FALSE,
	PRIMARY KEY (ITEM_ID),
    FOREIGN KEY (LIST_ID) REFERENCES LISTS(LIST_ID)
); 

CREATE SEQUENCE "ITEMS_SEQ" 
	MINVALUE 3 MAXVALUE 999999999 INCREMENT BY 1 
	START WITH 2000 NOCACHE NOCYCLE;
